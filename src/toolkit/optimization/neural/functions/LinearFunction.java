package toolkit.optimization.neural.functions;



public class LinearFunction extends SingleDimFunction{
	private double a,b;
	public LinearFunction(double a,double b){
		super(null);
		this.a=a;
		this.b=b;
	}
	
	public String toString(){
		return "Line: y = "+a+" * x + "+b;
	}

	@Override
	protected double fv(double x) {
		return a*x+b;
	}
	public SingleDimFunction getDer(){
		return new SingleDimFunction() {
			@Override
			protected double fv(double x) {
				return a;
			}
		};
	}
}