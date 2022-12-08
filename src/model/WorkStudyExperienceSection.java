package model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class WorkStudyExperienceSection extends Section {

    private final TreeSet<WSEItem> items = new TreeSet<>();

    public void addItem(WSEItem item) {
        items.add(item);
    }

    public void addItems(WSEItem... itemsArr) {
        items.addAll(Arrays.asList(itemsArr));
    }

    public List<WSEItem> getItems() {
        return items.stream().toList();
    }

    @Override
    public String toString() {
        var resultStr = new StringBuilder();
        items.forEach(resultStr::append);
        return resultStr.toString();
    }

    static public class WSEItem implements Comparable<WSEItem> {
        private final LocalDate from;
        private final LocalDate to;
        private final String organizationName;
        private final String url;
        private final String workPosition;
        private final String essenceOfWork;
        private final boolean isCurrentPosition;

        public WSEItem(LocalDate from, LocalDate to, String organizationName, String url, String workPosition, String essenceOfWork, boolean isCurrentPosition) {
            this.from = from;
            this.to = to;
            this.organizationName = organizationName;
            this.url = url;
            this.workPosition = workPosition;
            this.essenceOfWork = essenceOfWork;
            this.isCurrentPosition = isCurrentPosition;
        }

        @Override
        public int compareTo(WSEItem o) {
            return o.from.compareTo(from);
        }

        @Override
        public String toString() {
            var stringList = new ArrayList<String>(6);
            var dateInterval = new StringBuilder();

            if (from != null) {
                dateInterval.append(String.format("С %s", YearMonth.of(from.getYear(), from.getMonth())));
                if (to != null) {
                    dateInterval.append(String.format(" по %s", YearMonth.of(from.getYear(), from.getMonth())));
                } else if (isCurrentPosition) {
                    dateInterval.append(" по сейчас");
                }
            }
            if (!dateInterval.isEmpty()) {
                stringList.add(dateInterval.toString());
            }
            if (organizationName != null) {
                stringList.add(organizationName);
            }
            if (url != null) {
                stringList.add(url);
            }
            if (workPosition != null) {
                stringList.add(workPosition);
            }
            if (essenceOfWork != null) {
                stringList.add(essenceOfWork);
            }
            return String.join("\n", stringList);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WSEItem wseItem)) return false;
            return isCurrentPosition == wseItem.isCurrentPosition && Objects.equals(from, wseItem.from) && Objects.equals(to, wseItem.to) && Objects.equals(organizationName, wseItem.organizationName) && Objects.equals(url, wseItem.url) && Objects.equals(workPosition, wseItem.workPosition);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to, organizationName, url, workPosition, isCurrentPosition);
        }
    }
}


