import javafx.scene.image.Image;

public class Draw {
    Image snap;
    int type;//0 for line 1 for point
    boolean drawable;
    public Draw(Image snap,int type){
        this.snap=snap;
        this.type=type;
        drawable=true;
    }

}
