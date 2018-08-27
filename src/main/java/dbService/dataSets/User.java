package dbService.dataSets;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "login", unique = true, updatable = false)
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    public User(){

    }
    public User(String login, String password) {
        this(0,login,password,login);
    }

    public User(String login, String password, String name) {
        this(0,login,password, name);
    }

    public User(long id, String login, String password, String name) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
