import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class JavaPostreSQL {
    private static final String url = "jdbc:postgresql://rogue.db.elephantsql.com/xlkzkjzr";
    private static final String databaseUser = "xlkzkjzr";
    private static final String databasPassword = "5fxzzt3vHFnjbP4pyCzOuO8HMI4JtC46";



    public static User login(String username,String password){

        String query="SELECT * FROM users WHERE username= ? AND password=?";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection= DriverManager.getConnection(url,databaseUser,databasPassword);
            PreparedStatement pst= connection.prepareStatement(query);
            pst.setString(1,username);
            String passwordHashed= DigestUtils.sha256Hex(password);
            pst.setString(2,passwordHashed);
            ResultSet resultSet=pst.executeQuery();
            if(!resultSet.next()){
                connection.close();
                return null;
            }
            else{
                BigDecimal id=resultSet.getBigDecimal(1);
                String db_username=resultSet.getString(2);
                String db_email=resultSet.getString(3);
                String db_name=resultSet.getString(4);
                String db_surname=resultSet.getString(5);
                User u1=new User(db_name,db_surname,db_username,db_email,id);
                timeOfPeople(id,u1);
                connection.close();
                return u1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }




    public static void timeOfPeople(BigDecimal id,User user){
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection= DriverManager.getConnection(url,databaseUser,databasPassword);

            String query="SELECT COUNT(person.userid) FROM person JOIN users ON users.userid=person.userid WHERE users.userid=?;";
            PreparedStatement pst= connection.prepareStatement(query);
            pst.setBigDecimal(1,id);
            ResultSet resultSet=pst.executeQuery();
            if(resultSet.next()){
                user.numberOfPeople=resultSet.getInt(1);
                connection.close();
            }
            else{
                connection.close();
                System.out.println("Error!");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }




    public static int findPersonId(String cId) throws Exception {

        Class.forName("org.postgresql.Driver");
        Connection connection= DriverManager.getConnection(url,databaseUser,databasPassword);

        String query2= "SELECT * FROM person WHERE citizenid='"+cId+"'";;
        PreparedStatement pst2= connection.prepareStatement(query2);
        ResultSet resultSet=pst2.executeQuery();

        int personid=-1;
        if(resultSet.next()){
            BigDecimal personId=resultSet.getBigDecimal(1);
            personid=personId.intValue();

        }
        connection.close();
        return personid;

    }




    public static void registerPerson(Person person){
        String query="INSERT INTO person(userid,name,surname,citizenid,birthdate,birthplace,address,lastsavingtime,imagepath) " +
                                    "VALUES(?,?,?,?,?,?,?,?,?);";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection= DriverManager.getConnection(url,databaseUser,databasPassword);
            PreparedStatement pst= connection.prepareStatement(query);

            pst.setBigDecimal(1,person.userId);
            pst.setString(2,person.name);
            pst.setString(3,person.surname);
            pst.setString(4,person.citizenId);
            String[] dates=person.date.split("-");
            LocalDate localDate= LocalDate.of(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
            Date date=Date.valueOf(localDate);
            pst.setDate(5,date);
            pst.setString(6,person.birthPlace);
            pst.setString(7,person.address);
            java.util.Date d1= new java.util.Date();
            pst.setDate(8,new Date( d1.getTime()));
            pst.setString(9,person.imagePath);
            pst.executeUpdate();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static Person selectPerson(String name,String surname,BigDecimal id){
        String query="SELECT * FROM person WHERE name= ? AND surname=? AND personid=?";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection= DriverManager.getConnection(url,databaseUser,databasPassword);
            PreparedStatement pst= connection.prepareStatement(query);
            pst.setString(1,name);
            pst.setString(2,surname);
            pst.setBigDecimal(3,id);
            ResultSet resultSet=pst.executeQuery();
            if(!resultSet.next()){
                connection.close();
                return null;
            }
            else{
                String idC=resultSet.getString(5);
                String birthPlace=resultSet.getString(6);
                String address=resultSet.getString(8);
                String image=resultSet.getString(10);
                Date date=resultSet.getDate(7);
                Person p1=new Person(name,surname,idC,birthPlace,address,image,date,id);
                connection.close();
                return p1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }





    public static void editPerson(String personId,Person person){
        String query="UPDATE person SET (name,surname,citizenid,birthdate,birthplace,address,lastsavingtime,imagepath)= (?,?,?,?,?,?,?,?) WHERE personId="+new BigDecimal(Integer.parseInt(personId))+";";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection= DriverManager.getConnection(url,databaseUser,databasPassword);
            PreparedStatement pst= connection.prepareStatement(query);
            pst.setString(1,person.name);
            pst.setString(2,person.surname);
            pst.setString(3,person.citizenId);
            String[] dates=person.date.split("-");
            LocalDate localDate= LocalDate.of(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
            Date date=Date.valueOf(localDate);
            pst.setDate(4,date);
            pst.setString(5,person.birthPlace);
            pst.setString(6,person.address);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Date date1 = Date.valueOf(timestamp.toLocalDateTime().toLocalDate());
            pst.setDate(7,date1);
            pst.setString(8,person.imagePath);
            pst.executeUpdate();
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
