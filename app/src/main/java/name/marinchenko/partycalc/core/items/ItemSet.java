package name.marinchenko.partycalc.core.items;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashSet;

/**
 * Created by Valentin on 01.10.2018.
 */

public class ItemSet<T extends BaseItem> extends HashSet<T> {

    private int sum = 0;
    private int mean = 0;

    public static void main(String[] args) {
        final int i1 = 100;
        final int i2 = 3;
        final int m = i1 / i2;
        System.out.println(m);
    }

    public static void getResultItems(
            @NonNull final ItemSet<BuyItem> buyItems,
            @NonNull final ItemSet<PayItem> payItems,
            @NonNull final ItemSet<ResultItem> resultItems
    ) {
        /*
        final int costSum = buyItems.getSum();
        final int payedSum = payItems.getSum();
        */

    }

    @Override
    public boolean add(T t) {
        updatePayItemDebt(t, mean(this.sum + t.getValue(), size() + 1));
        final boolean added = super.add(t);
        if (added) {
            this.sum += t.getValue();
            calcMean();
        }
        return added;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof BaseItem)) return false;
        final boolean removed = super.remove(o);
        if (removed) {
            this.sum -= ((BaseItem) o).getValue();
            calcMean();
        }
        return removed;
    }

    public boolean edit(@NonNull final T t) {
        if (!this.contains(t)) return false;
        for (BaseItem item : this) {
            if (item.equals(t)) {
                item.setName(t.getName());

                this.sum -= item.getValue();
                item.setValue(t.getValue());
                this.sum += item.getValue();

                calcMean();
                updatePayItemDebt(item, this.mean);

                if (item instanceof ResultItem && t instanceof ResultItem) {
                    ((ResultItem) item).setTo(((ResultItem) t).getTo());
                }
            }
        }
        return true;
    }

    public int getSum() { return this.sum; }
    public int getMean() { return this.mean; }

    private void calcMean() {
        this.mean = mean(this.sum, size());
    }

    private static int mean(final int sum,
                            final int size) {
        if (size == 0) return 0;
        return sum / size;
    }

    private static void updatePayItemDebt(final BaseItem item,
                                   final int mean) {
        if (item instanceof PayItem) ((PayItem) item).calcDebt(mean);
    }
}
