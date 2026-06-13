package Rules;

import Data.Config;
import Interfaces.*;
import Models.Agent;
import Models.NormalAgentBehavior;
import Models.Space;

import java.util.ArrayList;

public class Emigration {
    public static void emigrate(IAgent_Emigration agent, ISpaceProvider space) {
        IPatch_Emigration[][] patches = space.getPatches();

        int x = agent.getX();
        int y = agent.getY();
        //---[creating vars]--
        double welfare, w1, w2;
        double maxWelfare = Double.NEGATIVE_INFINITY;
        int bestX = x;
        int bestY = y;
        ArrayList<IPatch_Emigration> sameCondition = new ArrayList<>();
        int distance = Integer.MAX_VALUE;

        for (int i = x - agent.getVision(); i <= x + agent.getVision(); i++) {
            if (i >= 0 && i < Config.SpaceRow && (patches[i][y].getPAgent() == null)) {
                //---[initializing vars]---
                w1 = agent.getASugar() + patches[i][y].getPSugar() - agent.getSugarMetabolism();
                w2 = agent.getASpice() + patches[i][y].getPSpice() - agent.getSpiceMetabolism();
                welfare = agent.getWelfare(w1, w2);
                //---[finding best patch]---
                if (welfare > maxWelfare) {
                    maxWelfare = welfare;
                    distance = Math.abs(x - i);
                    bestX = i;
                    bestY = y;
                    sameCondition.clear();
                    sameCondition.add(patches[i][y]);
                } else if (Math.abs(welfare - maxWelfare) < 1e-6) {
                    if (distance > Math.abs(i - x)) {
                        distance = Math.abs(x - i);
                        bestX = i;
                        bestY = y;
                        sameCondition.clear();
                        sameCondition.add(patches[i][y]);
                    } else if (distance == Math.abs(i - x)) {
                        sameCondition.add(patches[i][y]);
                    }
                }
            }
        }

        for (int j = y - agent.getVision(); j <= y + agent.getVision(); j++) {
            if (j >= 0 && j < patches.length && (patches[x][j].getPAgent() == null)) {
                //---[initializing vars]---
                w1 = agent.getASugar() + patches[x][j].getPSugar() - agent.getSugarMetabolism();
                w2 = agent.getASpice() + patches[x][j].getPSpice() - agent.getSpiceMetabolism();
                welfare = agent.getWelfare(w1, w2);
                //---[finding best patch]---
                if (welfare > maxWelfare) {
                    maxWelfare = welfare;
                    distance = Math.abs(y - j);
                    bestX = x;
                    bestY = j;
                    sameCondition.clear();
                    sameCondition.add(patches[x][j]);
                } else if (Math.abs(welfare - maxWelfare) < 1e-6) {
                    if (distance > Math.abs(j - y)) {
                        distance = Math.abs(y - j);
                        bestX = x;
                        bestY = j;
                        sameCondition.clear();
                        sameCondition.add(patches[x][j]);
                    } else if (distance == Math.abs(j - y)) {
                        sameCondition.add(patches[x][j]);
                    }
                }
            }
        }
        //---[if agent doesn't emigrate]---
        if ((bestX == x && bestY == y) || maxWelfare <= 0) {
            agent.survival((Space) space);
            return;
        }
        //---[if we have same condition patches]---
        if (sameCondition.size() > 1) {
            int index = (int) (Math.random() * sameCondition.size());
            IPatch_Emigration patch = sameCondition.get(index);
            bestX = patch.getPx();
            bestY = patch.getPy();
        }
        //---[agent status after emigration]---
        agent.setX(bestX);
        agent.setY(bestY);
        agent.setASugar(patches[bestX][bestY].getPSugar() + agent.getASugar());
        agent.setASpice(patches[bestX][bestY].getPSpice() + agent.getASpice());
        //---[patches status after emigration]---
        patches[bestX][bestY].setPSugar(0);
        patches[bestX][bestY].setPSpice(0);
        patches[bestX][bestY].setPAgent((Agent) agent);
        patches[x][y].setPAgent(null);
        //---[agent survival after emigration]---
        agent.survival((Space) space);

    }

}
