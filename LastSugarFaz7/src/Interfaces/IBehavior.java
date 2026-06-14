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

    public boolean canBeInfected();

    public void setCanBeInfected(boolean canBeInfected);

}
