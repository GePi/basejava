package exceptions;

public class NotExistStorageException extends StorageException {

    public NotExistStorageException(String uuid) {
        super("Резюме c UUID=" + uuid + " не существует.", uuid);
    }
}
