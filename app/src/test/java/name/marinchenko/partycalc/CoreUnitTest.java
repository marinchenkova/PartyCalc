package name.marinchenko.partycalc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import name.marinchenko.partycalc.core.items.BaseItem;
import name.marinchenko.partycalc.core.items.BuyItem;
import name.marinchenko.partycalc.core.items.PayItem;
import name.marinchenko.partycalc.core.items.ResultItem;

import static org.junit.Assert.*;

/**
 *
 */
public class CoreUnitTest {

    @Test
    public void itemToString() {
        final String name = "name";
        final String to = "toName";
        final int value = 100;
        final int id = 1;

        final String base = id + ":" + name + "(" + value + ")";
        final String buy = "buy" + base;
        final String pay = "pay" + base;
        final String pay0 = "pay" + id + ":" + name + "(0)";
        final String res = "res" + base + "->" + to;

        assertEquals(base, new BaseItem(name, value, id).toString());
        assertEquals(buy, new BuyItem(name, value, id).toString());

        final PayItem payItem = new PayItem(name, id);
        assertEquals(pay0, new PayItem(name, id).toString());

        payItem.setValue(value);
        assertEquals(pay, payItem.toString());

        assertEquals(res, new ResultItem(name, to, value, id).toString());
    }

}