import java.util.Scanner;

//Задача 2

public class DFS {

    public static int[][] GenerateConGraphMatrix(int vertexNum){//генерируем связный граф
        int[][] M=new int[vertexNum][vertexNum];
        for(int i=0;i<vertexNum;i++){
            for(int j=i+1;j<vertexNum;j++)
                M[i][j]=(int)Math.round(Math.random());
            int count=0;
            for(int j=i+1;j<vertexNum;j++)
                if(M[i][j]==0)count++;
            if(count==vertexNum-i)i--;
        }
        for(int i=0;i<vertexNum;i++)
            for(int j=i+1;j<vertexNum;j++)
                M[j][i]=M[i][j];
        return M;
    }
    private static int[][] Matrix=GenerateConGraphMatrix(5);//Сама матрица(нужно вывести в интерфейс)
    private static boolean[] visited=new boolean[Matrix.length];

    public static String depthFirstSearch(int begVer){
        String result="";
        visited[begVer]=true;
        result=(begVer+1)+" ";
        for(int i=0;i<Matrix.length;i++){
            if(Matrix[begVer][i]==1 && !visited[i])
                result=result+depthFirstSearch(i);
        }
        return result;
    }
    public static void check(){
        Scanner in=new Scanner(System.in);
        String progAns=depthFirstSearch(0);
        String userAns=in.nextLine();
        if(progAns.equals(userAns+' '))System.out.print("Ответ правильный");
        else System.out.print("Ответ неверный. Правильный ответ "+progAns);
    }
    public static void main(String[] args) {
        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++)
                System.out.print(Matrix[i][j]+" ");
            System.out.println();
        }
        check();
    }
}
