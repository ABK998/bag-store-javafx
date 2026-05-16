package APPP;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class UserManager {
    private static List<User> users = new ArrayList<>();
    private static final String FILE_NAME = "users.txt";

    static {
        loadUsersFromFile();
    }

    public static boolean registerUser(String name, String email, String password, double balance) {
        if (!isValidEmail(email) || name.isEmpty() || password.isEmpty()) {
            return false;
        }

        if (findUserByEmail(email) != null) {
            return false; 
        }

        User newUser = new User(name, email, password, false, balance);
        users.add(newUser);
        saveUserToFile(newUser);
        return true;
    }

    public static User login(String email, String password) {
        User user = findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private static User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    private static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    private static void saveUserToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
          
            writer.write(user.getName() + "," + user.getEmail() + "," + user.getPassword() + "," + user.isAdmin() + "," + user.getBalance());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadUsersFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; 
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String name = parts[0];
                    String email = parts[1];
                    String password = parts[2];
                    boolean isAdmin = Boolean.parseBoolean(parts[3]);
                    double balance = Double.parseDouble(parts[4]);
                    users.add(new User(name, email, password, isAdmin, balance));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
