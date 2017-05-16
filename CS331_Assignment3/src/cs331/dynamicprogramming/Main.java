package cs331.dynamicprogramming;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class Main extends Application {

	private static int[][] D;
	private static int[][] matrix;
	private static boolean[][] P;
	private static final int N = 20;

	public static void main(String[] args) {
		matrix = new int[N][N];
		generateCells(matrix);
		int current = 0;

		// Print the original matrix
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				current = matrix[i][j];
				if (current > 9) {
					System.out.printf(" %d", current);
				} else {
					System.out.printf(" %d ", current);
				}

			}
			System.out.println();
		}
		long start = System.nanoTime();
		Path path = Path.getPathSums(matrix);
		long end = System.nanoTime();

		D = path.getD();
		P = path.getP();

		System.out.println();
		// Print the matrix representing the path sums
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				current = D[i][j];
				if (current > 9) {
					System.out.printf(" %d", current);
				} else {
					System.out.printf(" %d ", current);
				}
			}
			System.out.println();
		}
		System.out.println();
		// Print the matrix representing the actual path
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (P[i][j]) {
					System.out.print("|" + 1 + "|");
				} else {
					System.out.print(" " + 0 + " ");
				}

			}
			System.out.println();
		}
		System.out.println(end - start);
		// Launch the JavaFX application
		launch();
	}

	/**
	 * Randomly generates a natural number in range [1,10] for each entry in the
	 * matrix
	 * 
	 * @param matrix
	 *            Matrix to be filled
	 */
	private static void generateCells(int[][] matrix) {
		Random rand = new Random();

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = rand.nextInt(10) + 1;
			}
		}
	}

	/**
	 * Generates the GUI that represents the original matrix as well as the
	 * optimal path to maximize score.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Press space to show the highest-scoring path. Press e to exit.");
		final int n = D.length;
		GridPane grid = new GridPane();
		grid.setGridLinesVisible(true);
		for (int i = 0; i < n; i++) {
			RowConstraints rConstraint = new RowConstraints();
			rConstraint.setPercentHeight(100.0 / n);
			ColumnConstraints cConstraint = new ColumnConstraints();
			cConstraint.setPercentWidth(100.0 / n);
		}

		String currentVal = null;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				currentVal = Integer.toString(matrix[i][j]);
				Label label = new Label(currentVal);
				grid.add(label, j, i);
				GridPane.setHgrow(label, Priority.ALWAYS);
				GridPane.setVgrow(label, Priority.ALWAYS);
			}
		}
		grid.setAlignment(Pos.CENTER);
		Scene scene = new Scene(grid, 600, 400);

		scene.setOnKeyPressed((event) -> {
			KeyCode input = event.getCode();
			// If the space button is pushed, reveal the optimal path
			if (KeyCode.SPACE == input) {
				Label label = null;
				HBox hbox2 = null;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						// If the current entry is a part of the optimal path,
						// set the background to green and increase the font
						// weight for the score
						if (P[i][j]) {
							label = new Label(Integer.toString(matrix[i][j]));
							hbox2 = new HBox();
							hbox2.getChildren().add(label);
							grid.add(hbox2, j, i);
							GridPane.setHgrow(hbox2, Priority.ALWAYS);
							GridPane.setVgrow(hbox2, Priority.ALWAYS);
							hbox2.setStyle("-fx-background-color: green; -fx-font-weight: bolder;");

						}
					}
				}
				stage.setTitle("Score: " + D[n - 1][n - 1]
						+ ". Press e to exit.");
				// If e is pushed, exit the program
			} else if (KeyCode.E == input) {
				System.exit(0);
			}
		});
		stage.setScene(scene);
		stage.show();
	}

}
