package Models;

import java.security.PublicKey;
import java.util.ArrayList;

import Data.AgeType;

import Interfaces.*;
import Interfaces.Agent.*;
import Interfaces.Patch.*;
import Interfaces.Rules.*;


public class Agent implements IAgent_Emigration, IAgent_Loan, IAgent_Paint, IAgent_Production, IAgent_Trade, IAgent_Aging, IAgent_Disease {
    
    private Position Aposition;
    private Wallet Awallet;
    private Physiology Aphysiology;
    private FertilityInfo AfertilityInfo;
    private IBehavior Behavior;
    private IEmigration Emigration;
    private IProduction Production;
    private ITrade Trade;
    private ILoan Loan;
    private IAging aging;
    private IDisease Disease;
    private ArrayList<LoanInfo> LoanInfos;
    
    public Agent(Position position, Wallet wallet, Physiology physiology, FertilityInfo fertilityInfo, IBehavior behavior){
        
        Aposition = position;
        Awallet = wallet;
        Aphysiology = physiology;
        AfertilityInfo = fertilityInfo;
        Behavior = behavior;
        Emigration = IFactoryRules.createEmigration();
        Production = IFactoryRules.createProduction();
        Loan = IFactoryRules.createLoan();
        Trade = IFactoryRules.createTrade();
        aging = IFactoryRules.createAging();
        Disease = IFactoryRules.createDisease();
        LoanInfos = new ArrayList<>();
    }

    public Wallet getWallet(){
        return Awallet;
    }

    public Position getPosition(){
        return Aposition;
    }

    public IBehavior getBehavior(){
        return Behavior;
    }

    public Physiology getPhysiology(){
        return Aphysiology;
    }

    public FertilityInfo getFertilityInfo(){
        return AfertilityInfo;
    }

    public void survival(ArrayList<Agent> agents, Patch[][] patches){
        Behavior.survival(this, agents, patches);
    }

    public void changeAge(){
        Aphysiology.setAge(Aphysiology.getAge() +1);
        updateAgeType();
    }

    private void updateAgeType() {
        if ((Aphysiology.getAge() >= AfertilityInfo.getFertileLimitMin()) && (Aphysiology.getAge() < AfertilityInfo.getFertileLimitMax())) {
            Aphysiology.setAgeType(AgeType.ReproductiveAdult);

        } else if (Aphysiology.getAge() >= AfertilityInfo.getFertileLimitMax()) {
            Aphysiology.setAgeType(AgeType.Elderly);
        }

        else {
            Aphysiology.setAgeType(AgeType.Child);
        }
    }


    public ArrayList<Integer> getPossibleDiseases(){
        return Disease.getPossibleDiseases();
    }

    public ArrayList<Integer> getInfectedDiseases(){
        return Disease.getInfectedDiseases();
    }

    public double getWelfare(double w1, double w2){
        return Behavior.getWelfare(this, w1, w2);
    }

     public double getMRS(double w1, double w2){
        return Behavior.getMRS(this, w1, w2);
    }

    public void reproductionInherit(){
        Behavior.reproductionInherit(this);
    }

    public boolean canBeParent(){
        return Behavior.canBeParent(this);
    }

    public boolean canBeInfected(){
        return Behavior.canBeInfected();
    }

    public boolean canBeLender(){
        return Behavior.canBeLender(this);
    }

    public int requiredSpiceAmount(){
        return Behavior.requiredSpiceAmount(this);
    }

    public int requiredSugarAmount(){
        return Behavior.requiredSugarAmount(this);
    }

    public boolean needsSpice(){
        return Behavior.needsSpice(this);
    }

    public boolean needsSugar() {
        return Behavior.needsSugar(this);
    }

    public void emigration(IPatch_Emigration[][] patches, ArrayList<Agent> agents ) {
        if (Behavior.CanEmigrate())
            Emigration.emigrate(this, patches, agents);
    }
    public void production(IPatch_Production[][] patches, ArrayList<Agent> agents) {
        if (Behavior.canProduce())
            Production.production(this, patches, agents);
    }
    public void aging(IPatch_Aging[][] patches, ArrayList<Agent> agents) {
        aging.ageRule(this, patches, agents);
    }
    public void loan(IPatch_Loan[][] patches, int tick ) {
        if (Behavior.canLoan())
            Loan.loan(this, patches, tick);
    }
    public void trade(IPatch_Trade[][] patches) {
        if (Behavior.canTrade())
            Trade.trade(this, patches);
    }
    public void disease(IPatch_Disease[][] patches, ArrayList<Integer> diseases) {
        if (Behavior.canBeInfected())
            Disease.disease(this, patches, diseases);
    }


    public void addInfectedDiseases(int disease){
        Disease.addInfectedDiseases(disease * 10);
    }

    public ArrayList<LoanInfo> getLoanInfos(){
        return LoanInfos;
    }

    public void print() {
        for (int i = 0; i < LoanInfos.size(); i++) {
            LoanInfo loanInfo = getLoanInfos().get(i);
            System.out.println(loanInfo.getLender() + " " + loanInfo.getBorrower() + " " + String.valueOf(loanInfo.getAmount()) + " " + String.valueOf(loanInfo.getResourceType()) + " " + String.valueOf(loanInfo.getLoanTick()));
        }
    }


}
