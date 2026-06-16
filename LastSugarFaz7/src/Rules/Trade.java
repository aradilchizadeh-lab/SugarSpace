package Rules;
import Data.Config;

import Interfaces.IAgent_Trade;
import Interfaces.IPatch_AgentProvider;
import Interfaces.IPatch_AgentProvider;
import Interfaces.ISpaceProvider;
import Models.Agent;

import java.util.ArrayList;
import java.util.Collections;

public class Trade {
    public void trade(IAgent_Trade agent, ISpaceProvider space) {
        IPatch_AgentProvider[][] patches = space.getPatches();

        ArrayList<IAgent_Trade> neighborAgents = new ArrayList<>();
        addNeighbor(agent, patches, neighborAgents);
        if (!neighborAgents.isEmpty())
            trading(agent, neighborAgents);
    }

    private static void addNeighbor(IAgent_Trade agent, IPatch_AgentProvider[][] patches, ArrayList<IAgent_Trade> neighbor) {
        int x = agent.getIdentity().getX();
        int y = agent.getIdentity().getY();
        for (int i = x - 1; i <= x + 1; ++i) {
            if (i < 0 || i > Config.SpaceRow - 1)
                continue;
            for (int j = y - 1; j <= y + 1; ++j) {
                if (j < 0 || j > Config.SpaceCol - 1 || (i == x && j == y))
                    continue;

                if (patches[i][j].getPAgent() != null && patches[i][j].getPAgent().getIdentity().canTrade() && (i == x || j == y))
                    neighbor.add((IAgent_Trade) patches[i][j].getPAgent());
            }
        }
    }

    private static void trading(IAgent_Trade agent, ArrayList<IAgent_Trade> neighbors) {
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

                MRS_Agent = agent.getMRS(agent.getWallet().getASugar(), agent.getWallet().getASpice());
                MRS_Neighbor = neighborAgent.getMRS(neighborAgent.getWallet().getASugar(), neighborAgent.getWallet().getASpice());

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
                    newSugarAgentHigh = AgentMRS_High.getWallet().getASugar() + 1;
                    newSpiceAgentHigh = AgentMRS_High.getWallet().getASpice() - P;
                    newSugarAgentLow = AgentMRS_Low.getWallet().getASugar() - 1;
                    newSpiceAgentLow = AgentMRS_Low.getWallet().getASpice() + P;

                    valid = isValid(
                            AgentMRS_High, AgentMRS_Low,
                            newSugarAgentHigh, newSugarAgentLow,
                            newSpiceAgentHigh, newSpiceAgentLow
                    );
                } else if (P > 0) {
                    newSugarAgentHigh = AgentMRS_High.getWallet().getASugar() + 1 / P;
                    newSpiceAgentHigh = AgentMRS_High.getWallet().getASpice() - 1;
                    newSugarAgentLow = AgentMRS_Low.getWallet().getASugar() - 1 / P;
                    newSpiceAgentLow = AgentMRS_Low.getWallet().getASpice() + 1;

                    valid = isValid(
                            AgentMRS_High, AgentMRS_Low,
                            newSugarAgentHigh, newSugarAgentLow,
                            newSpiceAgentHigh, newSpiceAgentLow
                    );
                }
                //---[trading if the trade is valid]---
                if (valid) {
                    AgentMRS_High.getWallet().setASugar((int) newSugarAgentHigh);
                    AgentMRS_Low.getWallet().setASugar((int) newSugarAgentLow);
                    AgentMRS_High.getWallet().setASpice((int) newSpiceAgentHigh);
                    AgentMRS_Low.getWallet().setASpice((int) newSpiceAgentLow);

                    tradeOccurred = true;
                }
            }
        }
    }


    private static boolean isValid(IAgent_Trade AgentMRS_High, IAgent_Trade AgentMRS_Low, double newSugarAgentHigh, double newSugarAgentLow, double newSpiceAgentHigh, double newSpiceAgentLow) {
        double WelfareAgentHigh_Old, WelfareAgentHigh_New, WelfareAgentLow_Old, WelfareAgentLow_New, AgentHigh_NewMRS, AgentLow_NewMRS;

        WelfareAgentHigh_Old = AgentMRS_High.getWelfare(AgentMRS_High.getWallet().getASugar(), AgentMRS_High.getWallet().getASpice());
        WelfareAgentLow_Old = AgentMRS_Low.getWelfare(AgentMRS_Low.getWallet().getASugar(), AgentMRS_Low.getWallet().getASpice());
        WelfareAgentHigh_New = AgentMRS_High.getWelfare(newSugarAgentHigh, newSpiceAgentHigh);
        WelfareAgentLow_New = AgentMRS_Low.getWelfare(newSugarAgentLow, newSpiceAgentLow);

        if (WelfareAgentHigh_Old < WelfareAgentHigh_New && WelfareAgentLow_Old < WelfareAgentLow_New) {
            AgentHigh_NewMRS = AgentMRS_High.getMRS(newSugarAgentHigh, newSpiceAgentHigh);
            AgentLow_NewMRS = AgentMRS_Low.getMRS(newSugarAgentLow, newSpiceAgentLow);

            if (AgentLow_NewMRS < AgentHigh_NewMRS)
                return true;
        }
        return false;
    }
}