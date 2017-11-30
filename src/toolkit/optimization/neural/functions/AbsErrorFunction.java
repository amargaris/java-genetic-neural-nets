package toolkit.optimization.neural.functions;


public class AbsErrorFunction extends ErrorFunction{

	@Override
	public double error(double x1, double x2) {
		return Math.abs(x1-x2);
	}
}