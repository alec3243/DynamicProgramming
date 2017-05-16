package cs331.dynamicprogramming;

public class Path {

	private int[][] D;
	private boolean[][] P;

	Path(int[][] D, boolean[][] P) {
		this.setD(D);
		this.setP(P);
	}

	/**
	 * Returns a Path object containing the matrices representing the path and
	 * the path sums.
	 * 
	 * @param matrix
	 *            Original Matrix
	 * @return Path object
	 */
	public static Path getPathSums(int[][] matrix) {
		int n = matrix.length;
		int[][] D = new int[n][n]; // D = matrix of the path sums
		// P = matrix that shows the actual longest path according to D
		boolean[][] P = new boolean[n][n];
		// Copy the matrix into D
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				P[i][j] = false;
				D[i][j] = matrix[i][j];
			}
		}
		int sumV = 0;
		int sumH = 0;
		// Establish the sums along the first row & column
		for (int i = 0; i < n; i++) {
			D[0][i] = sumH + matrix[0][i];
			sumH = D[0][i];
			D[i][0] = sumV + matrix[i][0];
			sumV = D[i][0];
		}
		// Dynamic Programming Algorithm
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < n; j++) {
				D[i][j] = max(D[i - 1][j] + matrix[i][j], D[i][j - 1]
						+ matrix[i][j], D[i - 1][j - 1] + matrix[i][j]);
			}
		}
		// Set the start and end points of the path to true
		P[0][0] = true;
		P[n - 1][n - 1] = true;

		// Set [i][j] to the lower right corner of the matrix
		int i = n - 1;
		int j = n - 1;
		int maxVal = 0;
		// Find the path with the largest sum
		mainLoop: while (true) {
			while (true) {
				// If [i][j] is not at the top left corner
				if ((i != 0) && (j != 0)) {
					maxVal = Math.max(D[i - 1][j], D[i][j - 1]);
					// If maxVal is above [i][j]
					if (maxVal == D[i - 1][j - 1]) {
						P[i - 1][j - 1] = true;
						i--;
						j--;
					} else if (maxVal == D[i - 1][j]) {
						P[i - 1][j] = true;
						i--;
						// If maxVal is to the left of [i][j]
					} else {
						P[i][j - 1] = true;
						j--;
					}
					// If [i][j] is at the top row of the matrix
				} else if ((i == 0) && (j != 0)) {
					P[i][j - 1] = true;
					j--;
					// If [i][j] is at the leftmost column of the matrix
				} else if ((i != 0) && (j == 0)) {
					P[i - 1][j] = true;
					i--;
				} else {
					// Break mainLoop when the entire path has been found
					break mainLoop;
				}
			}
		}

		Path path = new Path(D, P);
		return path;
	}

	private static int max(int x, int y, int z) {
		int temp = Math.max(x, y);
		return Math.max(temp, z);
	}

	public boolean[][] getP() {
		return P;
	}

	public void setP(boolean[][] p) {
		P = p;
	}

	public int[][] getD() {
		return D;
	}

	public void setD(int[][] d) {
		D = d;
	}

}
