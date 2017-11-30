package toolkit.optimization.linear.examples;

import toolkit.optimization.linear.LinearProblemModel;
import toolkit.optimization.linear.eval.Evaluator;
import toolkit.optimization.neural.functions.SingleDimFunction;

public class PolynomialExample {
	public static void main(String... args){
		new LinearProblemModel(1,new Evaluator() {
			SingleDimFunction simple = new SingleDimFunction() {
				@Override
				public double fv(double x) {return 3*Math.pow(x, 3)+9*Math.pow(x, 2)-11*x+22;
				}
			};
			@Override
			public double evaluate(double[] input) {
				return simple.f(input[0]);
			}
		},
		new double[]{15000}, 100.0) //ksekina apo to 15000 me bima 100 kai blepoyme!!
		.stopAfterMilliseconds(2000).printSolution();
	}
}
