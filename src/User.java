import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class User {

    Scanner scanner;
    Connection connection;

    User(Connection connection, Scanner scanner) {

        this.connection = connection;
        this.scanner = scanner;
    }

    public void register() {

        System.out.println();
        System.out.println("Username : ");
        String username = scanner.nextLine();
        System.out.println("Email : ");
        String email = scanner.nextLine();
        System.out.println("Password : ");
        String password = scanner.nextLine();

        if(!registration_exist()) {

            try {

                String query = "insert into users values(?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
    
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
    
                int affectedRow = preparedStatement.executeUpdate();
    
                if(affectedRow > 0) {
    
                    System.out.println("User registration successful");
                }
                else {
    
                    System.out.println("User registration failed");
                }
    
            } catch (SQLException e) {
                
                e.printStackTrace();
            }
        }
        else {

            System.out.println("Registration for this user already exist");
        }
    }

    public void login() {


    }

    public Boolean registration_exist() {


    }
}
