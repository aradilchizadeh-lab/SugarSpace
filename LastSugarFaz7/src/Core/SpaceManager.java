package Core;

import Data.Config;
import GUI.Paint;
import Interfaces.IFactoryModels;
import Models.Agent;
import Models.Patch;
import Rules.GrowBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SpaceManager {

    private Patch[][] patches;
    private ArrayList<Agent> agents;
    private ArrayList<Integer> diseases;
    private int tick;

    public SpaceManager() {
        patches = new Patch[Config.SpaceRow][Config.SpaceCol];
        agents = new ArrayList<>();
        diseases = new ArrayList<>();
        tick = 0;

        initializePatches();
        initializeDiseases();
        initializeAgents();
    }

    private void initializePatches() {
        for (int i = 0; i < Config.SpaceRow; i++) {
            for (int j = 0; j < Config.SpaceCol; j++) {
                patches[i][j] = IFactoryModels.patchCreator(i, j);
            }
        }
    }

    private void initializeDiseases() {
        for (int i = 0; i < Config.diseaseNum; ) {
            int disease = (int) ((Math.random() * Math.pow(2, 9)) + Math.pow(2, 9));

            if (!diseases.contains(disease)) {
                diseases.add(disease);
                i++;
            }
        }
    }

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

    public void runSimulation() throws InterruptedException {
        while (tick < Config.Tick) {
            tick++;

            ExecutorService executor = Executors.newFixedThreadPool(4);
            executor.submit(() -> GrowBack.growBack(patches, 0, 13, tick));
            executor.submit(() -> GrowBack.growBack(patches, 13, 26, tick));
            executor.submit(() -> GrowBack.growBack(patches, 26, 39, tick));
            executor.submit(() -> GrowBack.growBack(patches, 39, 51, tick));

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.SECONDS);

            runAgentsRules();

            Paint.rePaint(patches, agents, tick);

            if (agents.size() == 0) {
                System.out.println(tick);
            }
        }
    }

    private void runAgentsRules() {

        Collections.shuffle(agents);

        ArrayList<Agent> updatedAgents = new ArrayList<>(agents);

        for (int i = updatedAgents.size() - 1; i >= 0; i--) {

            Agent agent = updatedAgents.get(i);

            agent.emigration(patches, updatedAgents);
            if (!updatedAgents.contains(agent)) continue;

            agent.production(patches, updatedAgents);

            agent.trade(patches);

            agent.loan(patches, tick);

            agent.disease(patches, diseases);

            agent.aging(patches, updatedAgents);
        }

        agents = updatedAgents;
    }


    public ArrayList<Agent> getAgents() {
        return agents;
    }
}

