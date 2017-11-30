package toolkit.optimization.neural.functions;

public class ExponentialFunction extends SingleDimFunction{
	private double a,b;
	
	public ExponentialFunction(double a,double b,SingleDimFunction der) {
		super(null);
		this.a=a;
		this.b=b;
	}
	
	public static double exp(double a,double b,double x){
		return Math.pow(a*Math.E, b*x);
	}
	
	@Override
	protected double fv(double x) {
		return exp(a,b,x);
	}
	
	public SingleDimFunction getDer() throws Exception {
		return new SingleDimFunction() {
			
			@Override
			protected double fv(double x) {
				return b*exp(a,b,x);
			}
		};
	}

}
