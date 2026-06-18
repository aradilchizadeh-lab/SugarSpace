package Interfaces;

import Core.SpaceManager;
import Data.ResourceType;
import Models.*;
public interface IFactoryModels {

    public static Agent childCreator(int babyX, int babyY, int bSugar, int bSpice, int bVision, float bSuMetabolism, float bSpMetabolism){
        IBehavior behavior = new NormalAgentBehavior();
        Agent baby = new Agent(babyX, babyY, bSugar, bSpice, bVision, bSuMetabolism, bSpMetabolism, behavior);
        return baby;
    }

    public static Agent NormalAgentCreator(int x, int y, int disease){
        IBehavior behavior = new NormalAgentBehavior();
        Agent agent = new Agent(x, y, (int)(Math.random() * 21) + 5, (int)(Math.random() * 21) + 5,
                (int)(Math.random() * 10) + 1, (int)(Math.random() * 4) + 1, (int)(Math.random() * 4) + 1,behavior );
        agent.addInfectedDiseases(disease);
        return agent;
    }

    public static Patch patchCreator(int x, int y){
        return new Patch(x, y);
    }

    public static Space spaceCreator(){
        return new Space();
    }

    public static SpaceManager spaceManagerCreator(){
        return new SpaceManager();
    }

    public static LoanInfo loanInfoCreator(IAgent_Loan lender, IAgent_Loan borrower, ResourceType type, int amount, int tick){
        return new LoanInfo(lender, borrower, type, amount, tick);
    }
}
