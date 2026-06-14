package Interfaces;

public interface IAgent_Disease {
    public int getX();

    public int getY();

    public float getSugarMetabolism();

    public void setSugarMetabolism(int sugar);

    public float getSpiceMetabolism();

    public void setSpiceMetabolism(int spice);

    public IBehavior getBehavior();
}
