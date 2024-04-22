import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class task5GUI extends Application {
private TextArea adjacencyMatrixTextArea;
    private TextArea componentCountTextArea;
    private Pane graphPane;
    private int[][] adjacencyMatrix;
    private int[] visited;
    private int n;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Компоненты связности");

        // Создание интерфейса JavaFX
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label adjacencyMatrixLabel = new Label("Введите матрицу смежности:");
        adjacencyMatrixTextArea = new TextArea();
        adjacencyMatrixTextArea.setPrefRowCount(10);
        adjacencyMatrixTextArea.setPrefColumnCount(10);

        Button calculateButton = new Button("Вычислить компоненты связности");
        calculateButton.setOnAction(e -> calculateComponents());

        Label componentCountLabel = new Label("Количество компонент связности:");
        componentCountTextArea = new TextArea();
        componentCountTextArea.setEditable(false);

        graphPane = new Pane();
        graphPane.setPrefSize(300, 300);
        graphPane.setStyle("-fx-background-color: white;");

        gridPane.add(adjacencyMatrixLabel, 0, 0);
        gridPane.add(adjacencyMatrixTextArea, 0, 1, 2, 1);
        gridPane.add(calculateButton, 0, 2);
        gridPane.add(componentCountLabel, 0, 3);
        gridPane.add(componentCountTextArea, 0, 3, 2, 1);
        gridPane.add(graphPane, 2, 0, 1, 4);

        Scene scene = new Scene(gridPane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void calculateComponents() {
        try {
            String matrixText = adjacencyMatrixTextArea.getText();
            String[] rows = matrixText.split("\n");
            n = rows.length;
            adjacencyMatrix = new int[n][n];

            // Преобразование матрицы смежности в двумерный массив
            for (int i = 0; i < n; i++) {
                String[] elements = rows[i].split(" ");
                for (int j = 0; j < n; j++) {
                    adjacencyMatrix[i][j] = Integer.parseInt(elements[j]);
                }
            }

            // Вычисление числа компонент связности
            visited = new int[n];
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (visited[i] == 0) {
                    dfs(i, ++count);
                }
            }
            componentCountTextArea.setText(Integer.toString(count));
            drawGraph();
        } catch (NumberFormatException e) {
            componentCountTextArea.setText("Некорректный ввод.");
        }
    }

    private void dfs(int vertex, int componentIndex) {
        visited[vertex] = componentIndex;
        for (int i = 0; i < n; i++) {
            if (adjacencyMatrix[vertex][i] == 1 && visited[i] == 0) {
                dfs(i, componentIndex);
            }
        }
    }

    private void drawGraph() {
        // Очистка предыдущего отображения графа
        graphPane.getChildren().clear();
        List<Circle> circles = new ArrayList<>();
        List<Line> lines = new ArrayList<>();

        // Вычисление координат для отображения вершин графа
        double centerX = graphPane.getPrefWidth() / 2;
        double centerY = graphPane.getPrefHeight() / 2;
        double radius = 100;
        double angle = 2 * Math.PI / n;

        // Создание вершин графа и их отображение
        for (int i = 0; i < n; i++) {
            double x = centerX + radius * Math.cos(i * angle);
            double y = centerY + radius * Math.sin(i * angle);

            Circle circle = new Circle(x, y, 7);
            circles.add(circle);
            graphPane.getChildren().add(circle);

            Label label = new Label(Integer.toString(i + 1));
            label.setLayoutX(x + 10);
            label.setLayoutY(y - 15);
            graphPane.getChildren().add(label);
        }

        // Создание ребер графа и их отображение
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    Line line = new Line(circles.get(i).getCenterX(),
                            circles.get(i).getCenterY(),
                            circles.get(j).getCenterX(),
                            circles.get(j).getCenterY());
                    lines.add(line);
                    graphPane.getChildren().add(line);
                }
            }
        }

        // Окраска вершин разных компонент связности разными цветами
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PURPLE};
        for (int i = 0; i < n; i++) {
            int componentIndex = visited[i] - 1;
            circles.get(i).setFill(colors[componentIndex]);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}