package Interfaces;

import Models.Agent;
import Models.Patch;

import java.util.ArrayList;

public interface ISpaceProvider {

    public Patch[][] getPatches();

    public ArrayList<Agent> getAgents();

}
