import java.util.Scanner;


public class SolveForOdd {
    public static void main(String[] args){
        long ans;
        Scanner sc= SolverForEven.getMyScan();
        int numOfTest=Integer.parseInt(sc.nextLine());
        for(int i=1;i<=numOfTest;i++){
            OddBoard curBoard=arrangeBoard(sc);
            if(!curBoard.getCross().stabilizeCross() || !curBoard.getBoard().stabilizeBoard()){
                ans=-1;
            }
            else {
                curBoard.getCross().maximizeHunters();
                curBoard.getBoard().maximizeHunters();
                ans = curBoard.getCross().getAddedHuntersInCross() + curBoard.getBoard().getAddedHunters();
            }
            System.out.println("Case #"+i+": "+ans);
        }
    }

    /**
     * @param sc = input scanner.
     * @return OddBoard loaded with relevant input.
     */
    private static OddBoard arrangeBoard(Scanner sc){
        String[] result=sc.nextLine().split("\\s");
        int N=Integer.parseInt(result[0]);
        int B=Integer.parseInt(result[1]);
        int H=Integer.parseInt(result[2]);
        OddBoard curBoard=new OddBoard(N);
        for(int j=0;j<B;j++){
            result=sc.nextLine().split("\\s");
            int x=Integer.parseInt(result[0]);
            int y=Integer.parseInt(result[1]);
            checkLocationAndAdd(curBoard,x,y, Constants.Adders.BOXES);
        }
        for(int j=0;j<H;j++){
            result=sc.nextLine().split("\\s");
            int x=Integer.parseInt(result[0]);
            int y=Integer.parseInt(result[1]);
            checkLocationAndAdd(curBoard,x,y,Constants.Adders.HUNTERS);
        }
        return curBoard;
    }

    /**
     * @return result of Linear-Transformation applied to @num.
     */
    private static int applyTransformation(int num,Integer N){
        return num > ((N-1)/2) ? num-1 : num;
    }

    /**
     * Main purpose: Wrapper function which calculate if the object belongs to the inner cross or outside the inner cross.
     * If object belongs to the outside part, applying linear-transformation on the coordinates of the object.
     * Example : Given N=3 (Board N = 2, Cross N =3) and object on (x=3,y=3), we evaluate that the object is outside the inner-cross,
     * therefore, we apply transformation on it's coordinates and it becomes (x=2,y=2).
     */
    private static void checkLocationAndAdd(OddBoard board, int x, int y, Constants.Adders adder){
        if(crossCoordinates(x,y,board.getN())) { // Cross
            if (adder == Constants.Adders.BOXES) {
                board.getCross().addBox(x, y);
            } else {
                board.getCross().addHunter(x,y);
            }
        }
        else{ // Board
            x=applyTransformation(x,board.getN());
            y=applyTransformation(y,board.getN());
            if (adder == Constants.Adders.BOXES) {
                board.getBoard().addBox(x, y);
            } else {
                board.getBoard().addHunter(x,y);
            }
        }
    }

    /**
     * @return @True if x or y belongs to the inner-cross coordinates.
     */
    private static boolean crossCoordinates(int x,int y,Integer N){
        boolean ans=false;
        int midValue=(N+1)/2;
        if(x==midValue || y==midValue)
            ans=true;
        return ans;
    }

}
