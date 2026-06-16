package Interfaces;

import java.util.ArrayList;

import Data.AgeType;

public interface IAgent_Disease {

    public ArrayList<Integer> getInfectedDiseases();

    public ArrayList<Integer> getPossibleDiseases();

    public IBehavior getBehavior();

}
