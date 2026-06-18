package Interfaces;

import java.util.ArrayList;

import Data.AgeType;

public interface IAgent_Disease extends IPositionView, IMetabolismUpdate{

    public ArrayList<Integer> getInfectedDiseases();

    public ArrayList<Integer> getPossibleDiseases();

    public IBehavior_Ability getBehavior();
}
