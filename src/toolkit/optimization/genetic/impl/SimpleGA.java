package toolkit.optimization.genetic.impl;

import java.util.Random;

import toolkit.optimization.genetic.AbstractGA;

public abstract class SimpleGA<T> extends AbstractGA<T>{
	
	private int epochLimit;
	private int populationSize;
	private int elitism;
	
	public SimpleGA(int epochLimit,int populationSize,int elitism){
		super();
		this.epochLimit=epochLimit;
		this.populationSize=populationSize;
		this.elitism=elitism;
		setGsonCloner();
		setRandom(new Random());
		setRouletteSelector(elitism);
		setEpochThreshold(epochLimit);
		setAcceptAllValidator();
		setProgressGraphicsEnabled();
		setPrintBestChromosomeOnFinish();
	}
	public abstract SimpleGA<T> init();
	public int getEpochLimit() {
		return epochLimit;
	}
	public void setEpochLimit(int epochLimit) {
		this.epochLimit = epochLimit;
	}
	public int getPopulationSize() {
		return populationSize;
	}
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}
	public int getElitism() {
		return elitism;
	}
	public void setElitism(int elitism) {
		this.elitism = elitism;
	}
	
}
