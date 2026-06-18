package GUI;


import Data.Config;
import Interfaces.IAgent_Paint;
import Interfaces.IPatch_Paint;
import Models.Agent;

import java.util.ArrayList;

public class Paint {
    
    public static void rePaint(IPatch_Paint[][] patches, ArrayList<Agent> agents, int tick)
    {
        IAgent_Paint patchAgent;
        StdDraw.clear();
        
        for (int i = 0; i < Config.SpaceRow; ++i)
        {
            for (int j = 0; j < Config.SpaceCol; ++j)
            {
                int sugar = patches[i][j].getPSugar();
                int spice = patches[i][j].getPSpice();
                float ratio = (float)(sugar / Config.MaxCap);
                float ratio2 = (float)(spice / Config.MaxCap);

                int red = 255 - (int)(20 * ratio);
                int grean = 255 - (int)(170 * ratio);
                int blue = 255 - (int)(255 * ratio);

                StdDraw.setPenColor(red, grean, blue);
                StdDraw.filledRectangle(i + 0.25, j + 0.5, 0.25, 0.5);

                red = 255 - (int)(180 * ratio2);
                grean = 255 - (int)(220 * ratio2);
                blue = 255 - (int)(120 * ratio2);

                StdDraw.setPenColor(red, grean, blue);
                StdDraw.filledRectangle(i + 0.75 , j + 0.5, 0.25, 0.5);
                
                patchAgent = (IAgent_Paint) patches[i][j].getPAgent();

                if (patches[i][j].getPAgent() != null)
                {
                    if (patchAgent.getAge() >= patchAgent.getFertileLimitMax())
                    {
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.setPenRadius(0.015);
                        StdDraw.point(i + 0.5, j + 0.5);
                    }
                    else if(patchAgent.getGender() == 0)
                    {
                        StdDraw.setPenColor(StdDraw.RED);
                        StdDraw.setPenRadius(0.015);
                        StdDraw.point(i + 0.5, j + 0.5);
                    }
                    else if (patchAgent.getGender() == 1)
                    {
                        StdDraw.setPenColor(StdDraw.BLUE);
                        StdDraw.setPenRadius(0.015);
                        StdDraw.point(i + 0.5, j + 0.5);
                    }
                }
                
            }
        }
        String ticks= String.valueOf(tick);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(5, Config.SpaceRow + 1, "Tick: ");
        StdDraw.text(7.5, Config.SpaceRow + 1, ticks);

        String agentsAmount= String.valueOf(agents.size());
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(14, Config.SpaceRow + 1, "Agents: ");
        StdDraw.text(17.5, Config.SpaceRow + 1, agentsAmount);

        StdDraw.show();
        StdDraw.pause(1);


    }



}
