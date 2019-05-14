/**
 * Representing an interface with the current hunters on board.
 */

public class Hunters {
    private long[] hunters;

    public Hunters(){
        hunters=new long[4];
        for(int i=0;i<4;i++){
            hunters[i]=0;
        }
    }

    public long getHuntersInRegion(Integer i){
        return hunters[i];
    }

    /**
     * @return The first region with the minimal amount of hunters.
     */
    public Integer findFirstMinHuntersInRegion(){
        long min=Long.MAX_VALUE;
        int minIndex=-1;
        for(int i=0;i<4;i++){
            if(hunters[i]<min){
                min=hunters[i];
                minIndex=i;
            }
        }
        return minIndex;
    }
    public void addHunterInRegion(Integer region){
        hunters[region]+=1;
    }


    /**
     * Main purpose: Returns min hunters in Right or Left regions and min hunters in Up or Down regions.
     * @return ans[0]=RIGHT | LEFT , ans[1]= UP | DOWN
     */
    public Integer[] findMinOppositeRegions(){
        Integer[] ans=new Integer[2];
        long min=Long.MAX_VALUE;
        int minIndex=-1;
        for(int i=0;i<2;i++){
            if(hunters[i]<min){
                min=hunters[i];
                minIndex=i;
            }
        }
        ans[0]=minIndex;
        for(int i=2;i<4;i++){
            if(hunters[i]<min){
                min=hunters[i];
                minIndex=i;
            }
        }
        ans[1]=minIndex;
        return ans;
    }

    /**
     * Main purpose: Check if the number of hunters in regions are currently stable.
     * @return @True if stable, @False otherwise.
     */
    public Boolean checkBalance(){
        Boolean ans=false;
        if(hunters[Constants.RIGHT]==(hunters[Constants.LEFT]) &&
                hunters[Constants.UP]==(hunters[Constants.DOWN])){ ans=true;}
        return ans;
    }
}

