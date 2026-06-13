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
