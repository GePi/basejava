package model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

public class OrganizationSection extends AbstractSection {

    private final TreeSet<Organization> items = new TreeSet<>();

    public void addItem(Organization item) {
        items.add(item);
    }

    public void addItems(Organization... itemsArr) {
        items.addAll(Arrays.asList(itemsArr));
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
}


