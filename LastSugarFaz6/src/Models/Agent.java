package Models;

import Data.AgeType;

import Interfaces.*;
import Rules.*;

public class Agent implements IAgent_Emigration, IAgent_Histogram, IAgent_Loan, IAgent_Paint, IAgent_Prodution, IAgent_Trade,IAgent_Aging {
    private int Ax;
    private int Ay;
    private final int InitSugar;
    private final int InitSpice;
    private int ASugar;
    private int ASpice;
    private final int Vision;
    private final float SugarMetabolism;
    private final float SpiceMetabolism;
    private final int Gender;
    private final int MaxAge;
    private int Age;
    private final int[] FertileLimits;
    private boolean IsParent;
    private AgeType AgeType;
    private IBehavior Behavior;
    private Emigration emigration;
    private Production production;
    private Trade trade;
    private Loan loan;
    private Aging aging;
    
    public Agent(int x, int y, int initSugar, int initSpice, int vision, float sugarMetabolism, float spiceMetabolism, IBehavior behavior){
        Ax = x;
        Ay = y;
        InitSugar = initSugar;
        ASugar = InitSugar;
        InitSpice = initSpice;
        ASpice = InitSpice;
        Vision = vision;
        SugarMetabolism = sugarMetabolism;
        SpiceMetabolism = spiceMetabolism;
        Age = 0;
        MaxAge = (int)(Math.random()*41) + 60;
        Gender = (int)(Math.random()*2);
        IsParent = false;
        FertileLimits = new int[2];
        FertileLimits[0] = (int)(Math.random() * 16) + 45; //max
        FertileLimits[1] = (int)(Math.random() * 3) + 15; //min
        AgeType = AgeType.Child;
        Behavior = behavior;
        emigration = IFactoryRules.createEmigration();
        production = IFactoryRules.createProduction();
        loan = IFactoryRules.createLoan();
        trade = IFactoryRules.createTrade();
        aging = IFactoryRules.createAging();
    }

    
     public void setX(int x){
         Ax = x;
     }

     public void setY(int y){
         Ay = y;
     }

    public int getX(){
        return Ax;
    }

    public int getY(){
        return Ay;
    }

    public void setASugar(int ASugar){
        this.ASugar = ASugar;
    }

    public void setASpice(int ASpice){
        this.ASpice = ASpice;
    }

    public int getASugar(){
        return ASugar;
    }

    public int getASpice(){
        return ASpice;
    }

    public int getInitSugar(){
        return InitSugar;
    }

    public int getInitSpice(){
        return InitSpice;
    }

    public int getVision(){
        return Vision;
    }

    public int getMaxAge(){
        return MaxAge;
    }

    public int getGender(){
        return Gender;
    }

    public float getSugarMetabolism(){
        return SugarMetabolism;
    }

    public float getSpiceMetabolism(){
        return SpiceMetabolism;
    }

    public AgeType getAgeType() {
         return AgeType;
    }

     public void setAgeType(AgeType ageType){
        AgeType = ageType;
    }
     
     public int getFertileLimitMin(){
        return FertileLimits[1];
    }

     public int getFertileLimitMax(){
        return FertileLimits[0];
    }

     public boolean isParent(){
        return IsParent;
    }

     public void setParent(boolean parent){
        IsParent = parent;
    }

    public IBehavior getBehavior(){
        return Behavior;
    }
    public void survival(ISpaceProvider space){
        Behavior.survival(this, space);
    }

    public void changeAge(){
        Age++;
        updateAgeType();
    }
    
    public int getAge(){
        return Age;
    }
    
    private void updateAgeType() {
        if ((Age >= FertileLimits[1]) && (Age < FertileLimits[0])) {
            AgeType = AgeType.ReproductiveAdult;

        } else if (Age >= FertileLimits[0]) {
            AgeType = AgeType.Elderly;}

        else {
             AgeType = AgeType.Child;
        }
    }

    public double getWelfare(double w1, double w2){
        return Behavior.getWelfare(this, w1, w2);
    }

     public double getMRS(double w1, double w2){
        return Behavior.getMRS(this, w1, w2);
    }

    public void reproductionInherit(){
        Behavior.reproductionInherit(this);
    }

    public boolean canBeParent(){
        return Behavior.canBeParent(this);
    }

    public boolean canBeLender(){
        return Behavior.canBeLender(this);
    }

    public int requiredSpiceAmount(){
        return Behavior.requiredSpiceAmount(this);
    }

    public int requiredSugarAmount(){
        return Behavior.requiredSpiceAmount(this);
    }

    public boolean needsSpice(){
        return Behavior.needsSpice(this);
    }

    public boolean needsSugar() {
        return Behavior.needsSugar(this);
    }

