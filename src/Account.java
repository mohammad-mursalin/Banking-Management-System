import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Account {

    Scanner scanner;
    Connection connection;

    Account(Connection connection, Scanner scanner) {

        this.connection = connection;
        this.scanner = scanner;
    }

    public void openAccount(String email) {

        System.out.println();
        System.out.print("Full name : ");
        String fullName = scanner.nextLine();
        System.out.print("Security pin : ");
        String securityPin = scanner.nextLine();

        long account_number = generateAccountNumber();

        String query = "insert into accounts (account_number,full_name, email,security_pin) values (?,?,?,?)";

        PreparedStatement preparedStatement;
        try {

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, account_number);
            preparedStatement.setString(2, fullName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, securityPin);

        preparedStatement.executeUpdate();

        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        
    }

    public String getAccountNumber(String email) {

        return null;
    }

    public long generateAccountNumber() {

        Statement statement = connection.createStatement("select account_number from accounts order by account_number disc limit 1");

        return 100100;
    }

    public boolean accountExist(String email) {

        return false;
    }

}
