package Interfaces;

import java.util.ArrayList;

import Data.AgeType;

public interface IAgent_Disease {
    public int getX();

    public int getY();

    public float getSugarMetabolism();

    public void setSugarMetabolism(float sugar);

    public float getSpiceMetabolism();

    public void setSpiceMetabolism(float spice);

    public ArrayList<Integer> getInfectedDiseases();

    public ArrayList<Integer> getPossibleDiseases();

    public IBehavior_Ability getBehavior();

    public AgeType getAgeType();

    public int getAge();
}