    public void emigration(ISpaceProvider space) {
        if (Behavior.CanEmigrate())
            emigration.emigrate(this, space);
    }
    public void production(ISpaceProvider space) {
        if (Behavior.canProduce())
            production.production(this, space);
    }
    public void aging(ISpaceProvider space) {
        Aging.ageRule(this, space);
    }
    public void loan(ISpaceWithTickProvider space) {
        if (Behavior.canLoan())
            loan.loan(this, space);
    }
    public void trade(ISpaceProvider space) {
        if (Behavior.canTrade())
            trade.trade(this, space);
    }


}
---------------------------------------------
import Data.Config;
import Data.ResourceType;
import Interfaces.IAgent_Loan;

public class LoanInfo {
 private IAgent_Loan Lender;
 private IAgent_Loan Borrower;
 private ResourceType ResourceType;
 private int Amount;
 private int LoanTick;

    public LoanInfo(IAgent_Loan lender, IAgent_Loan borrower, ResourceType resourceType, int amount, int loanTick) {
        Lender = lender;
        Borrower = borrower;
        ResourceType = resourceType;
        Amount = amount * (Config.Interest + 1);
        LoanTick = loanTick;
    }

    public IAgent_Loan getLender() {
        return Lender;
    }

    public IAgent_Loan getBorrower() {
        return Borrower;
    }

    public ResourceType getResourceType() {
        return ResourceType;
    }

    public int getAmount() {
        return Amount;
    }

    public int getLoanTick() {
        return LoanTick;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public void setLoanTick(int loanTick) {
        LoanTick = loanTick;
    }
}
------------------------------------------------------
package Models;
import Data.AgeType;
import Data.LoanInfoList;
import Interfaces.*;


public class NormalAgentBehavior implements IBehavior {
    private boolean CanEmigrate = true;
    private boolean CanTrade = true;
    private boolean CanLoan = true;
    private boolean CanProduce = true;


    @Override
    public void survival(Agent agent, ISpaceProvider space) {

        agent.setASugar((int) (agent.getASugar() - agent.getSugarMetabolism()));
        agent.setASpice((int) (agent.getASpice() - agent.getSpiceMetabolism()));

        if (agent.getASugar() <= 0 || agent.getASpice() <= 0) {
            for (int i = LoanInfoList.loanInfos.size() - 1; i >= 0; i--) {
                if (LoanInfoList.loanInfos.get(i).getLender() == this) {
                    LoanInfoList.loanInfos.remove(i);
                } else if (LoanInfoList.loanInfos.get(i).getBorrower() == this) {
                    LoanInfoList.loanInfos.remove(i);
                }
            }
            space.getAgents().remove(agent);
            space.getPatches()[agent.getX()][agent.getY()].setPAgent(null);
        }

    }

    @Override
    public void reproductionInherit(Agent agent) {
        agent.setASugar(Math.round(agent.getASugar() - agent.getInitSugar() / 2));
        agent.setASpice(Math.round(agent.getASpice() - agent.getInitSpice() / 2));
    }

    //except gender
    @Override
    public boolean canBeParent(Agent agent) {
        if (agent.getAge() > agent.getFertileLimitMin() && agent.getAge() < agent.getFertileLimitMax()
                && agent.getASugar() >= agent.getInitSugar() && agent.getASpice() >= agent.getInitSpice() && !(agent.isParent()))
            return true;

        return false;
    }

    @Override
    public double getWelfare(Agent agent, double w1, double w2) {
        double m1 = agent.getSugarMetabolism();
        double m2 = agent.getSpiceMetabolism();
        double mT = m1 + m2;

        return Math.pow(w1, m1 / mT) * Math.pow(w2, m2 / mT);
    }

    @Override
    public double getMRS(Agent agent, double w1, double w2) {
        double m1 = agent.getSugarMetabolism();
        double m2 = agent.getSpiceMetabolism();

        return (m1 * w2) / (m2 * w1);
    }

    @Override
    public boolean canBeLender(Agent agent) {

        if (agent.getASugar() > 5 * agent.getSugarMetabolism() || agent.getASpice() > 5 * agent.getSpiceMetabolism())
            return true;

        return false;

    }

    @Override
    public int requiredSpiceAmount(Agent agent) {

        if (agent.getAgeType() == AgeType.ReproductiveAdult)
            return agent.getInitSpice() - agent.getASpice();

        if (agent.getAgeType() == AgeType.Elderly)
            return (int) (agent.getSpiceMetabolism() * 2);

        if (agent.getAgeType() == AgeType.Child)
            return agent.getInitSpice() - agent.getASpice();

        return 0;
    }

    @Override
    public int requiredSugarAmount(Agent agent) {

        if (agent.getAgeType() == AgeType.ReproductiveAdult)
            return agent.getInitSugar() - agent.getASugar();

        if (agent.getAgeType() == AgeType.Elderly)
            return (int) (agent.getSugarMetabolism() * 2);

        if (agent.getAgeType() == AgeType.Child)
            return agent.getInitSugar() - agent.getASugar();

        return 0;
    }

