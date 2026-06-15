package Rules;

import Data.AgeType;
import Interfaces.IAgent_Disease;
import Interfaces.IPatch_AgentProvider;
import Interfaces.ISpace_Diseases;

import java.util.ArrayList;

import Data.Config;

public class Disease {
    private long ImmuneSystem = 0;
    private ArrayList<Integer> InfectedDiseases = new ArrayList<>();
    private ArrayList<Integer> PossibleDiseases = new ArrayList<>();

    public Disease() {
        ImmuneSystem = (long) ((Math.random() * Math.pow(2, 49)) + Math.pow(2, 49)); //50 bit
    }

    public void disease(IAgent_Disease agent, ISpace_Diseases space) {
        //add new immuneSystem Variable (Long) -> Long.parseLong(binary, 2)
        ArrayList<Integer> Diseases = new ArrayList<>(space.getDiseases().keySet());
        int randomDisease = Diseases.get((int)(Math.random() * Diseases.size()));
       // if (agent.getInfectedDiseases().size() == 0){
            String newImmunity = improveImmunity(randomDisease, agent, space );
            ImmuneSystem = Long.parseLong(newImmunity, 2); //update immuneSystem
            diseaseClassification(newImmunity, space, agent);
       // }
        infectOthers(agent, space);
    }

    private String improveImmunity(int Disease ,IAgent_Disease agent, ISpace_Diseases space) { //random disease from the list

        String immuneSystem = Long.toBinaryString(ImmuneSystem);
        String disease = Integer.toBinaryString(Disease);
        int hamming = Integer.MAX_VALUE;
        int diff = 0;
        int startIndex = 0;
        for (int i = 0; i < 41; i++) {
            String test = immuneSystem.substring(i, i + 10);
            hamming = Integer.MAX_VALUE;
            diff = 0;
            for (int j = 0; j < disease.length(); j++) {
                if (disease.charAt(j) != test.charAt(j)) {
                    diff++;
                }
            }
            if (diff < hamming) {
                hamming = diff;
                startIndex = i;
            }
            if (hamming == 0) {
                break;
            }
        }
        char[] newImmuneSystemChars = immuneSystem.toCharArray();
        if (hamming != 0) {
            if(!InfectedDiseases.contains(Disease)){
            InfectedDiseases.add(Disease);
            if(agent.getAgeType() != AgeType.Child) {
                agent.setSugarMetabolism(agent.getSugarMetabolism() + space.getDiseases().get(Disease));
                agent.setSpiceMetabolism(agent.getSpiceMetabolism() + space.getDiseases().get(Disease));
            }
            for (int i = 0; i < 10; i++) {
                if (newImmuneSystemChars[i + startIndex] != disease.charAt(i)) {
                    newImmuneSystemChars[i + startIndex] = disease.charAt(i);
                    break;
                }
            }
            }
        }

        return new String(newImmuneSystemChars);
    }

    /*public void diseaseSideEffects(IAgent_Disease agent, ISpace){
        if(agent.getAgeType() == AgeType.Child) return;
        int increaseSugar = space.;
        int increaseSpice = (int)(Math.random() * 3) + 1;
        agent.setSugarMetabolism(agent.getSugarMetabolism() + increaseSugar);
        agent.setSpiceMetabolism(agent.getSpiceMetabolism() + increaseSpice);
    }*/

    private void diseaseClassification(String immuneSystem, ISpace_Diseases space ,IAgent_Disease agent) {
        ArrayList<Integer> diseases = new ArrayList<>(space.getDiseases().keySet());
        PossibleDiseases.clear();
        for (int i = 0; i < 10; i++) {
            int disease = diseases.get(i);
            String diseaseTemp = Integer.toBinaryString(disease);
            if (immuneSystem.contains(diseaseTemp)) {
                InfectedDiseases.remove(Integer.valueOf(disease));
                if(agent.getAgeType() != AgeType.Child) {
                    agent.setSugarMetabolism(Math.max(agent.getSugarMetabolism() - space.getDiseases().get(disease), 1));
                    agent.setSpiceMetabolism(Math.max(agent.getSpiceMetabolism() - space.getDiseases().get(disease), 1));
                }
            }
            else PossibleDiseases.add(disease);
        }
    }

    private void infectOthers(IAgent_Disease agent, ISpace_Diseases space){ //F
        IPatch_AgentProvider[][] patches = space.getPatches();
        ArrayList<IAgent_Disease> neighbors = new ArrayList<>();
        addNeighbor(agent, patches, neighbors);
        if(neighbors.isEmpty() || InfectedDiseases.isEmpty())
            return;
        
        for(int i = 0; i < neighbors.size(); i++){
            int randomDisease = (int)(Math.random() * InfectedDiseases.size());
             if(neighbors.get(i).getPossibleDiseases().contains(agent.getInfectedDiseases().get(randomDisease)) 
                && !neighbors.get(i).getInfectedDiseases().contains(agent.getInfectedDiseases().get(randomDisease)))
                //&& neighbors.get(i).getInfectedDiseases().size() == 0)
             {
                 neighbors.get(i).getInfectedDiseases().add(agent.getInfectedDiseases().get(randomDisease));
                 if (agent.getAgeType() != AgeType.Child) {
                     neighbors.get(i).setSugarMetabolism(neighbors.get(i).getSugarMetabolism() + space.getDiseases().get(agent.getInfectedDiseases().get(randomDisease)));
                     neighbors.get(i).setSpiceMetabolism(neighbors.get(i).getSpiceMetabolism() + space.getDiseases().get(agent.getInfectedDiseases().get(randomDisease)));
                 }
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
                    neighbor.add((IAgent_Disease) patches[i][j].getPAgent());
            }
        }
    }

    public ArrayList<Integer> getPossibleDiseases(){
        return PossibleDiseases;
    }

    public ArrayList<Integer> getInfectedDiseases(){
        return InfectedDiseases;
    }

}
