package Rules;
import Data.Config;

import Interfaces.IAgent_Trade;
import Interfaces.IPatch_Trade;
import Interfaces.ISpaceProvider;
import Models.Agent;

import java.util.ArrayList;
import java.util.Collections;

public class Trade {
    public  void trade(ISpaceProvider space) {
        ArrayList<Agent> agents = space.getAgents();
        IPatch_Trade[][] patches = space.getPatches();

        for (int k = space.getAgents().size() - 1; k >= 0; k--) {
            if (agents.get(k) instanceof IAgent_Trade agent) {
                ArrayList<IAgent_Trade> neighborAgents = new ArrayList<>();
                addNeighbor(agent, patches, neighborAgents);
                if (!neighborAgents.isEmpty())
                    trading(agent, neighborAgents);
            }
        }
    }

    private static void addNeighbor(IAgent_Trade a, IPatch_Trade[][] patchs, ArrayList<IAgent_Trade> neighbor) {
        int x = a.getX();
        int y = a.getY();
        for (int i = x - 1; i <= x + 1; ++i)
        {
            if (i < 0 || i > Config.SpaceRow - 1)
                continue;
            for(int j = y - 1; j <= y + 1; ++j) {
                if (j < 0 || j > Config.SpaceCol - 1 || (i == x && j == y))
                    continue;

                if (patchs[i][j].getPAgent() != null && patchs[i][j].getPAgent() instanceof IAgent_Trade && (i == x || j == y))
                    neighbor.add((IAgent_Trade) patchs[i][j].getPAgent());
            }
        }
    }

    private static void trading(IAgent_Trade agent, ArrayList<IAgent_Trade> neighbors){
        //---[MRS calc]---
        double MRS_Agent, MRS_Neighbor, newSugarAgentHigh = 0, newSugarAgentLow = 0, newSpiceAgentHigh = 0, newSpiceAgentLow = 0;
        IAgent_Trade AgentMRS_High, AgentMRS_Low;
        float P;

        boolean tradeOccurred = true;

        while (tradeOccurred) {
            tradeOccurred = false;
            //---[randomizing neighbor]---
            Collections.shuffle(neighbors);

            for (IAgent_Trade neighborAgent : neighbors) {
                boolean valid = false;

                MRS_Agent = agent.getMRS(agent.getASugar(), agent.getASpice());
                MRS_Neighbor = neighborAgent.getMRS(neighborAgent.getASugar(), neighborAgent.getASpice());

                if (Math.abs(MRS_Agent - MRS_Neighbor) <= 1e-6) {
                    continue;
                }

                P = (float) Math.sqrt(MRS_Agent * MRS_Neighbor);

                if (MRS_Agent > MRS_Neighbor) {
                    AgentMRS_High = agent;
                    AgentMRS_Low = neighborAgent;
                } else {
                    AgentMRS_High = neighborAgent;
                    AgentMRS_Low = agent;
                }
                //---[status of P]---
                if (P >= 1) {
                    newSugarAgentHigh = AgentMRS_High.getASugar() + 1;
                    newSpiceAgentHigh = AgentMRS_High.getASpice() - P;
                    newSugarAgentLow = AgentMRS_Low.getASugar() - 1;
                    newSpiceAgentLow = AgentMRS_Low.getASpice() + P;

                    valid = isValid(
                            AgentMRS_High, AgentMRS_Low,
                            newSugarAgentHigh, newSugarAgentLow,
                            newSpiceAgentHigh, newSpiceAgentLow
                    );
                } else if (P > 0) {
                    newSugarAgentHigh = AgentMRS_High.getASugar() + 1 / P;
                    newSpiceAgentHigh = AgentMRS_High.getASpice() - 1;
                    newSugarAgentLow = AgentMRS_Low.getASugar() - 1 / P;
                    newSpiceAgentLow = AgentMRS_Low.getASpice() + 1;

                    valid = isValid(
                            AgentMRS_High, AgentMRS_Low,
                            newSugarAgentHigh, newSugarAgentLow,
                            newSpiceAgentHigh, newSpiceAgentLow
                    );
                }
                //---[trading if the trade is valid]---
                if (valid) {
                    AgentMRS_High.setASugar((int) newSugarAgentHigh);
                    AgentMRS_Low.setASugar((int) newSugarAgentLow);
                    AgentMRS_High.setASpice((int) newSpiceAgentHigh);
                    AgentMRS_Low.setASpice((int) newSpiceAgentLow);

                    tradeOccurred = true;
                }
            }
        }
    }


    private static boolean isValid(IAgent_Trade AgentMRS_High, IAgent_Trade AgentMRS_Low, double newSugarAgentHigh, double newSugarAgentLow, double newSpiceAgentHigh, double newSpiceAgentLow){
        double WelfareAgentHigh_Old, WelfareAgentHigh_New, WelfareAgentLow_Old, WelfareAgentLow_New ,AgentHigh_NewMRS, AgentLow_NewMRS;

        WelfareAgentHigh_Old = AgentMRS_High.getWelfare(AgentMRS_High.getASugar(), AgentMRS_High.getASpice());
        WelfareAgentLow_Old = AgentMRS_Low.getWelfare(AgentMRS_Low.getASugar(), AgentMRS_Low.getASpice());
        WelfareAgentHigh_New = AgentMRS_High.getWelfare(newSugarAgentHigh, newSpiceAgentHigh);
        WelfareAgentLow_New = AgentMRS_Low.getWelfare(newSugarAgentLow, newSpiceAgentLow);

        if(WelfareAgentHigh_Old < WelfareAgentHigh_New && WelfareAgentLow_Old < WelfareAgentLow_New){
            AgentHigh_NewMRS = AgentMRS_High.getMRS(newSugarAgentHigh, newSpiceAgentHigh);
            AgentLow_NewMRS = AgentMRS_Low.getMRS(newSugarAgentLow, newSpiceAgentLow);

            if(AgentLow_NewMRS < AgentHigh_NewMRS)
                return true;
        }
        return false;
    }
}