package Interfaces.Agent;

import java.util.ArrayList;

import Interfaces.IPositionView;

public interface IAgent_Disease extends IMetabolismUpdate{

    public IPositionView getPosition();

    public ArrayList<Integer> getInfectedDiseases();

    public ArrayList<Integer> getPossibleDiseases();

    public IBehavior_Ability getBehavior();
}
