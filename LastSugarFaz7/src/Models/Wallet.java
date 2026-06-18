package Models;

import Interfaces.Agent.IResourceUpdate;

public class Wallet implements IResourceUpdate{

    private final int InitSugar;
    private final int InitSpice;
    private int ASugar;
    private int ASpice;

    public Wallet(int initSugar, int initSpice){
        InitSugar = initSugar;
        ASugar = InitSugar;
        InitSpice = initSpice;
        ASpice = InitSpice;
    }

    public int getInitSugar(){
        return InitSugar;
    }

    public int getInitSpice(){
        return InitSpice;
    }

    public int getASugar(){
        return ASugar;
    }

    public int getASpice(){
        return ASpice;
    }

    public void setASugar(int sugar){
        ASugar = sugar;
    }

    public void setASpice(int spice){
        ASpice = spice;
    }

}
