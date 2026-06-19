package Rules;

import Interfaces.Agent.IAgent_Aging;
import Interfaces.Patch.IPatch_Aging;
import Interfaces.Rules.IAging;
import Models.Agent;

import java.util.ArrayList;

public class Aging implements IAging{
    public void ageRule(IAgent_Aging agent, IPatch_Aging[][] patches, ArrayList<Agent> agents) {
        agent.changeAge();
        //---[reset parent status and checking age status]---
        if (agent.getPhysiology().getAge() < agent.getPhysiology().getMaxAge()) {
            agent.getFertilityInfo().setParent(false);
        } else {
            for (int i = agent.getLoanInfos().size() - 1; i >= 0; i--) {
                if (agent.getLoanInfos().get(i).getLender() == agent) {
                    agent.getLoanInfos().get(i).setStatus(false);

                } else if (agent.getLoanInfos().get(i).getBorrower() == agent) {
                    agent.getLoanInfos().get(i).setStatus(false);
                }
            }
            agents.remove((Agent)agent);
            patches[agent.getPosition().getX()][agent.getPosition().getY()].setPAgent(null);
        }
    }
}
