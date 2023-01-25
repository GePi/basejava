package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {

    private List<Organization> items = new ArrayList<>() {
    };

    public OrganizationSection() {
    }

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

    public void setItems(List<Organization> items) {
        this.items = items;
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


