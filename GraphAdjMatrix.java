
public class GraphAdjMatrix implements Graph {

	private int[][] mat;
	
	/**
	 * Initialize mat to a 2d array with
	 * vertices rows and columns
	 * @param vertices number of vertices in graph
	 */
	public GraphAdjMatrix(int vertices) {
		mat = new int[vertices][vertices];
	}

	/**
	 * Set row src and column tar in mat to 1.
	 * The adds an edge from vertex src pointing
	 * to tar
	 * @param src source that points
	 * @param tar target that is pointed to
	 * @throws IndexOutOfBoundsException when src or tar 
	 *                                   is out of range
	 */
	@Override
	public void addEdge(int src, int tar) {
		if (src<0 || tar<0 || src>=mat.length || tar>=mat.length) {
			throw new IndexOutOfBoundsException("Vertex out of bounds");
		}
		mat[src][tar] = 1;
	}

	/**
	 * Sort by dependencies. Print an order such that
	 * previous vertices depend on earlier dependencies 
	 * or have no dependencies.
	 * 
	 * ie. When src vertex points to dest vertex, dest 
	 *     depends on src.
	 *     
	 * If there is a cycle, the function will print so and
	 * will not print a partial solution.
	 */
	@Override
	public void topologicalSort() {
		int[] indegrees = getIndegrees();
		int[] sorted = new int[mat.length];
		int index = 0;
		int vertex;
		while (index<mat.length) {
			vertex = findZero(indegrees);
			if (vertex==-1) {
				// Or throw exception
				System.out.println("Error: There is a cycle in the graph");
				return;
			} else {
				sorted[index] = vertex;
				index++;
				// mark as printed
				indegrees[vertex] = -1;
				removeIndegrees(vertex, indegrees);
			}
		}
		for (int i=0; i<sorted.length; i++) {
			System.out.print(sorted[i] + " ");
		}
		System.out.println();
	}
	
	/**
	 * Remove 1 in the indegrees from all of the 
	 * vertices that vertex points to.
	 * @param vertex vertex
	 * @param indegrees array containing indegrees
	 */
	private void removeIndegrees(int vertex, int[] indegrees) {
		for (int i=0; i<mat[vertex].length; i++) {
			if (mat[vertex][i]==1) {
				indegrees[i]--;
			}
		}
	}
	
	/**
	 * Return the next zero element in arr or 
	 * return -1 if there is no zero
	 * @param arr an array containing int
	 * @return the index that the next zero is at
	 */
	private int findZero(int[] arr) {
		for (int i=0; i<arr.length; i++) {
			if (arr[i]==0) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Form an array with the number of indegrees
	 * each vertex has in the respective spot 
	 * of the array
	 * @return array with number of indegrees for each vertex
	 */
	private int[] getIndegrees() {
		int[] indegrees = new int[mat.length];
		for (int r=0; r<mat.length; r++) {
			for (int c=0; c<mat[r].length; c++) {
				if (mat[r][c]==1) {
					indegrees[c]++;
				}
			}
		}
		return indegrees;
	}

	/**
	 * Return array containing vertices that vertex points to
	 * @param vertex vertex that is being used as source
	 * @return array containing vertices that vertex points to
	 */
	@Override
	public int[] neighbors(int vertex) {
		int index = 0;
		int[] neigh = new int[mat.length];
		for (int i=0; i<mat.length; i++) {
			if (mat[vertex][i]==1) {
				neigh[index] = i;
				index++;
			}
		}
		int[] temp = new int[index];
		System.arraycopy(neigh, 0, temp, 0, temp.length);
		return temp;
	}

}
