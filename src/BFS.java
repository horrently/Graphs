// Задача 4
import java.util.*;

public class BFS {
    public static int[][] GenerateConGraphMatrix(int vertexNum){//генерируем связный граф
        int[][] M=new int[vertexNum][vertexNum];
        boolean correct=false;
        while(!correct) {
            for (int i = 0; i < vertexNum; i++) {
                for (int j = i + 1; j < vertexNum; j++) {
                    M[i][j] = (int) Math.round(Math.random());
                    M[j][i] = M[i][j];
                }
            }
            int cnt=0;
            for (int i = 0; i < vertexNum; i++) {
                for (int j = i + 1; j < vertexNum; j++)
                    if(M[i][j]==0)cnt++;
                cnt=0;
            }
            if(cnt!=vertexNum)correct=true;
        }
        return M;
    }
    private static int n=5;
    private static int[][] Matrix=GenerateConGraphMatrix(n);//Сама матрица(нужно вывести в интерфейс)

    public static String BFS(int s) {
        String result="";
        boolean visited[] = new boolean[n];
        LinkedList<Integer> queue = new LinkedList<Integer>();

        visited[s] = true;
        queue.add(s);

        while (queue.size() != 0) {
            s = queue.poll();
            result =result+(s+1)+ " ";
            for(int i=0;i<n;i++)
                if(Matrix[s][i]==1) {
                    int n = i;
                    if (!visited[n]) {
                        visited[n] = true;
                        queue.add(n);
                    }
                }
        }
        return result;
    }
    public static void check(){
        Scanner in=new Scanner(System.in);
        String progAns=BFS(0);
        String userAns=in.nextLine();
        if(progAns.equals(userAns+' '))System.out.print("Ответ правильный");
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
