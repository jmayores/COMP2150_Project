/*
 * uses Dijkstra's algorithm to find the cheapest path between vertices
 */
public class Dijkstra {
	private static final int INFINITY = 999999;

	// runs Dijkstra's algorithm to find shortest path between vertices
	// start and end of graph g. returns false if either vertex does not
	// exist. if both are valid, finds and prints shortest path, along
	// with the cost of the path, then returns true
	public static boolean dijkstra(Graph g, String start, String end) {
		boolean startExists = false;
		boolean endExists = false;
		boolean destination = false;
		String[] vertices = g.getVertices(); // stores vertices
		String[] previous = new String[g.getCount()]; // stores previous of vertices
		String[] order = new String[5]; // order of travel
		int[][] weightMatrix = g.getWeightMatrix(); // weight matrix
		int[] dist = new int[g.getCount()]; // distance from planet to other
		boolean[] visited = new boolean[g.getCount()]; // saves whether visited planet or not
		int startIndex;
		int endIndex;

		// checks if input exists
		for (int i = 0; i < vertices.length; i++) {
			if (start.equals(vertices[i]))
				startExists = true;
			if (end.equals(vertices[i]))
				endExists = true;
		}

		if (startExists && endExists) {
			// System.out.print("Shortest path found: ");
			startIndex = g.getIndexOf(vertices, start);
			endIndex = g.getIndexOf(vertices, end);

			// initializes every vertex's distance to infinity
			// except starting vertex, with distance 0
			for (int i = 0; i < g.getCount(); i++)
				dist[i] = INFINITY;
			dist[startIndex] = 0;

			// iterates through the vertices, finding the vertex with the minimum distance
			// and is not visited
			for (int i = 0; i < g.getCount(); i++) {
				int min = dist[i];
				int indexOfVertex = i;
				for (int j = 0; j < g.getCount(); j++) {
					if (dist[j] < min && visited[j] == false) {
						min = dist[j];
						indexOfVertex = j;
					}
				}

				// goes through the other vertices/neighbors
				for (int j = 0; j < g.getCount(); j++) {
					if (weightMatrix[indexOfVertex][j] > 0) { // if greater than 0 and not visited
						int tent = min + weightMatrix[indexOfVertex][j];
						if (tent < dist[j]) { // if tentative distance is less than given distance
							dist[j] = tent;
							previous[j] = vertices[indexOfVertex];
						}
					}
				}
				visited[indexOfVertex] = true;
			}

			int count = 0;
			String next;
			order[count] = end; // puts the end at the beginning
			int nextIndex = endIndex;
			count++;

			// saves the order of the planets backwards until start has been visited
			// based on the planets that were visited previously
			while (!destination) {
				next = previous[nextIndex];
				order[count] = next;
				nextIndex = g.getIndexOf(vertices, next);
				count++;
				if (count == order.length) { // creates new array if count equals length
					String[] newOrder = new String[count * 2];
					for (int i = 0; i < count; i++)
						newOrder[i] = order[i];
					order = newOrder;
				}
				if (next.equals(start))
					destination = true;
			}

			// prints out the correct order of planets
			for (int i = count - 1; i >= 0; i--) {
				if (order[i].equals(end))
					System.out.print(order[i]);
				else {
					System.out.print(order[i] + " -> ");

				}
			}

			// cost of travel
			int cost = dist[endIndex];

			System.out.println(" (cost = " + cost + ")");

			return true;
		} else
			return false;
	}

}
