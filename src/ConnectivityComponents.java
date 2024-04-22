import java.util.Scanner;
//Задача 5
public class ConnectivityComponents {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод размерности матрицы
        System.out.print("Введите размерность матрицы: ");
        int n = scanner.nextInt();

        // Ввод матрицы смежности
        int[][] adjacencyMatrix = new int[n][n];
        System.out.println("Введите матрицу смежности:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjacencyMatrix[i][j] = scanner.nextInt();
            }
        }

        // Вычисление числа компонент связности
        int[] visited = new int[n];
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) {
                dfs(adjacencyMatrix, visited, i);
                count++;
            }
        }

        System.out.println("Число компонент связности: " + count);
    }

    private static void dfs(int[][] adjacencyMatrix, int[] visited, int vertex) {
        visited[vertex] = 1;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[vertex][i] == 1 && visited[i] == 0) {
                dfs(adjacencyMatrix, visited, i);
            }
        }
    }
}