import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainFile {
    public static void main(String[] args) {
        File file = new File(".\\.gitignore");
        File dir = new File(".\\src\\model");

        try {
            System.out.println(file.getCanonicalPath());
            System.out.println(dir.getCanonicalPath());
            System.out.println(dir.isDirectory());
            String[] dirList = dir.list();
            if (dirList != null) {
                System.out.println(Arrays.asList(dirList));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        String filePath = ".\\basejava.iml";
        try (var fis = new FileInputStream(file);
             var fisIml = new FileInputStream(filePath)) {
            System.out.println(new String(fis.readAllBytes()));
            System.out.println(new String(fisIml.readAllBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
