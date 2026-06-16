package Rules;

import Interfaces.IAgent_Disease;
import Interfaces.IPatch_AgentProvider;
import Interfaces.ISpace_Diseases;

import java.util.ArrayList;

import Data.AgeType;
import Data.Config;

public class Disease {
    //private int[] SubImmuneSystem = new int[Config.ImmuneSystemSubsCount];
    private ArrayList<Integer> InfectedDiseases = new ArrayList<>();
    private ArrayList<Integer> PossibleDiseases = new ArrayList<>();
    private long immuneSystem;

    public Disease() {
        immuneSystem = (long) ((Math.random() * Math.pow(2, 49)) + Math.pow(2, 49)); //50 bit
    }

    public void disease(IAgent_Disease agent, ISpace_Diseases space) {

        improveImmunity(agent, space);
        if (!InfectedDiseases.isEmpty())
            infectOthers(agent, space);
    }

    private void improveImmunity(IAgent_Disease agent, ISpace_Diseases space) {

        ArrayList<Integer> Diseases = space.getDiseases();
        int randomDisease = Diseases.get((int) (Math.random() * Diseases.size()));
        int hamming = Integer.MAX_VALUE;
        int diff;
        int startIndex = 0;
        String immune_system = String.format("%50s", Long.toBinaryString(immuneSystem)).replace(' ', '0');
        String disease = String.format("%" + Config.diseaseLength + "s",
                Integer.toBinaryString(randomDisease)).replace(' ', '0');
        String sub;

        for (int i = 0; i < Config.ImmuneSystemSubsCount; ++i) {
            diff = 0;
            sub = immune_system.substring(i, i + Config.diseaseLength);
            for (int j = 0; j < Config.diseaseLength; j++) {
                if (sub.charAt(j) != disease.charAt(j)) {
                    diff++;
                }
            }
            if (diff < hamming) {
                hamming = diff;
                startIndex = i;
            }
            if (hamming == 0)
                break;
        }
        char[] immuneSystemChars = immune_system.toCharArray();
        if (hamming != 0) {
            for (int i = 0; i < Config.diseaseLength; i++) {
                if (immuneSystemChars[i + startIndex] != disease.charAt(i)) {
                    immuneSystemChars[i + startIndex] = disease.charAt(i);
                    break;
                }
            }
        }
        String newImmuneSystem = new String(immuneSystemChars);
        String targetDisease;
        boolean isImmune;
        PossibleDiseases.clear();
        for (int i = 0; i < Config.diseaseNum; ++i) {
            isImmune = false;
            targetDisease = String.format("%" + Config.diseaseLength + "s",
                    Integer.toBinaryString(Diseases.get(i))).replace(' ', '0');
            if (newImmuneSystem.contains(targetDisease)) {
                isImmune = true;
            }
            //removing diseases from infected list that agent is ammune against them
            if (isImmune) {
                for (int k = InfectedDiseases.size() - 1; k >= 0; --k) {
                    if (InfectedDiseases.get(k) / 10 == Diseases.get(i)) {

                        int effect = InfectedDiseases.get(k) % 10;
                        diseaseSideEffects(agent, -effect);
                        InfectedDiseases.remove(k);
                        break;
                    }
                }
            } else {
                PossibleDiseases.add(Diseases.get(i));
            }
        }
        immuneSystem = Long.parseLong(newImmuneSystem, 2);
    }

    public void diseaseSideEffects(IAgent_Disease agent, int effect) {
        agent.setSpiceMetabolism(agent.getSpiceMetabolism() + effect);
        agent.setSugarMetabolism(agent.getSugarMetabolism() + effect);
    }


    private void infectOthers(IAgent_Disease agent, ISpace_Diseases space) {
        IPatch_AgentProvider[][] patches = space.getPatches();
        ArrayList<IAgent_Disease> neighbors = new ArrayList<>();
        addNeighbor(agent, patches, neighbors);
        if (neighbors.isEmpty())
            return;

        for (int i = 0; i < neighbors.size(); i++) {

            int diseaseIndex = (int) (Math.random() * InfectedDiseases.size());
            IAgent_Disease neighbor = neighbors.get(i);
            int agentDisease = InfectedDiseases.get(diseaseIndex) / 10;

            boolean alreadyInfected = false;

            for (int d : neighbor.getInfectedDiseases()) {
                if (d / 10 == agentDisease) {
                    alreadyInfected = true;
                    break;
                }
            }

            if (neighbor.getPossibleDiseases().contains(agentDisease) && !alreadyInfected) {

                int increaseMetabolism = (int) (Math.random() * 3) + 1;
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

    public ArrayList<Integer> getPossibleDiseases() {
        return PossibleDiseases;
    }

    public ArrayList<Integer> getInfectedDiseases() {
        return InfectedDiseases;
    }

    public void addInfectedDiseases(int disease) {
        InfectedDiseases.add(disease);
    }

}
