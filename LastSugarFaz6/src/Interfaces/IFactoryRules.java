package Interfaces;

import Rules.Aging;
import Rules.Emigration;
import Rules.Loan;
import Rules.Production;
import Rules.Trade;

public interface IFactoryRules{

    public static Emigration createEmigration(){
        return new Emigration();
    }

    public static Production createProduction(){
        return new Production();
    }

    public static Loan createLoan(){
        return new Loan();
    }

    public static Trade createTrade(){
        return new Trade();
    }

    public static Aging createAging(){
        return new Aging();
    }
}