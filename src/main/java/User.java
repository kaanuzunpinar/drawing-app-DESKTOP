import java.math.BigDecimal;

public class User {
    public String username;
    public String name;
    public String surname;
    public String email;
    public BigDecimal id;
    public int numberOfPeople;
    public User(String name,String surname,String username,String email,BigDecimal id){
        this.email=email;
        this.surname=surname;
        this.name=name;
        this.username=username;
        this.id=id;
    }
}
