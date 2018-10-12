package name.marinchenko.partycalc.core.items;

import android.support.annotation.NonNull;

/**
 * Wraps base item for extending BuyItem and PayItem
 */
public class BaseItem {

    @NonNull
    protected String name;
    protected int value;
    protected final int id;


    public BaseItem(@NonNull final String name,
                    final int value,
                    final int id) {
        this.name = name;
        this.value = value;
        this.id = id;
    }


    @NonNull
    public String getName() { return this.name; }
    public int getValue() { return this.value; }
    public int getId() { return this.id; }

    public void setName(@NonNull final String name) { this.name = name; }
    public void setValue(final int value) { this.value = value; }

    @Override
    public String toString() {
        return this.id + ":" + this.name + "(" + this.value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseItem baseItem = (BaseItem) o;

        return this.id == baseItem.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
