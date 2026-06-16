package Models;

import java.util.ArrayList;

import Data.AgeType;

import Interfaces.*;
import Rules.*;

public class Agent implements IAgent_Histogram, IAgent_Emigration, IAgent_Trade, IAgent_Production, IAgent_Loan, IAgent_Disease, IAgent_Aging, IAgent_Paint{
    private Wallet Wallet;
    private Identity Identity;
    private IBehavior Behavior;
    private Emigration Emigration;
    private Production Production;
    private Trade Trade;
    private Loan Loan;
    private Aging aging;
    private Disease Disease;
    private ArrayList<LoanInfo> LoanInfos;

    public Agent(Wallet wallet, Identity identity, IBehavior behavior) {
        Behavior = behavior;
        Emigration = IFactoryRules.createEmigration();
        Production = IFactoryRules.createProduction();
        Loan = IFactoryRules.createLoan();
        Trade = IFactoryRules.createTrade();
        aging = IFactoryRules.createAging();
        Disease = IFactoryRules.createDisease();
        LoanInfos = new ArrayList<>();
        Wallet = wallet;
        Identity = identity;
    }

    public IBehavior getBehavior() {
        return Behavior;
    }

    /*public void upgradeBehavior(IBehavior behavior) {
        Behavior = behavior;
    }*/

    public double getWelfare(double w1, double w2) {
        return Behavior.getWelfare(this, w1, w2);
    }

    public double getMRS(double w1, double w2) {
        return Behavior.getMRS(this, w1, w2);
    }

    public void reproductionInherit() {
        Behavior.reproductionInherit(this);
    }

    public boolean canBeParent() {
        return Behavior.canBeParent(this);
    }

    public boolean canBeLender() {
        return Behavior.canBeLender(this);
    }


    public int requiredSpiceAmount() {
        return Behavior.requiredSpiceAmount(this);
    }

    public int requiredSugarAmount() {
        return Behavior.requiredSugarAmount(this);
    }

    public boolean needsSpice() {
        return Behavior.needsSpice(this);
    }

    public boolean needsSugar() {
        return Behavior.needsSugar(this);
    }

    public void emigration(ISpaceProvider space) {
        if (Identity.canEmigrate())
            Emigration.emigrate((IAgent_Emigration) this, space);
    }

    public void production(ISpaceProvider space) {
        if (Identity.canProduce())
            Production.production((IAgent_Production) this, space);
    }

    public void aging(ISpaceProvider space) {
        aging.ageRule((IAgent_Aging) this, space);
    }

    public void loan(ISpaceWithTickProvider space) {
        if (Identity.canLoan())
            Loan.loan((IAgent_Loan) this, space);
    }

    public void trade(ISpaceProvider space) {
        if (Identity.canTrade())
            Trade.trade((IAgent_Trade) this, space);
    }

    public void disease(ISpace_Diseases space) {
        if (Identity.canBeInfected())
            Disease.disease((IAgent_Disease) this, space);
    }


    public void addInfectedDiseases(int disease) {
        Disease.addInfectedDiseases(disease * 10);
    }

    public ArrayList<LoanInfo> getLoanInfos() {
        return LoanInfos;
    }

    public void survival(ISpaceProvider space) {
        Behavior.survival(this, space);
    }

    public ArrayList<Integer> getPossibleDiseases() {
        return Disease.getPossibleDiseases();
    }

    public ArrayList<Integer> getInfectedDiseases() {
        return Disease.getInfectedDiseases();
    }

    public Wallet getWallet() {
        return Wallet;
    }

    public Identity getIdentity() {
        return Identity;
    }
}
