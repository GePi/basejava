import java.io.File;

public class DirectoryTraversal {
    public static void main(String[] args) {
        doItRecursive(new File("."), 0);
    }

    private static void doItRecursive(File file, int depth) {
        if (!file.isDirectory()) {
            System.out.println(" ".repeat(depth * 2) + "[FILE] " + file.getPath());
            return;
        }
        System.out.println(" ".repeat(depth * 2) + "[DIR] " + file.getPath());
        var listFiles = file.listFiles();
        if (listFiles == null) {
            return;
        }
        for (File nextLevelFile : listFiles
        ) {
            doItRecursive(nextLevelFile, depth + 1);
        }
    }
}