    @Override
    public boolean needsSpice(Agent agent) {

        if (agent.getAgeType() == AgeType.ReproductiveAdult && (agent.getASpice() < agent.getInitSpice()))
            return true;
        if (agent.getAgeType() == AgeType.Elderly && (agent.getASpice() < agent.getSpiceMetabolism()))
            return true;
        if (agent.getAgeType() == AgeType.Child && (agent.getASpice() < agent.getInitSpice()))
            return true;

        return false;
    }

    @Override
    public boolean needsSugar(Agent agent) {

        if (agent.getAgeType() == AgeType.ReproductiveAdult && (agent.getASugar() < agent.getInitSugar()))
            return true;
        if (agent.getAgeType() == AgeType.Elderly && (agent.getASugar() <= agent.getSugarMetabolism()))
            return true;
        if (agent.getAgeType() == AgeType.Child && (agent.getASugar() < agent.getInitSugar()))
            return true;

        return false;
    }

    public boolean CanEmigrate() {
        return CanEmigrate;
    }

    public void setCanEmigrate(boolean canEmigrate) {
        CanEmigrate = canEmigrate;
    }

    public boolean canTrade() {
        return CanTrade;
    }

    public void setCanTrade(boolean canTrade) {
        CanTrade = canTrade;
    }

    public boolean canLoan() {
        return CanLoan;
    }

    public void setCanLoan(boolean canLoan) {
        CanLoan = canLoan;
    }

    public boolean canProduce() {
        return CanProduce;
    }

    public void setCanProduce(boolean canProduce) {
        CanProduce = canProduce;
    }
}
---------------------------------------
package Models;
import Data.Config;
import Interfaces.*;


public class Patch implements IPatch_GrowBack, IPatch_Emigration, IPatch_Production, IPatch_Aging, IPatch_Paint, IPatch_Trade, IPatch_Loan {
    private int PSugar;
    private int PSpice;
    private int MaxSugarCap;
    private int MaxSpiceCap;
    private int Px;
    private int Py;
    private Agent PAgent;

    public Patch(int x , int y){
       Px = x;
       Py = y;
       MaxSugarCap = initializeMaxValues(Config.SugarHill_X1, Config.SugarHill_Y1, Config.SugarHill_X2, Config.SugarHill_Y2);
       MaxSpiceCap = initializeMaxValues(Config.SpiceHill_X1, Config.SpiceHill_Y1, Config.SpiceHill_X2, Config.SpiceHill_Y2);
       PSugar = MaxSugarCap;
       PSpice = MaxSpiceCap;
       PAgent = null;
    }

    public int getPSugar() {
        return PSugar;
    }

    public void setPSugar(int PSugar) {
        this.PSugar = PSugar;
    }

    public int getPSpice() {
        return PSpice;
    }

    public void setPSpice(int PSpice) {
        this.PSpice = PSpice;
    }

    public int getMaxSugarCap() {
        return MaxSugarCap;
    }

    public int getMaxSpiceCap() {
        return MaxSpiceCap;
    }

    public int getPx() {
        return Px;
    }

    public int getPy() {
        return Py;
    }

    public Agent getPAgent() {
        return PAgent;
    }

    public void setPAgent(Agent PAgent) {
        this.PAgent = PAgent;
    }

    private int initializeMaxValues(int x1, int y1, int x2, int y2) {

        double g1 = Config.MaxCap * Math.exp(-(Math.pow(Px - x1, 2) + Math.pow(Py - y1, 2)) / (2.0 * Config.Sigma * Config.Sigma));

        double g2 = Config.MaxCap * Math.exp(-(Math.pow(Px - x2, 2) + Math.pow(Py - y2, 2)) / (2.0 * Config.Sigma * Config.Sigma));

        int value = Math.min((int)Config.MaxCap, (int)Math.round(g1 + g2));

        return (value / 5) * 5;
    }
    
}
------------------------------------------------------
package Models;
import java.util.ArrayList;
import Data.Config;
import Interfaces.IFactoryModels;
import Interfaces.ISpaceProvider;
import Interfaces.ISpaceWithTickProvider;
import Rules.Disease;

public class Space implements ISpaceProvider, ISpaceWithTickProvider {
    Patch[][] patches = new Patch[Config.SpaceRow][Config.SpaceCol];
    ArrayList<Agent> agents = new ArrayList<Agent>();
    ArrayList<Integer> diseases = new ArrayList<>();
    private int Tick;


    public Space() {

        //-----------------------[creating Patches]--------------------------
        for (int i = 0; i < Config.SpaceRow; ++i) {
            for (int j = 0; j < Config.SpaceCol; ++j) {
                patches[i][j] = IFactoryModels.patchCreator(i, j);
            }
        }
        //------------------------[creating agents]--------------------------
        for(int i = 0; i < Config.InitializeAgentNum;){
            int x = (int)(Math.random() * Config.SpaceRow);
            int y = (int)(Math.random() * Config.SpaceCol);
            if(patches[x][y].getPAgent() == null){
                Agent agent = IFactoryModels.NormalAgentCreator(x, y);
                agents.add(agent);
                patches[x][y].setPAgent(agent);
                i++;
            }
        }

        for (int i = 0; i < 10; ++i){
            int disease = (int) ((Math.random() * Math.pow(2, 9)) + Math.pow(2, 9));
            diseases.add(disease);
        }
    }

