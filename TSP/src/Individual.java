import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Individual {
	private ArrayList<Integer> gene = new ArrayList<Integer>();
	public static int defaultGeneLength = readFileDistances(); // read the file
	private static double distances[][]; // array n x n, distances of cities
	private static double m_rate = 0.1; // mutation rate
	private static Random rand = new Random(1); // random seed

	public Individual() {
	}

	// initialize gene of Individual
	// generate ordered number and shuffle them
	public void initGene() {
		for (int i = 0; i < defaultGeneLength; i++) {
			getGene().add(i + 1);
		}
		Collections.shuffle(getGene(), rand);
	}

	// get fitness of individual (sum of distances)
	public double getFitness() {
		double fitness = 0;
		for (int i = 0; i < defaultGeneLength - 1; i++) {
			fitness = fitness + distances[gene.get(i) - 1][gene.get(i + 1) - 1];
		}
		fitness += distances[gene.get(defaultGeneLength - 1) - 1][gene.get(0) - 1];
		return fitness;
	}

	// read distances of cities from file, return number of cities
	public static int readFileDistances() {
		String fileName = null;
		// read filename from keyboard
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Input file name:");
			fileName = buf.readLine();
		} catch (IOException ex) {
		}
		BufferedReader br = null; // string to store data from file
		int num = 0; // number of cities
		try {
			String sCurrentLine = null;
			br = new BufferedReader(new FileReader(fileName));
			// read lines 1..4
			for (int j = 0; j < 4; j++) {
				sCurrentLine = br.readLine();
			}
			String[] str = sCurrentLine.split(": "); // spilit from ": " string
			num = Integer.parseInt(str[1]); // line 4 is containing number
											// ofcities.
			distances = new double[num][num]; // generate array n x n of
												// distances
			sCurrentLine = br.readLine(); // read line 5
			sCurrentLine = br.readLine(); // read line 6
			City[] cities = new City[num];
			// read the coordinates of cities
			for (int j = 0; j < num; j++) {
				sCurrentLine = br.readLine();
				str = sCurrentLine.split(" "); // split by space
				cities[j] = new City();
				// set coordinates
				cities[j].setX(Double.parseDouble(str[1]));
				cities[j].setY(Double.parseDouble(str[2]));
				// calculate distances
				for (int i = 0; i <= j; i++) {
					if (i == j) {
						distances[j][i] = 0;
					} else {
						distances[j][i] = distances[i][j] = Math.sqrt(Math.pow((cities[j].getX() - cities[i].getX()), 2)
								+ Math.pow((cities[j].getY() - cities[i].getY()), 2));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return num;
	}

	// getter
	public ArrayList<Integer> getGene() {
		return gene;
	}

	// setter
	public void setGene(ArrayList<Integer> gene) {
		this.gene = gene;
	}

	// add to position
	public void addGene(int value, int index) {
		gene.add(index, value);
	}

	// do mutation with each gene if random number < m_rate
	public void mutation() {
		for (int i = 0; i < defaultGeneLength; i++) {
			double d = 0 + (1 - 0) * rand.nextDouble();
			if (d < m_rate) {
				swap(rand.nextInt(defaultGeneLength), rand.nextInt(defaultGeneLength));
			}
		}
	}

	private void swap(int a, int b) {
		int temp = gene.get(b);
		gene.set(b, gene.get(a));
		gene.set(a, temp);
	}

	public void printIndiv() {
		for (int i = 0; i < defaultGeneLength; i++) {
			System.out.print(gene.get(i) + " ");
		}
		System.out.print(this.getFitness());
		;
		System.out.println();
	}
}