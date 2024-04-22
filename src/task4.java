import java.util.LinkedList;
import java.util.Queue;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Random;
import static javafx.application.Application.launch;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//Задача 4

public class task4 extends Application {

    private int[][] adjacencyMatrix;
    private String traversalResult;
    private int size;
    Label resultLabel = new Label();
    private TextArea MatrixTextArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Проверка правильности обхода в ширину");

        // Создание случайной матрицы смежности
        Random random = new Random();
        size = random.nextInt(6) + 1;
        adjacencyMatrix = generateRandomMatrix(size);

        // Выполнение обхода графа в глубину
        StringBuilder resultBuilder = new StringBuilder();
        boolean[] visited = new boolean[size];
        breadthFirstTraversal(0, visited, resultBuilder);
        traversalResult = resultBuilder.toString();

        // Создание интерфейса JavaFX
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        Label questionLabel = new Label("Введите результат обхода в ширину:");
        MatrixTextArea = new TextArea(matrixToString(adjacencyMatrix));
        MatrixTextArea.setPrefColumnCount(7);
        MatrixTextArea.setPrefRowCount(5);

        TextField answerTextField = new TextField();
        Button submitButton = new Button("Проверить");
        submitButton.setOnAction(e -> checkAnswer(answerTextField.getText()));

        gridPane.add(questionLabel, 0, 0);
        gridPane.add(MatrixTextArea, 0, 1);
        gridPane.add(answerTextField, 0, 2);
        gridPane.add(submitButton, 0, 3);
        gridPane.add(resultLabel, 0, 4, 2, 1);

        Scene scene = new Scene(gridPane, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int[][] generateRandomMatrix(int vertexNum) {
        int[][] M=new int[vertexNum][vertexNum];
        for(int i=0;i<vertexNum;i++){
            for(int j=i+1;j<vertexNum;j++)
                M[i][j]=(int)Math.round(Math.random());
            int count=0;
            for(int j=i+1;j<vertexNum;j++)
                if(M[i][j]==0)count++;
            if(count==vertexNum-i)i--;
        }
        for(int i=0;i<vertexNum;i++)
            for(int j=i+1;j<vertexNum;j++)
                M[j][i]=M[i][j];
        return M;
    }

    private void breadthFirstTraversal(int startVertex, boolean[] visited, StringBuilder traversalResult) {
        Queue<Integer> queue = new LinkedList<>();
        visited[startVertex] = true;
        queue.offer(startVertex);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            traversalResult.append(vertex+1).append(" ");

            for (int i = 0; i < adjacencyMatrix[vertex].length; i++) {
                if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue.offer(i);
                }
            }
        }
    }

    private String matrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void checkAnswer(String answer) {
        if (answer.trim().equals(traversalResult.trim())) {
            resultLabel.setText("Правильно!");
        } else {
            resultLabel.setText("Неправильно!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}