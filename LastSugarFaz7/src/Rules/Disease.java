package Rules;

import Interfaces.IAgent_Disease;
import Interfaces.ISpaceProvider;
import Interfaces.ISpace_Diseases;

import java.util.ArrayList;
import java.util.Scanner;

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
    private String improveImmunity(Long Disease) {
        String immuneSystem = Long.toBinaryString(ImmuneSystem);
        String disease = Long.toBinaryString(Disease);
        int hamming = Integer.MAX_VALUE;
        int dist = 0;
        int startIndex = 0;
        for (int i = 0; i < 41; i++) {
            String test = immuneSystem.substring(i, i + 10);
            for (int j = 0; j < disease.length(); j++) {
                if (disease.charAt(j) != test.charAt(j)) {
                    dist++;
                }
            }
            if (dist < hamming) {
                hamming = dist;
                startIndex = i;
            }
            if (hamming == 0) {
                break;
            }
        }
        char[] immune = immuneSystem.toCharArray();
        if (hamming != 0) {
            for (int i = 0; i < 10; i++) {
                if (immune[i + startIndex] != disease.charAt(i)) {
                    immune[i + startIndex] = disease.charAt(i);
                    break;
                }
            }
        }

        return new String(immune);

    }


}
