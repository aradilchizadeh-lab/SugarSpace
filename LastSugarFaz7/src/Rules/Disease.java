package Rules;

import Interfaces.IAgent_Disease;
import Interfaces.ISpaceProvider;
import Interfaces.ISpace_Diseases;

import java.util.ArrayList;

public class Disease {
    private long ImmuneSystem = 0;
    private ArrayList<Short> InfectedDisease = new ArrayList<>();
    private ArrayList<Short> PossibleDisease = new ArrayList<>();

    public Disease(){
        ImmuneSystem = (long) ((Math.random() * Math.pow(2, 49)) + Math.pow(2, 49));
    }

    public void disease(IAgent_Disease agent, ISpace_Diseases space){
         //check Immune system
        //contagion
    }
}
