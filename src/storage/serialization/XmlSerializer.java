package storage.serialization;

import model.*;
import utils.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlSerializer implements SerializationStrategy {
    private XmlParser xmlParser;

    public XmlSerializer() {
        this.xmlParser =
                new XmlParser(
                        Resume.class,
                        Link.class,
                        Organization.class,
                        OrganizationSection.class,
                        TextSection.class,
                        ListSection.class,
                        Organization.Period.class);
    }

    @Override
    public void doWrite(OutputStream os, Resume r) throws IOException {
        try (var osw = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(r, osw);
        }

    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (var isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(isr);
        }
    }
}
