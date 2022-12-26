package storage.serialization;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;

public class DataSerializer implements SerializationStrategy {
    static final String NULL_LABEL = "The following values are missing";
    static final String NOT_NULL_LABEL = "The following values exist";

    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            writeWithException(dos, r.getContacts().entrySet(), (e) -> {
                dos.writeUTF(e.getKey().name());
                dos.writeUTF(e.getValue());
            });

            writeWithException(dos, r.getSections().entrySet(), (e) -> {
                dos.writeUTF(e.getKey().name());
                switch (e.getKey()) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) e.getValue()).getText());
                    case ACHIEVEMENT, QUALIFICATION ->
                            writeWithException(dos, ((ListSection) e.getValue()).getLines(), dos::writeUTF);
                    case EDUCATION, EXPERIENCE ->
                            writeWithException(dos, ((OrganizationSection) e.getValue()).getItems(), (o) -> {
                                dos.writeUTF(o.getLink().getName());
                                dos.writeUTF(o.getLink().getUrl());
                                writeWithException(dos, o.getPeriods(), (p) -> {
                                    dos.writeUTF(p.getFrom().toString());
                                    dos.writeUTF(p.getTo().toString());
                                    dos.writeUTF(p.getTitle());
                                    writeWithNullLabel(dos, p.getDescription(), dos::writeUTF);
                                });
                            });
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        Resume r = new Resume();
        try (DataInputStream dis = new DataInputStream(is)) {
            r.setUuid(dis.readUTF());
            r.setFullName(dis.readUTF());

            readWithException(dis, () -> r.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                var sectionType = SectionType.valueOf(dis.readUTF());
                r.addSection(sectionType, switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        TextSection section = new TextSection();
                        section.setText(dis.readUTF());
                        yield section;
                    }
                    case ACHIEVEMENT, QUALIFICATION -> {
                        ListSection section = new ListSection();
                        readWithException(dis, () -> section.addLine(dis.readUTF()));
                        yield section;
                    }
                    case EDUCATION, EXPERIENCE -> {
                        OrganizationSection section = new OrganizationSection();
                        readWithException(dis, () -> {
                            Organization o = new Organization();
                            Link link = new Link(dis.readUTF(), dis.readUTF());
                            o.setLink(link);

                            readWithException(dis, () -> {
                                Organization.Period period = new Organization.Period();
                                period.setFrom(LocalDate.parse(dis.readUTF()));
                                period.setTo(LocalDate.parse(dis.readUTF()));
                                period.setTitle(dis.readUTF());
                                if (dis.readUTF().equals(NOT_NULL_LABEL)) {
                                    period.setDescription(dis.readUTF());
                                }
                                o.addPeriod(period);
                            });
                            section.addItems(o);
                        });
                        yield section;
                    }
                });
            });
        }
        return r;
    }

    private <E> void writeWithNullLabel(DataOutputStream dos, E line, IterableWriter<E> writer) throws IOException {
        if (line == null) {
            dos.writeUTF(NULL_LABEL);
        } else {
            dos.writeUTF(NOT_NULL_LABEL);
            writer.writeElement(line);
        }
    }

    private <E> void writeWithException(DataOutputStream dos, Collection<E> collection, IterableWriter<E> writer) throws IOException {
        dos.writeInt(collection.size());
        for (var element : collection) {
            writer.writeElement(element);
        }
    }

    private void readWithException(DataInputStream dis, IterableReader reader) throws IOException {
        int numberElements = dis.readInt();
        for (var i = 0; i < numberElements; i++) {
            reader.readElement();
        }
    }
}
