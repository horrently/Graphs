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

// Задача 6
public class task6GUI extends Application {
    private int[][] matrix;
    private int n;
    private boolean[] visited;
    Label resultLabel = new Label();
    private TextArea MatrixTextArea;

    @Override
    public void start(Stage primaryStage) {
        // Генерация графа
        Random random = new Random();
        n = random.nextInt(6) + 1;
        matrix = generateGraphMatrix(n);
        visited = new boolean[matrix.length];

        // Создание интерфейса
        GridPane gridPane = new GridPane();
        Label matrixLabel = new Label(matrixToString(matrix));

        Label lblComponents = new Label("Число компонент связности:");
        TextField txtComponents = new TextField();

        MatrixTextArea = new TextArea(matrixToString(matrix));
        MatrixTextArea.setPrefColumnCount(7);
        MatrixTextArea.setPrefRowCount(5);
        
        Button btnCheck = new Button("Проверить");
        btnCheck.setOnAction(e -> {
            try {
                checkAnswer(Integer.parseInt(txtComponents.getText()));
            } catch (NumberFormatException a) {
                resultLabel.setText("Неправильный ввод.");
            }
        });
        
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        
        gridPane.add(lblComponents, 0, 0);
        gridPane.add(MatrixTextArea, 0, 1);
        gridPane.add(txtComponents, 0, 2);
        gridPane.add(btnCheck, 0, 3);
        gridPane.add(resultLabel, 0, 4, 2, 1);

        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setTitle("Компоненты связности");
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

    private int[][] generateGraphMatrix(int vertexNum) {
        int[][] M = new int[vertexNum][vertexNum];
        for (int i = 0; i < vertexNum; i++) {
            for (int j = i + 1; j < vertexNum; j++) {
                M[i][j] = (int) Math.round(Math.random());
                M[j][i] = M[i][j];
            }
        }
        return M;
    }

    public void depthFirstSearch(int begVer) {
        visited[begVer] = true;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[begVer][i] == 1 && !visited[i]) {
                depthFirstSearch(i);
            }
        }
    }

    public int compCon() {
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                depthFirstSearch(i);
                result++;
            }
        }
        return result;
    }

    public void checkAnswer(int userAns) {
        try {
            int progAns = compCon();
            if (progAns == userAns) {
                resultLabel.setText("Ответ правильный");
            } else {
                resultLabel.setText("Ответ неверный. Правильный ответ " + progAns);
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Некорректный ввод.");
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}