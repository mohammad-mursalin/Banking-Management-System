import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {

    Scanner scanner;
    Connection connection;

    AccountManager(Connection connection, Scanner scanner) {

        this.connection = connection;
        this.scanner = scanner;
    }

    public void debitMoney(long accountNumber) {


    }

    public void creditMoney(long accountNumber) {

        
    }

    public void transferMoney(long accountNumber) {

        
    }

    public void checkBalance(long accountNumber) {

        if(accountNumber != 0) {

            System.out.println();
            System.out.print("Enter security pin : ");
            String securityPin = scanner.next();
            scanner.nextLine();
            
            String query = "select balance from accounts where account_numbe = ? and security_pin = ?";

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);

            } catch (SQLException e) {
                
                e.printStackTrace();
            }
        }
        else {

            System.out.println();
            System.out.println("Invalid account number");
        }
    }

}
