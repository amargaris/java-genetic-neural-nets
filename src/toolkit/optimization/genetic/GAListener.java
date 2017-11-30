package toolkit.optimization.genetic;

public interface GAListener<T>{
	public void invoke(AbstractGA<T> ga);
}