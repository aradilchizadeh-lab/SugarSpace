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

    public void survival(Space space);

    public  double getWelfare(double w1, double w2);

    public float getSugarMetabolism();

    public float getSpiceMetabolism();

    public void setParent(boolean IsParent);
}
