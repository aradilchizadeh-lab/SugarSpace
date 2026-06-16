package Models;

import java.util.ArrayList;

import Data.AgeType;

import Interfaces.*;
import Rules.*;

public class Agent {
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

    public void upgradeBehavior(IBehavior behavior) {
        Behavior = behavior;
    }

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

    public boolean canBeInfected() {
        return Behavior.canBeInfected();
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
        if (Behavior.CanEmigrate())
            Emigration.emigrate(this, space);
    }

    public void production(ISpaceProvider space) {
        if (Behavior.canProduce())
            Production.production(this, space);
    }

    public void aging(ISpaceProvider space) {
        aging.ageRule(this, space);
    }

    public void loan(ISpaceWithTickProvider space) {
        if (Behavior.canLoan())
            Loan.loan(this, space);
    }

    public void trade(ISpaceProvider space) {
        if (Behavior.canTrade())
            Trade.trade(this, space);
    }

    public void disease(ISpace_Diseases space) {
        if (Behavior.canBeInfected())
            Disease.disease(this, space);
    }


    public void addInfectedDiseases(int disease) {
        Disease.addInfectedDiseases(disease * 10);
    }

    public ArrayList<LoanInfo> getLoanInfos() {
        return LoanInfos;
    }

    /*public void print() {
        for (int i = 0; i < LoanInfos.size(); i++) {
            LoanInfo loanInfo = getLoanInfos().get(i);
            System.out.println(loanInfo.getLender() + " " + loanInfo.getBorrower() + " " + String.valueOf(loanInfo.getAmount()) + " " + String.valueOf(loanInfo.getResourceType()) + " " + String.valueOf(loanInfo.getLoanTick()));
        }
    }*/

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
