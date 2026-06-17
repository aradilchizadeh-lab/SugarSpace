package Models;
import Data.Config;
import Interfaces.*;


public class Patch implements IPatch_GrowBack, IPatch_Emigration, IPatch_Production, IPatch_Aging, IPatch_Paint, IPatch_Trade, IPatch_Loan, IPatch_Disease{
    private int PSugar;
    private int PSpice;
    private int MaxSugarCap;
    private int MaxSpiceCap;
    private int Px;
    private int Py;
    private Agent PAgent;

    public Patch(int x , int y){
       Px = x;
       Py = y;
       MaxSugarCap = initializeMaxValues(Config.SugarHill_X1, Config.SugarHill_Y1, Config.SugarHill_X2, Config.SugarHill_Y2);
       MaxSpiceCap = initializeMaxValues(Config.SpiceHill_X1, Config.SpiceHill_Y1, Config.SpiceHill_X2, Config.SpiceHill_Y2);
       PSugar = MaxSugarCap;
       PSpice = MaxSpiceCap;
       PAgent = null;
    }

    public int getPSugar() {
        return PSugar;
    }

    public void setPSugar(int PSugar) {
        this.PSugar = PSugar;
    }

    public int getPSpice() {
        return PSpice;
    }

    public void setPSpice(int PSpice) {
        this.PSpice = PSpice;
    }

    public int getMaxSugarCap() {
        return MaxSugarCap;
    }

    public int getMaxSpiceCap() {
        return MaxSpiceCap;
    }

    public int getPx() {
        return Px;
    }

    public int getPy() {
        return Py;
    }

    public Agent getPAgent() {
        return PAgent;
    }

    public void setPAgent(Agent PAgent) {
        this.PAgent = PAgent;
    }

    private int initializeMaxValues(int x1, int y1, int x2, int y2) {

        double g1 = Config.MaxCap * Math.exp(-(Math.pow(Px - x1, 2) + Math.pow(Py - y1, 2)) / (2.0 * Config.Sigma * Config.Sigma));

        double g2 = Config.MaxCap * Math.exp(-(Math.pow(Px - x2, 2) + Math.pow(Py - y2, 2)) / (2.0 * Config.Sigma * Config.Sigma));

        int value = Math.min((int)Config.MaxCap, (int)Math.round(g1 + g2));

        return (value / 5) * 5;
    }
    
}
