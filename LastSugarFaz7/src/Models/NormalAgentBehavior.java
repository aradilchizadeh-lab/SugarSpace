package Models;
import Data.AgeType;
import Interfaces.Agent.IBehavior;

import java.util.ArrayList;


public class NormalAgentBehavior implements IBehavior {
    private boolean CanEmigrate = true;
    private boolean CanTrade = true;
    private boolean CanLoan = true;
    private boolean CanProduce = true;
    private boolean CanBeInfected = true;


    @Override
    public void survival(Agent agent, ArrayList<Agent> agents, Patch[][] patches) {

        agent.getWallet().setASugar((int) (agent.getWallet().getASugar() - agent.getSugarMetabolism()));
        agent.getWallet().setASpice((int) (agent.getWallet().getASpice() - agent.getSpiceMetabolism()));

        if (agent.getWallet().getASugar() <= 0 || agent.getWallet().getASpice() <= 0) {
            for (int i = agent.getLoanInfos().size() - 1; i >= 0; i--) {
                if (agent.getLoanInfos().get(i).getLender() == this) {
                    agent.getLoanInfos().remove(i);
                } else if (agent.getLoanInfos().get(i).getBorrower() == this) {
                    agent.getLoanInfos().remove(i);
                }
            }
            agents.remove(agent);
            patches[agent.getPosition().getX()][agent.getPosition().getY()].setPAgent(null);
        }

    }

    @Override
    public void reproductionInherit(Agent agent) {
        agent.getWallet().setASugar(Math.round(agent.getWallet().getASugar() - agent.getWallet().getInitSugar() / 2));
        agent.getWallet().setASpice(Math.round(agent.getWallet().getASpice() - agent.getWallet().getInitSpice() / 2));
    }

    //except gender
    @Override
    public boolean canBeParent(Agent agent) {
        int randomNum = (int) ((Math.random() * 31) + 30);
        if (agent.getAge() > agent.getFertileLimitMin() && agent.getAge() < agent.getFertileLimitMax()
                && agent.getWallet().getASugar() + agent.getWallet().getASpice() >= randomNum && !(agent.isParent()))
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

        if (agent.getWallet().getASugar() > 5 * agent.getSugarMetabolism() || agent.getWallet().getASpice() > 5 * agent.getSpiceMetabolism())
            return true;

        return false;

    }

    @Override
    public int requiredSpiceAmount(Agent agent) {

        if (agent.getAgeType() == AgeType.ReproductiveAdult)
            return agent.getWallet().getInitSpice() - agent.getWallet().getASpice();

        if (agent.getAgeType() == AgeType.Elderly)
            return (int) (agent.getSpiceMetabolism() * 2);

        if (agent.getAgeType() == AgeType.Child)
            return agent.getWallet().getInitSpice() - agent.getWallet().getASpice();

        return 0;
    }

    @Override
    public int requiredSugarAmount(Agent agent) {

        if (agent.getAgeType() == AgeType.ReproductiveAdult)
            return agent.getWallet().getInitSugar() - agent.getWallet().getASugar();

        if (agent.getAgeType() == AgeType.Elderly)
            return (int) (agent.getSugarMetabolism() * 2);

        if (agent.getAgeType() == AgeType.Child)
            return agent.getWallet().getInitSugar() - agent.getWallet().getASugar();

        return 0;
    }

    @Override
    public boolean needsSpice(Agent agent) {

        if (agent.getAgeType() == AgeType.ReproductiveAdult && (agent.getWallet().getASpice() < agent.getWallet().getInitSpice()))
            return true;
        if (agent.getAgeType() == AgeType.Elderly && (agent.getWallet().getASpice() < agent.getSpiceMetabolism()))
            return true;
        if (agent.getAgeType() == AgeType.Child && (agent.getWallet().getASpice() < agent.getWallet().getInitSpice()))
            return true;

        return false;
    }

    @Override
    public boolean needsSugar(Agent agent) {

        if (agent.getAgeType() == AgeType.ReproductiveAdult && (agent.getWallet().getASugar() < agent.getWallet().getInitSugar()))
            return true;
        if (agent.getAgeType() == AgeType.Elderly && (agent.getWallet().getASugar() <= agent.getSugarMetabolism()))
            return true;
        if (agent.getAgeType() == AgeType.Child && (agent.getWallet().getASugar() < agent.getWallet().getInitSugar()))
            return true;

        return false;
    }

    public boolean CanEmigrate() {
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
