import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MenuController {
    @FXML private Label name,surname;
    @FXML private TextField nameF,surnameF,citizenId,adress,birthPlace;
    @FXML private DatePicker birthDate;
    Image image=null;
    public void initialize(){

    }
    public void setUser(User user){
        this.name.setText(user.name);
        this.surname.setText(user.surname);
    }
    public void imageWindow() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root=loader.load();
        MainPageController mc=loader.getController();
        Stage stage=new Stage();
        Scene scene=new Scene(root,1200 ,800);
        stage.setScene(scene);
        stage.show();
        mc.setImage(this.image);
    }
    public void choose(){
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
        this.image=new Image(fi);
    }

    public void save(){
        File outputFile =new File(System.getProperty("user.dir")+"/image.png");
        try {
            ImageIO.write( SwingFXUtils.fromFXImage(MainPageController.download, null),"png",outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
