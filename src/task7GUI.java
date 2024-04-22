import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Scanner;

public class task7GUI extends Application {

    private static final int V = 5; // Количество вершин графа
    private TextArea adjacencyMatrixTextArea;
    private TextArea minimumSpanningTreeTextArea;
    private Canvas graphCanvas;

    // Вспомогательная функция для поиска вершины с минимальным значением ключа в наборе вершин, которые еще не включены в остовное дерево
    private int findMinKey(int[] key, boolean[] mstSet) {
        int minKey = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!mstSet[v] && key[v] < minKey) {
                minKey = key[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    // Функция для печати остовного дерева, представленного массивом parent
    private String printMST(int[] parent, int[][] graph) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ребро \tВес ребра").append("\n");
        for (int i = 1; i < V; i++) {
            sb.append((parent[i] + 1)).append(" ---- ").append((i + 1)).append("\t").append(graph[i][parent[i]]).append("\n");
        }
        return sb.toString();
    }

    // Функция для построения минимального остовного дерева взвешенного графа, представленного в виде матрицы смежности
    public String primMST(int[][] graph) {
        StringBuilder sb = new StringBuilder();
        int[] parent = new int[V]; // Массив для хранения остовного дерева
        int[] key = new int[V]; // Ключи для выбора минимального веса ребра в срезе
        boolean[] mstSet = new boolean[V]; // Массив, чтобы отслеживать вершины, уже включенные в остовное дерево

        // Инициализация всех ключей как бесконечность и mstSet[] как false
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        // Включение первой вершины в остовное дерево
        key[0] = 0; // Устанавливаем ключ первой вершины равным нулю
        parent[0] = -1; // Она не имеет родительской вершины

        // Построение остовного дерева для V-1 вершин
        for (int count = 0; count < V - 1; count++) {
            int u = findMinKey(key, mstSet); // Выбираем вершину с минимальным значением ключа из набора вершин, еще не включенных в остовное дерево
            mstSet[u] = true; // Добавляем выбранную вершину в остовное дерево

            // Обновляем значения ключей и родительских вершин смежных вершин, только если текущий вес меньше ключа и вершина еще не включена в остовное дерево
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        sb.append(printMST(parent, graph)); // Печатаем остовное дерево
        return sb.toString();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Алгоритм Прима");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label adjacencyMatrixLabel = new Label("Матрица:");
        adjacencyMatrixTextArea = new TextArea();
        adjacencyMatrixTextArea.setPrefRowCount(5);
        adjacencyMatrixTextArea.setWrapText(true);

        Label minimumSpanningTreeLabel = new Label("Минимальное остовное дерево:");
        minimumSpanningTreeTextArea = new TextArea();
        minimumSpanningTreeTextArea.setPrefRowCount(5);
        minimumSpanningTreeTextArea.setWrapText(true);
        minimumSpanningTreeTextArea.setEditable(false);

        graphCanvas = new Canvas(400, 300);

        Button calculateButton = new Button("Вычислить");
        calculateButton.setOnAction(e -> {
            String adjacencyMatrixText = adjacencyMatrixTextArea.getText();
            int[][] graph = parseAdjacencyMatrix(adjacencyMatrixText);
            String minimumSpanningTreeText = primMST(graph);
            minimumSpanningTreeTextArea.setText(minimumSpanningTreeText);
            drawGraph(graph);
        });

        gridPane.add(adjacencyMatrixLabel, 0, 0);
        gridPane.add(adjacencyMatrixTextArea, 0, 1);
        gridPane.add(minimumSpanningTreeLabel, 0, 2);
        gridPane.add(minimumSpanningTreeTextArea, 0, 3);
        gridPane.add(graphCanvas, 1, 0, 1, 4);
        gridPane.add(calculateButton, 0, 4);

        Scene scene = new Scene(gridPane, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private int[][] parseAdjacencyMatrix(String adjacencyMatrixText) {
        int[][] graph = new int[V][V];
        Scanner scanner = new Scanner(adjacencyMatrixText);

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (scanner.hasNextInt()) {
                    graph[i][j] = scanner.nextInt();
                } else {
                    // Handle invalid input
                    graph[i][j] = 0;
                    scanner.next();
                }
            }
        }

        scanner.close();
        return graph;
    }

    private void drawGraph(int[][] graph) {
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());

        double centerX = graphCanvas.getWidth() / 2;
        double centerY = graphCanvas.getHeight() / 2;
        double radius = Math.min(centerX, centerY) * 0.8;
        double angleIncrement = 2 * Math.PI / V;

        // Рисование вершин
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2.0);
        for (int i = 0; i < V; i++) {
            double x = centerX + radius * Math.cos(i * angleIncrement);
            double y = centerY + radius * Math.sin(i * angleIncrement);
            gc.fillOval(x - 10, y - 10, 20, 20);
            gc.strokeOval(x - 10, y - 10, 20, 20);

            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(i + 1), x + 10, y - 15);
        }

        // Рисование ребер
        gc.setLineWidth(1.0);
        for (int i = 0; i < V; i++) {
            double x1 = centerX + radius * Math.cos(i * angleIncrement);
            double y1 = centerY + radius * Math.sin(i * angleIncrement);

            for (int j = i + 1; j < V; j++) {
                if (graph[i][j] != 0) {
                    double x2 = centerX + radius * Math.cos(j * angleIncrement);
                    double y2 = centerY + radius * Math.sin(j * angleIncrement);

                    gc.setStroke(Color.BLUE);
                    gc.strokeLine(x1, y1, x2, y2);

                    double labelX = (x1 + x2) / 2;
                    double labelY = (y1 + y2) / 2;
                    gc.setFill(Color.BLACK);
                    gc.fillText(String.valueOf(graph[i][j]), labelX, labelY);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}