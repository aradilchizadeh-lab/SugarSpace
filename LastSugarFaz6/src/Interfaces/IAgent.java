package Interfaces;

import Models.Space;

public interface IAgent {

    public int getX();

    public int getY();

    public void setASugar(int ASugar);

    public void setASpice(int ASpice);

    public int getASugar();

    public int getASpice();

    public void changeAge();

    public int getAge();

    public int getInitSugar();

    public int getInitSpice();

    public int getVision();

    public int getMaxAge();

    public int getGender();

    public float getSugarMetabolism();

    public float getSpiceMetabolism();

    public int getFertileLimitMax();

    public void survival(Space space);
}
