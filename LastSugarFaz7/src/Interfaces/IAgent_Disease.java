package Interfaces;

import java.util.ArrayList;

import Data.AgeType;
import Models.Identity;
import Models.Wallet;

public interface IAgent_Disease {

    public ArrayList<Integer> getInfectedDiseases();

    public ArrayList<Integer> getPossibleDiseases();

    public IWallet_Disease getWallet();

    public IIdentity_Disease getIdentity();

}
