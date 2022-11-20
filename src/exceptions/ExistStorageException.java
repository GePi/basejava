package exceptions;

public class ExistStorageException extends StorageException {

    public ExistStorageException(String uuid) {
        super("Резюме c UUID=" + uuid + " уже существует.", uuid);
    }
}
