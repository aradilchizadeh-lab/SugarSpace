package Interfaces;

import Models.Agent;
import Models.Patch;

import java.util.ArrayList;

public interface IBehavior extends IBehavior_Ability{

    public void survival(Agent agent, ArrayList<Agent> agents, Patch[][] patches);

    public void reproductionInherit(Agent agent);

    public boolean canBeParent(Agent agent);

    public  double getWelfare(Agent agent, double w1, double w2);

    public  double getMRS(Agent agent, double w1, double w2);

    public boolean canBeLender(Agent agent);

    public int requiredSpiceAmount(Agent agent);

    public int requiredSugarAmount(Agent agent);

    public boolean needsSpice(Agent agent);

    public boolean needsSugar(Agent agent);

}
