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

    public IBehavior_Ability getBehavior();
}
