package Interfaces;

import Data.ResourceType;
import Models.*;
public interface IFactoryModels {

    public static Agent childCreator(int babyX, int babyY, int bSuger, int bSpice, int bVision, float bSuMetabolism, float bSpMetabolism){
        IBehavior behavior = new NormalAgentBehavior();
        Wallet wallet = new Wallet(bSuger, bSpice, bSuMetabolism, bSpMetabolism);
        Identity identity = new Identity(babyX, babyY, bVision);
        Agent baby = new Agent(wallet,identity, behavior);
        return baby;
    }

    public static Agent NormalAgentCreator(int x, int y, int disease){
        IBehavior behavior = new NormalAgentBehavior();
        Wallet wallet = new Wallet((int)(Math.random() * 21) + 5, (int)(Math.random() * 21) + 5, (int)(Math.random() * 4) + 1, (int)(Math.random() * 4) + 1);
        Identity identity = new Identity(x, y, (int)(Math.random() * 10) + 1);
        Agent agent = new Agent(wallet, identity, behavior);
        agent.addInfectedDiseases(disease);
        return agent;
    }

    public static Patch patchCreator(int x, int y){
        return new Patch(x, y);
    }

    public static Space spaceCreator(){
        return new Space();
    }

    public static LoanInfo loanInfoCreator(IAgent_Loan lender, IAgent_Loan borrower, ResourceType type, int amount, int tick){
        return new LoanInfo(lender, borrower, type, amount, tick);
    }
}
