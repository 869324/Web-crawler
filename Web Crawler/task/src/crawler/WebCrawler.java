package crawler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler extends JFrame {


    public WebCrawler() {
        super("Web Crawler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        JLabel url = new JLabel();
        url.setText("StartUrl:");
        url.setBounds(10, 10, 50, 25);
        add(url);

        JTextField urlField = new JTextField();
        urlField.setName("UrlTextField");
        urlField.setBounds(85, 10, 315, 25);
        add(urlField);

        JLabel workersLabel = new JLabel();
        workersLabel.setText("Workers:");
        workersLabel.setBounds(10, 45, 50, 25);
        add(workersLabel);

        JTextField workersField = new JTextField();
        workersField.setBounds(85, 45, 395, 25);
        add(workersField);

        JLabel depthLabel = new JLabel();
        depthLabel.setText("Max Depth:");
        depthLabel.setBounds(10, 75, 70, 25);
        add(depthLabel);

        JTextField depthField  = new JTextField();
        depthField.setName("DepthTextField");
        depthField.setBounds(85, 75, 315, 25);
        add(depthField);

        JCheckBox depthCheckBox = new JCheckBox();
        depthCheckBox.setName("DepthCheckBox");
        depthCheckBox.setBounds(405, 75, 20, 25);
        add(depthCheckBox);

        JLabel depthCheckBoxLabel = new JLabel();
        depthCheckBoxLabel.setText("Enabled");
        depthCheckBoxLabel.setBounds(430, 75, 60, 25);
        add(depthCheckBoxLabel);

        JLabel timeLabel = new JLabel();
        timeLabel.setText("Time Limit:");
        timeLabel.setBounds(10, 110, 70, 25);
        add(timeLabel);

        JTextField timeField  = new JTextField();
        timeField.setBounds(85, 110, 250, 25);
        add(timeField);

        JLabel timeTypeLabel = new JLabel();
        timeTypeLabel.setText("Seconds");
        timeTypeLabel.setBounds(345, 110, 60, 25);
        add(timeTypeLabel);

        JCheckBox timeCheckBox = new JCheckBox();
        timeCheckBox.setBounds(405, 110, 20, 25);
        add(timeCheckBox);

        JLabel timeCheckBoxLabel = new JLabel();
        timeCheckBoxLabel.setText("Enabled");
        timeCheckBoxLabel.setBounds(430, 110, 60, 25);
        add(timeCheckBoxLabel);

        JLabel parsedTimeLabel = new JLabel();
        parsedTimeLabel.setText("Elapsed time:");
        parsedTimeLabel.setBounds(10, 135, 80, 25);
        add(parsedTimeLabel);

        JLabel parsedTimeLabel2 = new JLabel();
        parsedTimeLabel2.setBounds(95, 135, 70, 25);
        add(parsedTimeLabel2);

        JLabel pageContLabel = new JLabel();
        pageContLabel.setText("Parsed Pages:");
        pageContLabel.setBounds(10, 170, 85, 25);
        add(pageContLabel);

        JLabel pageContLabel2 = new JLabel();
        pageContLabel2.setName("ParsedLabel");
        pageContLabel2.setBounds(100, 170, 70, 25);
        add(pageContLabel2);

        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable();
        table.setModel(tableModel);
        table.setName("TitlesTable");
        table.setEnabled(false);
        tableModel.addColumn("URL");
        tableModel.addColumn("Title");



        JToggleButton btn = new JToggleButton();
        btn.setName("RunButton");
        btn.setText("Run");
        btn.setBounds(410,10, 70, 25);
        add(btn);
        Queue<String> visitedLinks = new ArrayDeque<>();
        Queue<String> pendingLinks = new ArrayDeque<>();
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                btn.setSelected(true);
                tableModel.setRowCount(0);
                //final Instant startTime = Instant.now();
                final String url = urlField.getText();
                //final int workers = Integer.parseInt(workersField.getText());
                int depth = 0;
                if(!depthField.getText().equals("")){
                    depth = Integer.parseInt(depthField.getText());
                }
                final boolean isDepth = depthCheckBox.isSelected();
                //final int timeLimit = Integer.parseInt(timeField.getText());
                //final boolean isTime = timeCheckBox.isSelected();
                Pattern pattern = Pattern.compile("(https?://)(.*/).*");
                Matcher matcher = pattern.matcher(url);
                String protocol = "";
                String relativePath = "";
                if (matcher.matches()) {
                    protocol = matcher.group(1);
                    relativePath = matcher.group(2);
                }
                //Instant end = Instant.now();
                pendingLinks.offer(url);
                int level = 0;
                //while (level < depth && isDepth) {
                    while (!pendingLinks.isEmpty()) {
                        String url1 = pendingLinks.poll();
                        if (!visitedLinks.contains(url1)) {
                            String siteText = getContent(url1);
                            String title = getPageTitle(siteText);
                            tableModel.insertRow(tableModel.getRowCount(), new Object[]{url1, title});
                            visitedLinks.add(url1);
                            pageContLabel2.setText(String.valueOf(visitedLinks.size()));
                            pattern = Pattern.compile("<a href=[\"\'](.*)[\"\']>");
                            matcher = pattern.matcher(siteText);
                            while (matcher.find()) {
                                String link = matcher.group(1);
                                if (!link.equals("unavailablePage")) {
                                    String finalLink = protocol + relativePath + link;
                                    /*title = getPageTitle(getContent(finalLink));
                                    tableModel.insertRow(tableModel.getRowCount(), new Object[]{finalLink, title});*/
                                    pendingLinks.offer(finalLink);
                                }
                            }
                        }
                        level += 1;
                    //}
                }
                btn.setSelected(false);
            }
        });

        JLabel label1 = new JLabel();
        label1.setText("Export:");
        label1.setBounds(10, 500, 50, 25);
        add(label1);

        JTextField path = new JTextField();
        path.setName("ExportUrlTextField");
        path.setBounds(65, 500, 330, 25);
        add(path);

        JButton save = new JButton();
        save.setName("ExportButton");
        save.setText("Save");
        save.setBounds(400, 500, 70, 25);
        add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String filePath = path.getText();
                File file = new File(filePath);
                try(PrintWriter writer = new PrintWriter(file)){
                    for (int i=0; i < tableModel.getRowCount(); i++){
                        for (int j=0; j < tableModel.getColumnCount(); j++){
                            writer.println(table.getValueAt(i, j));
                        }
                    }

                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });

    }
    public String getContent(String url){
        String siteText = "";
        try {
            URLConnection connection = new URL(url).openConnection();
            if (connection.getContentType().equals("text/html")) {
                InputStream inputStream = connection.getInputStream();
                siteText = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                inputStream.close();
            }
        }catch (IOException ex) {
            System.out.println(ex.getMessage() + " "+url);
        }
        return siteText;
    }

    public String getPageTitle(String siteText){
        String title = "";
        Pattern pattern = Pattern.compile("<title>(.*)</title>");
        Matcher matcher = pattern.matcher(siteText);
        if (matcher.find()) {
            title = matcher.group(1);
        }
        return title;
    }


}