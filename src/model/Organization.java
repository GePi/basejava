package model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import utils.DateUtils;
import utils.XmlLocalDateAdapter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Comparable<Organization>, Serializable {
    private Link link;
    private List<Organization.Period> periods = new ArrayList<>();

    public Organization() {

    }

    public Organization(String name, String website, Organization.Period... periods) {
        this(new Link(name, website), new ArrayList<>(List.of(periods)));
    }

    public Organization(Link link, List<Organization.Period> periodList) {
        Objects.requireNonNull(link);
        Objects.requireNonNull(periodList);
        this.link = link;
        this.periods.addAll(periodList);
    }

    @Override
    public int compareTo(Organization o) {
        Objects.requireNonNull(o);
        return link.getName().compareTo(o.link.getName());
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public List<Organization.Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Organization.Period> periods) {
        this.periods = periods;
    }

    public void addPeriod(Organization.Period period) {
        periods.add(period);
    }

    @Override
    public String toString() {
        var stringList = new ArrayList<String>(6);
        for (var period : periods) {
            var dateInterval = new StringBuilder();
            dateInterval.append(String.format("С %s", YearMonth.from(period.getFrom())));
            if (period.getTo() != DateUtils.NOW) {
                dateInterval.append(String.format(" по %s", YearMonth.from(period.getTo())));
            } else {
                dateInterval.append(" по сейчас");
            }
            if (!dateInterval.isEmpty()) {
                stringList.add(dateInterval.toString());
            }
            stringList.add(link.getName());
            if (link.getUrl() != null) {
                stringList.add(link.getUrl());
            }
            if (period.getTitle() != null) {
                stringList.add(period.getTitle());
            }
            if (period.getDescription() != null) {
                stringList.add(period.getDescription());
            }
        }
        return String.join("\n", stringList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization that)) return false;
        return link.equals(that.link) && periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, periods);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Comparable<Period>, Serializable {
        @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
        private LocalDate from;
        @XmlJavaTypeAdapter(XmlLocalDateAdapter.class)
        private LocalDate to;
        private String title;
        private String description;

        public Period() {
        }

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

        public void setFrom(LocalDate from) {
            this.from = from;
        }

        public void setTo(LocalDate to) {
            this.to = to;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
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
