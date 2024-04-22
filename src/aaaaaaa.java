import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class aaaaaaa extends Application {
    private TextArea adjacencyMatrixTextArea;
    private TextArea traversalResultTextArea;
    private Pane graphPane;
    private List<Vertex> vertices;
    private List<Edge> edges;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Обход в глубину");

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
        graphPane.setPrefSize(600, 400);
        graphPane.setStyle("-fx-background-color: white;");

        gridPane.add(adjacencyMatrixLabel, 0, 0);
        gridPane.add(adjacencyMatrixTextArea, 0, 1, 2, 1);
        gridPane.add(traverseButton, 0, 2);
        gridPane.add(traversalResultTextArea, 0, 3, 2, 1);
        gridPane.add(graphPane, 2, 0, 1, 4);

        Scene scene = new Scene(gridPane, 800, 500);
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
            vertices = new ArrayList<>();
            edges = new ArrayList<>();

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

                Label label = new Label(Integer.toString(i + 1));
                label.setLayoutX(x + 10);
                label.setLayoutY(y - 15);

                Vertex vertex = new Vertex(circle, label);
                vertices.add(vertex);
                graphPane.getChildren().addAll(circle, label);

                makeVertexDraggable(vertex);
            }

            // Создание ребер графа
            for (int i = 0; i < numVertices; i++) {
                for (int j = i + 1; j < numVertices; j++) {
                    if (adjacencyMatrix[i][j] == 1) {
                        Vertex start = vertices.get(i);
                        Vertex end = vertices.get(j);

                        Line line = new Line(start.getCircle().getCenterX(), start.getCircle().getCenterY(),
                                end.getCircle().getCenterX(), end.getCircle().getCenterY());
                        line.setStroke(Color.BLACK);

                        Edge edge = new Edge(start, end, line);
                        edges.add(edge);
                        graphPane.getChildren().add(line);
                    }
                }
            }

            // Выполнение обхода графа в глубину
            StringBuilder traversalResult = new StringBuilder();
            boolean[] visited = new boolean[numVertices];
            depthFirstTraversal(adjacencyMatrix, 0, visited, traversalResult);

            traversalResultTextArea.setText(traversalResult.toString());
        } catch (NumberFormatException e) {
            traversalResultTextArea.setText("Некорректный ввод.");
        }
    }

    private void depthFirstTraversal(int[][] adjacencyMatrix, int vertex, boolean[] visited, StringBuilder traversalResult) {
        visited[vertex] = true;
        traversalResult.append(vertex + 1).append(" ");

        // Перекраска пройденного шага
        vertices.get(vertex).getCircle().setFill(Color.ORANGE);

        for (int i = 0; i < adjacencyMatrix[vertex].length; i++) {
            if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                depthFirstTraversal(adjacencyMatrix, i, visited, traversalResult);

                // Перекраска ребра после прохода
                for (Edge edge : edges) {
                    if (edge.hasVertices(vertex, i)) {
                        edge.getLine().setStroke(Color.ORANGE);

                        // Добавление стрелки
                        Polygon arrowHead = createArrowHead(edge.getLine().getEndX(), edge.getLine().getEndY(),
                                edge.getLine().getStartX(), edge.getLine().getStartY());
                        arrowHead.setFill(Color.ORANGE);
                        graphPane.getChildren().add(arrowHead);
                    }
                }
            }
        }
    }

    private void makeVertexDraggable(Vertex vertex) {
        Circle circle = vertex.getCircle();
        Label label = vertex.getLabel();

        circle.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                vertex.setDragging(true);
                vertex.setMouseDeltaX(circle.getCenterX() - e.getX());
                vertex.setMouseDeltaY(circle.getCenterY() - e.getY());
            }
        });

        circle.setOnMouseDragged(e -> {
            if (vertex.isDragging()) {
                double newX = e.getX() + vertex.getMouseDeltaX();
                double newY = e.getY() + vertex.getMouseDeltaY();

                circle.setCenterX(newX);
                circle.setCenterY(newY);
                label.setLayoutX(newX + 10);
                label.setLayoutY(newY - 15);

                updateEdges(vertex);
            }
        });

        circle.setOnMouseReleased(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                vertex.setDragging(false);
            }
        });
    }

    private void updateEdges(Vertex vertex) {
        for (Edge edge : edges) {
            if (edge.getStart() == vertex || edge.getEnd() == vertex) {
                Line line = edge.getLine();

                if (edge.getStart() == vertex) {
                    line.setStartX(vertex.getCircle().getCenterX());
                    line.setStartY(vertex.getCircle().getCenterY());
                } else {
                    line.setEndX(vertex.getCircle().getCenterX());
                    line.setEndY(vertex.getCircle().getCenterY());
                }
            }
        }
    }

    private Polygon createArrowHead(double x1, double y1, double x2, double y2) {
        double phi = Math.toRadians(30);
        double barb = 10;
        double dx = x2 - x1;
        double dy = y2 - y1;
        double theta = Math.atan2(dy, dx);
        double x, y, rho = theta + phi;

        Polygon polygon = new Polygon();
        polygon.setStroke(Color.ORANGE);
        polygon.setStrokeLineCap(StrokeLineCap.ROUND);
        polygon.setFill(Color.ORANGE);

        for (int j = 0; j < 2; j++) {
            x = x2 - barb * Math.cos(rho);
            y = y2 - barb * Math.sin(rho);
            polygon.getPoints().addAll(x2, y2, x, y);
            rho = theta - phi;
        }

        return polygon;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class Vertex {
        private Circle circle;
        private Label label;
        private boolean dragging;
        private double mouseDeltaX;
        private double mouseDeltaY;

        public Vertex(Circle circle, Label label) {
            this.circle = circle;
            this.label = label;
            this.dragging = false;
        }

        public Circle getCircle() {
            return circle;
        }

        public Label getLabel() {
            return label;
        }

        public boolean isDragging() {
            return dragging;
        }

        public void setDragging(boolean dragging) {
            this.dragging = dragging;
        }

        public double getMouseDeltaX() {
            return mouseDeltaX;
        }

        public void setMouseDeltaX(double mouseDeltaX) {
            this.mouseDeltaX = mouseDeltaX;
        }

        public double getMouseDeltaY() {
            return mouseDeltaY;
        }

        public void setMouseDeltaY(double mouseDeltaY) {
            this.mouseDeltaY = mouseDeltaY;
        }
    }

    private static class Edge {
        private Vertex start;
        private Vertex end;
        private Line line;

        public Edge(Vertex start, Vertex end, Line line) {
            this.start = start;
            this.end = end;
            this.line = line;
        }

        public Vertex getStart() {
            return start;
        }

        public Vertex getEnd() {
            return end;
        }

        public Line getLine() {
            return line;
        }

        public boolean hasVertices(int vertex1, int vertex2) {
    return (start.getLabel().getText().equals(Integer.toString(vertex1 + 1)) && end.getLabel().getText().equals(Integer.toString(vertex2 + 1)))
            || (start.getLabel().getText().equals(Integer.toString(vertex2 + 1)) && end.getLabel().getText().equals(Integer.toString(vertex1 + 1)));
}

    }
}
