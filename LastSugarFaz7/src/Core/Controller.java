package Core;

import Data.Config;
import GUI.Ending;
import GUI.Histogram;
import GUI.StdDraw;
import Interfaces.IFactoryModels;

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
        Histogram.saveFileWealth(spaceManager.getAgents());
        Histogram.processAndDraw();
        StdDraw.show();

        //---[Show ending]---
        Ending.ending();
    }
}
