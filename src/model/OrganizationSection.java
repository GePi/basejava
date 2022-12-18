package model;

import utils.DateUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {

    private final List<Organization> items = new ArrayList<>() {
    };

    public void addItems(Organization... itemsArr) {
        items.addAll(Arrays.asList(itemsArr));
    }

    public void addItems(List<Organization> organizationsList) {
        Objects.requireNonNull(organizationsList);
        items.addAll(organizationsList);
    }

    public List<Organization> getItems() {
        return items.stream().toList();
    }

    @Override
    public String toString() {
        var resultStr = new StringBuilder();
        for (var item : items) {
            String itemStr = item.toString();
            if (!itemStr.isEmpty()) {
                if (!resultStr.isEmpty()) {
                    resultStr.append("\n");
                }
                resultStr.append(itemStr);
            }
        }
        return resultStr.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationSection that)) return false;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    public static class Period implements Comparable<Period>, Serializable {

        private final LocalDate from;
        private final LocalDate to;
        private final String title;
        private final String description;

        public Period(LocalDate from, LocalDate to, String title, String description) {
            Objects.requireNonNull(from);
            Objects.requireNonNull(to);
            Objects.requireNonNull(title);
            this.from = from;
            this.to = to;
            this.title = title;
            this.description = description;
        }

        public Period(LocalDate from, String title, String description) {
            this(from, DateUtils.NOW, title, description);
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
}


