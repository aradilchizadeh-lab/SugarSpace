package Rules;

import Interfaces.IAgent_Aging;
import Interfaces.IPatch_Aging;
import Interfaces.ISpaceProvider;
import Models.Agent;

import java.util.ArrayList;

public class Aging {
    public static void ageRule(ISpaceProvider space) {
        IPatch_Aging[][] patches = space.getPatches();
        ArrayList<Agent> agents = space.getAgents();

        for (int i = agents.size() - 1; i >= 0; --i) {
            if (agents.get(i) instanceof IAgent_Aging agent) {
                agent.changeAge();
                //---[reset parent status and checking age status]---
                if (agent.getAge() < agent.getMaxAge()) {
                    agent.setParent(false);
                } else {
                    agents.remove(i);
                    patches[agent.getX()][agent.getY()].setPAgent(null);
                }
            }
        }
    }
}
