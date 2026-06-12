package Rules;

import Data.AgeType;
import Data.Config;
import Data.LoanInfoList;
import Data.ResourceType;
import Interfaces.IAgent_Loan;
import Interfaces.IFactory_LoanInfo;
import Interfaces.IPatch_Loan;
import Interfaces.ISpaceWithTickProvider;
import Models.*;

import java.util.ArrayList;

public class Loan {
    public static void loan(ISpaceWithTickProvider space) {
        IPatch_Loan[][] patches = space.getPatches();
        ArrayList<Agent> agents = space.getAgents();
        for (int i = agents.size() - 1; i >= 0; i--) {
            if (agents.get(i) instanceof IAgent_Loan agent) {
                debtPayment(space, agent);
                if (agent.canBeLender()) {
                    giveLoan(space, agent, patches);
                }
            }
        }
    }


    private static void giveLoan(ISpaceWithTickProvider space, IAgent_Loan a, IPatch_Loan[][] patchs) {
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
                if (patchs[i][j].getPAgent() != null && patchs[i][j].getPAgent() instanceof IAgent_Loan && (i == x || j == y)) {
                    IAgent_Loan neighbor = (IAgent_Loan) patchs[i][j].getPAgent();
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
            amount = (int) Math.min(a.getASpice() - a.getSpiceMetabolism() * 5, neighbor.requireSpiceAmount());
            //---[adding info to list]---
            LoanInfo l = IFactory_LoanInfo.loanInfoCreator(a, neighbor, ResourceType.Spice, amount, space.getTick());
            LoanInfoList.loanInfos.add(l);
            //---[payment]---
            neighbor.setASpice(neighbor.getASpice() + amount);
            a.setASpice(a.getASpice() - amount);
        }
        //---[checking status of resource for the payment for both agent and neighbor]---
        if (a.getASugar() > a.getSugarMetabolism() * 5 && neighbor.needsSugar()) {
            //---[initializing amount by priority of need and can give]---
            amount = (int) Math.min(a.getASugar() - a.getSugarMetabolism() * 5, neighbor.requireSugarAmount());
            //---[adding info to list]---
            LoanInfo l = IFactory_LoanInfo.loanInfoCreator(a, neighbor, ResourceType.Sugar, amount, space.getTick());
            LoanInfoList.loanInfos.add(l);
            //---[payment]---
            neighbor.setASugar(neighbor.getASugar() + amount);
            a.setASugar(a.getASugar() - amount);
        }
    }

    public static void debtPayment(ISpaceWithTickProvider space, IAgent_Loan agent) {
        int debtAmount = 0;
        for (int i = LoanInfoList.loanInfos.size() - 1; i >= 0; i--) {
            //---[checking if our agent is in the borrowers and if we are in the payment tick]---
            if (LoanInfoList.loanInfos.get(i).getBorrower() == agent && LoanInfoList.loanInfos.get(i).getLoanTick() + Config.NumberTickLoan == space.getTick()) {
                LoanInfo info = LoanInfoList.loanInfos.get(i);
                //---[initializing debt amount]---
                debtAmount = info.getAmount();

                int borrowerWealth = getResource(info.getBorrower(), info.getResourceType());
                int lenderWealth = getResource(info.getLender(), info.getResourceType());
                if(borrowerWealth > debtAmount) {
                    setResource(info.getBorrower(), info.getResourceType(), borrowerWealth - debtAmount);
                    setResource(info.getLender(), info.getResourceType(), lenderWealth + debtAmount);
                    LoanInfoList.loanInfos.remove(i);
                }
                else{

                    int halfWealth = borrowerWealth / 2;
                    setResource(info.getBorrower(), info.getResourceType(), halfWealth);
                    setResource(info.getLender(), info.getResourceType(), lenderWealth + halfWealth);
                    info.setAmount((debtAmount - halfWealth));
                    info.setLoanTick(info.getLoanTick() + Config.NumberTickLoan);
                }


                //---[checking debt type]---
//                if (LoanInfoList.loanInfos.get(i).getResourceType() == ResourceType.Spice) {
                    //---[checking wealth status and doing the payment]---
//                    if (agent.getASpice() > debtAmount) {
//                        LoanInfoList.loanInfos.get(i).getBorrower().setASpice(LoanInfoList.loanInfos.get(i).getBorrower().getASpice() - debtAmount);
//                        LoanInfoList.loanInfos.get(i).getLender().setASpice(LoanInfoList.loanInfos.get(i).getLender().getASpice() + debtAmount);
//                        LoanInfoList.loanInfos.remove(i);
//                    } else {
//                        LoanInfoList.loanInfos.get(i).setAmount((debtAmount - LoanInfoList.loanInfos.get(i).getBorrower().getASpice() / 2) / (Config.Interest + 1));
//                        LoanInfoList.loanInfos.get(i).getLender().setASpice(LoanInfoList.loanInfos.get(i).getLender().getASpice() + LoanInfoList.loanInfos.get(i).getBorrower().getASpice() / 2);
//                        LoanInfoList.loanInfos.get(i).getBorrower().setASpice(LoanInfoList.loanInfos.get(i).getBorrower().getASpice() / 2);
//                        LoanInfoList.loanInfos.get(i).setLoanTick(LoanInfoList.loanInfos.get(i).getLoanTick() + Config.NumberTickLoan);
//                    }
//                } else {
//                    if (agent.getASugar() > debtAmount) {
//                        LoanInfoList.loanInfos.get(i).getBorrower().setASugar(LoanInfoList.loanInfos.get(i).getBorrower().getASugar() - debtAmount);
//                        LoanInfoList.loanInfos.get(i).getLender().setASugar(LoanInfoList.loanInfos.get(i).getLender().getASugar() + debtAmount);
//                        LoanInfoList.loanInfos.remove(i);
//                    } else {
//                        LoanInfoList.loanInfos.get(i).setAmount((debtAmount - LoanInfoList.loanInfos.get(i).getBorrower().getASugar() / 2) / (Config.Interest + 1));
//                        LoanInfoList.loanInfos.get(i).getLender().setASugar(LoanInfoList.loanInfos.get(i).getLender().getASugar() + LoanInfoList.loanInfos.get(i).getBorrower().getASugar() / 2);
//                        LoanInfoList.loanInfos.get(i).getBorrower().setASugar(LoanInfoList.loanInfos.get(i).getBorrower().getASugar() / 2);
//                        LoanInfoList.loanInfos.get(i).setLoanTick(LoanInfoList.loanInfos.get(i).getLoanTick() + Config.NumberTickLoan);
//                    }
            }
        }
    }
    public static int getResource(IAgent_Loan agent, ResourceType type){
        if(ResourceType.Spice == type){
            return agent.getASpice();
        }
        return agent.getASugar();
    }

    public static void setResource(IAgent_Loan agent, ResourceType type, int value){
        if(ResourceType.Spice == type){
            agent.setASpice(value);
        }
        else {
         agent.setASugar(value);
        }
    }

}
