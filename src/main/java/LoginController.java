import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML private TextField userName;
    @FXML private PasswordField password;


    public void login(ActionEvent event) throws IOException {
        String name=userName.getText();
        String pw=password.getText();

        //check for username and pw
        //if true
        Parent root= FXMLLoader.load( getClass().getResource("MainPage.fxml") );
        Scene s1 = ((Button)(event.getSource())).getScene();
        s1.setRoot(root);

    }

}
