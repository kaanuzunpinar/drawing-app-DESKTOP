import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class MainPageController {
    @FXML private AnchorPane root;
    @FXML private Canvas canvas;
    @FXML private Button b1;
    //temp canvas for drawing lines etc.
    @FXML private Canvas temp;

    private GraphicsContext gc;
    private GraphicsContext tempGc;


    private double startX;
    private double startY;

    ArrayList<Image> snaps;
    public MainPageController(){
        snaps=new ArrayList<>();
    }
    public void press(){
        gc = canvas.getGraphicsContext2D();

        FileChooser fileChooser= new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.JPG","*.jpg","*.png","*.png","*.JPEG","*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        FileInputStream fi=null;
        try {
            fi=new FileInputStream( selectedFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Image image=new Image(fi);
        gc.drawImage(image,0,0);
        tempGc= temp.getGraphicsContext2D();
    }

/*
    private void setTempCanvas(){//temp canvas settings.
        temp.setWidth(canvas.getWidth());
        temp.setHeight(canvas.getHeight());
        temp.setLayoutX(canvas.getLayoutX());
        temp.setLayoutY(canvas.getLayoutY());
        tempGc= temp.getGraphicsContext2D();
        temp.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
    }
*/
    @FXML
    public void mousePressed(MouseEvent event){
        startX=event.getX();
        startY=event.getY();
    }
    @FXML
    public void mouseReleased(MouseEvent event){
        SnapshotParameters parameters=new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        Image snap=tempGc.getCanvas().snapshot(parameters,null);
        //snaps.add(snap);
        gc.drawImage(snap,0,0);
        //temp=new Canvas();
        //setTempCanvas();
    }
    @FXML
    public void mouseDragged(MouseEvent event){
       tempGc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
       tempGc.strokeLine(startX,startY, event.getX(), event.getY());
    /*
        GraphicsContext context = temp.getGraphicsContext2D();
        context.clearRect(0, 0, temp.getWidth(), temp.getHeight());
        context.strokeLine(startX,startY, event.getSceneX(), event.getSceneY());*/

    }

}
