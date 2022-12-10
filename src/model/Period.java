package model;

import java.time.LocalDate;
import java.util.Objects;

public class Period implements Comparable<Period> {

    private final LocalDate from;
    private final LocalDate to;
    private final String title;
    private final String description;

    public Period(LocalDate from, LocalDate to, String title, String description) {
        this.from = from;
        this.to = to;
        this.title = title;
        this.description = description;
    }

    public Period(LocalDate from, LocalDate to, String title) {
        this(from, to, title, null);
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Period period)) return false;
        return Objects.equals(from, period.from) && Objects.equals(to, period.to) && Objects.equals(title, period.title) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, title, description);
    }

    @Override
    public String toString() {
        return "from=" + from + ", to=" + to + ", title='" + title + '\'' + ", description='" + description + '\'';
    }

    @Override
    public int compareTo(Period period) {
        return period.from.compareTo(from);
    }
}
