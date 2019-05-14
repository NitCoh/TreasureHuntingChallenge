import java.math.BigInteger;

/**
 * Representing the space left in common regions {LEFT-UP,LEFT-DOWN,RIGHT-UP,RIGHT-DOWN}
 */
public class CommonRegions {
    private long[] CR;

    public CommonRegions(Integer n){
        CR=new long[4];
        BigInteger space=calculateInitializedSpace(n);
        for(int i=0;i<4;i++){
            CR[i]=(space.longValue());
        }
    }

    /**
     * Initializing space=@n according the case = {even,odd}
     * @return The init space in each common-region.
     */
    private BigInteger calculateInitializedSpace(Integer n){
        BigInteger ans;
        if(n%2==0){
            BigInteger nsqu=BigInteger.valueOf(n);
            nsqu=nsqu.pow(2);
            ans=nsqu.divide(BigInteger.valueOf(4));
        }
        else{
            ans=BigInteger.valueOf(n-1);
            ans=ans.multiply(BigInteger.valueOf(2));
            ans=ans.divide(BigInteger.valueOf(4));
        }
        return ans;
    }


    public long getSpaceLeftInCommonRegion(Integer comReg){
        return CR[comReg];
    }

    /**
     * Main purpose: Check if there is enough @space to decrease in @comReg.
     * @return @True if there is, @False otherwise.
     */
    public boolean checkIfPossibleToDec(Integer comReg, long space){
        boolean ans=false;
        if((CR[comReg]-space)>=0) {
            ans=true;
        }
        return ans;
    }

    /**
     * Main purpose: Decrease space in @comReg in a given amount @space.
     */
    public void decSpaceInCommonRegion(Integer comReg,long space){
            CR[comReg] -= space;
    }

    /**
     * @return Common region according to reg1 and reg2.
     * Example : reg1=Left,regs=Down --> LD = LEFT DOWN.
     */
    public Integer evaluateCommonRegion(Integer reg1,Integer reg2){
        Integer ans=-1;
        if((reg1.compareTo(Constants.RIGHT)==0 &&
            reg2.compareTo(Constants.UP)==0)
                ||
            (reg1.compareTo(Constants.UP)==0 &&
            reg2.compareTo(Constants.RIGHT)==0)){
            ans=Constants.RU;
        }
        else if((reg1.compareTo(Constants.RIGHT)==0 &&
                reg2.compareTo(Constants.DOWN)==0)
                ||
                (reg2.compareTo(Constants.RIGHT)==0 &&
                reg1.compareTo(Constants.DOWN)==0)){
            ans=Constants.RD;
        }
        else if((reg1.compareTo(Constants.LEFT)==0 &&
                reg2.compareTo(Constants.UP)==0)
                ||
                (reg2.compareTo(Constants.LEFT)==0 &&
                 reg1.compareTo(Constants.UP)==0)){
            ans=Constants.LU;
        }
        else if((reg1.compareTo(Constants.LEFT)==0 &&
                reg2.compareTo(Constants.DOWN)==0)
                ||
                (reg2.compareTo(Constants.LEFT)==0 &&
                 reg1.compareTo(Constants.DOWN)==0)){
            ans=Constants.LD;
        }
        return ans;
    }
}
