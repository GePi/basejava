package model;

import java.util.Comparator;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    public static final Comparator<Resume> COMPARE_BY_NAME = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    private final String uuid;
    private String fullName;

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public Resume() {
        this(randomUUID());
    }

    public Resume(String fullName) {
        this(randomUUID(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return uuid.equals(((Resume) obj).uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume r) {
        return uuid.compareTo(r.uuid);
    }
}
