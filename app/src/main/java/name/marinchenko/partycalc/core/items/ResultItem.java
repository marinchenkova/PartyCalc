package name.marinchenko.partycalc.core.items;


import android.support.annotation.NonNull;

/**
 * Wraps result item: who must pay to whom an how much.
 */
public class ResultItem extends BaseItem {

    @NonNull
    private String to;


    public ResultItem(@NonNull final String from,
                      @NonNull final String to,
                      final int value,
                      final int id) {
        super(from, value, id);
        this.to = to;
    }


    @NonNull
    public String getTo() { return to; }

    public void setTo(@NonNull final String to) { this.to = to; }

    @Override
    public String toString() {
        return "res" + super.toString() + "->" + this.to;
    }
}
