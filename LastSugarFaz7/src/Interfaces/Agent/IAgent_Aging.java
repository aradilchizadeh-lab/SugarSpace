package Interfaces.Agent;

import Interfaces.IPositionView;

public interface IAgent_Aging extends IBioView, IParentageUpdate {

    public IPositionView getPosition();

    public void changeAge();

}
