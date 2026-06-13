package Models;

import Data.AgeType;
import Interfaces.IAgent;
import Interfaces.IAgent_Aging;
import Interfaces.IAgent_Emigration;
import Interfaces.IAgent_Histogram;
import Interfaces.IAgent_Loan;
import Interfaces.IAgent_Paint;
import Interfaces.IAgent_Prodution;
import Interfaces.IAgent_Trade;
import Interfaces.ISpaceProvider;

public abstract class Agent implements IAgent, IAgent_Aging {
    protected int Ax;
    protected int Ay;
    protected final int InitSugar;
    protected final int InitSpice;
    protected int ASugar;
    protected int ASpice;
    protected final int Vision;
    protected final float SugarMetabolism;
    protected final float SpiceMetabolism;
    protected final int Gender;
    protected final int MaxAge;
    protected int Age;
    private final int[] FertileLimits;
    private boolean IsParent;
    private AgeType AgeType;
    //private IBehavior Behavior;
    
    public Agent(int x, int y, int initSugar, int initSpice, int vision, float sugarMetabolism, float spiceMetabolism, int maxAge, int gender /*,IBehavior behavior*/){
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
        MaxAge = maxAge;
        Gender = gender;
        IsParent = false;
        FertileLimits = new int[2];
        FertileLimits[0] = (int)(Math.random() * 16) + 45; //max
        FertileLimits[1] = (int)(Math.random() * 3) + 15; //min
        AgeType = AgeType.Child;
        //Behavior = behavior
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

    public AgeType getAgeType() {
         return AgeType;
    }

     public void setAgeType(AgeType ageType){
        AgeType = ageType;
    }
     
     public int getMinFertile(){
        return FertileLimits[1];
    }

     public int getMaxFertile(){
        return FertileLimits[0];
    }

     public boolean IsParent(){
        return IsParent;
    }

     public void setParent(boolean parent){
        IsParent = parent;
    }

    /*public IBehavior getBehavior(){
    return Behavior;
}*/
    public void survival(ISpaceProvider space){
        //Behavior.survival(this, space);
    }

    public void changeAge(){
        Age++;
        updateAgeType();
    }
    
    public int getAge(){
        return Age;
    }
    
    private void updateAgeType() {
        if ((Age >= FertileLimits[1]) && (Age < FertileLimits[0])) {
            AgeType = AgeType.ReproductiveAdult;

        } else if (Age >= FertileLimits[0]) {
            AgeType = AgeType.Elderly;}

        else {
             AgeType = AgeType.Child;
        }
    }

    public double getWelfare(double w1, double w2){
        //return Behavior.getWelfare(this, w1, w2);
    }

     public double getMRS(double w1, double w2){
        //return Behavior.getMRS(this, w1, w2);
    }

    public void reproductionInherit(){
        //Behavior.ReproductionInherit(this);
    }

    public boolean canBeParent(){
        //return Behavior.canBeParent(this);
    }

    public boolean canBeLender(){
        //return Behavior.canBeLender(this);
    }

    public int requireSpiceAmount(){
        //return Behavior.requireSpiceAmount(this);
    }

    public int requireSugarAmount(){
        //return Behavior.requireSugarAmount(this);
    }

    public boolean needsSpice(){
        //return Behavior.needsSpice(this);
    }

    public boolean needsSugar() {
        //return Behavior.needsSugar(this);
    }


}
