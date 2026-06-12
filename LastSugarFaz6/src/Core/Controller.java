package Core;

import Data.Config;
import Data.LoanInfoList;
import GUI.Histogram;
import GUI.Paint;
import GUI.StdDraw;
import Interfaces.IFactory;
import Models.Space;
import Rules.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller{

    public static void controller() throws InterruptedException {
        //---[creating space]---
        Space space = IFactory.spaceCreator();
        //---[creating paint space]---
        StdDraw.setCanvasSize(Config.CanvasSizeWidth,Config.CanvasSizeHeight);
        StdDraw.setXscale(0,Config.SpaceRow);
        StdDraw.setYscale(0,Config.SpaceCol + 2);
        StdDraw.enableDoubleBuffering();

        int tick = 0;
        while(tick <= Config.Tick){
            ++tick;
            int finalTick = tick;
            //---[grow back with threads]---
            ExecutorService executor = Executors.newFixedThreadPool(4);
            executor.submit(() -> GrowBack.growBack(space,0,13, finalTick));
            executor.submit(() -> GrowBack.growBack(space,13,26,finalTick));
            executor.submit(() -> GrowBack.growBack(space,26,39,finalTick));
            executor.submit(() -> GrowBack.growBack(space,39,51,finalTick));

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.SECONDS);

            //---[rest of the rules]---
            Emigration.emigrate(space);
            Production.production(space);
            Trade.trade(space);
            Loan.loan(space);
            Aging.ageRule(space);
            Paint.rePaint(space);
            space.setTick();

        }

        //---[drawing histogram]---
        StdDraw.clear();
        Histogram.saveFileWealth(space);
        Histogram.processAndDraw();
        StdDraw.show();
    }


}
