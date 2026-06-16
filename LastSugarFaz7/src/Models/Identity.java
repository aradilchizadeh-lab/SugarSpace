package Models;

import Data.AgeType;
import Interfaces.ISpaceProvider;

import java.util.ArrayList;

public class Identity {
    private int Ax;
    private int Ay;
    private final int Vision;
    private final int Gender;
    private final int MaxAge;
    private int Age;
    private final int[] FertileLimits;
    private boolean IsParent;
    private AgeType ageType;

    public Identity(int x, int y){
        Ax = x;
        Ay = y;
        Vision = (int)(Math.random() * 10) + 1;
        Age = 0;
        MaxAge = (int)(Math.random()*41) + 60;
        Gender = (int)(Math.random()*2);
        IsParent = false;
        FertileLimits = new int[2];
        FertileLimits[0] = (int)(Math.random() * 16) + 45; //max
        FertileLimits[1] = (int)(Math.random() * 3) + 15; //min
        ageType = AgeType.Child;
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

    public int getVision(){
        return Vision;
    }

    public int getMaxAge(){
        return MaxAge;
    }

    public int getGender(){
        return Gender;
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

    public void changeAge(){
        Age++;
        updateAgeType();
    }

    public int getAge(){
        return Age;
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
}
