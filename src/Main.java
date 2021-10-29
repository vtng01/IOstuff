import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        doTryCatchFinally();
        try(FileSystem zipFs = openZip(Paths.get("myData.zip"))) {

        } catch (Exception e) {
            e.getStackTrace();
        }

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

    public static FileSystem openZip(Path zipPath) throws IOException, URISyntaxException {
        Map<String, String> providerProps = new HashMap<>();
        providerProps.put("create", "true");

        URI zipURI = new URI("jar:file", zipPath.toUri().getPath(), null);
        FileSystem zipFs = FileSystems.newFileSystem(zipURI, providerProps);

        return zipFs;
    }
}
