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
        SpaceManager spaceManager = IFactoryModels.spaceManagerCreator();
        //---[creating paint space]---
        StdDraw.setCanvasSize(Config.CanvasSizeWidth, Config.CanvasSizeHeight);
        StdDraw.setXscale(0, Config.SpaceRow);
        StdDraw.setYscale(0, Config.SpaceCol + 2);
        StdDraw.enableDoubleBuffering();

        //---[call the main Simulation loop ]---
        spaceManager.runSimulation();

        //---[drawing histogram]---
        StdDraw.clear(StdDraw.BLACK);
        Histogram.saveFileWealth(spaceManager.getSpace());
        Histogram.processAndDraw();
        StdDraw.show();

        //---[Show ending]---
        Ending.ending();
    }
}
