package ba.unsa.etf.rpr.projekat;

public class Employee extends Person {
    private String username;
    private String password;

    public Employee(String firstName, String lastName, String username, String password) {
        super(firstName, lastName);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean correctPassword(String password) {
        return password.equals(this.password);
    }
}
