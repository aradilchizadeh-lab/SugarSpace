package Models;
import java.util.ArrayList;
import Data.Config;
import Interfaces.IFactoryModels;
import Interfaces.ISpaceProvider;
import Interfaces.ISpaceWithTickProvider;

public class Space implements ISpaceProvider, ISpaceWithTickProvider {
    Patch[][] patches = new Patch[Config.SpaceRow][Config.SpaceCol];
    ArrayList<Agent> agents = new ArrayList<Agent>();
    private int Tick;


    public Space() {

        //-----------------------[creating Patches]--------------------------
        for (int i = 0; i < Config.SpaceRow; ++i) {
            for (int j = 0; j < Config.SpaceCol; ++j) {
                patches[i][j] = IFactoryModels.patchCreator(i, j);
            }
        }
        //------------------------[creating agents]--------------------------
        for(int i = 0; i < Config.InitializeAgentNum;){
            int x = (int)(Math.random() * Config.SpaceRow);
            int y = (int)(Math.random() * Config.SpaceCol);
            if(patches[x][y].getPAgent() == null){
                Agent agent = IFactoryModels.NormalAgentCreator(x, y);
                agents.add(agent);
                patches[x][y].setPAgent(agent);
                i++;
            }
        }
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
}


