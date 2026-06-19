package Models;

import Data.AgeType;
import Interfaces.Agent.IParentageUpdate;

public class FertilityInfo implements IParentageUpdate {
    private final int Gender;
    private final int[] FertileLimits;
    private boolean IsParent;

    public FertilityInfo(){
        Gender = (int)(Math.random()*2);
        IsParent = false;
        FertileLimits = new int[2];
        FertileLimits[0] = (int)(Math.random() * 16) + 45; //max
        FertileLimits[1] = (int)(Math.random() * 3) + 15; //min
    }



    public int getGender(){
        return Gender;
    }

    public int getFertileLimitMin(){
        return FertileLimits[1];
    }

    public int getFertileLimitMax(){
        return FertileLimits[0];
    }

    public boolean isParent(){
        return IsParent;
    }

    public void setParent(boolean parent){
        IsParent = parent;
    }
}
