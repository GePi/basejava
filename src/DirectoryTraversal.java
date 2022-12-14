import java.io.File;

public class DirectoryTraversal {
    public static void main(String[] args) {
        doItRecursive(new File("."), 0);
    }

    private static void doItRecursive(File file, int depth) {
        System.out.println(" ".repeat(depth * 2) + file.getPath());
        if (!file.isDirectory()) {
            return;
        }
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
