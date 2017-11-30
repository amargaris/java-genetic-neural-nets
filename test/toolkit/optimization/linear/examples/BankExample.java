package toolkit.optimization.linear.examples;

import toolkit.optimization.linear.LinearProblemModel;
import toolkit.optimization.linear.eval.BankEvaluator;

public class BankExample {
	public static void main(String... args){
		new LinearProblemModel(1,new BankEvaluator(2000,0.05,18),
		new double[]{15000}, 100.0).setGraphics(true) //ksekina apo to 15000 me bima 100 kai blepoyme!!
		.stopAfterMilliseconds(2000).printSolution();
	}
}
