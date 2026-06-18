package Interfaces.Rules;

import java.util.ArrayList;

import Interfaces.Agent.IAgent_Disease;
import Interfaces.Patch.IPatch_Disease;

public interface IDisease {

    public void disease(IAgent_Disease agent,IPatch_Disease[][] patches, ArrayList<Integer> diseases);

    public ArrayList<Integer> getPossibleDiseases();

    public ArrayList<Integer> getInfectedDiseases();

    public void addInfectedDiseases(int disease);
}
