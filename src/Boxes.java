
/**
 * Representing an interface to reach the number of boxes in each region.
 */
public class Boxes {
    private long[] boxes;

    public Boxes(){
        boxes=new long[4];
        for(int i=0;i<4;i++){
            boxes[i]=0;
        }
    }

    public void addBoxInRegion(Integer region){
        boxes[region]+=1;
    }


}
