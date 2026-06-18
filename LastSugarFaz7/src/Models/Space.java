package Models;
import java.util.ArrayList;

import Data.Config;
import Interfaces.ISpaceProvider;
import Interfaces.ISpaceWithTickProvider;
import Interfaces.ISpace_Diseases;

public class Space implements ISpaceProvider, ISpaceWithTickProvider, ISpace_Diseases {
     Patch[][] patches;
     ArrayList<Agent> agents;
     ArrayList<Integer> diseases;
     private int Tick;

    public Space() {
        this.patches = new Patch[Config.SpaceRow][Config.SpaceCol];
        this.agents = new ArrayList<>();
        this.diseases = new ArrayList<>();
        this.Tick = 0;
    }

    public Patch[][] getPatches() {
        return patches;
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }

    public int getTick() {
        return Tick;
    }

    public void setTick() {
        Tick++;
    }

    public ArrayList<Integer> getDiseases(){
        return diseases;
    }
}


