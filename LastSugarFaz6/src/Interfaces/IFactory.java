package Interfaces;

import Data.ResourceType;
import Models.*;
public interface IFactory {

    public static Agent childCreator(int babyX, int babyY, int bSuger, int bSpice, int bVision, float bSuMetabolism, float bSpMetabolism){
        NormalAgentBehavior baby = new NormalAgentBehavior(babyX, babyY, bSuger, bSpice, bVision, bSuMetabolism, bSpMetabolism);
        return baby;
    }

    public static Agent agentCreator(int x, int y){
        NormalAgentBehavior agent = new NormalAgentBehavior(x, y, (int)(Math.random() * 21) + 5, (int)(Math.random() * 21) + 5,
                (int)(Math.random() * 10) + 1, (int)(Math.random() * 4) + 1, (int)(Math.random() * 4) + 1);
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
