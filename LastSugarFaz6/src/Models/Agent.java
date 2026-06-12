package Models;

import Interfaces.IAgent;
import Interfaces.IAgent_Aging;

public abstract class Agent implements IAgent, IAgent_Aging {
    protected int Ax;
    protected int Ay;
    protected final int InitSugar;
    protected final int InitSpice;
    protected int ASugar;
    protected int ASpice;
    protected final int Vision;
    protected final float SugarMetabolism;
    protected final float SpiceMetabolism;
    protected final int Gender;
    protected final int MaxAge;
    protected int Age;

    public Agent(int x, int y, int initSugar, int initSpice, int vision, float sugarMetabolism, float spiceMetabolism, int maxAge, int gender){
        Ax = x;
        Ay = y;
        InitSugar = initSugar;
        ASugar = InitSugar;
        InitSpice = initSpice;
        ASpice = InitSpice;
        Vision = vision;
        SugarMetabolism = sugarMetabolism;
        SpiceMetabolism = spiceMetabolism;
        Age = 0;
        MaxAge = maxAge;
        Gender = gender;
    }


    public int getX(){
        return Ax;
    }

    public int getY(){
        return Ay;
    }

    public void setASugar(int ASugar){
        this.ASugar = ASugar;
    }

    public void setASpice(int ASpice){
        this.ASpice = ASpice;
    }

    public int getASugar(){
        return ASugar;
    }

    public int getASpice(){
        return ASpice;
    }

    public void changeAge(){}

    public int getAge(){
        return Age;
    }

    public int getInitSugar(){
        return InitSugar;
    }

    public int getInitSpice(){
        return InitSpice;
    }

    public int getVision(){
        return Vision;
    }

    public int getMaxAge(){
        return MaxAge;
    }

    public int getGender(){
        return Gender;
    }

    public float getSugarMetabolism(){
        return SugarMetabolism;
    }

    public float getSpiceMetabolism(){
        return SpiceMetabolism;
    }

    public void survival(Space space){}
}
