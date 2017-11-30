package toolkit.optimization.linear.eval;


public class BankEvaluator implements Evaluator{
	private double monthlyRequests,interest;
	private int months;
	
	public BankEvaluator(double monthlyRequests, double interest, int months) {
		super();
		this.monthlyRequests = monthlyRequests;
		this.interest = interest;
		this.months = months;
	}

	@Override
	public double evaluate(double[] input) {
		double initialBankAmount=input[0];
		for(int i=0;i<months;i++){
			double interestValue=initialBankAmount*interest;
			initialBankAmount+=interestValue;
			initialBankAmount-=monthlyRequests;
		}
		return initialBankAmount;
	}
	
}