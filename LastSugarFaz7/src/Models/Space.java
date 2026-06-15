package Models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Data.Config;
import Interfaces.IFactoryModels;
import Interfaces.ISpaceProvider;
import Interfaces.ISpaceWithTickProvider;
import Interfaces.ISpace_Diseases;
import Rules.Disease;

public class Space implements ISpaceProvider, ISpaceWithTickProvider, ISpace_Diseases {
    Patch[][] patches = new Patch[Config.SpaceRow][Config.SpaceCol];
    ArrayList<Agent> agents = new ArrayList<Agent>();
    Map<Integer, Integer> diseases = new HashMap<>();
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

        for (int i = 0; i < Config.diseaseNum;){
            int disease = (int) ((Math.random() * Math.pow(2, 9)) + Math.pow(2, 9));
            if(!diseases.containsKey(disease)) {
                diseases.put(disease, (int) (Math.random() * 3 + 1));
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

    public Map<Integer, Integer> getDiseases(){
        return diseases;
    }
}


