import java.io.*;

public class Main {
    public static void main(String[] args) {
        doTryCatchFinally();
    }

    public static void doTryCatchFinally() {
        char[] buff = new char[8];
        int length;
        try (BufferedReader br = new BufferedReader(new FileReader("file1.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("outfile.txt"))){
            String line;
            while( (line = br.readLine()) != null) {
                System.out.println(line);
                bw.write(line);
                bw.newLine();
                bw.write("test!!");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
