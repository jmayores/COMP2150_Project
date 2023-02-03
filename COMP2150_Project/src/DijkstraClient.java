import java.util.Scanner;

// client for running dijkstra's algorithm based on a file
public class DijkstraClient {
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		Graph graph = new Graph();
		Dijkstra d = new Dijkstra();

		boolean exists = false;

		// while loop if file does not exist
		while (!exists) {
			System.out.print("Enter name of data file to load: ");
			String txtFile = scnr.next();
			exists = graph.readFromFile(txtFile);
			if (!exists)
				System.out.println("Error: " + txtFile + " not found");
			else
				System.out.println(txtFile + " successfully loaded!");
			scnr.nextLine();
		}

		char userInput = 'a';

		// while loop that exits when user inputs 'q'
		while (userInput != 'q') {
			System.out.println("\nRun (D)ijkstra's algorithm\n(C)hange data file\n(Q)uit\n");
			System.out.print("Enter your choice: ");
			userInput = scnr.next().charAt(0);
			scnr.nextLine();

			if (userInput == 'd' || userInput == 'c' || userInput == 'q') {
				// dijkstra algorithm
				if (userInput == 'd') {
					System.out.print("Enter name of starting vertex: ");
					String start = scnr.nextLine();
					System.out.print("Enter name of end vertex: ");
					String end = scnr.nextLine();
					exists = d.dijkstra(graph, start, end);
					if (!exists)
						System.out.println("Error: Invalid start and/or end vertices\n");
					
					// changes file
				} else if (userInput == 'c') {
					exists = false;
					while (!exists) {
						System.out.print("Enter name of data file to load: ");
						String txtFile = scnr.next();
						graph = new Graph();
						exists = graph.readFromFile(txtFile);
						if (!exists)
							System.out.println("Error: " + txtFile + " not found");
						else
							System.out.println(txtFile + " successfully loaded!");
					}
					scnr.nextLine();
				}
			} else {
				System.out.println("Error: invalid choice");
				continue;
			}
		}

		// prints when user inputs 'q'
		System.out.println("Thanks, see ya lol");
		scnr.close();
	}

}
