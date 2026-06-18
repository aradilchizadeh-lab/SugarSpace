package Models;

import Interfaces.Patch.IPatch_ResourceUpdate;


public class PatchResources implements IPatch_ResourceUpdate{
    private int PSugar;
    private int PSpice;
    private int MaxSugarCap;
    private int MaxSpiceCap;

    public PatchResources(int maxSugar, int maxSpice ){
        MaxSugarCap = maxSugar;
        MaxSpiceCap = maxSpice;
       PSugar = MaxSugarCap;
       PSpice = MaxSpiceCap;
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

    
    
}
