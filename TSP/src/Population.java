import java.util.ArrayList;
import java.util.Random;

public class Population {
	public static int defaultPopLength = 100; // default length of population
	// Population is an arraylist of individuals
	public ArrayList<Individual> population = new ArrayList<Individual>(defaultPopLength);

	public Population() {
	};

	// initialize population
	public void initPopulation() {
		for (int i = 0; i < defaultPopLength; i++) {
			Individual indiv = new Individual();
			indiv.initGene();
			population.add(indiv);
		}
	}

	// get gene of an Individual
	public ArrayList<Integer> getIndiv(int i) {
		return population.get(i).getGene();
	}

	// get a Population
	public ArrayList<Individual> getPopulation() {
		return population;
	}

	// set Population
	public void setPopulation(ArrayList<Individual> population) {
		this.population = population;
	}

	// get the best Individual of Population - the Individual with minimum
	// fitness
	public Individual getBestIndividual() {
		double min = population.get(0).getFitness();
		int flag = 0;
		for (int i = 1; i < defaultPopLength; i++) {
			if (population.get(i).getFitness() < min) {
				min = population.get(i).getFitness();
				flag = i;
			}
		}
		return population.get(flag);
	}

	// add and Individual to Population
	public void addIndividual(Individual indiv) {
		population.add(indiv);
	}

	// set to position
	public void setIndividual(Individual indiv, int pos) {
		population.add(pos, indiv);
	}

	// randomize tournament_size Individual, return which better fitness
	public Individual selectIndiv(int tournament_size, Random r) {
		int k = r.nextInt(defaultPopLength);
		for (int i = 0; i < tournament_size; i++) {
			int j = r.nextInt(defaultPopLength);
			if (population.get(k).getFitness() > population.get(j).getFitness()) {
				k = j;
			}
		}
		return population.get(k);
	}
}
