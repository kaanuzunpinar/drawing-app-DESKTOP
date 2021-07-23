import java.math.BigDecimal;
import java.sql.Date;

public class Person {
    BigDecimal userId,personId;
    String name,surname,citizenId,birthPlace,address,imagePath,date;
    Date dateAsSql;
    public Person(BigDecimal userId,String name,String surname,
                  String citizenId,String birthPlace,String address,
                  String imagePath, String date){
        this.userId=userId;
        this.name=name;
        this.surname=surname;
        this.citizenId=citizenId;
        this.birthPlace=birthPlace;
        this.address=address;
        this.imagePath=imagePath;
        this.date=date;
    }
    public Person(String name,String surname,
                  String citizenId,String birthPlace,String address,
                  String imagePath, Date d,BigDecimal personId){
        this.personId=personId;
        this.name=name;
        this.surname=surname;
        this.citizenId=citizenId;
        this.birthPlace=birthPlace;
        this.address=address;
        this.imagePath=imagePath;
        this.dateAsSql=d;
    }
}
