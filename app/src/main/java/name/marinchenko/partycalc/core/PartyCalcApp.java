package name.marinchenko.partycalc.core;

import java.util.ArrayList;
import java.util.HashSet;

import name.marinchenko.partycalc.core.items.*;


/**
 *
 */
public class PartyCalcApp {

    private final ItemSet<BuyItem> buyItems = new ItemSet<>();
    private final ItemSet<PayItem> payItems = new ItemSet<>();
    private final HashSet<ResultItem> resultItems = new HashSet<>();


    public PartyCalcApp() {}
}
