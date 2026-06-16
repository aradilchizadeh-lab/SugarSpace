-----------------------------------
package Models;
import java.util.ArrayList;

import Data.Config;
import Interfaces.IFactoryModels;
import Interfaces.ISpaceProvider;
import Interfaces.ISpaceWithTickProvider;
import Interfaces.ISpace_Diseases;

public class Space implements ISpaceProvider, ISpaceWithTickProvider, ISpace_Diseases {
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
        //------------------------[creating diseases]--------------------------
        for (int i = 0; i < Config.diseaseNum; ) {
            int disease = (int) ((Math.random() * Math.pow(2, 9)) + Math.pow(2, 9));
            if (!diseases.contains(disease)) {
                diseases.add(disease);
                i++;
            }
        }
        //------------------------[creating agents]--------------------------
        for (int i = 0; i < Config.InitializeAgentNum; ) {
            int x = (int) (Math.random() * Config.SpaceRow);
            int y = (int) (Math.random() * Config.SpaceCol);
            if (patches[x][y].getPAgent() == null) {
                int randomIndex = (int) (Math.random() * diseases.size());
                Agent agent = IFactoryModels.NormalAgentCreator(x, y, diseases.get(randomIndex));
                agents.add(agent);
                patches[x][y].setPAgent(agent);
                i++;
            }
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
-----------------------------------
package Models;
import Data.Config;
import Interfaces.*;


public class Patch implements IPatch_GrowBack, IPatch_Emigration, IPatch_Production, IPatch_Aging, IPatch_Paint, IPatch_AgentProvider {
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

-----------------------------------
package Models;

import Interfaces.*;

public class Wallet implements IWallet_Disease, IWallet_Emigration, IWallet_ResourceProvider, IWallet_Loan, IWallet_Production, IWallet_Trade {
    private final int InitSugar;
    private final int InitSpice;
    private int ASugar;
    private int ASpice;
    private float SugarMetabolism;
    private float SpiceMetabolism;

    public Wallet(int initSugar, int initSpice, float sugarMetabolism, float spiceMetabolism) {
        InitSugar = initSugar;
        InitSpice = initSpice;
        this.ASugar = InitSugar;
        this.ASpice = InitSpice;
        SugarMetabolism = sugarMetabolism;
        SpiceMetabolism = spiceMetabolism;
    }

    public void setASugar(int ASugar) {
        this.ASugar = ASugar;
    }

    public void setASpice(int ASpice) {
        this.ASpice = ASpice;
    }

    public int getASugar() {
        return ASugar;
    }

    public int getASpice() {
        return ASpice;
    }

    public int getInitSugar() {
        return InitSugar;
    }

    public int getInitSpice() {
        return InitSpice;
    }

    public float getSugarMetabolism() {
        return SugarMetabolism;
    }

    public float getSpiceMetabolism() {
        return SpiceMetabolism;
    }

    public void setSugarMetabolism(float sugar) {
        SugarMetabolism = sugar;
    }

    public void setSpiceMetabolism(float spice) {
        SpiceMetabolism = spice;
    }
}
-----------------------------------
package Models;

import Data.AgeType;
import Interfaces.*;

import java.util.ArrayList;

public class Identity implements IIdentity_Aging, IIdentity_Emigration, IIdentity_Loan, IIdentity_Paint, IIdentity_Production, IIdentity_Trade, IIdentity_Disease {
    private int Ax;
    private int Ay;
    private final int Vision;
    private final int Gender;
    private final int MaxAge;
    private int Age;
    private final int[] FertileLimits;
    private boolean IsParent;
    private AgeType ageType;
    private boolean CanEmigrate;
    private boolean CanTrade;
    private boolean CanLoan;
    private boolean CanProduce;
    private boolean CanBeInfected ;

    public Identity(int x, int y, int vision, boolean canEmigrate, boolean canTrade, boolean canLoan, boolean canProduce , boolean canBeInfected) {
        Ax = x;
        Ay = y;
        Vision = vision;
        Age = 0;
        MaxAge = (int) (Math.random() * 41) + 60;
        Gender = (int) (Math.random() * 2);
        IsParent = false;
        FertileLimits = new int[2];
        FertileLimits[0] = (int) (Math.random() * 16) + 45; //max
        FertileLimits[1] = (int) (Math.random() * 3) + 15; //min
        ageType = AgeType.Child;
        CanEmigrate = canEmigrate;
        CanTrade = canTrade;
        CanLoan = canLoan;
        CanProduce = canProduce;
        CanBeInfected = canBeInfected;

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

    public int getVision(){
        return Vision;
    }

    public int getMaxAge(){
        return MaxAge;
    }

    public int getGender(){
        return Gender;
    }

    public AgeType getAgeType() {
        return ageType;
    }

    public void setAgeType(AgeType ageType){
        this.ageType = ageType;
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

    public void changeAge(){
        Age++;
        updateAgeType();
    }

    public int getAge(){
        return Age;
    }

    private void updateAgeType() {
        if ((Age >= FertileLimits[1]) && (Age < FertileLimits[0])) {
            ageType = AgeType.ReproductiveAdult;

        } else if (Age >= FertileLimits[0]) {
            ageType = AgeType.Elderly;}

        else {
            ageType = AgeType.Child;
        }
    }

    public boolean canEmigrate() {
        return CanEmigrate;
    }

    public void setCanEmigrate(boolean canEmigrate) {
        CanEmigrate = canEmigrate;
    }

    public boolean canBeInfected() {
        return CanBeInfected;
    }

    public void setCanBeInfected(boolean canBeInfected) {
        CanBeInfected = canBeInfected;
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

-----------------------------------
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

-----------------------------------
package Models;
import Data.AgeType;
import Interfaces.*;


public class NormalAgentBehavior implements IBehavior {


    @Override
    public void survival(Agent agent, ISpaceProvider space) {

        agent.getWallet().setASugar((int) (agent.getWallet().getASugar() - agent.getWallet().getSugarMetabolism()));
        agent.getWallet().setASpice((int) (agent.getWallet().getASpice() - agent.getWallet().getSpiceMetabolism()));

        if (agent.getWallet().getASugar() <= 0 || agent.getWallet().getASpice() <= 0) {
            for (int i = agent.getLoanInfos().size() - 1; i >= 0; i--) {
                if (agent.getLoanInfos().get(i).getLender() == this) {
                    agent.getLoanInfos().remove(i);
                } else if (agent.getLoanInfos().get(i).getBorrower() == this) {
                    agent.getLoanInfos().remove(i);
                }
            }
            space.getAgents().remove(agent);
            space.getPatches()[agent.getIdentity().getX()][agent.getIdentity().getY()].setPAgent(null);
        }

    }

    @Override
    public void reproductionInherit(Agent agent) {
        agent.getWallet().setASugar(Math.round(agent.getWallet().getASugar() - agent.getWallet().getInitSugar() / 2));
        agent.getWallet().setASpice(Math.round(agent.getWallet().getASpice() - agent.getWallet().getASugar() / 2));
    }

    //except gender
    @Override
    public boolean canBeParent(Agent agent) {
        if (agent.getIdentity().getAge() > agent.getIdentity().getFertileLimitMin() && agent.getIdentity().getAge() < agent.getIdentity().getFertileLimitMax()
                && agent.getWallet().getASugar() >= agent.getWallet().getInitSugar() && agent.getWallet().getASpice() >= agent.getWallet().getInitSpice() && !(agent.getIdentity().isParent()))
            return true;

        return false;
    }

    @Override
    public double getWelfare(Agent agent, double w1, double w2) {
        double m1 = agent.getWallet().getSugarMetabolism();
        double m2 = agent.getWallet().getSpiceMetabolism();
        double mT = m1 + m2;

        return Math.pow(w1, m1 / mT) * Math.pow(w2, m2 / mT);
    }

    @Override
    public double getMRS(Agent agent, double w1, double w2) {
        double m1 = agent.getWallet().getSugarMetabolism();
        double m2 = agent.getWallet().getSpiceMetabolism();

        return (m1 * w2) / (m2 * w1);
    }

    @Override
    public boolean canBeLender(Agent agent) {

        if (agent.getWallet().getASugar() > 5 * agent.getWallet().getSugarMetabolism() || agent.getWallet().getASpice() > 5 * agent.getWallet().getSpiceMetabolism())
            return true;

        return false;

    }

    @Override
    public int requiredSpiceAmount(Agent agent) {

        if (agent.getIdentity().getAgeType() == AgeType.ReproductiveAdult)
            return agent.getWallet().getInitSpice() - agent.getWallet().getASpice();

        if (agent.getIdentity().getAgeType() == AgeType.Elderly)
            return (int) (agent.getWallet().getSpiceMetabolism() * 2);

        if (agent.getIdentity().getAgeType() == AgeType.Child)
            return agent.getWallet().getInitSpice() - agent.getWallet().getASpice();

        return 0;
    }

    @Override
    public int requiredSugarAmount(Agent agent) {

        if (agent.getIdentity().getAgeType() == AgeType.ReproductiveAdult)
            return agent.getWallet().getInitSugar() - agent.getWallet().getASugar();

        if (agent.getIdentity().getAgeType() == AgeType.Elderly)
            return (int) (agent.getWallet().getSugarMetabolism() * 2);

        if (agent.getIdentity().getAgeType() == AgeType.Child)
            return agent.getWallet().getInitSugar() - agent.getWallet().getASugar();

        return 0;
    }

    @Override
    public boolean needsSpice(Agent agent) {

        if (agent.getIdentity().getAgeType() == AgeType.ReproductiveAdult && (agent.getWallet().getASpice() < agent.getWallet().getInitSpice()))
            return true;
        if (agent.getIdentity().getAgeType() == AgeType.Elderly && (agent.getWallet().getASpice() <= agent.getWallet().getSpiceMetabolism()))
            return true;
        if (agent.getIdentity().getAgeType() == AgeType.Child && (agent.getWallet().getASpice() < agent.getWallet().getInitSpice()))
            return true;

        return false;
    }

    @Override
    public boolean needsSugar(Agent agent) {

        if (agent.getIdentity().getAgeType() == AgeType.ReproductiveAdult && (agent.getWallet().getASugar() < agent.getWallet().getInitSugar()))
            return true;
        if (agent.getIdentity().getAgeType() == AgeType.Elderly && (agent.getWallet().getASugar() <= agent.getWallet().getSugarMetabolism()))
            return true;
        if (agent.getIdentity().getAgeType() == AgeType.Child && (agent.getWallet().getASugar() < agent.getWallet().getInitSugar()))
            return true;

        return false;
    }

}

-----------------------------------
package Models;
import Data.AgeType;
import Interfaces.*;


public class NormalAgentBehavior implements IBehavior {


    @Override
    public void survival(Agent agent, ISpaceProvider space) {

        agent.getWallet().setASugar((int) (agent.getWallet().getASugar() - agent.getWallet().getSugarMetabolism()));
        agent.getWallet().setASpice((int) (agent.getWallet().getASpice() - agent.getWallet().getSpiceMetabolism()));

        if (agent.getWallet().getASugar() <= 0 || agent.getWallet().getASpice() <= 0) {
            for (int i = agent.getLoanInfos().size() - 1; i >= 0; i--) {
                if (agent.getLoanInfos().get(i).getLender() == this) {
                    agent.getLoanInfos().remove(i);
                } else if (agent.getLoanInfos().get(i).getBorrower() == this) {
                    agent.getLoanInfos().remove(i);
                }
            }
            space.getAgents().remove(agent);
            space.getPatches()[agent.getIdentity().getX()][agent.getIdentity().getY()].setPAgent(null);
        }

    }

    @Override
    public void reproductionInherit(Agent agent) {
        agent.getWallet().setASugar(Math.round(agent.getWallet().getASugar() - agent.getWallet().getInitSugar() / 2));
        agent.getWallet().setASpice(Math.round(agent.getWallet().getASpice() - agent.getWallet().getASugar() / 2));
    }

    //except gender
    @Override
    public boolean canBeParent(Agent agent) {
        if (agent.getIdentity().getAge() > agent.getIdentity().getFertileLimitMin() && agent.getIdentity().getAge() < agent.getIdentity().getFertileLimitMax()
                && agent.getWallet().getASugar() >= agent.getWallet().getInitSugar() && agent.getWallet().getASpice() >= agent.getWallet().getInitSpice() && !(agent.getIdentity().isParent()))
            return true;

        return false;
    }

    @Override
    public double getWelfare(Agent agent, double w1, double w2) {
        double m1 = agent.getWallet().getSugarMetabolism();
        double m2 = agent.getWallet().getSpiceMetabolism();
        double mT = m1 + m2;

        return Math.pow(w1, m1 / mT) * Math.pow(w2, m2 / mT);
    }

    @Override
    public double getMRS(Agent agent, double w1, double w2) {
        double m1 = agent.getWallet().getSugarMetabolism();
        double m2 = agent.getWallet().getSpiceMetabolism();

        return (m1 * w2) / (m2 * w1);
    }

    @Override
    public boolean canBeLender(Agent agent) {

        if (agent.getWallet().getASugar() > 5 * agent.getWallet().getSugarMetabolism() || agent.getWallet().getASpice() > 5 * agent.getWallet().getSpiceMetabolism())
            return true;

        return false;

    }

    @Override
    public int requiredSpiceAmount(Agent agent) {

        if (agent.getIdentity().getAgeType() == AgeType.ReproductiveAdult)
            return agent.getWallet().getInitSpice() - agent.getWallet().getASpice();

        if (agent.getIdentity().getAgeType() == AgeType.Elderly)
            return (int) (agent.getWallet().getSpiceMetabolism() * 2);

        if (agent.getIdentity().getAgeType() == AgeType.Child)
            return agent.getWallet().getInitSpice() - agent.getWallet().getASpice();

        return 0;
    }

    @Override
    public int requiredSugarAmount(Agent agent) {

        if (agent.getIdentity().getAgeType() == AgeType.ReproductiveAdult)
            return agent.getWallet().getInitSugar() - agent.getWallet().getASugar();

        if (agent.getIdentity().getAgeType() == AgeType.Elderly)
            return (int) (agent.getWallet().getSugarMetabolism() * 2);

        if (agent.getIdentity().getAgeType() == AgeType.Child)
            return agent.getWallet().getInitSugar() - agent.getWallet().getASugar();

        return 0;
    }

    @Override
    public boolean needsSpice(Agent agent) {

        if (agent.getIdentity().getAgeType() == AgeType.ReproductiveAdult && (agent.getWallet().getASpice() < agent.getWallet().getInitSpice()))
            return true;
        if (agent.getIdentity().getAgeType() == AgeType.Elderly && (agent.getWallet().getASpice() <= agent.getWallet().getSpiceMetabolism()))
            return true;
        if (agent.getIdentity().getAgeType() == AgeType.Child && (agent.getWallet().getASpice() < agent.getWallet().getInitSpice()))
            return true;

        return false;
    }

    @Override
    public boolean needsSugar(Agent agent) {

        if (agent.getIdentity().getAgeType() == AgeType.ReproductiveAdult && (agent.getWallet().getASugar() < agent.getWallet().getInitSugar()))
            return true;
        if (agent.getIdentity().getAgeType() == AgeType.Elderly && (agent.getWallet().getASugar() <= agent.getWallet().getSugarMetabolism()))
            return true;
        if (agent.getIdentity().getAgeType() == AgeType.Child && (agent.getWallet().getASugar() < agent.getWallet().getInitSugar()))
            return true;

        return false;
    }

}
-----------------------------------
package Models;

import java.util.ArrayList;

import Data.AgeType;

import Interfaces.*;
import Rules.*;

public class Agent implements IAgent_Histogram, IAgent_Emigration, IAgent_Trade, IAgent_Production, IAgent_Loan, IAgent_Disease, IAgent_Aging, IAgent_Paint{
    private Wallet Wallet;
    private Identity Identity;
    private IBehavior Behavior;
    private Emigration Emigration;
    private Production Production;
    private Trade Trade;
    private Loan Loan;
    private Aging aging;
    private Disease Disease;
    private ArrayList<LoanInfo> LoanInfos;

    public Agent(Wallet wallet, Identity identity, IBehavior behavior) {
        Behavior = behavior;
        Emigration = IFactoryRules.createEmigration();
        Production = IFactoryRules.createProduction();
        Loan = IFactoryRules.createLoan();
        Trade = IFactoryRules.createTrade();
        aging = IFactoryRules.createAging();
        Disease = IFactoryRules.createDisease();
        LoanInfos = new ArrayList<>();
        Wallet = wallet;
        Identity = identity;
    }

    public IBehavior getBehavior() {
        return Behavior;
    }

    /*public void upgradeBehavior(IBehavior behavior) {
        Behavior = behavior;
    }*/

    public double getWelfare(double w1, double w2) {
        return Behavior.getWelfare(this, w1, w2);
    }

    public double getMRS(double w1, double w2) {
        return Behavior.getMRS(this, w1, w2);
    }

    public void reproductionInherit() {
        Behavior.reproductionInherit(this);
    }

    public boolean canBeParent() {
        return Behavior.canBeParent(this);
    }

    public boolean canBeLender() {
        return Behavior.canBeLender(this);
    }


    public int requiredSpiceAmount() {
        return Behavior.requiredSpiceAmount(this);
    }

    public int requiredSugarAmount() {
        return Behavior.requiredSugarAmount(this);
    }

    public boolean needsSpice() {
        return Behavior.needsSpice(this);
    }

    public boolean needsSugar() {
        return Behavior.needsSugar(this);
    }

    public void emigration(ISpaceProvider space) {
        if (Identity.canEmigrate())
            Emigration.emigrate((IAgent_Emigration) this, space);
    }

    public void production(ISpaceProvider space) {
        if (Identity.canProduce())
            Production.production((IAgent_Production) this, space);
    }

    public void aging(ISpaceProvider space) {
        aging.ageRule((IAgent_Aging) this, space);
    }

    public void loan(ISpaceWithTickProvider space) {
        if (Identity.canLoan())
            Loan.loan((IAgent_Loan) this, space);
    }

    public void trade(ISpaceProvider space) {
        if (Identity.canTrade())
            Trade.trade((IAgent_Trade) this, space);
    }

    public void disease(ISpace_Diseases space) {
        if (Identity.canBeInfected())
            Disease.disease((IAgent_Disease) this, space);
    }


    public void addInfectedDiseases(int disease) {
        Disease.addInfectedDiseases(disease * 10);
    }

    public ArrayList<LoanInfo> getLoanInfos() {
        return LoanInfos;
    }

    public void survival(ISpaceProvider space) {
        Behavior.survival(this, space);
    }

    public ArrayList<Integer> getPossibleDiseases() {
        return Disease.getPossibleDiseases();
    }

    public ArrayList<Integer> getInfectedDiseases() {
        return Disease.getInfectedDiseases();
    }

    public Wallet getWallet() {
        return Wallet;
    }

    public Identity getIdentity() {
        return Identity;
    }
}

-----------------------------------
package Rules;

import Interfaces.IAgent_Aging;
import Interfaces.IIdentity_Aging;
import Interfaces.IPatch_Aging;
import Interfaces.ISpaceProvider;
import Models.Agent;

import java.util.ArrayList;

public class Aging {
    public void ageRule(IAgent_Aging agent, ISpaceProvider space) {
        IPatch_Aging[][] patches = space.getPatches();
        ArrayList<Agent> agents = space.getAgents();

        agent.getIdentity().changeAge();
        //---[reset parent status and checking age status]---
        if (agent.getIdentity().getAge() < agent.getIdentity().getMaxAge()) {
            agent.getIdentity().setParent(false);
        } else {
            agents.remove(agent);
            patches[agent.getIdentity().getX()][agent.getIdentity().getY()].setPAgent(null);
        }
    }
}
-----------------------------------
package Rules;

import Interfaces.IAgent_Disease;
import Interfaces.IPatch_AgentProvider;
import Interfaces.ISpace_Diseases;

import java.util.ArrayList;

import Data.AgeType;
import Data.Config;

public class Disease {
    private int[] SubImmuneSystem = new int[Config.ImmuneSystemSubsCount];
    private ArrayList<Integer> InfectedDiseases = new ArrayList<>();
    private ArrayList<Integer> PossibleDiseases = new ArrayList<>();
    private long ImmuneSystem;
    public Disease() {
        ImmuneSystem = (long) ((Math.random() * Math.pow(2, 49)) + Math.pow(2, 49)); //50 bit
        int length = Config.diseaseLength;
        int mask = (1 << length) - 1;

        for (int i = 0; i < Config.ImmuneSystemSubsCount; ++i) {
            int sub = (int) ((ImmuneSystem >> i) & mask);
            SubImmuneSystem[i] = sub;
        }
    }

    public void disease(IAgent_Disease agent, ISpace_Diseases space) {

        improveImmunity(agent, space);
        if (!InfectedDiseases.isEmpty())
            infectOthers(agent, space);
    }

    private void improveImmunity(IAgent_Disease agent, ISpace_Diseases space) {
        ArrayList<Integer> Diseases = space.getDiseases();
        int randomDisease = Diseases.get((int) (Math.random() * Diseases.size()));

        int hamming = Integer.MAX_VALUE;
        int diff;
        int subIndex = 0;
        for (int i = 0; i < Config.ImmuneSystemSubsCount; ++i) {
            diff = 0;
            diff = Integer.bitCount(SubImmuneSystem[i] ^ randomDisease);
            if (diff < hamming) {
                hamming = diff;
                subIndex = i;
            }
            if (hamming == 0)
                break;
        }
        if (hamming != 0) {
            int xor = SubImmuneSystem[subIndex] ^ randomDisease;
            int firstDifferentBit = (xor & -xor);
            SubImmuneSystem[subIndex] ^= firstDifferentBit;
        }

        boolean isImmune;
        PossibleDiseases.clear();
        for (int i = 0; i < Config.diseaseNum; ++i) {
            isImmune = false;
            for (int j = 0; j < Config.ImmuneSystemSubsCount; ++j) {

                if (Diseases.get(i) == SubImmuneSystem[j]) {
                    isImmune = true;
                    break;
                }
            }
            //removing diseases from infected list that agent is ammune against them
            if (isImmune) {
                for (int k = InfectedDiseases.size() - 1; k >= 0; --k) {
                    if (InfectedDiseases.get(k) / 10 == Diseases.get(i)) {

                        int effect = InfectedDiseases.get(k) % 10;
                        diseaseSideEffects(agent, -effect);
                        InfectedDiseases.remove(k);
                        break;
                    }
                }
            } else {
                PossibleDiseases.add(Diseases.get(i));
            }
        }
    }

    public void diseaseSideEffects(IAgent_Disease agent, int effect) {
        agent.getWallet().setSugarMetabolism(agent.getWallet().getSugarMetabolism() + effect);
        agent.getWallet().setSpiceMetabolism(agent.getWallet().getSpiceMetabolism() + effect);
    }


    private void infectOthers(IAgent_Disease agent, ISpace_Diseases space) {
        IPatch_AgentProvider[][] patches = space.getPatches();
        ArrayList<IAgent_Disease> neighbors = new ArrayList<>();
        addNeighbor(agent, patches, neighbors);
        if (neighbors.isEmpty())
            return;

        for (int i = 0; i < neighbors.size(); i++) {

            int diseaseIndex = (int) (Math.random() * InfectedDiseases.size());
            IAgent_Disease neighbor = neighbors.get(i);
            int agentDisease = agent.getInfectedDiseases().get(diseaseIndex) / 10;

            boolean alreadyInfected = false;

            for (int d : neighbor.getInfectedDiseases()) {
                if (d / 10 == agentDisease) {
                    alreadyInfected = true;
                    break;
                }
            }

            if (neighbor.getPossibleDiseases().contains(agentDisease) && !alreadyInfected) {

                int increaseMetabolism = (int) (Math.random() * 3) + 1;
                if (neighbor.getIdentity().getAgeType() == AgeType.Child)
                    increaseMetabolism = 0;
                agentDisease = agentDisease * 10 + increaseMetabolism;
                neighbor.getInfectedDiseases().add(agentDisease);

                if (neighbor.getIdentity().getAgeType() != AgeType.Child)
                    diseaseSideEffects(neighbor, increaseMetabolism);

            }
        }
    }

    private static void addNeighbor(IAgent_Disease a, IPatch_AgentProvider[][] patches, ArrayList<IAgent_Disease> neighbor) {
        int x = a.getIdentity().getX();
        int y = a.getIdentity().getY();
        for (int i = x - 1; i <= x + 1; ++i) {
            if (i < 0 || i > Config.SpaceRow - 1)
                continue;
            for (int j = y - 1; j <= y + 1; ++j) {
                if (j < 0 || j > Config.SpaceCol - 1 || (i == x && j == y))
                    continue;

                if (patches[i][j].getPAgent() != null && patches[i][j].getPAgent().getIdentity().canBeInfected() && (i == x || j == y))
                    neighbor.add((IAgent_Disease) patches[i][j].getPAgent());
            }
        }
    }

    public ArrayList<Integer> getPossibleDiseases() {
        return PossibleDiseases;
    }

    public ArrayList<Integer> getInfectedDiseases() {
        return InfectedDiseases;
    }

    public void addInfectedDiseases(int disease) {
        InfectedDiseases.add(disease);
    }

}
-----------------------------------
package Rules;

import Data.Config;
import Interfaces.*;
import Models.Agent;
import Models.NormalAgentBehavior;
import Models.Space;

import java.util.ArrayList;

public class Emigration {
    public void emigrate(IAgent_Emigration agent, ISpaceProvider space) {
        IPatch_Emigration[][] patches = space.getPatches();

        int x = agent.getIdentity().getX();
        int y = agent.getIdentity().getY();
        //---[creating vars]--
        double welfare, w1, w2;
        double maxWelfare = Double.NEGATIVE_INFINITY;
        int bestX = x;
        int bestY = y;
        ArrayList<IPatch_Emigration> sameCondition = new ArrayList<>();
        int distance = Integer.MAX_VALUE;

        for (int i = x - agent.getIdentity().getVision(); i <= x + agent.getIdentity().getVision(); i++) {
            if (i >= 0 && i < Config.SpaceRow && (patches[i][y].getPAgent() == null)) {
                //---[initializing vars]---
                w1 = agent.getWallet().getASugar() + patches[i][y].getPSugar() - agent.getWallet().getSugarMetabolism();
                w2 = agent.getWallet().getASpice() + patches[i][y].getPSpice() - agent.getWallet().getSpiceMetabolism();
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

        for (int j = y - agent.getIdentity().getVision(); j <= y + agent.getIdentity().getVision(); j++) {
            if (j >= 0 && j < patches.length && (patches[x][j].getPAgent() == null)) {
                //---[initializing vars]---
                w1 = agent.getWallet().getASugar() + patches[x][j].getPSugar() - agent.getWallet().getSugarMetabolism();
                w2 = agent.getWallet().getASpice() + patches[x][j].getPSpice() - agent.getWallet().getSpiceMetabolism();
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
            agent.survival(space);
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
        agent.getIdentity().setX(bestX);
        agent.getIdentity().setY(bestY);
        agent.getWallet().setASugar(patches[bestX][bestY].getPSugar() + agent.getWallet().getASugar());
        agent.getWallet().setASpice(patches[bestX][bestY].getPSpice() + agent.getWallet().getASpice());
        //---[patches status after emigration]---
        patches[bestX][bestY].setPSugar(0);
        patches[bestX][bestY].setPSpice(0);
        patches[bestX][bestY].setPAgent((Agent) agent);
        patches[x][y].setPAgent(null);
        //---[agent survival after emigration]---
        agent.survival(space);

    }

}
-----------------------------------
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

-----------------------------------
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
        int x = a.getIdentity().getX();
        int y = a.getIdentity().getY();
        //---[adding neighbors]---
        for (int i = x - 1; i <= x + 1; ++i) {
            //---[checking if we are in the space]---
            if (i < 0 || i > Config.SpaceRow - 1)
                continue;
            for (int j = y - 1; j <= y + 1; ++j) {
                if (j < 0 || j > Config.SpaceCol - 1 || (i == x && j == y))
                    continue;
                //---[checking if we have valid patch]---
                if (patches[i][j].getPAgent() != null && patches[i][j].getPAgent().getIdentity().canLoan() && (i == x || j == y)) {
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
                if (neighbors.get(k).getIdentity().getAgeType() == type) {
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
        if (a.getWallet().getASpice() > a.getWallet().getSpiceMetabolism() * 5 && neighbor.needsSpice()) {
            //---[initializing amount by priority of need and can give]---
            amount = (int) Math.min(a.getWallet().getASugar() - a.getWallet().getSpiceMetabolism() * 5, neighbor.requiredSpiceAmount());
            //---[adding info to list]---
            a.getLoanInfos().add(IFactoryModels.loanInfoCreator(a, neighbor, ResourceType.Spice, amount, space.getTick()));
            //---[payment]---
            neighbor.getWallet().setASpice(neighbor.getWallet().getASpice() + amount);
            a.getWallet().setASpice(a.getWallet().getASpice() - amount);
        }
        //---[checking status of resource for the payment for both agent and neighbor]---
        if (a.getWallet().getASugar() > a.getWallet().getSugarMetabolism() * 5 && neighbor.needsSugar()) {
            //---[initializing amount by priority of need and can give]---
            amount = (int) Math.min(a.getWallet().getASugar() - a.getWallet().getSugarMetabolism() * 5, neighbor.requiredSugarAmount());
            //---[adding info to list]---
            a.getLoanInfos().add(IFactoryModels.loanInfoCreator(a, neighbor, ResourceType.Sugar, amount, space.getTick()));
            //---[payment]---
            neighbor.getWallet().setASugar(neighbor.getWallet().getASugar() + amount);
            a.getWallet().setASugar(a.getWallet().getASugar() - amount);
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
            return agent.getWallet().getASpice();
        }
        return agent.getWallet().getASugar();
    }

    public static void setResource(IAgent_Loan agent, ResourceType type, int value) {
        if (ResourceType.Spice == type) {
            agent.getWallet().setASpice(value);
        } else {
            agent.getWallet().setASugar(value);
        }
    }
}
-----------------------------------
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
        int x = a.getIdentity().getX();
        int y = a.getIdentity().getY();
        //---[adding neighbors]---
        for (int i = x - 1; i <= x + 1; ++i) {
            //---[checking if we are in the space]---
            if (i < 0 || i > Config.SpaceRow - 1)
                continue;
            for (int j = y - 1; j <= y + 1; ++j) {
                if (j < 0 || j > Config.SpaceCol - 1 || (i == x && j == y))
                    continue;
                //---[checking if we have valid patch]---
                if (patches[i][j].getPAgent() != null && patches[i][j].getPAgent().getIdentity().canLoan() && (i == x || j == y)) {
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
                if (neighbors.get(k).getIdentity().getAgeType() == type) {
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
        if (a.getWallet().getASpice() > a.getWallet().getSpiceMetabolism() * 5 && neighbor.needsSpice()) {
            //---[initializing amount by priority of need and can give]---
            amount = (int) Math.min(a.getWallet().getASugar() - a.getWallet().getSpiceMetabolism() * 5, neighbor.requiredSpiceAmount());
            //---[adding info to list]---
            a.getLoanInfos().add(IFactoryModels.loanInfoCreator(a, neighbor, ResourceType.Spice, amount, space.getTick()));
            //---[payment]---
            neighbor.getWallet().setASpice(neighbor.getWallet().getASpice() + amount);
            a.getWallet().setASpice(a.getWallet().getASpice() - amount);
        }
        //---[checking status of resource for the payment for both agent and neighbor]---
        if (a.getWallet().getASugar() > a.getWallet().getSugarMetabolism() * 5 && neighbor.needsSugar()) {
            //---[initializing amount by priority of need and can give]---
            amount = (int) Math.min(a.getWallet().getASugar() - a.getWallet().getSugarMetabolism() * 5, neighbor.requiredSugarAmount());
            //---[adding info to list]---
            a.getLoanInfos().add(IFactoryModels.loanInfoCreator(a, neighbor, ResourceType.Sugar, amount, space.getTick()));
            //---[payment]---
            neighbor.getWallet().setASugar(neighbor.getWallet().getASugar() + amount);
            a.getWallet().setASugar(a.getWallet().getASugar() - amount);
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
            return agent.getWallet().getASpice();
        }
        return agent.getWallet().getASugar();
    }

    public static void setResource(IAgent_Loan agent, ResourceType type, int value) {
        if (ResourceType.Spice == type) {
            agent.getWallet().setASpice(value);
        } else {
            agent.getWallet().setASugar(value);
        }
    }
}
-----------------------------------
package Rules;

import Data.Config;
import Interfaces.IAgent_Production;
import Interfaces.IFactoryModels;
import Interfaces.IPatch_Production;
import Interfaces.ISpaceProvider;
import Models.Agent;

import java.util.ArrayList;

public class Production {
    public void production(IAgent_Production agent, ISpaceProvider space) {
        IPatch_Production[][] patches = space.getPatches();
        ArrayList<Agent> agents = space.getAgents();
        if (!agent.canBeParent())
            return;

        int agentX = agent.getIdentity().getX();
        int agentY = agent.getIdentity().getY();

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
        IAgent_Production neighborAgent = null;

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
                if (randomPatch.getPAgent().getIdentity().canProduce()) {
                    IAgent_Production neighbor = (IAgent_Production) randomPatch.getPAgent();
                    if (neighbor.canBeParent() && randomPatch.getPAgent().getIdentity().getGender() != agent.getIdentity().getGender()) {
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

            int bSuger = Math.round(agent.getWallet().getInitSugar() / 2 + neighborAgent.getWallet().getInitSugar() / 2);
            int bSpice = Math.round(agent.getWallet().getInitSpice() / 2 + neighborAgent.getWallet().getInitSpice() / 2);
            int bSuMetabolism = Math.round(agent.getWallet().getSugarMetabolism() / 2 + neighborAgent.getWallet().getSugarMetabolism() / 2);
            int bSpMetabolism = Math.round(agent.getWallet().getSpiceMetabolism() / 2 + neighborAgent.getWallet().getSpiceMetabolism() / 2);
            int bVision = Math.round(agent.getIdentity().getVision() / 2 + neighborAgent.getIdentity().getVision() / 2);
            //---[creating baby]---
            Agent baby = IFactoryModels.childCreator(babyX, babyY, bSuger, bSpice, bVision, bSuMetabolism, bSpMetabolism);
            agents.add(baby);
            //---[parents status initializing]---
            agent.getIdentity().setParent(true);
            neighborAgent.getIdentity().setParent(true);
            agent.reproductionInherit();
            neighborAgent.reproductionInherit();
            babyPatch.setPAgent(baby);
        }
    }
}
-----------------------------------
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

    private static void addNeighbor(IAgent_Trade a, IPatch_AgentProvider[][] patches, ArrayList<IAgent_Trade> neighbor) {
        int x = a.getIdentity().getX();
        int y = a.getIdentity().getY();
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
-----------------------------------
package Data;

public enum AgeType {
    Child, ReproductiveAdult, Elderly;
}

-----------------------------------
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
    public static int diseaseNum = 10;
    public static int diseaseLength = 10;
    public static int ImmuneSystemSubsCount = 41;
}
-----------------------------------
package Data;

public enum ResourceType {
    Sugar,Spice;
}
-----------------------------------
package GUI;

import Interfaces.IAgent_Histogram;
import Models.Agent;
import Models.Space;

import java.awt.*;
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
                myfile.write(a1.getMRS(a1.getWallet().getASugar(), a1.getWallet().getASpice()) + "\n");
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
            //System.out.println("داده‌ای برای رسم وجود ندارد!");
            StdDraw.setCanvasSize(800,800);
            Color color = StdDraw.BLACK;
            StdDraw.clear(color);
            StdDraw.setFont(new Font("Impact",Font.PLAIN,50));
            String text = "this is the end!";
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(0.5 , 0.5, text);
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
-----------------------------------
package GUI;


import Data.Config;
import Interfaces.IAgent_Paint;
import Interfaces.IIdentity_Paint;
import Interfaces.IPatch_Paint;
import Interfaces.ISpaceWithTickProvider;

public class Paint {

    public static void rePaint(ISpaceWithTickProvider space) {
        IPatch_Paint[][] patches = space.getPatches();
        IAgent_Paint patchAgent;
        StdDraw.clear();

        for (int i = 0; i < Config.SpaceRow; ++i) {
            for (int j = 0; j < Config.SpaceCol; ++j) {
                int sugar = patches[i][j].getPSugar();
                int spice = patches[i][j].getPSpice();
                float ratio = (float) (sugar / Config.MaxCap);
                float ratio2 = (float) (spice / Config.MaxCap);

                int red = 255 - (int) (20 * ratio);
                int grean = 255 - (int) (170 * ratio);
                int blue = 255 - (int) (255 * ratio);

                StdDraw.setPenColor(red, grean, blue);
                StdDraw.filledRectangle(i + 0.25, j + 0.5, 0.25, 0.5);

                red = 255 - (int) (180 * ratio2);
                grean = 255 - (int) (220 * ratio2);
                blue = 255 - (int) (120 * ratio2);

                StdDraw.setPenColor(red, grean, blue);
                StdDraw.filledRectangle(i + 0.75, j + 0.5, 0.25, 0.5);

                patchAgent = (IAgent_Paint) patches[i][j].getPAgent();

                if (patches[i][j].getPAgent() != null) {
                    if (patchAgent.getIdentity().getAge() >= patchAgent.getIdentity().getMaxAge()) {
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.setPenRadius(0.015);
                        StdDraw.point(i + 0.5, j + 0.5);
                    } else if (patchAgent.getIdentity().getGender() == 0) {
                        StdDraw.setPenColor(StdDraw.RED);
                        StdDraw.setPenRadius(0.015);
                        StdDraw.point(i + 0.5, j + 0.5);
                    } else if (patchAgent.getIdentity().getGender() == 1) {
                        StdDraw.setPenColor(StdDraw.BLUE);
                        StdDraw.setPenRadius(0.015);
                        StdDraw.point(i + 0.5, j + 0.5);
                    }
                }

            }
        }
        String ticks = String.valueOf(space.getTick());
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(5, Config.SpaceRow + 1, "Tick: ");
        StdDraw.text(7.5, Config.SpaceRow + 1, ticks);

        String agents = String.valueOf(space.getAgents().size());
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(14, Config.SpaceRow + 1, "Agents: ");
        StdDraw.text(17.5, Config.SpaceRow + 1, agents);

        StdDraw.show();
        StdDraw.pause(1);


    }


}
-----------------------------------
package Interfaces;

public interface IAgent_Aging {

    public IIdentity_Aging getIdentity();
}
-----------------------------------
package Interfaces;

import java.util.ArrayList;

import Data.AgeType;
import Models.Identity;
import Models.Wallet;

public interface IAgent_Disease {

    public ArrayList<Integer> getInfectedDiseases();

    public ArrayList<Integer> getPossibleDiseases();

    public IWallet_Disease getWallet();

    public IIdentity_Disease getIdentity();

}
-----------------------------------
package Interfaces;

import Models.Space;

public interface IAgent_Emigration {

    public void survival(ISpaceProvider space);

    public  double getWelfare(double w1, double w2);

    public IWallet_Emigration getWallet();

    public IIdentity_Emigration getIdentity();

}
-----------------------------------
package Interfaces;

public interface IAgent_Histogram {

    public  double getMRS(double w1, double w2);

    public IWallet_ResourceProvider getWallet();
    
}
-----------------------------------
package Interfaces;

import Data.AgeType;
import Models.LoanInfo;

import java.util.ArrayList;

public interface IAgent_Loan {

    public boolean canBeLender();

    public int requiredSpiceAmount();

    public int requiredSugarAmount();

    public boolean needsSugar();

    public boolean needsSpice();

    public ArrayList<LoanInfo> getLoanInfos();

    public IWallet_Loan getWallet();

    public IIdentity_Loan getIdentity();

}
-----------------------------------
package Interfaces;

public interface IAgent_Paint {

    public IIdentity_Paint getIdentity();
}
-----------------------------------
package Interfaces;

public interface IAgent_Production {

    public void reproductionInherit();

    public boolean canBeParent();

    public IWallet_Production getWallet();

    public IIdentity_Production getIdentity();

}
-----------------------------------
package Interfaces;

public interface IAgent_Trade {

    public  double getWelfare(double w1, double w2);

    public  double getMRS(double w1, double w2);

    public IWallet_Trade getWallet();

    public IIdentity_Trade getIdentity();

}
-----------------------------------
package Interfaces;

import Models.Agent;

public interface IBehavior {

    public void survival(Agent agent, ISpaceProvider space);

    public void reproductionInherit(Agent agent);

    public boolean canBeParent(Agent agent);

    public double getWelfare(Agent agent, double w1, double w2);

    public double getMRS(Agent agent, double w1, double w2);

    public boolean canBeLender(Agent agent);

    public int requiredSpiceAmount(Agent agent);

    public int requiredSugarAmount(Agent agent);

    public boolean needsSpice(Agent agent);

    public boolean needsSugar(Agent agent);
}
-----------------------------------
package Interfaces;

import Data.ResourceType;
import Models.*;
public interface IFactoryModels {

    public static Agent childCreator(int babyX, int babyY, int bSuger, int bSpice, int bVision, float bSuMetabolism, float bSpMetabolism){
        IBehavior behavior = new NormalAgentBehavior();
        Wallet wallet = new Wallet(bSuger, bSpice, bSuMetabolism, bSpMetabolism);
        Identity identity = new Identity(babyX, babyY, bVision, true , true, true , true, true);
        Agent baby = new Agent(wallet,identity, behavior);
        return baby;
    }

    public static Agent NormalAgentCreator(int x, int y, int disease){
        IBehavior behavior = new NormalAgentBehavior();
        Wallet wallet = new Wallet((int)(Math.random() * 21) + 5, (int)(Math.random() * 21) + 5, (int)(Math.random() * 4) + 1, (int)(Math.random() * 4) + 1);
        Identity identity = new Identity(x, y, (int)(Math.random() * 10) + 1, true, true , true , true , true);
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
-----------------------------------
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

    public static Disease createDisease(){
        return new Disease();
    }
}
-----------------------------------
package Interfaces;

public interface IIdentity_Aging {

    public int getX();

    public int getY();

    public int getAge();

    public void changeAge();

    public int getMaxAge();

    public void setParent(boolean IsParent);
}
-----------------------------------
package Interfaces;

import Data.AgeType;

public interface IIdentity_Disease {

    public int getX();

    public int getY();

    public AgeType getAgeType();

    public int getAge();

    public boolean canBeInfected();

    public void setCanBeInfected(boolean canBeInfected);

}
-----------------------------------
package Interfaces;

public interface IIdentity_Emigration {

    public void setX(int x);

    public void setY(int y);

    public int getX();

    public int getY();

    public int getVision();

    public void setParent(boolean IsParent);

    public boolean canEmigrate();

    public void setCanEmigrate(boolean canEmigrate);

}
-----------------------------------
package Interfaces;

import Data.AgeType;

public interface IIdentity_Loan {

    public int getX();

    public int getY();

    public AgeType getAgeType();

    public boolean canLoan();

    public void setCanLoan(boolean canLoan);
}

-----------------------------------
package Interfaces;

public interface IIdentity_Paint {

    public int getFertileLimitMax();

    public int getAge();

    public int getGender();

    public int getMaxAge();

}
-----------------------------------
package Interfaces;

public interface IIdentity_Production {

    public int getX();

    public int getY();

    public int getVision();

    public void setParent(boolean IsParent);

    public int getGender();

    public boolean canProduce();

    public void setCanProduce(boolean canProduce);
}

-----------------------------------
package Interfaces;

public interface IIdentity_Trade {

    public int getX();

    public int getY();

    public boolean canTrade();

    public void setCanTrade(boolean canTrade);
}

-----------------------------------
package Interfaces;

import Models.Agent;

public interface IPatch_AgentProvider {
    
    public Agent getPAgent();
}
-----------------------------------
package Interfaces;

import Models.Agent;

public interface IPatch_Aging {

    public void setPAgent(Agent PAgent);
}

-----------------------------------
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
-----------------------------------
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
-----------------------------------
package Interfaces;

public interface IPatch_GrowBack {

    public int getPSugar();

    public void setPSugar(int PSugar);

    public int getPSpice();

    public void setPSpice(int PSpice);

    public int getMaxSugarCap();

    public int getMaxSpiceCap();
}
-----------------------------------
package Interfaces;

import Models.Agent;

public interface IPatch_Paint {

    public Agent getPAgent();

    public int getPSugar();

    public int getPSpice();
}
-----------------------------------
package Interfaces;

import Models.Agent;

public interface IPatch_Production {

    public Agent getPAgent();

    public void setPAgent(Agent PAgent);

    public int getPx();

    public int getPy();
}
-----------------------------------
package Interfaces;

import java.util.ArrayList;

public interface ISpace_Diseases extends ISpaceProvider{

    public ArrayList<Integer> getDiseases();
}
-----------------------------------
package Interfaces;

import Models.Agent;
import Models.Patch;

import java.util.ArrayList;

public interface ISpaceProvider {

    public Patch[][] getPatches();

    public ArrayList<Agent> getAgents();
}
-----------------------------------
package Interfaces;

public interface ISpaceWithTickProvider extends ISpaceProvider {

    public int getTick();
}

-----------------------------------
package Interfaces;

public interface IWallet_Disease {

    public float getSugarMetabolism();

    public void setSugarMetabolism(float sugar);

    public float getSpiceMetabolism();

    public void setSpiceMetabolism(float spice);

}
-----------------------------------
package Interfaces;

public interface IWallet_Emigration extends IWallet_Trade {

    public float getSugarMetabolism();

    public float getSpiceMetabolism();
}
-----------------------------------
package Interfaces;

public interface IWallet_Loan extends IWallet_Trade {

    public float getSugarMetabolism();

    public float getSpiceMetabolism();
}
-----------------------------------
package Interfaces;

public interface IWallet_Production {

    public int getInitSugar();

    public int getInitSpice();

    public float getSugarMetabolism();

    public float getSpiceMetabolism();
}
-----------------------------------
package Interfaces;

public interface IWallet_ResourceProvider {

    public int getASugar();

    public int getASpice();
}

-----------------------------------
package Interfaces;

public interface IWallet_Trade extends IWallet_ResourceProvider{

    public void setASugar(int ASugar);

    public void setASpice(int ASpice);
}

-----------------------------------
package Core;

import Data.Config;
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
        StdDraw.setCanvasSize(Config.CanvasSizeWidth, Config.CanvasSizeHeight);
        StdDraw.setXscale(0, Config.SpaceRow);
        StdDraw.setYscale(0, Config.SpaceCol + 2);
        StdDraw.enableDoubleBuffering();

        int tick = 0;
        while (tick < Config.Tick) {
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
                agents.get(i).disease(space);
            }

            for (int i = agents.size() - 1; i >= 0; i--) {
                agents.get(i).aging(space);
            }

            space.setTick();
            Paint.rePaint(space);
            if (agents.size() == 0) System.out.println(tick);
        }

        //---[drawing histogram]---
        StdDraw.clear(StdDraw.BLACK);
        Histogram.saveFileWealth(space);
        Histogram.processAndDraw();
        StdDraw.show();
    }
}
-----------------------------------
package Core;

public class Shakerestan {
    public static void main() throws InterruptedException {
        Controller.controller();
    }
}
-----------------------------------