    public Patch[][] getPatches() {
        return patches;
    }


    public ArrayList<Agent> getAgents() {
        return agents;
    }


    public int getTick() {
        return Tick;
    }


    public void setTick() {
        Tick++;
    }

    public ArrayList<Integer> getDiseases(){
        return diseases;
    }
}
------------------------------------------------------
package Interfaces;

public interface IAgent_Aging {

    public int getX();

    public int getY();

    public int getAge();

    public void changeAge();

    public int getMaxAge();

    public void setParent(boolean IsParent);
}
------------------------------------------------------
------------------------------------------------------
package Interfaces;

import Models.Space;

public interface IAgent_Emigration {

    public void setX(int x);

    public void setY(int y);

    public int getX();

    public int getY();

    public void setASugar(int ASugar);

    public void setASpice(int ASpice);

    public int getASugar();

    public int getASpice();

    public int getVision();

    public void survival(ISpaceProvider space);

    public  double getWelfare(double w1, double w2);

    public float getSugarMetabolism();

    public float getSpiceMetabolism();

    public void setParent(boolean IsParent);

}
------------------------------------------------------
package Interfaces;

public interface IAgent_Histogram {
    public  double getWelfare(double w1, double w2);

    public  double getMRS(double w1, double w2);

    public int getASugar();

    public int getASpice();
    
}
------------------------------------------------------
package Interfaces;

import Data.AgeType;

public interface IAgent_Loan {

    public int getX();

    public int getY();

    public boolean canBeLender();

    public AgeType getAgeType();

    public void setASugar(int ASugar);

    public void setASpice(int ASpice);

    public int getASugar();

    public int getASpice();

    public int getInitSugar();

    public int getInitSpice();

    public int requiredSpiceAmount();

    public int requiredSugarAmount();

    public float getSugarMetabolism();

    public float getSpiceMetabolism();

    public boolean needsSugar();

    public boolean needsSpice();

}
------------------------------------------------------
package Interfaces;

public interface IAgent_Paint {
    public int getFertileLimitMax();

    public int getAge();

    public int getGender();

}
------------------------------------------------------
package Interfaces;

public interface IAgent_Prodution {
    public int getX();

    public int getY();

    public int getInitSugar();

    public int getInitSpice();

    public int getVision();

    public void reproductionInherit();

    public boolean canBeParent();

    public int getGender();

    public float getSugarMetabolism();

    public float getSpiceMetabolism();

    public void setParent(boolean IsParent);
}
------------------------------------------------------
package Interfaces;

public interface IAgent_Trade {

    public int getX();

    public int getY();

    public void setASugar(int ASugar);

    public void setASpice(int ASpice);

    public int getASugar();

    public int getASpice();

    public  double getWelfare(double w1, double w2);

    public  double getMRS(double w1, double w2);

}
------------------------------------------------------
package Interfaces;

import Models.Agent;

public interface IBehavior {

    public void survival(Agent agent, ISpaceProvider space);

    public void reproductionInherit(Agent agent);

    public boolean canBeParent(Agent agent);

    public  double getWelfare(Agent agent, double w1, double w2);

    public  double getMRS(Agent agent, double w1, double w2);

    public boolean canBeLender(Agent agent);

    public int requiredSpiceAmount(Agent agent);

    public int requiredSugarAmount(Agent agent);

    public boolean needsSpice(Agent agent);

    public boolean needsSugar(Agent agent);

    public boolean CanEmigrate();

    public void setCanEmigrate(boolean canEmigrate);

    public boolean canTrade();

    public void setCanTrade(boolean canTrade);

    public boolean canLoan();

    public void setCanLoan(boolean canLoan);

    public boolean canProduce();

    public void setCanProduce(boolean canProduce);

}
------------------------------------------------------
package Interfaces;

import Data.ResourceType;
import Models.*;
public interface IFactoryModels {

    public static Agent childCreator(int babyX, int babyY, int bSuger, int bSpice, int bVision, float bSuMetabolism, float bSpMetabolism){
        IBehavior behavior = new NormalAgentBehavior();
        Agent baby = new Agent(babyX, babyY, bSuger, bSpice, bVision, bSuMetabolism, bSpMetabolism, behavior);
        return baby;
    }

