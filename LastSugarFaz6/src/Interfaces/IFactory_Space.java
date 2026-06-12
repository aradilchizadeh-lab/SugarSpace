package Interfaces;

import Models.Space;

public interface IFactory_Space {
    public static Space spaceCreator(){
        return new Space();
    }
}
