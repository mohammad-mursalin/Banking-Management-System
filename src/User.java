import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        System.out.print("Username : ");
        String username = scanner.next();
        scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();

        if(!registration_exist(email)) {

            try {

                String query = "insert into users(username, email, password) values(?,?,?)";
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

            System.out.println();
            System.out.println("user already exist");
        }
    }

    public String login() {

        System.out.println();
        System.out.print("Enter email : ");
        String email = scanner.next();
        scanner.nextLine();
        System.out.print("Enter password : ");
        String password = scanner.next();

        String query = "select * from users where email = ? and password = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {

                return email;
            }
            else {

                return null;
            }
            
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }

        return null;
    }

    public Boolean registration_exist(String email) {

        String query = "select * from users where email = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            

            if(resultSet.next()) {

                return true;
            }
            else {

                return false;
            }

        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        return false;
    }
}
