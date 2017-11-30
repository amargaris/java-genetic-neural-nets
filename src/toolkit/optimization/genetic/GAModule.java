package toolkit.optimization.genetic;

import java.util.Random;

public class GAModule<T>{
	private AbstractGA<T>parent;
	
	public AbstractGA<T> getParent(){
		return parent;
	}
	public void setParent(AbstractGA<T>parent){
		this.parent=parent;
	}
	public Random getRandom(){
		return parent.getRandom();
	}
}