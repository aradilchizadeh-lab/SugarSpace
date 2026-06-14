package Rules;

import Interfaces.IAgent_Disease;
import Interfaces.ISpace_Diseases;

import java.util.ArrayList;

public class Disease {
    private long ImmuneSystem = 0;
    private ArrayList<Integer> InfectedDisease = new ArrayList<>();
    private ArrayList<Integer> PossibleDisease = new ArrayList<>();

    public Disease() {
        ImmuneSystem = (long) ((Math.random() * Math.pow(2, 49)) + Math.pow(2, 49));
    }

    public void disease(IAgent_Disease agent, ISpace_Diseases space) {
        //add new immuneSystem Variable (Long) -> Long.parseLong(binary, 2)
    }

    private String improveImmunity(int Disease) {

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
        char[] immuneChars = immuneSystem.toCharArray();
        if (hamming != 0) {
            InfectedDisease.add(Disease);
            for (int i = 0; i < 10; i++) {
                if (immuneChars[i + startIndex] != disease.charAt(i)) {
                    immuneChars[i + startIndex] = disease.charAt(i);
                    break;
                }
            }
        }

        return new String(immuneChars);



    }

    private void DiseaseClassification(String immuneSystem, ISpace_Diseases space) {
        ArrayList<Integer> diseases = space.getDiseases();
        for (int i = 0; i < 10; i++) {
            String disease = Integer.toBinaryString(diseases.get(i));
            PossibleDisease.clear();
            if (immuneSystem.contains(disease)) {
                InfectedDisease.remove(Integer.valueOf(disease));
            }
            else PossibleDisease.add(Integer.valueOf(disease));
        }
    }
}
