package Models;

import Interfaces.IPositionView;

public class PatchPosition implements IPositionView {
    
    private int Px;
    private int Py;

    public PatchPosition(int x, int y) {
        Px = x;
        Py = y;
    }

    public int getX() {
        return Px;
    }

    public int getY() {
        return Py;
    }

}
