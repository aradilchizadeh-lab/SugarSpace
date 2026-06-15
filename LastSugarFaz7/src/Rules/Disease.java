package Rules;

import Interfaces.IAgent_Disease;
import Interfaces.IPatch_AgentProvider;
import Interfaces.ISpace_Diseases;

import java.util.ArrayList;

import Data.AgeType;
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
        int randomDisease = space.getDiseases().get((int)(Math.random() * space.getDiseases().size()));
        if (agent.getInfectedDiseases().size() == 0){
            String newImmunity = improveImmunity(randomDisease, agent );
            ImmuneSystem = Long.parseLong(newImmunity, 2); //update immuneSystem
            diseaseClassification(newImmunity, space);
        }
        infectOthers(agent, space);
    }

    private String improveImmunity(int Disease ,IAgent_Disease agent) { //random disease from the list

        String immuneSystem = Long.toBinaryString(ImmuneSystem);
        String disease = Integer.toBinaryString(Disease);
        int hamming = Integer.MAX_VALUE;
        int diff = 0;
        int startIndex = 0;
        for (int i = 0; i < 41; i++) {
            String test = immuneSystem.substring(i, i + 10);
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
            InfectedDiseases.add(Disease);
            diseaseSideEffects(agent);
            for (int i = 0; i < 10; i++) {
                if (newImmuneSystemChars[i + startIndex] != disease.charAt(i)) {
                    newImmuneSystemChars[i + startIndex] = disease.charAt(i);
                    break;
                }
            }
        }

        return new String(newImmuneSystemChars);
    }

    public void diseaseSideEffects(IAgent_Disease agent){
        if(agent.getAgeType() == AgeType.Child) return;
        int increaseSugar = (int)(Math.random() * 3) + 1;
        int increaseSpice = (int)(Math.random() * 3) + 1;
        agent.setSugarMetabolism(agent.getSugarMetabolism() + increaseSugar);
        agent.setSpiceMetabolism(agent.getSpiceMetabolism() + increaseSpice);
    }

    private void diseaseClassification(String immuneSystem, ISpace_Diseases space) {
        ArrayList<Integer> diseases = space.getDiseases();
        for (int i = 0; i < 10; i++) {
            String disease = Integer.toBinaryString(diseases.get(i));
            PossibleDiseases.clear();
            if (immuneSystem.contains(disease)) {
                InfectedDiseases.remove(Integer.valueOf(disease));
            }
            else PossibleDiseases.add(Integer.valueOf(disease));
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
                && !neighbors.get(i).getInfectedDiseases().contains(agent.getInfectedDiseases().get(randomDisease))
                && neighbors.get(i).getInfectedDiseases().size() == 0)
            {
                neighbors.get(i).getInfectedDiseases().add(agent.getInfectedDiseases().get(randomDisease));
                diseaseSideEffects(neighbors.get(i));
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
