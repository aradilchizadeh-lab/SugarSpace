package Models;

public class Wallet {
    private final int InitSugar;
    private final int InitSpice;
    private int ASugar;
    private int ASpice;
    private float SugarMetabolism;
    private float SpiceMetabolism;

    public Wallet() {
        InitSugar = (int)(Math.random() * 21) + 5;
        InitSpice = (int)(Math.random() * 21) + 5;
        this.ASugar = InitSugar;
        this.ASpice = InitSpice;
        SugarMetabolism = (int)(Math.random() * 4) + 1;
        SpiceMetabolism = (int)(Math.random() * 4) + 1;
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
