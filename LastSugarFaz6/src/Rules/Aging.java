package Rules;

import Interfaces.IAgent_Aging;
import Interfaces.IPatch_Aging;
import Interfaces.ISpaceProvider;
import Models.Agent;

import java.util.ArrayList;

public class Aging {
    public static void ageRule(IAgent_Aging agent, ISpaceProvider space) {
        IPatch_Aging[][] patches = space.getPatches();
        ArrayList<Agent> agents = space.getAgents();

        agent.changeAge();
        //---[reset parent status and checking age status]---
        if (agent.getAge() < agent.getMaxAge()) {
            agent.setParent(false);
        } else {
            agents.remove(agent);
            patches[agent.getX()][agent.getY()].setPAgent(null);
        }
    }
}
