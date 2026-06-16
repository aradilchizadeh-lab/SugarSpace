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
        int randomNum = (int) ((Math.random() * 31) + 30);
        if (agent.getIdentity().getAge() > agent.getIdentity().getFertileLimitMin() && agent.getIdentity().getAge() < agent.getIdentity().getFertileLimitMax()
                && agent.getWallet().getASugar() + agent.getWallet().getASpice() >=  randomNum && !(agent.getIdentity().isParent()))
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
