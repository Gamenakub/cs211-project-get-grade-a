package ku.cs.controllers.components.tables;

import ku.cs.services.SortDirection;

import java.util.ArrayList;
import java.util.Comparator;

class ModelSorter<E> {
    private Comparator<E> comparator;
    private SortDirection sortDirection;

    public ModelSorter() {
        this.comparator = null;
        this.sortDirection = SortDirection.ASCENDING;
    }

    public void setComparator(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public void sort(ArrayList<E> models) {
        if (comparator != null) {
            models.sort(comparator);
            if (sortDirection.equals(SortDirection.DESCENDING)) {
                models.sort(comparator.reversed());
            }
        }
    }
}