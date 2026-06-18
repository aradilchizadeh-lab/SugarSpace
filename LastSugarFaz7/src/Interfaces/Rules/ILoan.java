package Interfaces.Rules;

import Interfaces.Agent.IAgent_Loan;
import Interfaces.Patch.IPatch_Loan;

public interface ILoan {

    public void loan(IAgent_Loan agent, IPatch_Loan[][] patches, int tick);
}
