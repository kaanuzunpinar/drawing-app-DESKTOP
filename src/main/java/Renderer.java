import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Stack;

public class Renderer {
    Stack<Events> undoStack;
    Stack<Events> redoStack;
    ArrayList<Draw> drawings;
    GraphicsContext gc;
    public Renderer(GraphicsContext gc){
        drawings=new ArrayList<>();
        undoStack=new Stack<>();
        redoStack=new Stack<>();
        this.gc=gc;
    }

    public void render(){
        for(Draw draw : drawings){
            if(!draw.drawable) continue;
            Image img=draw.snap;
            gc.drawImage(img,0,0);
        }
    }


    public void AddShape(Draw draw){
        this.drawings.add(draw);
        Events event=new Events(draw,"add");
        undoStack.push(event);
    }

    public void undo(){
        if(undoStack.size()==0) return;
        Events event=undoStack.pop();
        if(event.type.equals("add")){
            event.draw.drawable=false;
        }
        redoStack.push(event);
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        gc.drawImage(MainPageController.image,0,0);
        render();
    }

    public void redo(){
        if(redoStack.size()==0) return;
        Events event=redoStack.pop();
        if(event.type.equals("add")){
            event.draw.drawable=true;
        }
        undoStack.push(event);
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        gc.drawImage(MainPageController.image,0,0);
        render();
    }
}


class Events{
    String type;
    Draw draw;
    public Events(Draw d,String type){
        this.draw=d;
        this.type=type;
    }
}