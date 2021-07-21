import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;

public class JavaPostreSQL {
    public static User login(String username,String password){
        String url="jdbc:postgresql://localhost:5432/postgres";
        String query="SELECT * FROM users WHERE username= ? AND password=?";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection= DriverManager.getConnection(url,"postgres","root");
            PreparedStatement pst= connection.prepareStatement(query);
            pst.setString(1,username);
            pst.setString(2,password);
            ResultSet resultSet=pst.executeQuery();
            if(!resultSet.next()){
                return null;
            }
            else{
                BigDecimal id=resultSet.getBigDecimal(1);
                String db_username=resultSet.getString(2);
                String db_email=resultSet.getString(3);
                String db_name=resultSet.getString(4);
                String db_surname=resultSet.getString(5);
                User u1=new User(db_name,db_surname,db_username,db_email);
                return u1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
