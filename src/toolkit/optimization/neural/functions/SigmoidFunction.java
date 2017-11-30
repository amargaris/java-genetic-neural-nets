package toolkit.optimization.neural.functions;


public class SigmoidFunction extends SingleDimFunction{
	
	private double factor;
	
	public SigmoidFunction(){
		this(1);
	}
	
	public SigmoidFunction(double factor){
		super(null);
		this.factor=factor;
	}

	public static double sig(double x,double factor){
		return (1/( 1 + Math.pow(Math.E,(-1*x*factor))));
	}
	
	@Override
	protected double fv(double x) {
		return sig(x,factor);
	}
	
	public SingleDimFunction getDir() throws Exception {
		return new SingleDimFunction() {
			@Override
			protected double fv(double x) {
				return sig(x,factor)*(1-sig(x,factor));
			}
		};
	}
}