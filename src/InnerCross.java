public class InnerCross {
    private Boxes boxes;
    private Hunters hunters;
    private CommonRegions comRegs;
    private long addedHunters;
    private Integer N;
    private boolean midValObject;

    public InnerCross(Integer n){
        midValObject=false;
        N=n;
        addedHunters=0;
        boxes=new Boxes();
        hunters=new Hunters();
        comRegs=new CommonRegions(n);
    }
    /** Assumes: Board has already been loaded with input.
     * Main Purpose: Check if it is possible to stabilize the inner cross according to the space left in the common regions
     * If possible, adding the hunters necessary in order to stabilize the cross.
     * Example : | B H E |  ----> | B H E |    Where E=Empty,B=Box,H=Hunter.
     *           | E E E |        | E E E |
     *           | E E E |        | E H E |
     * @return @True if Inner Cross can be stabilized , @False otherwise.
     */
    public boolean stabilizeCross(){
        boolean ans=false;
        if(hunters.checkBalance()){
            ans=true;
        }
        else {
            Integer[] minRegs = hunters.findMinOppositeRegions();
            Integer oppoReg1 = Board.getCoupleRegion(minRegs[0]);
            Integer oppoReg2 = Board.getCoupleRegion(minRegs[1]);
            long huntersToAddInReg1 = hunters.getHuntersInRegion(oppoReg1) - hunters.getHuntersInRegion(minRegs[0]);
            long huntersToAddInReg2 = hunters.getHuntersInRegion(oppoReg2) - hunters.getHuntersInRegion(minRegs[1]);
            if(comRegs.checkIfPossibleToDec(minRegs[0],huntersToAddInReg1) &&
                    comRegs.checkIfPossibleToDec(minRegs[1],huntersToAddInReg2)){
                comRegs.decSpaceInCommonRegion(minRegs[0],huntersToAddInReg1);
                comRegs.decSpaceInCommonRegion(minRegs[1],huntersToAddInReg1);
                addedHunters+=huntersToAddInReg1;
                addedHunters+=huntersToAddInReg2;
                ans=true;
            }
        }
        return ans;
    }
    /** Assumes: Cross is stable.
     * Main Purpose: Adding the minimum between 2 opposite regions twice (for each region)
     * And also check if possible to add a hunter in the middle of the matrix.
     * Example : | B H E |  ----> | B H E |    Where E=Empty,B=Box,H=Hunter.
     *           | E E E |        | H H H |
     *           | E H E |        | E H E |
     */
    public void maximizeHunters(){
        long firstAddition=2*Math.min((comRegs.getSpaceLeftInCommonRegion(Constants.RIGHT)),comRegs.getSpaceLeftInCommonRegion(Constants.LEFT));
        long secondAddition=2*Math.min((comRegs.getSpaceLeftInCommonRegion(Constants.UP)),comRegs.getSpaceLeftInCommonRegion(Constants.DOWN));
        addedHunters+=(firstAddition+secondAddition);
        if(!midValObject)
            addedHunters+=1;
    }

    public long getAddedHuntersInCross(){return addedHunters;}


    public void addBox(int x, int y){
        wrapperAdder(x,y,Constants.Adders.BOXES);
    }

    public void addHunter(int x,int y){
        wrapperAdder(x,y,Constants.Adders.HUNTERS);
    }

    /**
     * Main purpose: Calculate the region which the object defined by @x @y
     * And add it to the relevant region.
     * @param chooser = HUNTERS | BOXES
     */
    private void wrapperAdder(int x,int y,Constants.Adders chooser){
        Integer reg=-1;
        if(x==((N+1)/2) && y==((N+1)/2)){ // Middle-Case
                midValObject=true;
                reg=Constants.MID;
        }
        else if(x==((N+1)/2)){ // Up or Down Case
            reg=Board.upOrDownReg(y,(N-1)/2);
        }
        else if(y==((N+1)/2)){ // Right or Left case
            reg=Board.leftOrRightReg(x,(N-1)/2);
        }
        if (reg != -1 && reg<Constants.MID) {
            if (chooser == Constants.Adders.BOXES) {
                adder(reg, Constants.Adders.BOXES);
            } else {
                adder(reg, Constants.Adders.HUNTERS);
            }
        }
        else if(reg==-1){
            System.out.println("Couldn't Add inside Cross");
        }
    }

    private void adder(Integer reg, Constants.Adders chooser){
        if (comRegs.checkIfPossibleToDec(reg, 1)) {
            comRegs.decSpaceInCommonRegion(reg, 1);
            if(chooser==Constants.Adders.HUNTERS) {
                hunters.addHunterInRegion(reg);
            }
            else{
                boxes.addBoxInRegion(reg);
            }
        }
    }
}
