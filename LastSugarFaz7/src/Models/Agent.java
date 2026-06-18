package Models;

import java.util.ArrayList;

import Data.AgeType;

import Interfaces.*;
import Rules.*;

public class Agent implements IAgent_Emigration, IAgent_Histogram, IAgent_Loan, IAgent_Paint, IAgent_Prodution, IAgent_Trade,IAgent_Aging, IAgent_Disease {
    private int Ax;
    private int Ay;
    private final int InitSugar;
    private final int InitSpice;
    private int ASugar;
    private int ASpice;
    private final int Vision;
    private float SugarMetabolism;
    private float SpiceMetabolism;
    private final int Gender;
    private final int MaxAge;
    private int Age;
    private final int[] FertileLimits;
    private boolean IsParent;
    private AgeType ageType;
    private IBehavior Behavior;
    private Emigration Emigration;
    private Production Production;
    private Trade Trade;
    private Loan Loan;
    private Aging aging;
    private Disease Disease;
    private ArrayList<LoanInfo> LoanInfos;
    
    public Agent(int x, int y, int initSugar, int initSpice, int vision, float sugarMetabolism, float spiceMetabolism, IBehavior behavior){
        Ax = x;
        Ay = y;
        InitSugar = initSugar;
        ASugar = InitSugar;
        InitSpice = initSpice;
        ASpice = InitSpice;
        Vision = vision;
        SugarMetabolism = sugarMetabolism;
        SpiceMetabolism = spiceMetabolism;
        Age = 0;
        MaxAge = (int)(Math.random()*41) + 60;
        Gender = (int)(Math.random()*2);
        IsParent = false;
        FertileLimits = new int[2];
        FertileLimits[0] = (int)(Math.random() * 16) + 45; //max
        FertileLimits[1] = (int)(Math.random() * 3) + 15; //min
        ageType = AgeType.Child;
        Behavior = behavior;
        Emigration = IFactoryRules.createEmigration();
        Production = IFactoryRules.createProduction();
        Loan = IFactoryRules.createLoan();
        Trade = IFactoryRules.createTrade();
        aging = IFactoryRules.createAging();
        Disease = IFactoryRules.createDisease();
        LoanInfos = new ArrayList<>();
    }

    
     public void setX(int x){
         Ax = x;
     }

     public void setY(int y){
         Ay = y;
     }

    public int getX(){
        return Ax;
    }

    public int getY(){
        return Ay;
    }

    public void setASugar(int ASugar){
        this.ASugar = ASugar;
    }

    public void setASpice(int ASpice){
        this.ASpice = ASpice;
    }

    public int getASugar(){
        return ASugar;
    }

    public int getASpice(){
        return ASpice;
    }

    public int getInitSugar(){
        return InitSugar;
    }

    public int getInitSpice(){
        return InitSpice;
    }

    public int getVision(){
        return Vision;
    }

    public int getMaxAge(){
        return MaxAge;
    }

    public int getGender(){
        return Gender;
    }

    public float getSugarMetabolism(){
        return SugarMetabolism;
    }

    public float getSpiceMetabolism(){
        return SpiceMetabolism;
    }

    public void setSugarMetabolism(float sugar){
        SugarMetabolism = sugar;
    }

    public void setSpiceMetabolism(float spice){
        SpiceMetabolism = spice;
    }

    public AgeType getAgeType() {
         return ageType;
    }

     public void setAgeType(AgeType ageType){
        this.ageType = ageType;
    }
     
     public int getFertileLimitMin(){
        return FertileLimits[1];
    }

     public int getFertileLimitMax(){
        return FertileLimits[0];
    }

     public boolean isParent(){
        return IsParent;
    }

     public void setParent(boolean parent){
        IsParent = parent;
    }

    public IBehavior getBehavior(){
        return Behavior;
    }

    public void survival(ArrayList<Agent> agents, Patch[][] patches){
        Behavior.survival(this, agents, patches);
    }

    public void changeAge(){
        Age++;
        updateAgeType();
    }
    
    public int getAge(){
        return Age;
    }

    public ArrayList<Integer> getPossibleDiseases(){
        return Disease.getPossibleDiseases();
    }

    public ArrayList<Integer> getInfectedDiseases(){
        return Disease.getInfectedDiseases();
    }
    
    private void updateAgeType() {
        if ((Age >= FertileLimits[1]) && (Age < FertileLimits[0])) {
            ageType = AgeType.ReproductiveAdult;

        } else if (Age >= FertileLimits[0]) {
            ageType = AgeType.Elderly;}

        else {
             ageType = AgeType.Child;
        }
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
