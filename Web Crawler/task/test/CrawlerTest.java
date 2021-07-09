import crawler.WebCrawler;
import org.assertj.swing.fixture.*;
import org.hyperskill.hstest.v6.mocks.web.WebPage;
import org.hyperskill.hstest.v6.mocks.web.WebServerMock;
import org.hyperskill.hstest.v6.stage.SwingTest;
import org.hyperskill.hstest.v6.testcase.CheckResult;
import org.hyperskill.hstest.v6.testcase.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class CrawlerTest extends SwingTest<CrawlerTest.Attach> {
    private static final int port = 25555;
    private static WebServerMock webServerMock;
    private JTextComponentFixture urlTextField;
    private JTextComponentFixture exportUrlTextField;
    private JTextComponentFixture depthTextField;
    private JCheckBoxFixture depthCheckBox;
    private JToggleButtonFixture runButton;
    private JButtonFixture exportButton;
    private JLabelFixture parsedLabel;
    private static PageContent pageContent;
    private Map<String, String> mapOfTitles;
    private static List<String> parsedPages;
    
    private final String EXPORT_DIRECTORY = Paths.get("").toAbsolutePath().toString() + "/temp.txt";
    
    public CrawlerTest() {
        super(new WebCrawler());
    }
    
    @BeforeClass
    public static void initWebServer(){
        System.out.println("Initializing server");
        pageContent = new PageContent();
        parsedPages = new ArrayList<>();
        
        WebPage exampleDotComPage = new WebPage();
        exampleDotComPage.setContent(pageContent.getContentWithLink("http://localhost:25555/exampleDotCom"));
        exampleDotComPage.setContentType("text/html");
        
        WebPage circular1Page = new WebPage();
        circular1Page.setContent( pageContent.getContentWithLink("http://localhost:25555/circular1"));
        circular1Page.setContentType("text/html");
        
        WebPage circular2Page = new WebPage();
        circular2Page.setContent(pageContent.getContentWithLink("http://localhost:25555/circular2"));
        circular2Page.setContentType("text/html");
        
        WebPage circular3Page = new WebPage();
        circular3Page.setContent(pageContent.getContentWithLink("http://localhost:25555/circular3"));
        circular3Page.setContentType("text/html");
        
        WebPage unavailablePage = new WebPage();
        unavailablePage.setContent("Web Page not found");
        
        webServerMock = new WebServerMock(port);
        webServerMock.setPage("/exampleDotCom", exampleDotComPage);
        webServerMock.setPage("/circular1", circular1Page);
        webServerMock.setPage("/circular2", circular2Page);
        webServerMock.setPage("/circular3", circular3Page);
        webServerMock.setPage("/unavailablePage", unavailablePage);
        
        Thread thread = new Thread(() -> {
            webServerMock.start();
            webServerMock.run();
        });
        
        thread.start();
        
    }
    
    //Deletes the file created after each test 
    @After
    public void deleteFile(){
        File file = new File(EXPORT_DIRECTORY);
        if (file.exists()){
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }
    
    @Override
    public List<TestCase<Attach>> generate() {
        return List.of(
                new TestCase<Attach>().setAttach(new Attach(
                        "Window title is empty.",
                        () -> frame.getTitle().length() > 0
                )),
                
                new TestCase<Attach>().setAttach(new Attach(
                        "Window is not visible.",
                        () -> frame.isVisible()
                )),
                
                new TestCase<Attach>().setAttach(new Attach(
                        "No component found with the name \"UrlTextField\"",
                        () -> checkExistence(() -> {
                            urlTextField = window.textBox("UrlTextField");
                            return urlTextField;
                        })
                )),
        
                new TestCase<Attach>().setAttach(new Attach(
                        "No component found with the name \"RunButton\"",
                        () -> checkExistence(() -> {
                            runButton = window.toggleButton("RunButton");
                            return runButton;
                        })
                )),
        
                new TestCase<Attach>().setAttach(new Attach(
                        "No component found with the name \"ExportUrlTextField\"",
                        () -> checkExistence(() -> {
                            exportUrlTextField = window.textBox("ExportUrlTextField");
                            return exportUrlTextField;
                        })
                )),
        
                new TestCase<Attach>().setAttach(new Attach(
                        "No component found with the name \"ExportButton\"",
                        () -> checkExistence(() -> {
                            exportButton = window.button("ExportButton");
                            return exportButton;
                        })
                )),
                
                new TestCase<Attach>().setAttach(new Attach(
                        "No component found with the name \"DepthTextField\"",
                        () -> checkExistence(() -> {
                            depthTextField = window.textBox("DepthTextField");
                            return depthTextField;
                        })
                )),
        
                new TestCase<Attach>().setAttach(new Attach(
                        "No component found with the name \"DepthCheckBox\"",
                        () -> checkExistence(() -> {
                            depthCheckBox = window.checkBox("DepthCheckBox");
                            return depthCheckBox;
                        })
                )),
        
                new TestCase<Attach>().setAttach(new Attach(
                        "No component found with the name \"ParsedLabel\"",
                        () -> checkExistence(() -> {
                            parsedLabel = window.label("ParsedLabel");
                            return parsedLabel;
                        })
                )),
                
                new TestCase<Attach>().setAttach(new Attach(
                        "UrlTextField should be enabled.",
                        () -> urlTextField.isEnabled()
                )),
        
                new TestCase<Attach>().setAttach(new Attach(
                        "RunButton should be enabled.",
                        () -> runButton.isEnabled()
                )),
        
                new TestCase<Attach>().setAttach(new Attach(
                        "ExportUrlTextField should be enabled.",
                        () -> exportUrlTextField.isEnabled()
                )),
        
                new TestCase<Attach>().setAttach(new Attach(
                        "ExportButton should be enabled.",
                        () -> exportButton.isEnabled()
                )),
        
                new TestCase<Attach>().setAttach(new Attach(
                        "DepthTextField should be enabled.",
                        () -> depthTextField.isEnabled()
                )),
        
                new TestCase<Attach>().setAttach(new Attach(
                        "DepthCheckBox should be enabled.",
                        () -> depthCheckBox.isEnabled()
                )),
        
                new TestCase<Attach>().setAttach(new Attach(
                        "ParsedLabel should be enabled.",
                        () -> parsedLabel.isEnabled()
                )),

                new TestCase<Attach>().setAttach(new Attach(
                        "ParsedLabel shows wrong number of parsed pages",
                        () -> {
                            String link = "http://localhost:25555/exampleDotCom";
                            urlTextField.setText(link);
                            runButton.click();
                            //A little pause because sometimes the test finishes before the user's program updates
                            // the UI
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                System.out.println();
                            }

                            int numberOfSubLinks = pageContent.getSubLinksWithLink(link);

                            try {
                                int parsedLabelText = Integer.parseInt(parsedLabel.text());
                                return parsedLabelText == numberOfSubLinks;

                            }catch (NumberFormatException e){
                                return false;
                            }
                        }
                )),

                new TestCase<Attach>().setAttach(new Attach(
                        "RunButton should be selected when the parsing begins",
                        () -> {
                            //Checking if button is selected runs in a loop because sometimes the user's program parses
                            // all the pages and
                            // reactivates the button before the test finishes, so i've written this test to try a
                            // couple of times before it's safe to assume the proper behaviour was not implemented.
                            for (int i = 0; i < 5; i++){
                                urlTextField.setText("http://localhost:25555/circular3");
                                runButton.click();
                                runButton = runButton.requireSelected(true);
                                if (runButton != null){
                                    return true;
                                }
                            }

                            return false;
                        }
                )),

                new TestCase<Attach>().setAttach(new Attach(
                        "RunButton should be deselected when there are no more links to parse",
                        () -> {
                            urlTextField.setText("http://localhost:25555/exampleDotCom");
                            runButton.click();

                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            
                            runButton = runButton.requireSelected(false);

                            return runButton != null;
                        }
                )),

                new TestCase<Attach>().setAttach(new Attach(
                        "Your app did not save a file after exporting.",
                        () -> {
                            exportUrlTextField.setText(EXPORT_DIRECTORY);
                            mapOfTitles = pageContent.getLinksNTitles();
                            for (Map.Entry m: mapOfTitles.entrySet()) {
                                String link = (String) m.getKey();
                                urlTextField.setText(link);
                                runButton.click();
                                exportButton.click();
                                boolean fileExists = checkFileExistence();
                                if (!fileExists){
                                    return false;
                                }
                                deleteFile();
                            }
                            return true;
                        }
                )),

                new TestCase<Attach>().setAttach(new Attach(
                        "You should not save links that you have previously parsed.",
                        () -> {
                            for (Map.Entry m: mapOfTitles.entrySet()) {
                                String link = (String) m.getKey();
                                urlTextField.setText(link);
                                runButton.click();
                                exportButton.click();
                                boolean duplicateLinks = checkForDuplicateLinks();
                                if (duplicateLinks){
                                    return false;
                                }
                            }
                            return true;
                        }
                )),

                new TestCase<Attach>().setAttach(new Attach(
                        "The file your app saves contains wrong number of lines",
                        () -> {
                            urlTextField.setText("http://localhost:25555/exampleDotCom");
                            runButton.click();
                            exportButton.click();
                            boolean checkOne = checkFileNumberOfLines(2);

                            urlTextField.setText("http://localhost:25555/circular3");
                            runButton.click();
                            exportButton.click();
                            boolean checkTwo = checkFileNumberOfLines(8);

                            return checkOne && checkTwo;
                        }
                )),
                
                new TestCase<Attach>().setAttach(new Attach(
                        "Your program parsed links deeper than the maximum depth",
                        () -> {
                            
                            for (int i = 1; i <= 3; i++) {
                                depthTextField.setText(String.valueOf(i));
                                depthCheckBox.check(true);
                                urlTextField.setText("http://localhost:25555/circular1");
                                runButton.click();
                                exportButton.click();
    
                                boolean maxDepthExceeded = checkMaxDepthExceeded(i * 2);
                                if (maxDepthExceeded) {
                                    return false;
                                }
                            }
                            return true;

                        }
                )),

                new TestCase<Attach>().setAttach(new Attach(
                        "The file your app saves contains wrong title for it's parent url",
                        () -> {
                            depthTextField.setText("");
                            depthCheckBox.check(false);
                            for (Map.Entry m: mapOfTitles.entrySet()) {
                                String link = (String) m.getKey();
                                urlTextField.setText(link);
                                runButton.click();
                                exportButton.click();
                                boolean valid = checkEvenLines();
                                if (!valid){
                                    return false;
                                }
                            }
                            return true;
                        }
                )),
                
                new TestCase<Attach>().setAttach(new Attach(
                        "The file your app saves contains wrong title for it's parent url",
                        () -> {
                            for (int i = 1; i <= 3; i++) {
                                depthTextField.setText(String.valueOf(i));
                                depthCheckBox.check(true);
                                urlTextField.setText("http://localhost:25555/circular1");
                                runButton.click();
                                exportButton.click();
    
                                boolean valid = checkEvenLines();
                                if (!valid){
                                    return false;
                                }
                            }
                            return true;
                        }
                )));
    }
    
    //If the user's program saves a file that contains lines that exceed the expected number of lines, then it is
    // safe to assume that they went deeper than the maximum depth.
    private boolean checkMaxDepthExceeded(int expectedLines) {
        int fileLines = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(EXPORT_DIRECTORY))) {
            while (reader.readLine() != null){
                fileLines++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        
        return fileLines > expectedLines;
    }
    
    
    //Check if file saved contains duplicate links
    private boolean checkForDuplicateLinks(){
        parsedPages.clear();
        
        boolean duplicateLinks = false;
        int lineNumber = 1;
        String line = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(EXPORT_DIRECTORY))) {
            while ((line = reader.readLine()) != null){
                //Every odd line contains a link
                if (lineNumber % 2 != 0){
                    if (parsedPages.contains(line)){
                        duplicateLinks = true;
                    }
                    parsedPages.add(line);
                }
                lineNumber++;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return duplicateLinks;
    }
    //Checks if every even line contains the correct title
    private boolean checkEvenLines() {
        boolean valid = true;
        int lineNumber = 1;
        String line;
        String originalTitle = "";
        try(BufferedReader reader = new BufferedReader(new FileReader(EXPORT_DIRECTORY))) {
            while ((line = reader.readLine()) != null){
                //Every odd line contains a link
                if (lineNumber % 2 != 0){
                    originalTitle = pageContent.getTitleWithLink(line);
                }else {
                    //Every even line contains a title
                    if (!line.equals(originalTitle)){
                        valid = false;
                    }
                }
                lineNumber++;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return valid;
    
    }
    
    //Checks the number of lines in the file saved
    private boolean checkFileNumberOfLines(int expectedLineNumber) {
        int fileLines = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(EXPORT_DIRECTORY))) {
            while (reader.readLine() != null){
                fileLines++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return expectedLineNumber == fileLines;
    }
    
    private boolean checkFileExistence(){
        File file = new File(EXPORT_DIRECTORY);
        return file.exists();
    }
    
    
    @Override
    public CheckResult check(String reply, Attach clue) {
        try {
            return new CheckResult(clue.function.get(), clue.feedback);
        }catch (AssertionError error){
            return CheckResult.FALSE(clue.feedback);
        }
        
    }
    
    @AfterClass
    public static void stopServer(){
        System.out.println("Stopping server");
        webServerMock.stop();
    }
    
    static class Attach {
        public Supplier<Boolean> function;
        public String feedback;
        
        public Attach(String feedback, Supplier<Boolean> function){
            this.feedback = feedback;
            this.function = function;
        }
    }

}
