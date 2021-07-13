import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainController {
    @FXML private TextField userName;
    @FXML private PasswordField password;
    public void login(ActionEvent event){
        String name=userName.getText();
        String pw=password.getText();
        System.out.println(name+" "+pw);
    }
}
