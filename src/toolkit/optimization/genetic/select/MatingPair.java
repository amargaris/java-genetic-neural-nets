package toolkit.optimization.genetic.select;

import toolkit.optimization.genetic.data.GAChromosome;

public class MatingPair<T>{
	
	private GAChromosome<T> first,second;
	private boolean masturbation;
	
	public MatingPair(GAChromosome<T> first,GAChromosome<T> second){
		this.first=first;
		this.second=second;
		masturbation=first==second;
	}
	
	public boolean isMasturbation(){
		return masturbation;
	}
	public GAChromosome<T> getFirst() {
		return first;
	}
	public void setFirst(GAChromosome<T> first) {
		this.first = first;
	}
	public GAChromosome<T> getSecond() {
		return second;
	}
	public void setSecond(GAChromosome<T> second) {
		this.second = second;
	}
	@Override
	public String toString() {
		return "MatingPair [first=" + first + ", second=" + second + "]";
	}
	
	
	
}