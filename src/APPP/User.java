package APPP;
public class User {
    private String name;
    private String email;
    private String password;
    private boolean isAdmin;
    private double balance;

    public User(String name, String email, String password, boolean isAdmin, double balance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.balance = balance;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return isAdmin; }
    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }
}
