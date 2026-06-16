package Rules;

import Data.Config;
import Interfaces.IAgent_Prodution;
import Interfaces.IFactoryModels;
import Interfaces.IPatch_Production;
import Interfaces.ISpaceProvider;
import Models.Agent;

import java.util.ArrayList;

public class Production {
    public void production(IAgent_Prodution agent, ISpaceProvider space) {
        IPatch_Production[][] patches = space.getPatches();
        ArrayList<Agent> agents = space.getAgents();
        if (!agent.canBeParent())
            return;

        int agentX = agent.getX();
        int agentY = agent.getY();

        ArrayList<IPatch_Production> selectedPatches = new ArrayList<>();
        //---[all patches around agent]---
        for (int i = agentX - 1; i <= agentX + 1; ++i) {
            if (i >= 0 && i < Config.SpaceRow) {
                for (int j = agentY - 1; j <= agentY + 1; ++j) {
                    if (j >= 0 && j < Config.SpaceCol) {
                        selectedPatches.add(patches[i][j]);
                    }
                }
            }
        }
        //---[bools to check if we have found neighbor and free patch for reproduction]---
        boolean neghiborFlag = false;
        boolean emptyPatchFlag = false;
        IPatch_Production babyPatch = null;
        IAgent_Prodution neighborAgent = null;

        while (!selectedPatches.isEmpty()) {
            //---[break when we found neighbor and free patch]---
            if (neghiborFlag && emptyPatchFlag)
                break;
            //---[getting random patch]---
            int index = (int) (Math.random() * selectedPatches.size());
            IPatch_Production randomPatch = selectedPatches.get(index);
            //---[checking random patch if its free]---
            if (randomPatch.getPAgent() == null && !emptyPatchFlag) {
                babyPatch = randomPatch;
                emptyPatchFlag = true;
            } else if (randomPatch.getPAgent() != null && !neghiborFlag) {
                if (randomPatch.getPAgent().getBehavior().canProduce()) {
                    IAgent_Prodution neighbor = randomPatch.getPAgent();
                    if (neighbor.canBeParent() && randomPatch.getPAgent().getGender() != agent.getGender()) {
                        neighborAgent = neighbor;
                        neghiborFlag = true;
                    }
                }
            }
            selectedPatches.remove(index);
        }//end while

        //---[checking if we have baby condition]---
        if (neghiborFlag && emptyPatchFlag) {
            //---[initializing baby values]---
            int babyX = babyPatch.getPx();
            int babyY = babyPatch.getPy();

            int bSuger = Math.round(agent.getInitSugar() / 2 + neighborAgent.getInitSugar() / 2);
            int bSpice = Math.round(agent.getInitSpice() / 2 + neighborAgent.getInitSpice() / 2);
            int bSuMetabolism = Math.round(agent.getSugarMetabolism() / 2 + neighborAgent.getSugarMetabolism() / 2);
            int bSpMetabolism = Math.round(agent.getSpiceMetabolism() / 2 + neighborAgent.getSpiceMetabolism() / 2);
            int bVision = Math.round(agent.getVision() / 2 + neighborAgent.getVision() / 2);
            //---[creating baby]---
            Agent baby = IFactoryModels.childCreator(babyX, babyY, bSuger, bSpice, bVision, bSuMetabolism, bSpMetabolism);
            agents.add(baby);
            //---[parents status initializing]---
            agent.setParent(true);
            neighborAgent.setParent(true);
            agent.reproductionInherit();
            neighborAgent.reproductionInherit();
            babyPatch.setPAgent(baby);
        }
    }
}






