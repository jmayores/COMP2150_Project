import java.io.*;
import java.util.Scanner;
/*
 * class that uses a 2D array to store the adjacency matrix of all the
 * weights of neighboring planets
 */
public class Graph {
	// stores only the weights of the graph's edges
	public int[][] weightMatrix = new int[2][2];
	
	// stores the Strings of the planets
	private String[] vertices = new String[5];
	
	// keeps track of the number of planets saved
	private int count = 0;

	// reads from a given .txt file and fills up the
	// adjacency matrix based on that data
	public boolean readFromFile(String filename) {
		try {
			File myObj = new File(filename);
			Scanner firstReader = new Scanner(myObj);
			String currLine; // current line being read in file
			String currWord; // current word being read in line
			int currWeight; // current weight being stored
			int first; // stores word(s) before first "|"
			int second; // stores word(s) before second "|"
			int firstIndex; // index of first word in vertices[]
			int secondIndex; // index of second word in vertices[]
			while (firstReader.hasNextLine()) {
				currLine = firstReader.nextLine();

				first = currLine.indexOf("|");
				currWord = currLine.substring(0, first - 1); // stores word without whitespace
				firstIndex = exists(currWord); // index of first word

				second = currLine.indexOf("|", first + 1);
				currWord = currLine.substring(first + 2, second - 1); // stores word without whitespace
				secondIndex = exists(currWord); // index of first word

				currWord = currLine.substring(second + 2, currLine.length()); // stores weight
				currWeight = Integer.parseInt(currWord); // turns string into int

				if (count > weightMatrix.length) { // increases weightMatrix[][] if no space
					int[][] newWeightMatrix = new int[count][count];
					for (int i = 0; i < weightMatrix.length; i++) {
						for (int j = 0; j < weightMatrix.length; j++) {
							newWeightMatrix[i][j] = weightMatrix[i][j];
							newWeightMatrix[j][i] = weightMatrix[j][i];
						}
					}
					weightMatrix = newWeightMatrix;
				}

				// stores the weight based on the index of the first planet and second planet
				// stores it in [i][j] and [j][i]
				weightMatrix[firstIndex][secondIndex] = currWeight;
				weightMatrix[secondIndex][firstIndex] = currWeight;
			}
			firstReader.close();
			return true;
		} catch (FileNotFoundException e) { // if file does not exist
			return false;
		}
	}
	
	// getter method for vertices
	public String[] getVertices() {
		return vertices;
	}
	
	// getter method for weight matrix
	public int[][] getWeightMatrix() {
		return weightMatrix;
	}
	
	// getter method for count
	public int getCount() {
		return count;
	}

	// determines if planet is already saved in vertices[], if false, adds to vertices[]
	// returns the index of item in vertices[]
	public int exists(String item) {
		boolean exists = false;
		int index = count;
		for (int i = 0; i < vertices.length; i++) { // searches through vertices[] if exists
			if (item.equals(vertices[i])) {
				exists = true;
				index = i;
			}
		}
		if (!exists)
			addVertex(item);
		return index;
	}
	
	// adds vertex into vertices[]
	public void addVertex(String item) {
		if (count == vertices.length) { // if out of space, increase by doubling size
			String[] newVer = new String[count * 2];
			for (int i = 0; i < count; i++)
				newVer[i] = vertices[i];
			vertices = newVer;
		}
		vertices[count] = item;
		count++;
	}

	// returns String of neighbor of given vertex
	public String neighbor(String vertex) {
		String neighbors = "";
		int indexOfVertex = getIndexOf(vertices, vertex); // gets index of given vertex in vertices[]
		for (int i = 0; i < weightMatrix.length; i++) {
			if (weightMatrix[indexOfVertex][i] > 0) // if weight if greater that zero, planet is neighbor and gets its String in vertices[]
				neighbors += vertices[i] + "(" + weightMatrix[indexOfVertex][i] + ") ";
		}
		return neighbors;
	}

	// gets index of planet in vertices[]
	public int getIndexOf(String[] strings, String item) {
		for (int i = 0; i < strings.length; i++) {
			if (item.equals(strings[i]))
				return i;
		}
		return -1;
	}

	// toString method returning vertices and their neighbors
	public String toString() {
		String vertex = "";
		for (int i = 0; i < count; i++) {
			if (vertices[i] == null)
				break;
			vertex += "Vertex: " + vertices[i] + ", neighbors: " + neighbor(vertices[i]) + "\n";
		}
		return vertex;
	}
}