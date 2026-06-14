package Rules;

import Interfaces.IAgent_Disease;
import Interfaces.IAgent_Trade;
import Interfaces.IPatch_Disease;
import Interfaces.IPatch_Trade;
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

    public static void disease(IAgent_Disease agent, ISpace_Diseases space) {
        //add new immuneSystem Variable (Long) -> Long.parseLong(binary, 2)
        //int randomDisease = Integer.parseInt(space.getDiseases().get((int)(Math.random()*11)))
        //improveImmunity()
    }

    private String improveImmunity(int Disease) { //random disease from the list

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
            for (int i = 0; i < 10; i++) {
                if (newImmuneSystemChars[i + startIndex] != disease.charAt(i)) {
                    newImmuneSystemChars[i + startIndex] = disease.charAt(i);
                    break;
                }
            }
        }

        return new String(newImmuneSystemChars);



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

    private void infectOthers(){ //F

    }

    private static void addNeighbor(IAgent_Disease a, IPatch_Disease[][] patches, ArrayList<IAgent_Disease> neighbor) {
        int x = a.getX();
        int y = a.getY();
        for (int i = x - 1; i <= x + 1; ++i) {
            if (i < 0 || i > Config.SpaceRow - 1)
                continue;
            for (int j = y - 1; j <= y + 1; ++j) {
                if (j < 0 || j > Config.SpaceCol - 1 || (i == x && j == y))
                    continue;

                if (patches[i][j].getPAgent() != null && patches[i][j].getPAgent().getBehavior().canTrade() && (i == x || j == y))
                    neighbor.add((IAgent_Disease) patches[i][j].getPAgent());
            }
        }
    }

}
