

public class Board {
    private Boxes boxes;
    private Hunters hunters;
    private CommonRegions comRegs;
    private long addedHunters;
    private Integer N;

    public Board(Integer n){
        N=n;
        addedHunters=0;
        boxes=new Boxes();
        hunters=new Hunters();
        comRegs=new CommonRegions(n);
    }

    /** Assumes: Board has already been loaded with input.
     * Main Purpose: Check if it is possible to stabilize the board according to the space left in the common regions
     * If possible, adding the hunters necessary in order to stabilize the board.
     * Example : | B H |  ----> | B H |    Where E=Empty,B=Box,H=Hunter.
     *           | E E |        | H E |
     * @return @True if board can be stabilized , @False otherwise.
     */
    public Boolean stabilizeBoard(){
        Boolean ans=false;
        if(hunters.checkBalance()){
            ans=true;
        }
        else{
            Integer minRegion=hunters.findFirstMinHuntersInRegion();
            Integer coupledminReg=getCoupleRegion(minRegion);
            long hunInReg=hunters.getHuntersInRegion(coupledminReg);
            Integer[] oppRegs=getOppositeRegions(coupledminReg);
            Integer comReg1=comRegs.evaluateCommonRegion(minRegion,oppRegs[0]);
            long huntersToAddToCommonRegion1=hunInReg-hunters.getHuntersInRegion(oppRegs[0]);
            Integer comReg2=comRegs.evaluateCommonRegion(minRegion,oppRegs[1]);
            long huntersToAddToCommonRegion2=hunInReg-hunters.getHuntersInRegion(oppRegs[1]);
            if(comRegs.checkIfPossibleToDec(comReg1,huntersToAddToCommonRegion1) &&
                comRegs.checkIfPossibleToDec(comReg2,huntersToAddToCommonRegion2)){
                comRegs.decSpaceInCommonRegion(comReg1,huntersToAddToCommonRegion1);
                comRegs.decSpaceInCommonRegion(comReg2,huntersToAddToCommonRegion2);
                addedHunters+=huntersToAddToCommonRegion1;
                addedHunters+=huntersToAddToCommonRegion2;
                ans=true;
            }
        }
        return ans;
    }

    /** Assumes: Board is stable.
     * Main Purpose: Adding the minimum between 2 opposite regions twice (for each region)
     * Example : If there is 10 seats left in Left-Up region and 6 seats left in Right-Down (opposite common regions), we add 2*6=12
     */
    public void maximizeHunters(){
        long firstAddition=2*Math.min((comRegs.getSpaceLeftInCommonRegion(Constants.LD)),comRegs.getSpaceLeftInCommonRegion(Constants.RU));
        long secondAddition=2*Math.min((comRegs.getSpaceLeftInCommonRegion(Constants.LU)),comRegs.getSpaceLeftInCommonRegion(Constants.RD));
        addedHunters+=(firstAddition+secondAddition);
    }
    public long getAddedHunters(){return addedHunters;}

    /** addBox/addHunter:
     * Main purpose: In even situation, each object belongs to 2 regions at the time, so
     * We first find which are the regions, evaluate their common region and then add it
     * and decrease 1 in their common region.
     * Example : N=2, (x=1,y=1) -> Object belongs to Left and Up, and also Left-Up common region.
     */
    public void addBox(int i,int j){
        Integer reg1=leftOrRightReg(j,N/2);
        Integer reg2=upOrDownReg(i,N/2);
        Integer common=comRegs.evaluateCommonRegion(reg1,reg2);
        if(comRegs.checkIfPossibleToDec(common,1)) {
            boxes.addBoxInRegion(reg1);
            boxes.addBoxInRegion(reg2);
            comRegs.decSpaceInCommonRegion(common, 1);
        }
        else
            System.out.println("Couldn't Add Box");
    }
    public void addHunter(int i,int j){
        Integer reg1=leftOrRightReg(j,N/2);
        Integer reg2=upOrDownReg(i,N/2);
        Integer common=comRegs.evaluateCommonRegion(reg1,reg2);
        if(comRegs.checkIfPossibleToDec(common,1)) {
            hunters.addHunterInRegion(reg1);
            hunters.addHunterInRegion(reg2);
            comRegs.decSpaceInCommonRegion(common, 1);
        }
        else
            System.out.println("Couldn't Add Hunter");
    }


    /**
     * @param midValue = the middle index of the matrix (N/2) | ((N+1)/2))
     * @return The corresponding region which the index belong to.
     */
   public static Integer leftOrRightReg(int j,int midValue){
        if(j<=(midValue))
            return Constants.LEFT;
        else
            return Constants.RIGHT;
    }
    public static Integer upOrDownReg(int i,int midValue){
        if(i<=(midValue))
            return Constants.UP;
        else
            return Constants.DOWN;
    }

    /**
     * @param region = Right | Left | Up | Down
     * @return The coupled region.
     * Example : Right -> Left, If Up -> Down and so on.
     */
    public static Integer getCoupleRegion(Integer region){
        Integer ans=-1;
        if(region.compareTo(Constants.RIGHT)==0)
            ans= Constants.LEFT;
        else if(region.compareTo(Constants.LEFT)==0)
            ans= Constants.RIGHT;
        else if(region.compareTo(Constants.UP)==0)
            ans= Constants.DOWN;
        else if(region.compareTo(Constants.DOWN)==0)
            ans= Constants.UP;
        return ans;
    }

    /**
     * @param region = Right | Left | Up | Down
     * @return An array includes the opposite regions.
     * Example : Left -> [Up,Down], Up->[Right,Left].
     */
    private static Integer[] getOppositeRegions(Integer region){
        Integer[] ans=new Integer[2];
        if(region.compareTo(Constants.RIGHT)==0 || region.compareTo(Constants.LEFT)==0){
            ans[0]=Constants.UP;
            ans[1]=Constants.DOWN;
        }
        else{
            ans[0]=Constants.RIGHT;
            ans[1]=Constants.LEFT;
        }
        return ans;
    }
}
