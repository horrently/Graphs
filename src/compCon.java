// Задача 6
import java.util.*;
public class compCon {
    public static int[][] GenerateGraphMatrix(int vertexNum){//генерируем граф
        int[][] M=new int[vertexNum][vertexNum];
        for(int i=0;i<vertexNum;i++) {
            for (int j = i + 1; j < vertexNum; j++) {
                M[i][j] = (int) Math.round(Math.random());
                M[j][i] = M[i][j];
            }
        }
        return M;
    }
    private static int n=7;
    private static int[][] Matrix=GenerateGraphMatrix(n);//Сама матрица(нужно вывести в интерфейс)
    private static boolean[] visited=new boolean[Matrix.length];

    public static void depthFirstSearch(int begVer){
        visited[begVer]=true;
        for(int i=0;i<Matrix.length;i++)
            if(Matrix[begVer][i]==1 && !visited[i])
                depthFirstSearch(i);
    }
    public static int compCon() {
        int result=0;
        for(int i=0;i<n;i++)
            if(!visited[i]) {
                depthFirstSearch(i);
                result++;
            }
        return result;
    }
    public static void check(){
        Scanner in=new Scanner(System.in);
        int progAns=compCon();
        int userAns=in.nextInt();
        if(progAns==userAns)System.out.print("Ответ правильный");
        else System.out.print("Ответ неверный. Правильный ответ "+progAns);
    }

    public static void main(String[] args) {
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++)
                System.out.print(Matrix[i][j]+" ");
            System.out.println();
        }
        check();
    }
}
