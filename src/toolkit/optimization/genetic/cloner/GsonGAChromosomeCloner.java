package toolkit.optimization.genetic.cloner;

import com.google.gson.Gson;

public class GsonGAChromosomeCloner<T> extends GADataModelCloner<T>{
	private Gson gson;
	public GsonGAChromosomeCloner(Gson gson) {
		this.gson=gson;
	}
	@Override
	public T clone(T input) {
		@SuppressWarnings("unchecked")
		Class<T>classs=(Class<T>) input.getClass();
		T new_t= (T) gson.fromJson(gson.toJson(input),classs);
		return new_t;
	}
	
}