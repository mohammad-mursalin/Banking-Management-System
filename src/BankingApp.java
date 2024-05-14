import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {

    private static String url = "jdbc:postgresql://localhost/postgresdemo";
    private static String username = "postgres";
    private static String password = "mursalin@postgres"; 

    public static void main(String[] args) {

        try {
            
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scanner = new Scanner(System.in);

            User user = new User(connection, scanner);

            String email;
            
            while(true) {

                System.out.println();
                System.out.println("Banking Management App");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println();
                System.out.print("Chose your option : ");
                int choice = scanner.nextInt();

                switch(choice) {

                    case 1 -> user.register();

                    case 2 -> email = user.login();
                }
            }

        } catch (SQLException e) {
            
            System.out.println(e.getMessage());
        }
    }

}
