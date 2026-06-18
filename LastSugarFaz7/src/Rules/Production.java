package Rules;

import Data.Config;
import Interfaces.IFactoryModels;
import Interfaces.Agent.IAgent_Production;
import Interfaces.Patch.IPatch_Production;
import Interfaces.Rules.IProduction;
import Models.Agent;

import java.util.ArrayList;

public class Production implements IProduction{

    public void production(IAgent_Production agent, IPatch_Production[][] patches, ArrayList<Agent> agents ) {
        if (!agent.canBeParent())
            return;

        int agentX = agent.getPosition().getX();
        int agentY = agent.getPosition().getY();

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
        boolean neighborFlag = false;
        boolean emptyPatchFlag = false;
        IPatch_Production babyPatch = null;
        IAgent_Production neighborAgent = null;

        while (!selectedPatches.isEmpty()) {
            //---[break when we found neighbor and free patch]---
            if (neighborFlag && emptyPatchFlag)
                break;
            //---[getting random patch]---
            int index = (int) (Math.random() * selectedPatches.size());
            IPatch_Production randomPatch = selectedPatches.get(index);
            //---[checking random patch if its free]---
            if (randomPatch.getPAgent() == null && !emptyPatchFlag) {
                babyPatch = randomPatch;
                emptyPatchFlag = true;
            } else if (randomPatch.getPAgent() != null && !neighborFlag) {
                if (randomPatch.getPAgent().getBehavior().canProduce()) {
                    IAgent_Production neighbor = randomPatch.getPAgent();
                    if (neighbor.canBeParent() && randomPatch.getPAgent().getFertilityInfo().getGender() != agent.getFertilityInfo().getGender()) {
                        neighborAgent = neighbor;
                        neighborFlag = true;
                    }
                }
            }
            selectedPatches.remove(index);
        }//end while

        //---[checking if we have baby condition]---
        if (neighborFlag && emptyPatchFlag) {
            //---[initializing baby values]---
            int babyX = babyPatch.getPosition().getX();
            int babyY = babyPatch.getPosition().getY();

            int bSuger = Math.round(agent.getWallet().getInitSugar() / 2 + neighborAgent.getWallet().getInitSugar() / 2);
            int bSpice = Math.round(agent.getWallet().getInitSpice() / 2 + neighborAgent.getWallet().getInitSpice() / 2);
            int bSuMetabolism = Math.round(agent.getPhysiology().getSugarMetabolism() / 2 + neighborAgent.getPhysiology().getSugarMetabolism() / 2);
            int bSpMetabolism = Math.round(agent.getPhysiology().getSpiceMetabolism() / 2 + neighborAgent.getPhysiology().getSpiceMetabolism() / 2);
            int bVision = Math.round(agent.getPhysiology().getVision() / 2 + neighborAgent.getPhysiology().getVision() / 2);
            //---[creating baby]---
            Agent baby = IFactoryModels.childCreator(babyX, babyY, bSuger, bSpice, bVision, bSuMetabolism, bSpMetabolism);
            agents.add(baby);
            //---[parents status initializing]---
            agent.getFertilityInfo().setParent(true);
            neighborAgent.getFertilityInfo().setParent(true);
            agent.reproductionInherit();
            neighborAgent.reproductionInherit();
            babyPatch.setPAgent(baby);
        }
    }
}






