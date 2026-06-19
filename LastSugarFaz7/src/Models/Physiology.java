package Models;

import Data.AgeType;
import Interfaces.Agent.IMetabolismUpdate;

public class Physiology implements IMetabolismUpdate{
    private final int Vision;
    private float SugarMetabolism;
    private float SpiceMetabolism;
    private int Age;
    private final int MaxAge;
    private AgeType ageType;



    public Physiology(int vision, float sugarMetabolism, float spiceMetabolism){
        Vision = vision;
        SugarMetabolism = sugarMetabolism;
        SpiceMetabolism = spiceMetabolism;
        Age = 0;
        MaxAge = (int)(Math.random()*41) + 60;
        ageType = AgeType.Child;

    }

    public int getVision(){
        return Vision;
    }

    public int getMaxAge(){
        return MaxAge;
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

    public int getAge(){
        return Age;
    }
    public void setAge(int age){
        Age = age;
    }

    public AgeType getAgeType() {
        return ageType;
    }

    public void setAgeType(AgeType ageType){
        this.ageType = ageType;
    }

}
