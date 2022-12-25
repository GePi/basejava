package storage.serialization;

import model.*;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Map;

public class DataSerializer implements SerializationStrategy {
    static String NULL_LABEL = "The following values are missing";
    static String NOT_NULL_LABEL = "The following values exist";

    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            dos.writeInt(r.getContacts().size());
            for (var contactEntry : r.getContacts().entrySet()) {
                dos.writeUTF(contactEntry.getKey().name());
                dos.writeUTF(contactEntry.getValue());
            }

            dos.writeInt(r.getSections().size());
            for (var sectionEntry : r.getSections().entrySet()) {
                dos.writeUTF(sectionEntry.getKey().name());
                dos.writeUTF(sectionEntry.getValue().getClass().getName());
                if (sectionEntry.getValue() instanceof TextSection) {
                    writeTextSection(sectionEntry, dos);
                } else if (sectionEntry.getValue() instanceof ListSection) {
                    writeListSection(sectionEntry, dos);
                } else if (sectionEntry.getValue() instanceof OrganizationSection) {
                    writeOrganizationSection(sectionEntry, dos);
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        Resume r = new Resume();
        try (DataInputStream dis = new DataInputStream(is)) {
            r.setUuid(dis.readUTF());
            r.setFullName(dis.readUTF());
            int countContacts = dis.readInt();
            for (int i = 0; i < countContacts; i++) {
                r.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int countSection = dis.readInt();
            for (int i = 0; i < countSection; i++) {
                r.addSection(SectionType.valueOf(dis.readUTF()), readSection(dis));
            }
        }
        return r;
    }

    private void writeOrganizationSection(Map.Entry<SectionType, AbstractSection> sectionEntry, DataOutputStream dos) throws IOException {
        var organizationSection = (OrganizationSection) sectionEntry.getValue();
        dos.writeInt(organizationSection.getItems().size());
        for (var organization : organizationSection.getItems()) {
            writeOrganization(organization, dos);
        }
    }

    private void writeOrganization(Organization organization, DataOutputStream dos) throws IOException {
        dos.writeUTF(organization.getLink().getName());
        dos.writeUTF(organization.getLink().getUrl());
        dos.writeInt(organization.getPeriods().size());
        for (var period : organization.getPeriods()) {
            writePeriod(period, dos);
        }
    }

    private void writePeriod(Organization.Period period, DataOutputStream dos) throws IOException {
        dos.writeUTF(period.getFrom().toString());
        dos.writeUTF(period.getTo().toString());
        dos.writeUTF(period.getTitle());
        if (period.getDescription() == null) {
            dos.writeUTF(NULL_LABEL);
        } else {
            dos.writeUTF(NOT_NULL_LABEL);
            dos.writeUTF(period.getDescription());
        }
    }

    private void writeListSection(Map.Entry<SectionType, AbstractSection> sectionEntry, DataOutputStream dos) throws IOException {
        var listSection = (ListSection) sectionEntry.getValue();
        dos.writeInt(listSection.getLines().size());
        for (var line : listSection.getLines()) {
            dos.writeUTF(line);
        }
    }

    private void writeTextSection(Map.Entry<SectionType, AbstractSection> sectionEntry, DataOutputStream dos) throws IOException {
        var textSection = (TextSection) sectionEntry.getValue();
        dos.writeUTF(textSection.getText());
    }

    private AbstractSection readSection(DataInputStream dis) throws IOException {
        try {
            Class<?> clazz = Class.forName(dis.readUTF());
            Constructor<?> sectionConstructor = clazz.getConstructor();
            AbstractSection section = (AbstractSection) sectionConstructor.newInstance();
            if (section instanceof TextSection) {
                readTextSection((TextSection) section, dis);
            } else if (section instanceof ListSection) {
                readListSection((ListSection) section, dis);
            } else if (section instanceof OrganizationSection) {
                readOrganizationSection((OrganizationSection) section, dis);
            }
            return section;
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void readOrganizationSection(OrganizationSection section, DataInputStream dis) throws IOException {
        int numberOrganization = dis.readInt();
        for (var i = 0; i < numberOrganization; i++) {
            section.addItems(readOrganization(dis));
        }
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        Organization o = new Organization();
        Link link = new Link(dis.readUTF(), dis.readUTF());
        o.setLink(link);
        int countPeriods = dis.readInt();
        for (var i = 0; i < countPeriods; i++) {
            o.addPeriod(readPeriod(dis));
        }
        return o;
    }

    private Organization.Period readPeriod(DataInputStream dis) throws IOException {
        Organization.Period period = new Organization.Period();
        period.setFrom(LocalDate.parse(dis.readUTF()));
        period.setTo(LocalDate.parse(dis.readUTF()));
        period.setTitle(dis.readUTF());
        if (dis.readUTF().equals(NOT_NULL_LABEL)) {
            period.setDescription(dis.readUTF());
        }
        return period;
    }

    private void readListSection(ListSection section, DataInputStream dis) throws IOException {
        int linesCount = dis.readInt();
        for (var i = 0; i < linesCount; i++) {
            section.addLine(dis.readUTF());
        }
    }

    private void readTextSection(TextSection section, DataInputStream dis) throws IOException {
        section.setText(dis.readUTF());
    }
}
