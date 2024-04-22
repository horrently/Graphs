// Задача 9
public class shortenPaths {
    public static int[][] GenerateGraphMatrix(int vertexNum){//генерируем связный граф
        int[][] M=new int[vertexNum][vertexNum];
        boolean correct=false;
        while(!correct) {
            for (int i = 0; i < vertexNum; i++)
                for (int j = 0; j < vertexNum; j++) {
                    M[i][j] = 1+(int) (Math.random() * 5);
                    M[i][i]=0;
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
    private static int n=4;
    private static int[][] Matrix=GenerateGraphMatrix(n);//Сама матрица
    private static int[][] dist=Matrix;

    public static void floydWarshall() {
        int i, j, k;

        for (i = 0; i < n; i++)
            for (j = 0; j < n; j++)
                dist[i][j] = Matrix[i][j];
        for (k = 0; k < n; k++)
            for (i = 0; i < n; i++)
                for (j = 0; j < n; j++)
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
    }
    public static void main(String[] args) {
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++)
                System.out.print(Matrix[i][j]+" ");
            System.out.println();
        }

        floydWarshall();
        System.out.println();

        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++)
                System.out.print(dist[i][j]+" ");
            System.out.println();
        }


    }
}
