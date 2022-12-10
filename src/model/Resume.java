package model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    public static final Comparator<Resume> COMPARE_BY_NAME = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    private final String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new HashMap<>();
    private final Map<SectionType, AbstractSection> sections = new HashMap<>();

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public Resume() {
        this(randomUUID(), "dummy");
    }

    public Resume(String fullName) {
        this(randomUUID(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "UUID should not be null");
        Objects.requireNonNull(fullName, "fullName should not be null");
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

    public void addContact(ContactType contactType, String val) {
        contacts.put(contactType, val);
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void addSection(SectionType sectionType, AbstractSection abstractSection) {
        sections.put(sectionType, abstractSection);
    }

    public AbstractSection getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume resume)) return false;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume r) {
        int fullNameComparisonRes = fullName.compareTo(r.getFullName());
        return (fullNameComparisonRes == 0) ? uuid.compareTo(r.getUuid()) : fullNameComparisonRes;
    }
}
