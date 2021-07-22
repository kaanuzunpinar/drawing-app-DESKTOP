import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML private TextField userName;
    @FXML private PasswordField password;


    public void login(ActionEvent event) throws IOException {
        String name=userName.getText();
        String pw=password.getText();

        //check for username and pw
        //if true
        User user= JavaPostreSQL.login(name,pw);
        if(user!=null){
            FXMLLoader loader=new FXMLLoader(getClass().getResource("Menu.fxml"));
            Parent root= loader.load();
            MenuController menuController=loader.getController();
            menuController.setUser(user);
            Scene s1 = ((Button)(event.getSource())).getScene();
            s1.setRoot(root);
        }
        else{
            System.out.println("Error");
        }



    }

}
