package Rules;
import Data.Config;
import Interfaces.Patch.IPatch_GrowBack;

public class GrowBack {
    public static void growBack(IPatch_GrowBack[][] patches, int x1, int x2, int tick) {

        for (int i = x1; i < x2; i++) {
            for (int j = 0; j < Config.SpaceCol; j++) {

                //---[checking status of tick and interval then sugar and spice grow back]---
                if (tick % Config.SugarGrowBackInterval == 0) {
                    int newSugar = Math.min(patches[i][j].getResource().getPSugar() + Config.SugarGrowBackRate, patches[i][j].getResource().getMaxSugarCap());
                    patches[i][j].getResource().setPSugar(newSugar);
                }
                if (tick % Config.SpiceGrowBackInterval == 0) {
                    int newSpice = Math.min(patches[i][j].getResource().getPSpice() + Config.SpiceGrowBackRate, patches[i][j].getResource().getMaxSpiceCap());
                    patches[i][j].getResource().setPSpice(newSpice);
                }
            }
        }
    }
}
