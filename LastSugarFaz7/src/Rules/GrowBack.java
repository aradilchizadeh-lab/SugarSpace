package Rules;
import Data.Config;
import Interfaces.IPatch_GrowBack;
import Interfaces.ISpaceProvider;

public class GrowBack {
    public static void growBack(ISpaceProvider space, int x1, int x2, int tick) {
        IPatch_GrowBack[][] patches = space.getPatches();
        for (int i = x1; i < x2; i++) {
            for (int j = 0; j < Config.SpaceCol; j++) {
                //---[checking status of tick and interval then sugar and spice grow back]---
                if (tick % Config.SugarGrowBackInterval == 0) {
                    int newSugar = Math.min(patches[i][j].getPSugar() + Config.SugarGrowBackRate, patches[i][j].getMaxSugarCap());
                    patches[i][j].setPSugar(newSugar);
                }
                if (tick % Config.SpiceGrowBackInterval == 0) {
                    int newSpice = Math.min(patches[i][j].getPSpice() + Config.SpiceGrowBackRate, patches[i][j].getMaxSpiceCap());
                    patches[i][j].setPSpice(newSpice);
                }
            }
        }
    }
}
