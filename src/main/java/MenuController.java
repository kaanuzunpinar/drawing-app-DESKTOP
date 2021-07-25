import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MenuController {
    @FXML private Label name,surname,error,info,savedPeople;
    @FXML private TextField nameF,surnameF,citizenId,address,birthPlace,
            nameEdit,surnameEdit,citizenIdEdit,addressEdit,birthPlaceEdit,nameSearch,surnameSearch,idSearch;
    @FXML private DatePicker birthDate,birthDateEdit;
    @FXML private VBox personInfo;
    private User user;
    Image image=null;
    Image editedImage;

    private String personId;
    public void initialize(){

    }
    public void setUser(User user){
        this.user=user;
        this.name.setText(user.name);
        this.surname.setText(user.surname);
        this.savedPeople.setText(user.numberOfPeople+"");
    }
    public void imageWindow() throws IOException {
        if(image==null){
            error.setText("PLEASE UPLOAD AN IMAGE FIRST!");
            return;
        }
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
            this.image=new Image(fi);
            MainPageController.download=image;
        } catch (Exception e) {
            System.out.println("Cancelled Image picking");
        }

    }

    public void save(){
        Person person;
        String cId=citizenId.getText();
        try{
            LocalDate d=birthDate.getValue();//yyyy-mm-dd
            person=new Person(user.id,nameF.getText(),surnameF.getText(),
                    citizenId.getText(),birthPlace.getText(),address.getText(),null,d.toString());
            if(this.image==null){
                error.setText("Please Upload an Image!");
                return;
            }
            try{
                Integer.parseInt(citizenId.getText());
            }
            catch (Exception e){
                error.setText("Citizen ID Must be an INTEGER");
                return;
            }
            JavaPostreSQL.registerPerson(person);
        }
        catch (Exception e){
            error.setText("Please Fill All Areas!");
            return;
        }

        int personId= 0;

        try {
            personId = JavaPostreSQL.findPersonId(cId);

        } catch (Exception e) {
            error.setText("Something Went Wrong! Try Again.");
            return;
        }
        String filePath=System.getProperty("user.dir")+"/image_"+personId+ ".png";
        File outputFile =new File(filePath);
        person.imagePath=filePath;
        try {
            ImageIO.write( SwingFXUtils.fromFXImage(MainPageController.download, null),"png",outputFile);
        } catch (IOException e) {
            error.setText("Please Upload an Image!");
            e.printStackTrace();
            return;
        }
        error.setText("PERSON SAVED WITH ID: "+personId);

       int yeni=Integer.parseInt( this.savedPeople.getText())+1;
       this.savedPeople.setText(yeni+"");
    }
    public void editName(){
        this.nameEdit.setEditable(true);
        this.nameEdit.setDisable(false);
    }
    public void editSurname(){
        this.surnameEdit.setEditable(true);
        this.surnameEdit.setDisable(false);
    }
    public void editId(){
        this.citizenIdEdit.setEditable(true);
        this.citizenIdEdit.setDisable(false);
    }
    public void editAdress(){
        this.addressEdit.setEditable(true);
        this.addressEdit.setDisable(false);
    }
    public void editBirthplace(){
        this.birthPlaceEdit.setEditable(true);
        this.birthPlaceEdit.setDisable(false);
    }
    public void editBirthDate(){
        this.birthDateEdit.setEditable(true);
        this.birthDateEdit.setDisable(false);
    }

    public void edit(){
        String name=nameSearch.getText();
        String surname=surnameSearch.getText();
        String id=idSearch.getText();
        if(name.length()==0||surname.length()==0||id.length()==0){
            info.setText("Please Fill All The Search Areas!");
            return;
        }
        int idInt;
        try{
            idInt=Integer.parseInt(id);
        }
        catch(Exception e){
            info.setText("Id Must be an Integer!");
            return;
        }
        Person person=JavaPostreSQL.selectPerson(name,surname,new BigDecimal(idInt));
        if(person==null){
            info.setText("No person found!");
            return;
        }
        personInfo.setVisible(true);

        this.nameEdit.setText(person.name);
        this.surnameEdit.setText(person.surname);
        this.birthPlaceEdit.setText(person.birthPlace);
        this.birthDateEdit.setValue(person.dateAsSql.toLocalDate());
        this.addressEdit.setText(person.address);
        this.citizenIdEdit.setText(person.citizenId);
        personId=id;
        File file=new File(System.getProperty("user.dir")+"/image_"+personId+ ".png");
        Image image=new Image(file.toURI().toString());
        this.editedImage=image;
        MainPageController.download=this.editedImage;
        MainPageController.image=this.editedImage;
        info.setText("Person Info is Loaded.");
    }

    public void chooseEdited(){
        FileChooser fileChooser= new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.JPG","*.jpg","*.png","*.png","*.JPEG","*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        FileInputStream fi=null;
        try {
            fi=new FileInputStream( selectedFile.getAbsolutePath());
            this.image=new Image(fi);
            editedImage=image;
        } catch (Exception e) {
            System.out.println("Cancelled Image picking");
        }

    }
    public void secondWindowEdited(){

        if(editedImage==null){
            info.setText("PLEASE UPLOAD AN IMAGE FIRST!");
            return;
        }
        FXMLLoader loader=new FXMLLoader(getClass().getResource("MainPage.fxml"));
        Parent root= null;
        try {
            root = loader.load();
            MainPageController mc=loader.getController();
            Stage stage=new Stage();
            Scene scene=new Scene(root,1200 ,800);
            stage.setScene(scene);
            stage.show();
            mc.setImage(this.editedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveEdited(){
        String filePath=System.getProperty("user.dir")+"/image_"+personId+ ".png";
        File outputFile =new File(filePath);

        if(this.nameEdit.getText().length()==0 || this.surnameEdit.getText().length()==0 || this.birthPlaceEdit.getText().length()==0 ||
                this.addressEdit.getText().length()==0 || this.citizenIdEdit.getText().length()==0){
            info.setText("Please Fill All Areas!!");

            return;
        }
        try{
            LocalDate d=birthDateEdit.getValue();//yyyy-mm-dd
            Person person=new Person(user.id,nameEdit.getText(),surnameEdit.getText(),
                    citizenIdEdit.getText(),birthPlaceEdit.getText(),addressEdit.getText(),System.getProperty("user.dir")+"/image_"+personId+ ".png",d.toString());
            try{
                Integer.parseInt(citizenIdEdit.getText());
            }
            catch (Exception e){
                info.setText("Citizen ID Must be an INTEGER");
                return;
            }
            JavaPostreSQL.editPerson(personId,person);
        }
        catch (Exception e){
            info.setText("Please Fill All Areas!");
            e.printStackTrace();
            return;
        }


        try {
            outputFile.delete();
            outputFile=new File(filePath);
            ImageIO.write( SwingFXUtils.fromFXImage(MainPageController.download, null),"png",outputFile);
            this.editedImage=MainPageController.download;
        } catch (IOException e) {
            info.setText("Please Upload an Image!");
            e.printStackTrace();
            return;
        }

        info.setText("SAVED!");


    }
    public void logout(ActionEvent event) throws IOException {
        this.user=null;

        FXMLLoader loader=new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root= loader.load();
        Scene s1 = ((Button)(event.getSource())).getScene();
        s1.setRoot(root);
    }
}
