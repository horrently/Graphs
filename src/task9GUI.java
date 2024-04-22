import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class task9GUI extends Application {

    private TextArea graphMatrixTextArea;
    private TextArea shortestPathsMatrixTextArea;
    private int n;

    public static int[][] generateGraphMatrix(int vertexNum) {
        int[][] M = new int[vertexNum][vertexNum];
        boolean correct = false;
        while (!correct) {
            for (int i = 0; i < vertexNum; i++)
                for (int j = 0; j < vertexNum; j++) {
                    M[i][j] = 1 + (int) (Math.random() * 5);
                    M[i][i] = 0;
                }
            int cnt = 0;
            for (int i = 0; i < vertexNum; i++) {
                for (int j = i + 1; j < vertexNum; j++)
                    if (M[i][j] == 0) cnt++;
                cnt = 0;
            }
            if (cnt != vertexNum) correct = true;
        }
        return M;
    }

    public static int[][] floydWarshall(int[][] Matrix) {
        int n = Matrix.length;
        int[][] dist = new int[n][n];

        int i, j, k;

        for (i = 0; i < n; i++)
            for (j = 0; j < n; j++)
                dist[i][j] = Matrix[i][j];
        for (k = 0; k < n; k++)
            for (i = 0; i < n; i++)
                for (j = 0; j < n; j++)
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];

        return dist;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Матрица кратчайших путей");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        Random random = new Random();
        n = random.nextInt(6) + 1;

        Label graphMatrixLabel = new Label("Сгенерированная матрица:");
        graphMatrixTextArea = new TextArea();
        graphMatrixTextArea.setPrefRowCount(5);
        graphMatrixTextArea.setPrefColumnCount(10);
        graphMatrixTextArea.setEditable(false);

        Label shortestPathsMatrixLabel = new Label("Матрица кратчайших путей:");
        shortestPathsMatrixTextArea = new TextArea();
        shortestPathsMatrixTextArea.setPrefRowCount(5);
        shortestPathsMatrixTextArea.setPrefColumnCount(10);
        shortestPathsMatrixTextArea.setEditable(false);

        gridPane.add(graphMatrixLabel, 0, 0);
        gridPane.add(graphMatrixTextArea, 0, 1);
        gridPane.add(shortestPathsMatrixLabel, 0, 2);
        gridPane.add(shortestPathsMatrixTextArea, 0, 3);

        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        int[][] graphMatrix = generateGraphMatrix(n);
        int[][] shortestPathsMatrix = floydWarshall(graphMatrix);

        StringBuilder graphMatrixText = new StringBuilder();
        for (int[] row : graphMatrix) {
            for (int value : row) {
                graphMatrixText.append(value).append(" ");
            }
            graphMatrixText.append("\n");
        }
        graphMatrixTextArea.setText(graphMatrixText.toString());

        StringBuilder shortestPathsMatrixText = new StringBuilder();
        for (int[] row : shortestPathsMatrix) {
            for (int value : row) {
                shortestPathsMatrixText.append(value).append(" ");
            }
            shortestPathsMatrixText.append("\n");
        }
        shortestPathsMatrixTextArea.setText(shortestPathsMatrixText.toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}