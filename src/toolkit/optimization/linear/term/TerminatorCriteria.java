package toolkit.optimization.linear.term;

import toolkit.optimization.linear.LinearProblemModel;

public interface TerminatorCriteria{
	public boolean willTerminate(LinearProblemModel input);
}