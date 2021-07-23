import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

public class MainPageController {
    @FXML private Slider slider;
    @FXML private ChoiceBox shape;

    @FXML private Label name;
    @FXML private Label surname;

    //Main canvas and graphic content.
    @FXML private Canvas canvas;
    private GraphicsContext gc;

    //temp canvas for drawing lines etc.
    @FXML private Canvas temp;
    private GraphicsContext tempGc;

    public static Image image;
    public static Image download;

    private double startX;
    private double startY;
    private boolean startedDrawing=false;
    private double lineWidth;
    private Color lineColor;
    private int shapeCode;//0 for line 1 for point

    private Renderer renderer;

    public User user;

    @FXML
    public void initialize(){
        lineWidth=1.0;
        shapeCode=0;
        lineColor=Color.BLACK;
        gc = canvas.getGraphicsContext2D();
        renderer=new Renderer(gc);

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lineWidth=newValue.doubleValue();
            }
        });

        shape.setItems(FXCollections.observableArrayList(
                "Line", "Point"));
        shape.setValue("Line");
        shape.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                shapeCode=newValue.intValue();
            }
        });
     }


    private void setTempCanvas(Image img){//temp canvas settings.
        double width =img.getWidth();
        double height=img.getHeight();
        canvas.setWidth(width);
        canvas.setHeight(height);
        temp.setWidth(canvas.getWidth());
        temp.setHeight(canvas.getHeight());
        temp.setLayoutX(canvas.getLayoutX());
        temp.setLayoutY(canvas.getLayoutY());
        tempGc= temp.getGraphicsContext2D();
    }
    @FXML
    public void mousePressed(MouseEvent event){
        tempGc.setLineWidth(this.lineWidth);
        tempGc.setStroke(this.lineColor);


        tempGc.setFill(this.lineColor);
        startX=event.getX();
        startY=event.getY();
        if(shapeCode==1){
            tempGc.fillOval(startX-(lineWidth/2),startY-(lineWidth/2),lineWidth,lineWidth);
        }
    }

    @FXML
    public void mouseReleased(MouseEvent event){
        if(shapeCode==1){
            SnapshotParameters parameters=new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            Image snap=tempGc.getCanvas().snapshot(parameters,null);
            Draw draw=new Draw(snap,1);
            tempGc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
            drawAndRender(draw);
        }
        else if(startedDrawing){
            SnapshotParameters parameters=new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            Image snap=tempGc.getCanvas().snapshot(parameters,null);
            Draw draw=new Draw(snap,0);
            tempGc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
            drawAndRender(draw);
            startedDrawing=false;
        }
        download=null;
        download=canvas.snapshot(new SnapshotParameters(),null);
    }


    private void drawAndRender(Draw draw){
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        gc.drawImage(image,0,0);
        renderer.AddShape(draw);
        renderer.render();
    }


    @FXML
    public void mouseDragged(MouseEvent event){
        if(shapeCode==1) return;
        startedDrawing=true;
        tempGc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        tempGc.strokeLine(startX,startY, event.getX(), event.getY());

    }
    @FXML
    public void colorChanged(Event e){
        Color color=((ColorPicker)(e.getSource())).getValue();
        this.lineColor=color;
    }
    @FXML
    public void undo(){
        renderer.undo();
    }
    @FXML
    public void redo(){
        renderer.redo();
    }




    public void setImage(Image img){
        this.gc.clearRect(0,0,this.gc.getCanvas().getWidth(),this.gc.getCanvas().getHeight());
        this.image=img;
        gc.drawImage(image,0,0);
        setTempCanvas(image);
    }


}
