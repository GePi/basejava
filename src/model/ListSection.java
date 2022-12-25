package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private List<String> textLines = new ArrayList<>();

    public ListSection(List<String> textLines) {
        this.textLines = textLines;
    }

    public ListSection() {
    }

    public void addLine(String textLine) {
        textLines.add(textLine);
    }

    public ListSection addLineByLine(String textLine) {
        textLines.add(textLine);
        return this;
    }

    public List<String> getLines() {
        return textLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection that)) return false;
        return textLines.equals(that.textLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textLines);
    }

    @Override
    public String toString() {
        return String.join("\n", textLines);
    }
}
