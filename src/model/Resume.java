package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Resume implements Comparable<Resume>, Serializable {

    public static final Comparator<Resume> COMPARE_BY_NAME = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    private String uuid;
    private String fullName;
    private Map<ContactType, String> contacts = new HashMap<>();
    private Map<SectionType, AbstractSection> sections = new HashMap<>();

    public Resume() {
        this(UUID.randomUUID().toString(), "");
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }

    public void setSections(Map<SectionType, AbstractSection> sections) {
        this.sections = sections;
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
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName) && Objects.equals(contacts, resume.contacts) && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
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
