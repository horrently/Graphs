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
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import static javafx.application.Application.launch;

//Задача 3

public class task3 extends Application {

    private TextArea adjacencyMatrixTextArea;
    private TextArea traversalResultTextArea;
    private Pane graphPane;
    private List<Circle> circles;
    private List<Line> lines;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Обход в ширину");

        // Создание интерфейса JavaFX
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label adjacencyMatrixLabel = new Label("Введите матрицу смежности:");
        adjacencyMatrixTextArea = new TextArea();
        adjacencyMatrixTextArea.setPrefRowCount(10);
        adjacencyMatrixTextArea.setPrefColumnCount(10);

        Button traverseButton = new Button("Выполнить обход");
        traverseButton.setOnAction(e -> traverseGraph());

        traversalResultTextArea = new TextArea();
        traversalResultTextArea.setEditable(false);

        graphPane = new Pane();
        graphPane.setPrefSize(300, 300);
        graphPane.setStyle("-fx-background-color: white;");

        gridPane.add(adjacencyMatrixLabel, 0, 0);
        gridPane.add(adjacencyMatrixTextArea, 0, 1, 2, 1);
        gridPane.add(traverseButton, 0, 2);
        gridPane.add(traversalResultTextArea, 0, 3, 2, 1);
        gridPane.add(graphPane, 2, 0, 1, 4);

        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void traverseGraph() {
        try {
            String matrixText = adjacencyMatrixTextArea.getText();
            String[] rows = matrixText.split("\n");
            int[][] adjacencyMatrix = new int[rows.length][rows.length];

            // Преобразование матрицы смежности в двумерный массив
            for (int i = 0; i < rows.length; i++) {
                String[] elements = rows[i].split(" ");
                for (int j = 0; j < elements.length; j++) {
                    adjacencyMatrix[i][j] = Integer.parseInt(elements[j]);
                }
            }

            // Очистка предыдущего отображения графа
            graphPane.getChildren().clear();
            circles = new ArrayList<>();
            lines = new ArrayList<>();

            // Создание вершин графа
            int numVertices = adjacencyMatrix.length;
            double centerX = graphPane.getPrefWidth() / 2;
            double centerY = graphPane.getPrefHeight() / 2;
            double radius = 100;
            double angle = 2 * Math.PI / numVertices;

            for (int i = 0; i < numVertices; i++) {
                double x = centerX + radius * Math.cos(i * angle);
                double y = centerY + radius * Math.sin(i * angle);

                Circle circle = new Circle(x, y, 7);
                circle.setFill(Color.TRANSPARENT);
                circle.setStroke(Color.BLACK);
                circle.setStrokeType(StrokeType.OUTSIDE);
                circles.add(circle);
                graphPane.getChildren().add(circle);

                Label label = new Label(Integer.toString(i+1));
                label.setLayoutX(x + 10);
                label.setLayoutY(y - 15);
                graphPane.getChildren().add(label);
            }

            // Создание ребер графа
            for (int i = 0; i < numVertices; i++) {
                for (int j = i + 1; j < numVertices; j++) {
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

            // Выполнение обхода графа в глубину
            StringBuilder traversalResult = new StringBuilder();
            boolean[] visited = new boolean[numVertices];
            breadthFirstTraversal(adjacencyMatrix, 0, visited, traversalResult);

            traversalResultTextArea.setText(traversalResult.toString());
        } catch (NumberFormatException e) {
            traversalResultTextArea.setText("Некорректный ввод.");
        }
    }

    private void breadthFirstTraversal(int[][] adjacencyMatrix, int startVertex, boolean[] visited, StringBuilder traversalResult) {
        Queue<Integer> queue = new LinkedList<>();
        visited[startVertex] = true;
        queue.offer(startVertex);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            traversalResult.append(vertex+1).append(" ");
            
            // Перекраска пройденного шага
            circles.get(vertex).setFill(Color.ORANGE);

            for (int i = 0; i < adjacencyMatrix[vertex].length; i++) {
                if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    queue.offer(i);
                    
                    // Перекраска ребра после прохода
                for (Line line : lines) {
                    if ((line.getStartX() == circles.get(vertex).getCenterX() &&
                        line.getStartY() == circles.get(vertex).getCenterY() &&
                        line.getEndX() == circles.get(i).getCenterX() &&
                        line.getEndY() == circles.get(i).getCenterY()) ||
                        (line.getEndX() == circles.get(vertex).getCenterX() &&
                        line.getEndY() == circles.get(vertex).getCenterY() &&
                        line.getStartX() == circles.get(i).getCenterX() &&
                        line.getStartY() == circles.get(i).getCenterY())) {
                            line.setStroke(Color.ORANGE);
                    }
                }
                    
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
