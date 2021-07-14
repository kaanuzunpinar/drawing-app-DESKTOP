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
    @FXML private Button b1;

    //Main canvas and graphic content.
    @FXML private Canvas canvas;
    private GraphicsContext gc;

    //temp canvas for drawing lines etc.
    @FXML private Canvas temp;
    private GraphicsContext tempGc;

    private Image image;

    private double startX;
    private double startY;
    private boolean startedDrawing=false;

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
        image=new Image(fi);
        gc.drawImage(image,0,0);
        setTempCanvas(image);
    }


    private void setTempCanvas(Image img){//temp canvas settings.
        double width =img.getWidth() , height=img.getHeight();
        canvas.setWidth(width);
        canvas.setHeight(height);
        temp.setWidth(canvas.getWidth());
        temp.setHeight(canvas.getHeight());
        tempGc= temp.getGraphicsContext2D();
    }

    @FXML
    public void mousePressed(MouseEvent event){
        startX=event.getX();
        startY=event.getY();
    }
    @FXML
    public void mouseReleased(MouseEvent event){
        if(startedDrawing){
            SnapshotParameters parameters=new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            Image snap=tempGc.getCanvas().snapshot(parameters,null);
            snaps.add(snap);
            render();
            startedDrawing=false;
        }
    }
    @FXML
    public void mouseDragged(MouseEvent event){
        startedDrawing=true;
       tempGc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
       tempGc.strokeLine(startX,startY, event.getX(), event.getY());

    }

    public void render(){
        gc.drawImage(image,0,0);
        for(Image img : snaps){
            gc.drawImage(img,0,0);
        }
    }
}
