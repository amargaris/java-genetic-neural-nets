package toolkit.optimization.neural.functions;


public class SquareErrorFunction extends ErrorFunction{
	@Override
	public double error(double x1, double x2) {
		return Math.pow(x1-x2, 2);
	}
}