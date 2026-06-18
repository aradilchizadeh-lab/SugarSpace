package Models;
import Data.Config;
import Interfaces.*;


public class Patch implements IPatch_GrowBack, IPatch_Emigration, IPatch_Production, IPatch_Aging, IPatch_Paint, IPatch_Trade, IPatch_Loan, IPatch_Disease{
    
    private PatchPosition Position;
    private PatchResources Resources;
    private Agent PAgent;

    public Patch(PatchPosition patchPosition){
        
       int maxSugarCap = initializeMaxValues(Config.SugarHill_X1, Config.SugarHill_Y1, Config.SugarHill_X2, Config.SugarHill_Y2);
       int maxSpiceCap = initializeMaxValues(Config.SpiceHill_X1, Config.SpiceHill_Y1, Config.SpiceHill_X2, Config.SpiceHill_Y2);
       Resources = IFactoryModels.patchResourcesCreator(maxSugarCap, maxSpiceCap);

       Position = patchPosition;
       PAgent = null;
    }

    public Agent getPAgent() {
        return PAgent;
    }

    public void setPAgent(Agent PAgent) {
        this.PAgent = PAgent;
    }

    private int initializeMaxValues(int x1, int y1, int x2, int y2) {

        double g1 = Config.MaxCap * Math.exp(-(Math.pow(Position.getX() - x1, 2) + Math.pow(Position.getY() - y1, 2)) / (2.0 * Config.Sigma * Config.Sigma));

        double g2 = Config.MaxCap * Math.exp(-(Math.pow(Position.getX() - x2, 2) + Math.pow(Position.getY() - y2, 2)) / (2.0 * Config.Sigma * Config.Sigma));

        int value = Math.min((int)Config.MaxCap, (int)Math.round(g1 + g2));

        return (value / 5) * 5;
    }
}
