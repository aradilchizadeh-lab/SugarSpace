package Rules;

import Interfaces.IAgent_Aging;
import Interfaces.IIdentity_Aging;
import Interfaces.IPatch_Aging;
import Interfaces.ISpaceProvider;
import Models.Agent;

import java.util.ArrayList;

public class Aging {
    public void ageRule(IAgent_Aging agent, ISpaceProvider space) {
        IPatch_Aging[][] patches = space.getPatches();
        ArrayList<Agent> agents = space.getAgents();

        agent.getIdentity().changeAge();
        //---[reset parent status and checking age status]---
        if (agent.getIdentity().getAge() < agent.getIdentity().getMaxAge()) {
            agent.getIdentity().setParent(false);
        } else {
            agents.remove(agent);
            patches[agent.getIdentity().getX()][agent.getIdentity().getY()].setPAgent(null);
        }
    }
}
