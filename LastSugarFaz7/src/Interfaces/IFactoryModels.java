package Interfaces;

import Core.SpaceManager;
import Data.ResourceType;
import Interfaces.Agent.IAgent_Loan;
import Interfaces.Agent.IBehavior;
import Models.*;
public interface IFactoryModels {

    public static Agent childCreator(int babyX, int babyY, int bSugar, int bSpice, int bVision, float bSuMetabolism, float bSpMetabolism){
        
        IBehavior behavior = new NormalAgentBehavior();
        Wallet wallet = new Wallet(bSugar, bSpice);
        Position position = new Position(babyX, babyY);
        Physiology physiology = new Physiology(bVision, bSuMetabolism, bSpMetabolism);
        FertilityInfo fertilityInfo = new FertilityInfo();

        Agent baby = new Agent(position, wallet, physiology, fertilityInfo, behavior);
        return baby;
    }
//---------------------------------------------------------------------------------------
    public static Agent NormalAgentCreator(int x, int y, int disease){
        
        IBehavior behavior = new NormalAgentBehavior();
        Wallet wallet = new Wallet((int)(Math.random() * 21) + 5, (int)(Math.random() * 21) + 5);
        Position position = new Position(x, y);
        Physiology physiology = new Physiology((int)(Math.random() * 10) + 1, (int)(Math.random() * 4) + 1, (int)(Math.random() * 4) + 1);
        FertilityInfo fertilityInfo = new FertilityInfo();

        Agent agent = new Agent(position, wallet, physiology, fertilityInfo, behavior );
        agent.addInfectedDiseases(disease);
        return agent;
    }
//-------------------------------------------------------------------------------------------
    public static Patch patchCreator(int x, int y){
        Position position = new Position(x, y);
        return new Patch(position);
    }
//----------------------------------------------------------------------------------------------
    public static PatchResources patchResourcesCreator(int maxSug, int maxSpi){
        return new PatchResources(maxSug, maxSpi);
    }
//----------------------------------------------------------------------------------------------
    public static SpaceManager spaceManagerCreator(){
        return new SpaceManager();
    }
//-------------------------------------------------------------------------------------------------
    public static LoanInfo loanInfoCreator(IAgent_Loan lender, IAgent_Loan borrower, ResourceType type, int amount, int tick){
        return new LoanInfo(lender, borrower, type, amount, tick);
    }
}
