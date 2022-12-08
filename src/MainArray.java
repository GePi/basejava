import model.Resume;
import storage.MapStorage;
import storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Interactive test for storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
    private final static Storage ARRAY_STORAGE = new MapStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | size | save uuid fullName | delete uuid | get uuid | clear | exit | update uuid fullName): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            String fullName = "dummy";
            if (params.length == 2) {
                uuid = params[1].intern();
            } else if (params.length == 3) {
                uuid = params[1].intern();
                fullName = params[2].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    r = new Resume(uuid, fullName);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "update":
                    r = ARRAY_STORAGE.get(uuid);
                    if (r != null) {
                        r.setFullName(fullName);
                        ARRAY_STORAGE.update(r);
                    }
                    break;
                case "delete":
                    ARRAY_STORAGE.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(ARRAY_STORAGE.get(uuid));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        Resume[] all = ARRAY_STORAGE.getAll();
        System.out.println("----------------------------");
        if (all.length == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}