    public static Agent NormalAgentCreator(int x, int y){
        IBehavior behavior = new NormalAgentBehavior();
        Agent agent = new Agent(x, y, (int)(Math.random() * 21) + 5, (int)(Math.random() * 21) + 5,
                (int)(Math.random() * 10) + 1, (int)(Math.random() * 4) + 1, (int)(Math.random() * 4) + 1,behavior );
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
------------------------------------------------------
package Interfaces;

import Rules.*;

public interface IFactoryRules{

    public static Emigration createEmigration(){
        return new Emigration();
    }

    public static Production createProduction(){
        return new Production();
    }

    public static Loan createLoan(){
        return new Loan();
    }

    public static Trade createTrade(){
        return new Trade();
    }

    public static Aging createAging(){
        return new Aging();
    }
}
------------------------------------------------------
package Interfaces;

import Models.Agent;

public interface IPatch_Aging {

    public void setPAgent(Agent PAgent);
}
------------------------------------------------------
package Interfaces;

import Models.Agent;

public interface IPatch_Aging {

    public void setPAgent(Agent PAgent);
}
------------------------------------------------------
package Interfaces;

import Models.Agent;

public interface IPatch_Emigration {

    public int getPSugar();

    public void setPSugar(int PSugar);

    public int getPSpice();

    public void setPSpice(int PSpice);

    public Agent getPAgent();

    public void setPAgent(Agent PAgent);

    public int getPx();

    public int getPy();
}
------------------------------------------------------
package Interfaces;

public interface IPatch_GrowBack {

    public int getPSugar();

    public void setPSugar(int PSugar);

    public int getPSpice();

    public void setPSpice(int PSpice);

    public int getMaxSugarCap();

    public int getMaxSpiceCap();
}
------------------------------------------------------
package Interfaces;

import Models.Agent;

public interface IPatch_Loan {

    public Agent getPAgent();
}
------------------------------------------------------
package Interfaces;

import Models.Agent;

public interface IPatch_Paint {

    public Agent getPAgent();

    public int getPSugar();

    public int getPSpice();
}
------------------------------------------------------
package Interfaces;

import Models.Agent;

public interface IPatch_Production {

    public Agent getPAgent();

    public void setPAgent(Agent PAgent);

    public int getPx();

    public int getPy();
}

------------------------------------------------------
package Rules;

import Interfaces.IAgent_Aging;
import Interfaces.IPatch_Aging;
import Interfaces.ISpaceProvider;
import Models.Agent;

import java.util.ArrayList;

public class Aging {
    public static void ageRule(IAgent_Aging agent, ISpaceProvider space) {
        IPatch_Aging[][] patches = space.getPatches();
        ArrayList<Agent> agents = space.getAgents();

        agent.changeAge();
        //---[reset parent status and checking age status]---
        if (agent.getAge() < agent.getMaxAge()) {
            agent.setParent(false);
        } else {
            agents.remove(agent);
            patches[agent.getX()][agent.getY()].setPAgent(null);
        }
    }
}
------------------------------------------------------
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

------------------------------------------------------
package Rules;
import Data.Config;
import Interfaces.IPatch_GrowBack;
import Interfaces.ISpaceProvider;

public class GrowBack {
    public static void growBack(ISpaceProvider space, int x1, int x2, int tick) {
        IPatch_GrowBack[][] patches = space.getPatches();
        for (int i = x1; i < x2; i++) {
            for (int j = 0; j < Config.SpaceCol; j++) {
                //---[checking status of tick and interval then sugar and spice grow back]---
                if (tick % Config.SugarGrowBackInterval == 0) {
                    int newSugar = Math.min(patches[i][j].getPSugar() + Config.SugarGrowBackRate, patches[i][j].getMaxSugarCap());
                    patches[i][j].setPSugar(newSugar);
                }
                if (tick % Config.SpiceGrowBackInterval == 0) {
                    int newSpice = Math.min(patches[i][j].getPSpice() + Config.SpiceGrowBackRate, patches[i][j].getMaxSpiceCap());
                    patches[i][j].setPSpice(newSpice);
                }
            }
        }
    }
}
------------------------------------------------------
package Rules;

import Data.AgeType;
import Data.Config;
import Data.LoanInfoList;
import Data.ResourceType;
import Interfaces.IAgent_Loan;
import Interfaces.IFactoryModels;
import Interfaces.IPatch_Loan;
import Interfaces.ISpaceWithTickProvider;
import Models.*;

import java.util.ArrayList;

public class Loan {
    public static void loan(IAgent_Loan agent, ISpaceWithTickProvider space) {
        IPatch_Loan[][] patches = space.getPatches();
        debtPayment(space, agent);
        if (agent.canBeLender()) {
            giveLoan(space, agent, patches);
        }
    }


    private static void giveLoan(ISpaceWithTickProvider space, IAgent_Loan a, IPatch_Loan[][] patches) {
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
                    IAgent_Loan neighbor = (IAgent_Loan) patches[i][j].getPAgent();
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
            LoanInfo l = IFactoryModels.loanInfoCreator(a, neighbor, ResourceType.Spice, amount, space.getTick());
            LoanInfoList.loanInfos.add(l);
            //---[payment]---
            neighbor.setASpice(neighbor.getASpice() + amount);
            a.setASpice(a.getASpice() - amount);
        }
        //---[checking status of resource for the payment for both agent and neighbor]---
        if (a.getASugar() > a.getSugarMetabolism() * 5 && neighbor.needsSugar()) {
            //---[initializing amount by priority of need and can give]---
            amount = (int) Math.min(a.getASugar() - a.getSugarMetabolism() * 5, neighbor.requiredSugarAmount());
            //---[adding info to list]---
            LoanInfo l = IFactoryModels.loanInfoCreator(a, neighbor, ResourceType.Sugar, amount, space.getTick());
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
                if (borrowerWealth > debtAmount) {
                    setResource(info.getBorrower(), info.getResourceType(), borrowerWealth - debtAmount);
                    setResource(info.getLender(), info.getResourceType(), lenderWealth + debtAmount);
                    LoanInfoList.loanInfos.remove(i);
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
------------------------------------------------------
ackage Rules;

import Data.Config;
import Interfaces.IAgent_Prodution;
import Interfaces.IFactoryModels;
import Interfaces.IPatch_Production;
import Interfaces.ISpaceProvider;
import Models.Agent;

import java.util.ArrayList;

public class Production {
    public static void production(IAgent_Prodution agent, ISpaceProvider space) {
        IPatch_Production[][] patches = space.getPatches();
        ArrayList<Agent> agents = space.getAgents();
        if (!agent.canBeParent())
            return;

        int agentX = agent.getX();
        int agentY = agent.getY();

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
        boolean neghiborFlag = false;
        boolean emptyPatchFlag = false;
        IPatch_Production babyPatch = null;
        IAgent_Prodution neighborAgent = null;

        while (!selectedPatches.isEmpty()) {
            //---[break when we found neighbor and free patch]---
            if (neghiborFlag && emptyPatchFlag)
                break;
            //---[getting random patch]---
            int index = (int) (Math.random() * selectedPatches.size());
            IPatch_Production randomPatch = selectedPatches.get(index);
            //---[checking random patch if its free]---
            if (randomPatch.getPAgent() == null && !emptyPatchFlag) {
                babyPatch = randomPatch;
                emptyPatchFlag = true;
            } else if (randomPatch.getPAgent() != null && !neghiborFlag) {
                if (randomPatch.getPAgent().getBehavior().canProduce()) {
                    IAgent_Prodution neighbor = randomPatch.getPAgent();
                    if (neighbor.canBeParent() && randomPatch.getPAgent().getGender() != agent.getGender()) {
                        neighborAgent = neighbor;
                        neghiborFlag = true;
                    }
                }
            }
            selectedPatches.remove(index);
        }//end while

        //---[checking if we have baby condition]---
        if (neghiborFlag && emptyPatchFlag) {
            //---[initializing baby values]---
            int babyX = babyPatch.getPx();
            int babyY = babyPatch.getPy();

            int bSuger = Math.round(agent.getInitSugar() / 2 + neighborAgent.getInitSugar() / 2);
            int bSpice = Math.round(agent.getInitSpice() / 2 + neighborAgent.getInitSpice() / 2);
            int bSuMetabolism = Math.round(agent.getSugarMetabolism() / 2 + neighborAgent.getSugarMetabolism() / 2);
            int bSpMetabolism = Math.round(agent.getSpiceMetabolism() / 2 + neighborAgent.getSpiceMetabolism() / 2);
            int bVision = Math.round(agent.getVision() / 2 + neighborAgent.getVision() / 2);
            //---[creating baby]---
            Agent baby = IFactoryModels.childCreator(babyX, babyY, bSuger, bSpice, bVision, bSuMetabolism, bSpMetabolism);
            agents.add(baby);
            //---[parents status initializing]---
            agent.setParent(true);
            neighborAgent.setParent(true);
            agent.reproductionInherit();
            neighborAgent.reproductionInherit();
            babyPatch.setPAgent(baby);
        }
    }
}
------------------------------------------------------
package Rules;
import Data.Config;

import Interfaces.IAgent_Trade;
import Interfaces.IPatch_Trade;
import Interfaces.ISpaceProvider;
import Models.Agent;

import java.util.ArrayList;
import java.util.Collections;

public class Trade {
    public  void trade(IAgent_Trade agent, ISpaceProvider space) {
        IPatch_Trade[][] patches = space.getPatches();

        ArrayList<IAgent_Trade> neighborAgents = new ArrayList<>();
        addNeighbor(agent, patches, neighborAgents);
        if (!neighborAgents.isEmpty())
            trading(agent, neighborAgents);
    }

    private static void addNeighbor(IAgent_Trade a, IPatch_Trade[][] patches, ArrayList<IAgent_Trade> neighbor) {
        int x = a.getX();
        int y = a.getY();
        for (int i = x - 1; i <= x + 1; ++i) {
            if (i < 0 || i > Config.SpaceRow - 1)
                continue;
            for (int j = y - 1; j <= y + 1; ++j) {
                if (j < 0 || j > Config.SpaceCol - 1 || (i == x && j == y))
                    continue;

                if (patches[i][j].getPAgent() != null && patches[i][j].getPAgent().getBehavior().canTrade() && (i == x || j == y))
                    neighbor.add((IAgent_Trade) patches[i][j].getPAgent());
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
------------------------------------------------------
package Data;

public enum AgeType {
    Child, ReproductiveAdult, Elderly;
}

------------------------------------------------------
package Data;

public class Config {
    public static int SugarGrowBackInterval = 1;
    public static int SugarGrowBackRate = 1;
    public static int SpiceGrowBackInterval = 1;
    public static int SpiceGrowBackRate = 1;
    public static int InitializeAgentNum = 400;
    public static int SpaceRow = 51;
    public static int SpaceCol = 51;
    public static int SugarHill_X1 = 12;
    public static int SugarHill_Y1 = 12;
    public static int SugarHill_X2 = 38;
    public static int SugarHill_Y2 = 38;
    public static int SpiceHill_X1 = 12;
    public static int SpiceHill_Y1 = 38;
    public static int SpiceHill_X2 = 38;
    public static int SpiceHill_Y2 = 12;
    public static int NumberTickLoan = 10;
    public static int Interest = 4;
    public static double Sigma = 10.0;
    public static double MaxCap = 25.0;
    public static int Tick = 600;
    public static int CanvasSizeWidth = 800;
    public static int CanvasSizeHeight = 800;
    
}
------------------------------------------------------
package Data;

import Models.LoanInfo;

import java.util.ArrayList;

public class LoanInfoList {
    public static ArrayList<LoanInfo> loanInfos = new ArrayList<>();
}

------------------------------------------------------
package Data;

public enum ResourceType {
    Sugar,Spice;
}

------------------------------------------------------
package GUI;

import Interfaces.IAgent_Histogram;
import Models.Agent;
import Models.Space;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Histogram {
    public static void saveFileWealth(Space space) {
        try {

            FileWriter myfile = new FileWriter("wealth.txt");
            for (Agent a : space.getAgents()) {
                IAgent_Histogram a1 = (IAgent_Histogram)a;
                myfile.write(a1.getMRS(a1.getASugar(), a1.getASpice()) + "\n");
            }
            myfile.close();
        } catch (Exception e) {
            System.out.println("There is a problem");
        }
    }




    public static ArrayList<Double> readMRSFromFile() {
        ArrayList<Double> MRSList = new ArrayList<>();
        File file = new File("wealth.txt");

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextDouble()) {
                MRSList.add(scanner.nextDouble());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("فایل پیدا نشد! مسیر چک شده: " + file.getAbsolutePath());
        }
        return MRSList;
    }

    public static void processAndDraw() {
        ArrayList<Double> allData = readMRSFromFile();

        ArrayList<Double> cleanData = new ArrayList<>();
        for (Double d : allData) {
            if (d != null && !d.isInfinite() && !d.isNaN()) {
                cleanData.add(d);
            }
        }
        if (cleanData.isEmpty()) {
            System.out.println("داده‌ای برای رسم وجود ندارد!");
            return;
        }

        double minMRS = Double.POSITIVE_INFINITY;
        double maxMRS = Double.NEGATIVE_INFINITY;

        for (double mrs : cleanData) {
            if (mrs < minMRS) minMRS = mrs;
            if (mrs > maxMRS) maxMRS = mrs;
        }


        int numBins = 8;
        double sizeBin = (maxMRS - minMRS) / numBins;
        int[] bins = new int[numBins];

        for (double mrs : cleanData) {
            int index = (int)((mrs - minMRS) / sizeBin);
            if (index == numBins) index = numBins - 1;
            bins[index]++;
        }

        int maxFreq = 0;
        for (int f : bins) {
            if (f > maxFreq) maxFreq = f;
        }

        int n = bins.length;
        StdDraw.clear();
        StdDraw.setXscale(-1, n);
        StdDraw.setYscale(-maxFreq * 0.15, maxFreq * 1.2);

        for (int i = 0; i < n; i++) {
            double freq = bins[i];

            StdDraw.setPenColor(StdDraw.MAGENTA);
            StdDraw.filledRectangle(i, freq / 2.0, 0.4, freq / 2.0);

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(i, freq / 2.0, 0.4, freq / 2.0);

            int start = (int) (i * sizeBin);
            int end = (int) ((i + 1) * sizeBin);
            String rangeText ="(" +start + " - " + end+")";

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(i, -maxFreq * 0.06, rangeText);


            if (bins[i] >= 0) {
                StdDraw.text(i, freq + maxFreq * 0.03, String.valueOf(bins[i]));
            }
        }
        StdDraw.show();
    }
}
------------------------------------------------------
package GUI;


import Data.Config;
import Interfaces.IAgent_Paint;
import Interfaces.IPatch_Paint;
import Interfaces.ISpaceWithTickProvider;

public class Paint {
    
    public static void rePaint(ISpaceWithTickProvider space)
    {
        IPatch_Paint[][] patches = space.getPatches();
        IAgent_Paint patchAgent;
        StdDraw.clear();
        
        for (int i = 0; i < Config.SpaceRow; ++i)
        {
            for (int j = 0; j < Config.SpaceCol; ++j)
            {
                int sugar = patches[i][j].getPSugar();
                int spice = patches[i][j].getPSpice();
                float ratio = (float)(sugar / Config.MaxCap);
                float ratio2 = (float)(spice / Config.MaxCap);

                int red = 255 - (int)(20 * ratio);
                int grean = 255 - (int)(170 * ratio);
                int blue = 255 - (int)(255 * ratio);

                StdDraw.setPenColor(red, grean, blue);
                StdDraw.filledRectangle(i + 0.25, j + 0.5, 0.25, 0.5);

                red = 255 - (int)(180 * ratio2);
                grean = 255 - (int)(220 * ratio2);
                blue = 255 - (int)(120 * ratio2);

                StdDraw.setPenColor(red, grean, blue);
                StdDraw.filledRectangle(i + 0.75 , j + 0.5, 0.25, 0.5);
                
                patchAgent = (IAgent_Paint) patches[i][j].getPAgent();

                if (patches[i][j].getPAgent() != null)
                {
                    if (patchAgent.getAge() >= patchAgent.getFertileLimitMax())
                    {
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.setPenRadius(0.015);
                        StdDraw.point(i + 0.5, j + 0.5);
                    }
                    else if(patchAgent.getGender() == 0)
                    {
                        StdDraw.setPenColor(StdDraw.RED);
                        StdDraw.setPenRadius(0.015);
                        StdDraw.point(i + 0.5, j + 0.5);
                    }
                    else if (patchAgent.getGender() == 1)
                    {
                        StdDraw.setPenColor(StdDraw.BLUE);
                        StdDraw.setPenRadius(0.015);
                        StdDraw.point(i + 0.5, j + 0.5);
                    }
                }
                
            }
        }
        String ticks= String.valueOf(space.getTick());
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(5, Config.SpaceRow + 1, "Tick: ");
        StdDraw.text(7.5, Config.SpaceRow + 1, ticks);

        String agents= String.valueOf(space.getAgents().size());
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(14, Config.SpaceRow + 1, "Agents: ");
        StdDraw.text(17.5, Config.SpaceRow + 1, agents);

        StdDraw.show();
        StdDraw.pause(10);


    }



}
------------------------------------------------------
package Core;

import Data.Config;
import Data.LoanInfoList;
import GUI.Histogram;
import GUI.Paint;
import GUI.StdDraw;
import Interfaces.IFactoryModels;
import Models.Agent;
import Models.Space;
import Rules.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller{

    public static void controller() throws InterruptedException {
        //---[creating space]---
        Space space = IFactoryModels.spaceCreator();
        ArrayList<Agent> agents = space.getAgents();
        //---[creating paint space]---
        StdDraw.setCanvasSize(Config.CanvasSizeWidth,Config.CanvasSizeHeight);
        StdDraw.setXscale(0,Config.SpaceRow);
        StdDraw.setYscale(0,Config.SpaceCol + 2);
        StdDraw.enableDoubleBuffering();

        int tick = 0;
        while(tick < Config.Tick) {
            ++tick;
            int finalTick = tick;
            //---[grow back with threads]---
            ExecutorService executor = Executors.newFixedThreadPool(4);
            executor.submit(() -> GrowBack.growBack(space, 0, 13, finalTick));
            executor.submit(() -> GrowBack.growBack(space, 13, 26, finalTick));
            executor.submit(() -> GrowBack.growBack(space, 26, 39, finalTick));
            executor.submit(() -> GrowBack.growBack(space, 39, 51, finalTick));

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.SECONDS);

            for (int i = agents.size() - 1; i >= 0; i--) {
                agents.get(i).emigration(space);
            }

            for (int i = agents.size() - 1; i >= 0; i--) {
                agents.get(i).production(space);
            }

            for (int i = agents.size() - 1; i >= 0; i--) {
                agents.get(i).trade(space);
            }

            for (int i = agents.size() - 1; i >= 0; i--) {
                agents.get(i).loan(space);
            }

            for (int i = agents.size() - 1; i >= 0; i--) {
                agents.get(i).aging(space);
            }

            space.setTick();
            Paint.rePaint(space);

        }

        //---[drawing histogram]---
        StdDraw.clear();
        Histogram.saveFileWealth(space);
        Histogram.processAndDraw();
        StdDraw.show();
    }
}

------------------------------------------------------
package Core;

public class Shakerestan {
    public static void main() throws InterruptedException {
        Controller.controller();
        // This is just for the test

    }
}
------------------------------------------------------


