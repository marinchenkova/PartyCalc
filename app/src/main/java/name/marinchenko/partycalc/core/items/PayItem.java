package name.marinchenko.partycalc.core.items;

import android.support.annotation.NonNull;

import java.util.HashSet;

/**
 * Wraps pay item: who and how much payed for listed products.
 */
public class PayItem extends BaseItem {

    private static int DEFAULT_VALUE = 0;

    private int debt;
    private final HashSet<Integer> payedForSet = new HashSet<>();


    public PayItem(@NonNull final String name,
                   final int id) {
        super(name, DEFAULT_VALUE, id);
    }

    public void calcDebt(final int mean) {
        this.debt = mean - this.value;
    }

    public void addPayedForItem(final int buyItemId) {
        this.payedForSet.add(buyItemId);
    }

    public void removePayedForItem(final int buyItemId) {
        this.payedForSet.remove(buyItemId);
    }

    @NonNull
    public HashSet<Integer> getPayedForSet() { return this.payedForSet; }

    @Override
    public String toString() {
        return "pay" + super.toString();
    }
}
