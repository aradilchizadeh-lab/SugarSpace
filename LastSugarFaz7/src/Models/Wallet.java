package Models;

public class Wallet {
    private final int InitSugar;
    private final int InitSpice;
    private int ASugar;
    private int ASpice;
    private float SugarMetabolism;
    private float SpiceMetabolism;

    public Wallet(int initSugar, int initSpice, float sugarMetabolism, float spiceMetabolism) {
        InitSugar = initSugar;
        InitSpice = initSpice;
        this.ASugar = InitSugar;
        this.ASpice = InitSpice;
        SugarMetabolism = sugarMetabolism;
        SpiceMetabolism = spiceMetabolism;
    }

    public void setASugar(int ASugar) {
        this.ASugar = ASugar;
    }

    public void setASpice(int ASpice) {
        this.ASpice = ASpice;
    }

    public int getASugar() {
        return ASugar;
    }

    public int getASpice() {
        return ASpice;
    }

    public int getInitSugar() {
        return InitSugar;
    }

    public int getInitSpice() {
        return InitSpice;
    }

    public float getSugarMetabolism() {
        return SugarMetabolism;
    }

    public float getSpiceMetabolism() {
        return SpiceMetabolism;
    }

    public void setSugarMetabolism(float sugar) {
        SugarMetabolism = sugar;
    }

    public void setSpiceMetabolism(float spice) {
        SpiceMetabolism = spice;
    }
}
