package model;

import utils.DateUtils;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Comparable<Organization>, Serializable {
    private final Link link;
    private final List<OrganizationSection.Period> periods = new ArrayList<>();

    public Organization(String name, String website, OrganizationSection.Period... periods) {
        this(new Link(name, website), new ArrayList<>(List.of(periods)));
    }

    public Organization(Link link, List<OrganizationSection.Period> periodList) {
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

    public List<OrganizationSection.Period> getPeriods() {
        return periods;
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
}
