package toolkit.optimization.neural.functions;


public abstract class ErrorFunction extends MultiToSingleFunction{
	
	public abstract double error(double x1,double x2);

	@Override
	public double fv(double[] x) {
		return error(x[0],x[1]);
	}
}