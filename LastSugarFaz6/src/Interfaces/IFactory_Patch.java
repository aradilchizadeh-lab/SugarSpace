package Interfaces;

import Models.Patch;

public interface IFactory_Patch {

    public static Patch patchCreator(int x, int y){
        return new Patch(x, y);
    }
}
