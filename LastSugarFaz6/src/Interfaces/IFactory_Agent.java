package Interfaces;

import Models.Agent;
import Models.NormalAgent;

public interface IFactory_Agent {

    public static Agent childCreator(int babyX, int babyY, int bSuger, int bSpice, int bVision, float bSuMetabolism, float bSpMetabolism){
        NormalAgent baby = new NormalAgent(babyX, babyY, bSuger, bSpice, bVision, bSuMetabolism, bSpMetabolism);
        return baby;
    }

    public static Agent agentCreator(int x, int y){
        NormalAgent agent = new NormalAgent(x, y, (int)(Math.random() * 21) + 5, (int)(Math.random() * 21) + 5,
                (int)(Math.random() * 10) + 1, (int)(Math.random() * 4) + 1, (int)(Math.random() * 4) + 1);
        return agent;
    }

}
