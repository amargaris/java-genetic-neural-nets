package toolkit.optimization.genetic.data;

import toolkit.optimization.genetic.phenotype.GAPhenotype;

public class GAChromosome<T> {
	private transient T content;
	private Double previousFitness;
	private Double currentFitness;
	private int geneLength;//an einai p.x. syntetagmeni x kai y tote 2
	/*
	 * edw borw na balw diafores metablites p.x. poso kairo itan best, posa paidia exei kanei klp
	 */
	public GAChromosome(T t){
		this(t,1);
	}

	public GAChromosome(T t,int length){
		this.content=t;
		this.geneLength=length;
	}
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}
	public Double getPreviousFitness() {
		return previousFitness;
	}
	public void setPreviousFitness(Double previousFitness) {
		this.previousFitness = previousFitness;
	}
	public Double getCurrentFitness() {
		return currentFitness;
	}
	public void setCurrentFitness(Double currentFitness) {
		this.currentFitness = currentFitness;
	}
	public int getGeneLength() {
		return geneLength;
	}
	public void setGeneLength(int geneLength) {
		this.geneLength = geneLength;
	}
	public void updateFitness(Double newFitness){
		this.previousFitness=this.currentFitness;
		this.currentFitness=newFitness;
	}
	public void phenotype(GAPhenotype<T> phenotype){
		phenotype.resolvePhenotype(getContent());
	}
	@Override
	public String toString() {
		return "GAChromosome [content=" + content + ", previousFitness="
				+ previousFitness + ", currentFitness=" + currentFitness
				+ ", geneLength=" + geneLength + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		GAChromosome other = (GAChromosome) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		return true;
	}
}