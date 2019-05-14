import java.util.Scanner;

public class SolverForEven {

    public static void main(String[] args){
        long ans;
        Scanner sc=getMyScan();
        int numOfTest=Integer.parseInt(sc.nextLine());
        for(int i=1;i<=numOfTest;i++){
            Board curBoard=arrangeBoard(sc);
            if(!curBoard.stabilizeBoard()){
                ans=-1;
            }
            else {
                curBoard.maximizeHunters();
                ans = curBoard.getAddedHunters();
            }
            System.out.println("Case #"+i+": "+ans);
        }
    }

    private static Board arrangeBoard(Scanner sc){
        String[] result=sc.nextLine().split("\\s");
        int N=Integer.parseInt(result[0]);
        int B=Integer.parseInt(result[1]);
        int H=Integer.parseInt(result[2]);
        Board curBoard=new Board(N);
        for(int j=0;j<B;j++){
            result=sc.nextLine().split("\\s");
            int x=Integer.parseInt(result[0]);
            int y=Integer.parseInt(result[1]);
            curBoard.addBox(y,x);
        }
        for(int j=0;j<H;j++){
            result=sc.nextLine().split("\\s");
            int x=Integer.parseInt(result[0]);
            int y=Integer.parseInt(result[1]);
            curBoard.addHunter(y,x);
        }
        return curBoard;
    }

    public static Scanner getMyScan(){
        System.out.println("Please enter input and click Enter");
        Scanner sc= new Scanner(System.in);
        return sc;
    }
}
