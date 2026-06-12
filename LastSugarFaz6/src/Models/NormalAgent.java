package Models;
import Data.AgeType;
import Data.LoanInfoList;
import Interfaces.*;


public class NormalAgent extends Agent implements IAgent_Emigration, IAgent_Prodution, IAgent_Paint, IAgent_Trade, IAgent_Histogram, IAgent_Loan
 {
    private final int[] FertileLimits;
    private boolean IsParent;
    private AgeType AgeType;

    public NormalAgent(int x, int y, int InitSugar, int InitSpice, int Vision, float SugarMetabolism, float SpiceMetabolism){
        super(x, y, InitSugar, InitSpice, Vision, SugarMetabolism, SpiceMetabolism, (int)(Math.random() * 41) + 60, (int)(Math.random() * 2));
        IsParent = false;
        FertileLimits = new int[2];
        FertileLimits[0] = (int)(Math.random() * 16) + 45; //max
        FertileLimits[1] = (int)(Math.random() * 3) + 15; //min
        AgeType = AgeType.Child;
    }

    public void setParent(boolean IsParent){
        this.IsParent = IsParent;
    }

    public boolean isParent(){
        return IsParent;
    }

    public int getFertileLimitMax(){
        return FertileLimits[0];
    }

    @Override
    public void survival(Space space) {
        ASugar -= SugarMetabolism;
        ASpice -= SpiceMetabolism;

        if (ASugar <= 0 || ASpice <= 0) {
            for (int i = LoanInfoList.loanInfos.size() - 1; i>= 0; i--) {
                if (LoanInfoList.loanInfos.get(i).getLender() == this) {
                    LoanInfoList.loanInfos.remove(i);
                }
                else if (LoanInfoList.loanInfos.get(i).getBorrower() == this) {
                    LoanInfoList.loanInfos.remove(i);
                }
            }
            space.agents.remove(this);
            space.patches[this.getX()][this.getY()].setPAgent(null);
        }

    }

     public void setX(int x){
         Ax = x;
     }

     public void setY(int y){
         Ay = y;
     }

    public void reproductionInherit(){
        ASugar -= Math.round(InitSugar / 2);
        ASpice -= Math.round(InitSpice / 2);
    }
    //except gender
    public boolean canBeParent(){
        if (Age > FertileLimits[1] && Age < FertileLimits[0] && ASugar >= InitSugar && ASpice >= InitSpice && !(this.IsParent))
            return true;

        return false;
    }

    public  double getWelfare(double w1, double w2){
        double m1 = SugarMetabolism;
        double m2 = SpiceMetabolism;
        double mT = m1 + m2;

        return Math.pow(w1, m1 / mT) * Math.pow(w2, m2 / mT);
    }

     public  double getMRS(double w1, double w2){
         double m1 = SugarMetabolism;
         double m2 = SpiceMetabolism;

         return (m1*w2)/(m2*w1);
     }

     @Override
     public void changeAge(){
        this.Age++;
        updateAgeType();
     }



     public AgeType getAgeType() {
         return AgeType;
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

     public boolean canBeLender() {

         if (ASugar > 5*SugarMetabolism || ASpice > 5*SpiceMetabolism)
             return true;

         return false;

     }

     public int requireSpiceAmount() {

         if (AgeType == AgeType.ReproductiveAdult)
             return InitSpice - ASpice;

         if (AgeType == AgeType.Elderly)
             return (int) (SpiceMetabolism * 2);

         if (AgeType == AgeType.Child )
             return InitSpice - ASpice;

         return 0;
     }

     public int requireSugarAmount() {

         if (AgeType == AgeType.ReproductiveAdult )
             return InitSugar - ASugar;

         if (AgeType == AgeType.Elderly)
             return (int) (SugarMetabolism * 2);

         if (AgeType == AgeType.Child)
             return InitSugar - ASugar;

         return 0;
     }

     public boolean needsSpice(){

         if (AgeType == AgeType.ReproductiveAdult && (ASpice < InitSpice))
             return true;
         if (AgeType == AgeType.Elderly && (ASpice < SpiceMetabolism))
             return true;
         if (AgeType == AgeType.Child && (ASpice < InitSpice))
             return true;

         return false;
     }


     public boolean needsSugar(){

         if (AgeType == AgeType.ReproductiveAdult && (ASugar < InitSugar))
             return true;
         if (AgeType == AgeType.Elderly && (ASugar <= SugarMetabolism))
             return true;
         if (AgeType == AgeType.Child && (ASugar < InitSugar))
             return true;

         return false;
     }
 }
