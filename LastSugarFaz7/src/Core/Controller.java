package Core;

import Data.Config;
import GUI.Ending;
import GUI.Histogram;
import GUI.Paint;
import GUI.StdDraw;
import Interfaces.IFactoryModels;
import Models.Agent;
import Models.Space;
import Rules.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller{

    public static void controller() throws InterruptedException {
        //---[creating space]---
        Space space = IFactoryModels.spaceCreator();
        ArrayList<Agent> agents = space.getAgents();
        //---[creating paint space]---
        StdDraw.setCanvasSize(Config.CanvasSizeWidth, Config.CanvasSizeHeight);
        StdDraw.setXscale(0, Config.SpaceRow);
        StdDraw.setYscale(0, Config.SpaceCol + 2);
        StdDraw.enableDoubleBuffering();

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

            space.setTick();
            Paint.rePaint(space);
            if (agents.size() == 0) System.out.println(tick);
        }
        for (int i = 0; i < agents.size(); i++) {
            agents.get(i).print();
        }

        //---[drawing histogram]---
        StdDraw.clear(StdDraw.BLACK);
        Histogram.saveFileWealth(space);
        Histogram.processAndDraw();
        StdDraw.show();

        Ending.ending();
    }
}
