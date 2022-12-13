package model;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Objects;

public class Organization implements Comparable<Organization> {
    private final String name;
    private final String website;
    private final Period period;

    public Organization(String name, String website, Period period) {
        this.name = name;
        this.website = website;
        this.period = period;
    }

    @Override
    public int compareTo(Organization o) {
        Objects.requireNonNull(o);
        Objects.requireNonNull(o.getPeriod());
        Objects.requireNonNull(period);
        return o.getPeriod().getFrom().compareTo(period.getFrom());
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public Period getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        var stringList = new ArrayList<String>(6);
        var dateInterval = new StringBuilder();

        if (period.getFrom() != null) {
            dateInterval.append(String.format("С %s", YearMonth.from(period.getFrom())));
            if (period.getTo() != null) {
                dateInterval.append(String.format(" по %s", YearMonth.from(period.getTo())));
            } else {
                dateInterval.append(" по сейчас");
            }
        }
        if (!dateInterval.isEmpty()) {
            stringList.add(dateInterval.toString());
        }
        if (name != null) {
            stringList.add(name);
        }
        if (website != null) {
            stringList.add(website);
        }
        if (period.getTitle() != null) {
            stringList.add(period.getTitle());
        }
        if (period.getDescription() != null) {
            stringList.add(period.getDescription());
        }
        return String.join("\n", stringList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(website, that.website) && Objects.equals(period, that.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website, period);
    }
}
