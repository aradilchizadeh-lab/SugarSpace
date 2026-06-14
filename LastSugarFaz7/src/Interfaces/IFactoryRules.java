package Interfaces;

import Rules.*;

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

    public static Disease createDisease(){
        return new Disease();
    }
}