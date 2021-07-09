import java.io.InputStream;

class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        int bytes = inputStream.read();
        while (bytes != -1){
            System.out.print(bytes);
            bytes = inputStream.read();
        }
        inputStream.close();
    }
}