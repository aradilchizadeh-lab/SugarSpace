package Interfaces;

import Models.Agent;
import Models.Patch;

import java.util.ArrayList;

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

    public  double getWelfare(double w1, double w2);

    public float getSugarMetabolism();

    public float getSpiceMetabolism();

    void survival(ArrayList<Agent> agents, Patch[][] patches );

}
