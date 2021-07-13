import javafx.event.ActionEvent;

public class MainController {
    public void generateRandom(ActionEvent event){
        int rand=(int)(Math.random()*50+1);
        System.out.println(rand);
    }
}
