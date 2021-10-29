import java.io.*;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String[] data = {
                "line 1",
                "line 2 2",
                "line 3 3 3",
                "line 4 4 4 4",
                "line 5 5 5 5 5"
        };
//        doTryCatchFinally();
        try(FileSystem zipFs = openZip(Paths.get("myData.zip"))) {
            copyToZip(zipFs);
            writeToFileInZip1(zipFs,data);
            writeToFileInZip2(zipFs,data);
        } catch (Exception e) {
            e.getStackTrace();
        }

        readAndWriteUsingFilesMethods();

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

    public static void readAndWriteUsingFilesMethods() {
        try ( BufferedReader br = Files.newBufferedReader(Paths.get("file1.txt")) ) {
            System.out.println(br.readLine());
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

    public static void copyToZip(FileSystem zipFs) throws IOException {
        Path sourceFile = Paths.get("file1.txt");
        Path destFile = zipFs.getPath("/file1Copied.txt");
        Files.copy(sourceFile, destFile, StandardCopyOption.REPLACE_EXISTING);
    }

    private static void writeToFileInZip1(FileSystem zipFs, String[] data) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(zipFs.getPath("/newFile1.txt"))) {
            for (String d: data) {
                bw.write(d);
                bw.newLine();
            }
        }
    }

    private static void writeToFileInZip2(FileSystem zipFs, String[] data) throws IOException {
        Files.write(zipFs.getPath("/newFile2.txt")
                , Arrays.asList(data)
                , Charset.defaultCharset()
                , StandardOpenOption.CREATE);
    }
}
