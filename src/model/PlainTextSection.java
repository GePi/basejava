package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlainTextSection extends Section {
    private List<String> textLines = new ArrayList<>();

    public PlainTextSection(List<String> textLines) {
        this.textLines = textLines;
    }

    public PlainTextSection(String textLine) {
        textLines.add(textLine);
    }

    public PlainTextSection() {
    }

    public void addLine(String textLine) {
        textLines.add(textLine);
    }

    public PlainTextSection addLineByLine(String textLine) {
        textLines.add(textLine);
        return this;
    }

    public List<String> getLines() {
        return textLines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlainTextSection that)) return false;
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
