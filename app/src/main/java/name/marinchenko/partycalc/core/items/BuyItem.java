package name.marinchenko.partycalc.core.items;

import android.support.annotation.NonNull;

/**
 * Wraps buy item: what product was bought and how much did it cost.
 */
public class BuyItem extends BaseItem {

    public BuyItem(@NonNull final String name,
                   final int value,
                   final int id) {
        super(name, value, id);
    }

    @Override
    public String toString() {
        return "BuyItem" + super.toString();
    }
}
