import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
//Задача 2
public class bbbbbbb extends Application {
    private int[][] matrix;
    private int size;
    private boolean[] visited;
    Label resultLabel = new Label();
    private TextArea MatrixTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Создание случайной матрицы смежности
        Random random = new Random();
        size = random.nextInt(6) + 1;
        matrix = generateConGraphMatrix(size);
        visited = new boolean[matrix.length];

        Label lblAnswer = new Label("Введите результат обхода в глубину\nдля данной матрицы:");
        TextField txtAnswer = new TextField();

        Button btnCheck = new Button("Проверить");
        btnCheck.setOnAction(e -> checkAnswer(txtAnswer.getText()));
        
        primaryStage.setTitle("Проверка правильности обхода в глубину");

        // Создание интерфейса JavaFX
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        MatrixTextArea = new TextArea(matrixToString(matrix));
        MatrixTextArea.setPrefColumnCount(7);
        MatrixTextArea.setPrefRowCount(5);

        TextField answerTextField = new TextField();
        Button submitButton = new Button("Проверить");
        submitButton.setOnAction(e -> checkAnswer(answerTextField.getText()));

        gridPane.add(lblAnswer, 0, 0);
        gridPane.add(MatrixTextArea, 0, 1, 2, 1);
        gridPane.add(txtAnswer, 0, 2);
        gridPane.add(btnCheck, 0, 3);
        gridPane.add(resultLabel, 0, 4, 2, 1);

        Scene scene = new Scene(gridPane, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
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

    private int[][] generateConGraphMatrix(int vertexNum) {
        int[][] M = new int[vertexNum][vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            for (int j = i + 1; j < vertexNum; j++)
                M[i][j] = (int) Math.round(Math.random());
            int count = 0;
            for (int j = i + 1; j < vertexNum; j++)
                if (M[i][j] == 0) count++;
            if (count == vertexNum - i) i--;
        }
        for (int i = 0; i < vertexNum; i++)
            for (int j = i + 1; j < vertexNum; j++)
                M[j][i] = M[i][j];
        return M;
    }

    public String depthFirstSearch(int begVer) {
        String result = "";
        visited[begVer] = true;
        result = (begVer + 1) + " ";
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[begVer][i] == 1 && !visited[i])
                result = result + depthFirstSearch(i);
        }
        return result;
    }

    public void checkAnswer(String userAns) {
        try {
            String progAns = depthFirstSearch(0);
            if (progAns.equals(userAns + " ")) {
                resultLabel.setText("Ответ правильный");
            } else {
                resultLabel.setText("Ответ неверный. Правильный ответ " + progAns);
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Некорректный ввод.");
        }
    }
}