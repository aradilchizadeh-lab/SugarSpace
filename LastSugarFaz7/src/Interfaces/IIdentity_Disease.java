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
