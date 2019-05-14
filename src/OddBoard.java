public class OddBoard {

    private Board board;
    private InnerCross cross;
    private Integer N;

    /**
     * Solving odd case using reduction to even board with N=n-1 and InnerCross.
     * Delegation of InnerCross and Board objects.
     * @param n
     */
    public OddBoard(Integer n){
        N=n;
        board = new Board(n-1);
        cross=new InnerCross(n);
    }


    public InnerCross getCross() {
        return cross;
    }

    public Board getBoard() {
        return board;
    }

    public Integer getN() {
        return N;
    }
}
