package toolkit.optimization.genetic;

public class MissingComponentException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingComponentException(String reason){
		super(reason);
	}
}