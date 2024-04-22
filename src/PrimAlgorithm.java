//Задача 7
import java.util.*;

public class PrimAlgorithm {

    private static final int V = 5; // Количество вершин графа

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
    private void printMST(int[] parent, int[][] graph) {
        System.out.println("Ребро \tВес ребра");
        for (int i = 1; i < V; i++) {
            System.out.println((parent[i]+1) + " - " + (i+1) + "\t" + graph[i][parent[i]]);
        }
    }

    // Функция для построения минимального остовного дерева взвешенного графа, представленного в виде матрицы смежности
    public void primMST(int[][] graph) {
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

        printMST(parent, graph); // Печатаем остовное дерево
    }

    public static void main(String[] args) {
        PrimAlgorithm algorithm = new PrimAlgorithm();

// Чтение входных данных с помощью Scanner
        Scanner scanner = new Scanner(System.in);
        int[][] graph = new int[V][V];

        System.out.println("Введите элементы матрицы смежности:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                graph[i][j] = scanner.nextInt();
            }
        }

        algorithm.primMST(graph); // Построение минимального остовного дерева

        scanner.close(); // Закрываем Scanner
    }
}