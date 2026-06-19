package Interfaces.Agent;

import Interfaces.IPositionView;

public interface IAgent_Aging{

    public IPositionView getPosition();

    public void changeAge();

    public IPhysiologyView getPhysiology();

    public IParentageUpdate getFertilityInfo();


}
