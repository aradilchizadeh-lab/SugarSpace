package Core;

import Data.Config;
import GUI.Ending;
import GUI.Histogram;
import GUI.Paint;
import GUI.StdDraw;
import Interfaces.IFactoryModels;
import Models.Agent;
import Models.Patch;
import Models.Space;
import Rules.GrowBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SpaceManager {

    private Space space;
    private Patch[][] patches;
    private ArrayList<Agent> agents;
    private ArrayList<Integer> diseases;

    public SpaceManager(){
        space = IFactoryModels.spaceCreator();
        patches = space.getPatches();
        agents = space.getAgents();
        diseases = space.getDiseases();

        initializePatches();
        initializeDiseases();
        initializeAgents();
    }

    //-----------------------[creating Patches]--------------------------
    private void initializePatches() {
        for (int i = 0; i < Config.SpaceRow; ++i) {
            for (int j = 0; j < Config.SpaceCol; ++j) {
                patches[i][j] = IFactoryModels.patchCreator(i, j);
            }
        }
    }
    //------------------------[creating diseases]--------------------------
    private void initializeDiseases() {
        for (int i = 0; i < Config.diseaseNum; ) {
            int disease = (int) ((Math.random() * Math.pow(2, 9)) + Math.pow(2, 9));
            if (!diseases.contains(disease)) {
                diseases.add(disease);
                i++;
            }
        }
    }
    //------------------------[creating agents]--------------------------
    private void initializeAgents() {
        for (int i = 0; i < Config.InitializeAgentNum; ) {
            int x = (int) (Math.random() * Config.SpaceRow);
            int y = (int) (Math.random() * Config.SpaceCol);
            if (patches[x][y].getPAgent() == null) {
                int randomIndex = (int) (Math.random() * diseases.size());
                Agent agent = IFactoryModels.NormalAgentCreator(x, y, diseases.get(randomIndex));
                agents.add(agent);
                patches[x][y].setPAgent(agent);
                i++;
            }
        }
    }

    //------------------------[start the main Simulation loop ]--------------------------
    public void runSimulation() throws InterruptedException {
        int tick = 0;

        while (tick < Config.Tick) {

            ++tick;
            int finalTick = tick;
            //---[grow back with threads]---
            ExecutorService executor = Executors.newFixedThreadPool(4);
            executor.submit(() -> GrowBack.growBack(space, 0, 13, finalTick));
            executor.submit(() -> GrowBack.growBack(space, 13, 26, finalTick));
            executor.submit(() -> GrowBack.growBack(space, 26, 39, finalTick));
            executor.submit(() -> GrowBack.growBack(space, 39, 51, finalTick));

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.SECONDS);

            Collections.shuffle(agents);

            runAgentsRules();

            space.setTick();
            Paint.rePaint(space);
            if (agents.size() == 0) {
                System.out.println(tick);
            }
        }

    }

    private void runAgentsRules() {
        for (int i = agents.size() - 1; i >= 0; i--) {
            agents.get(i).emigration(space);
        }

        for (int i = agents.size() - 1; i >= 0; i--) {
            agents.get(i).production(space);
        }

        for (int i = agents.size() - 1; i >= 0; i--) {
            agents.get(i).trade(space);
        }

        for (int i = agents.size() - 1; i >= 0; i--) {
            agents.get(i).loan(space);
        }

        for (int i = agents.size() - 1; i >= 0; i--) {
            agents.get(i).disease(space);
        }

        for (int i = agents.size() - 1; i >= 0; i--) {
            agents.get(i).aging(space);
        }
    }

    public Space getSpace() {
        return space;
    }

    public ArrayList<Agent> getAgents() {
        return agents;
    }
}