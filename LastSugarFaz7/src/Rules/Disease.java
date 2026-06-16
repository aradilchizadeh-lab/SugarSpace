package Rules;

import Interfaces.IAgent_Disease;
import Interfaces.IPatch_AgentProvider;
import Interfaces.ISpace_Diseases;

import java.util.ArrayList;

import Data.AgeType;
import Data.Config;

public class Disease {
    private int[] SubImmuneSystem = new int[Config.ImmuneSystemSubsCount];
    private ArrayList<Integer> InfectedDiseases = new ArrayList<>();
    private ArrayList<Integer> PossibleDiseases = new ArrayList<>();

    public Disease() {
        long immuneSystem = (long) ((Math.random() * Math.pow(2, 49)) + Math.pow(2, 49)); //50 bit
        int length = Config.diseaseLength;
        int mask = (1 << length) -1;

        for (int i = 0; i < Config.ImmuneSystemSubsCount; ++i){
            int sub = (int)((immuneSystem >> i) & mask);
            SubImmuneSystem[i] = sub;
        }
    }

    public void disease(IAgent_Disease agent, ISpace_Diseases space) {

        improveImmunity(agent, space);
        if (!InfectedDiseases.isEmpty())
            infectOthers(agent, space);
    }

    private void improveImmunity(IAgent_Disease agent, ISpace_Diseases space) {
        ArrayList<Integer> Diseases = space.getDiseases();
        int randomDisease = Diseases.get((int)(Math.random() * Diseases.size()));

        int hamming = Integer.MAX_VALUE;
        int diff;
        int subIndex = 0;
        for (int i = 0; i < Config.ImmuneSystemSubsCount; ++i){
            diff = 0;
            diff = Integer.bitCount(SubImmuneSystem[i] ^ randomDisease);
            if (diff < hamming)
            {
                hamming = diff;
                subIndex = i;
            }
            if (hamming == 0)
                break;
        }
        if (hamming != 0)
        {
            int xor = SubImmuneSystem[subIndex] ^ randomDisease;
            int firstDifferentBit = (xor & -xor);
            SubImmuneSystem[subIndex] ^= firstDifferentBit;
    
            /*int increaseMetabolism = (int)(Math.random() * 3) + 1;
            if (agent.getAgeType() == AgeType.Child)
                increaseMetabolism = 0;
            randomDisease = randomDisease * 10 + increaseMetabolism;
            InfectedDiseases.add(randomDisease);
            if (agent.getAgeType() != AgeType.Child)
                diseaseSideEffects(agent, increaseMetabolism);*/
        }

        boolean isImmune;
        PossibleDiseases.clear();
        for (int i = 0; i < Diseases.size(); ++i){
            isImmune = false;
            for (int j = 0; j < Config.ImmuneSystemSubsCount; ++j){

                if (Diseases.get(i) == SubImmuneSystem[j])
                {
                    isImmune = true;
                    break;
                }   
            }
            //removing diseases from infected list that agent is ammune against them
            if (isImmune){
                for(int k = InfectedDiseases.size() - 1; k >= 0; --k){
                    if (InfectedDiseases.get(k) / 10 == Diseases.get(i)){

                        int effect = InfectedDiseases.get(k) % 10;
                        diseaseSideEffects(agent, -effect);
                        InfectedDiseases.remove(k);
                        break;
                    }
                }
            }
            else {
                PossibleDiseases.add(Diseases.get(i));
            }
        }
    }

    public void diseaseSideEffects(IAgent_Disease agent, int effect){
        agent.setSpiceMetabolism(agent.getSpiceMetabolism() + effect);
        agent.setSugarMetabolism(agent.getSugarMetabolism() + effect);
    }


    private void infectOthers(IAgent_Disease agent, ISpace_Diseases space){
        IPatch_AgentProvider[][] patches = space.getPatches();
        ArrayList<IAgent_Disease> neighbors = new ArrayList<>();
        addNeighbor(agent, patches, neighbors);
        if(neighbors.isEmpty())
            return;
        
        for(int i = 0; i < neighbors.size(); i++){

            int diseaseIndex = (int)(Math.random() * InfectedDiseases.size());
            IAgent_Disease neighbor  = neighbors.get(i);
            int agentDisease = agent.getInfectedDiseases().get(diseaseIndex) / 10;

            if(neighbor.getPossibleDiseases().contains(agentDisease) && !neighbor.getInfectedDiseases().contains(agentDisease)){
                
                int increaseMetabolism = (int)(Math.random() * 3) + 1;
                if (neighbor.getAgeType() == AgeType.Child)
                    increaseMetabolism = 0;
                agentDisease = agentDisease * 10 + increaseMetabolism;
                neighbor.getInfectedDiseases().add(agentDisease);

                if (neighbor.getAgeType() != AgeType.Child)
                    diseaseSideEffects(neighbor, increaseMetabolism);
                
            }
        }
    }

    private static void addNeighbor(IAgent_Disease a, IPatch_AgentProvider[][] patches, ArrayList<IAgent_Disease> neighbor) {
        int x = a.getX();
        int y = a.getY();
        for (int i = x - 1; i <= x + 1; ++i) {
            if (i < 0 || i > Config.SpaceRow - 1)
                continue;
            for (int j = y - 1; j <= y + 1; ++j) {
                if (j < 0 || j > Config.SpaceCol - 1 || (i == x && j == y))
                    continue;

                if (patches[i][j].getPAgent() != null && patches[i][j].getPAgent().getBehavior().canBeInfected() && (i == x || j == y))
                    neighbor.add(patches[i][j].getPAgent());
            }
        }
    }

    public ArrayList<Integer> getPossibleDiseases(){
        return PossibleDiseases;
    }

    public ArrayList<Integer> getInfectedDiseases(){
        return InfectedDiseases;
    }

    public void addInfectedDiseases(int disease){
        InfectedDiseases.add(disease);
    }

}
