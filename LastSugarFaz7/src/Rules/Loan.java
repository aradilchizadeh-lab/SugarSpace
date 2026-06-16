package Rules;

import Data.AgeType;
import Data.Config;
import Data.ResourceType;
import Interfaces.IAgent_Loan;
import Interfaces.IFactoryModels;
import Interfaces.IPatch_AgentProvider;
import Interfaces.ISpaceWithTickProvider;
import Models.*;

import java.util.ArrayList;

public class Loan {
    public void loan(IAgent_Loan agent, ISpaceWithTickProvider space) {
        IPatch_AgentProvider[][] patches = space.getPatches();
        debtPayment(space, agent);
        if (agent.canBeLender()) {
            giveLoan(space, agent, patches);
        }
    }


    private static void giveLoan(ISpaceWithTickProvider space, IAgent_Loan a, IPatch_AgentProvider[][] patches) {
        ArrayList<IAgent_Loan> neighbors = new ArrayList<>();
        int x = a.getX();
        int y = a.getY();
        //---[adding neighbors]---
        for (int i = x - 1; i <= x + 1; ++i) {
            //---[checking if we are in the space]---
            if (i < 0 || i > Config.SpaceRow - 1)
                continue;
            for (int j = y - 1; j <= y + 1; ++j) {
                if (j < 0 || j > Config.SpaceCol - 1 || (i == x && j == y))
                    continue;
                //---[checking if we have valid patch]---
                if (patches[i][j].getPAgent() != null && patches[i][j].getPAgent().getBehavior().canLoan() && (i == x || j == y)) {
                    IAgent_Loan neighbor = patches[i][j].getPAgent();
                    //---[checking if neighbor needs loan]---
                    if (!neighbor.canBeLender() && (neighbor.needsSugar() || neighbor.needsSpice()))
                        neighbors.add(neighbor);
                }
            }
        }

        ArrayList<IAgent_Loan> sameCondition = new ArrayList<>();
        AgeType type = AgeType.ReproductiveAdult;
        sameCondition.clear();

        //---[adding neighbors by priority]---
        if (neighbors.isEmpty()) return;
        while (true) {
            for (int k = 0; k < neighbors.size(); k++) {
                if (neighbors.get(k).getAgeType() == type) {
                    sameCondition.add(neighbors.get(k));
                }
            }
            if (!sameCondition.isEmpty()) {
                break;
            }
            if (sameCondition.isEmpty() && type == AgeType.ReproductiveAdult) {
                type = AgeType.Elderly;
                continue;
            }
            if (sameCondition.isEmpty() && type == AgeType.Elderly) {
                type = AgeType.Child;
            }
        }
        int amount;
        //---[selecting random neighbor]---
        int index = (int) (Math.random() * sameCondition.size());
        IAgent_Loan neighbor = sameCondition.get(index);

        //---[checking status of resource for the payment for both agent and neighbor]---
        if (a.getASpice() > a.getSpiceMetabolism() * 5 && neighbor.needsSpice()) {
            //---[initializing amount by priority of need and can give]---
            amount = (int) Math.min(a.getASpice() - a.getSpiceMetabolism() * 5, neighbor.requiredSpiceAmount());
            //---[adding info to list]---
            a.getLoanInfos().add(IFactoryModels.loanInfoCreator(a, neighbor, ResourceType.Spice, amount, space.getTick()));
            //---[payment]---
            neighbor.setASpice(neighbor.getASpice() + amount);
            a.setASpice(a.getASpice() - amount);
        }
        //---[checking status of resource for the payment for both agent and neighbor]---
        if (a.getASugar() > a.getSugarMetabolism() * 5 && neighbor.needsSugar()) {
            //---[initializing amount by priority of need and can give]---
            amount = (int) Math.min(a.getASugar() - a.getSugarMetabolism() * 5, neighbor.requiredSugarAmount());
            //---[adding info to list]---
            a.getLoanInfos().add(IFactoryModels.loanInfoCreator(a, neighbor, ResourceType.Sugar, amount, space.getTick()));
            //---[payment]---
            neighbor.setASugar(neighbor.getASugar() + amount);
            a.setASugar(a.getASugar() - amount);
        }
    }

    public static void debtPayment(ISpaceWithTickProvider space, IAgent_Loan agent) {
        int debtAmount = 0;
        for (int i = agent.getLoanInfos().size() - 1; i >= 0; i--) {
            //---[checking if our agent is in the borrowers and if we are in the payment tick]---
            if (agent.getLoanInfos().get(i).getBorrower() == agent && agent.getLoanInfos().get(i).getLoanTick() + Config.NumberTickLoan == space.getTick()) {
                LoanInfo info = agent.getLoanInfos().get(i);
                //---[initializing debt amount]---
                debtAmount = info.getAmount();

                int borrowerWealth = getResource(info.getBorrower(), info.getResourceType());
                int lenderWealth = getResource(info.getLender(), info.getResourceType());
                if (borrowerWealth > debtAmount) {
                    setResource(info.getBorrower(), info.getResourceType(), borrowerWealth - debtAmount);
                    setResource(info.getLender(), info.getResourceType(), lenderWealth + debtAmount);
                    agent.getLoanInfos().remove(i);
                } else {

                    int halfWealth = borrowerWealth / 2;
                    setResource(info.getBorrower(), info.getResourceType(), halfWealth);
                    setResource(info.getLender(), info.getResourceType(), lenderWealth + halfWealth);
                    info.setAmount((debtAmount - halfWealth));
                    info.setLoanTick(info.getLoanTick() + Config.NumberTickLoan);
                }

            }
        }
    }

    public static int getResource(IAgent_Loan agent, ResourceType type) {
        if (ResourceType.Spice == type) {
            return agent.getASpice();
        }
        return agent.getASugar();
    }

    public static void setResource(IAgent_Loan agent, ResourceType type, int value) {
        if (ResourceType.Spice == type) {
            agent.setASpice(value);
        } else {
            agent.setASugar(value);
        }
    }
}
