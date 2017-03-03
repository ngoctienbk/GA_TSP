import java.util.ArrayList;
import java.util.Random;

public class Algorithm {
	private static final double p_rate = 0.8; // pmx rate
	static Random r = new Random(100); // random with seed

	public static void main(String[] args) {
		Population pop = new Population();
		pop.initPopulation(); // initialize population
		System.out.print("The best before: ");
		pop.getBestIndividual().printIndiv(); // the best Individual before loop
		// loop for 10000 times
		for (int i = 0; i < 5000; i++) {
			Population new_pop = new Population(); // define a new population to
													// store new individual
			for (int j = 0; j < Population.defaultPopLength / 2; j++) {
				Individual ind1 = new Individual();
				Individual ind2 = new Individual();
				ind1 = pop.selectIndiv(2, r); // select Individual with
				ind2 = pop.selectIndiv(2, r); // tournament_size = 2, random r
				// arraylist to store child
				ArrayList<Individual> childs = new ArrayList<Individual>();
				double d = 0 + (1 - 0) * r.nextDouble();
				// if random number > pmx rate, do nothing
				if (d > p_rate) {
					new_pop.population.add(ind1);
					new_pop.population.add(ind2);
				} else {
					// else do cross over, generate two childs, do mutation
					childs = Algorithm.pMX(ind1, ind2);
					childs.get(0).mutation();
					childs.get(1).mutation();
					new_pop.population.add(childs.get(0));
					new_pop.population.add(childs.get(1));
				}
			}
			// add the best Indiv of old Population to the random position
			// of new Population
			new_pop.population.set(r.nextInt(Population.defaultPopLength), pop.getBestIndividual());
			pop = new_pop;
		}
		// the best Individual after loop
		System.out.print("The best after: ");
		pop.getBestIndividual().printIndiv();
	}

	// PMX cross over, input two parents, return arrayList of two childs
	public static ArrayList<Individual> pMX(Individual indiv1, Individual indiv2) {
		ArrayList<Individual> child = new ArrayList<Individual>();
		Individual ind1 = new Individual();
		Individual ind2 = new Individual();
		int left, right; // cross over point
		left = r.nextInt(Individual.defaultGeneLength);
		do {
			right = r.nextInt(Individual.defaultGeneLength);
		} while (right == left); // left, right must be different number
		if (left > right) {
			int temp = right;
			right = left;
			left = temp;
		} // left always must be smaller than right
			// generate child1
		for (int i = 0; i < Individual.defaultGeneLength; i++) {
			int temp = -1;
			// if i out of two cross over point, find the mapped of gene(i)
			if (i < left || i > right) {
				temp = indiv2.getGene().get(i);
				for (int k = left; k < right + 1; k++) {
					for (int j = left; j < right + 1; j++) {
						if (indiv1.getGene().get(j) == temp) {
							temp = indiv2.getGene().get(j);
						}
					}
				}
				// else add gene i to child
				ind1.addGene(temp, i);
				continue;
			}
			ind1.addGene(indiv1.getGene().get(i), i);
		}
		// generate child2
		child.add(ind1);
		for (int i = 0; i < Individual.defaultGeneLength; i++) {
			int temp = -1;
			if (i < left || i > right) {
				temp = indiv1.getGene().get(i);
				for (int k = left; k < right + 1; k++) {
					for (int j = left; j < right + 1; j++) {
						if (indiv2.getGene().get(j) == temp) {
							temp = indiv1.getGene().get(j);
						}
					}
				}
				ind2.addGene(temp, i);
				continue;
			}
			ind2.addGene(indiv2.getGene().get(i), i);
		}
		child.add(ind2);
		return child;
	}
}
