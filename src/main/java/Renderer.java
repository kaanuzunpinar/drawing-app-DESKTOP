import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Stack;

public class Renderer {
    Stack<Events> undoStack;
    Stack<Events> redoStack;
    ArrayList<Draw> drawings;
    Image image;
    public Renderer(Image img){
        drawings=new ArrayList<>();
        image=img;
    }

    public void render(GraphicsContext gc){
        gc.drawImage(image,0,0);
        for(Draw draw : drawings){
            Image img=draw.snap;
            gc.drawImage(img,0,0);
        }
    }
}


class Events{
    String type;
    Draw draw;
}