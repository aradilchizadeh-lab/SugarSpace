package Interfaces.Agent;

import java.util.ArrayList;

import Interfaces.IPositionView;
import Models.LoanInfo;

public interface IAgent_Aging{

    public IPositionView getPosition();

    public void changeAge();

    public IPhysiologyView getPhysiology();

    public IParentageUpdate getFertilityInfo();

    public ArrayList<LoanInfo> getLoanInfos();

}
