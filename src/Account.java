import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

        if(!accountExist(email)) {

            System.out.println();
            System.out.print("Full name : ");
            String fullName = scanner.next();
            scanner.nextLine();
            System.out.print("Security pin : ");
            String securityPin = scanner.next();
            scanner.nextLine();

            long account_number = generateAccountNumber();

            String query = "insert into accounts (account_number,full_name, email,security_pin) values (?,?,?,?)";

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, fullName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, securityPin);

                int affectedRow = preparedStatement.executeUpdate();

                if( affectedRow > 0 ) {

                    System.out.println();
                    System.out.println("Account created successfully");
                    System.out.println();
                    System.out.println("Your account number is " +account_number);
                }
                else {

                    System.out.println();
                    System.out.println("Account creation failed");
                }

            } catch (SQLException e) {
                
                e.printStackTrace();
            }
        }

        else {

            System.out.println();
            System.out.println("Account already exists for this email");
        }

    }

    public long getAccountNumber(String email) {

        String query = "select account_number from accounts where email = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if( resultSet.next()) {

                return resultSet.getInt("account_number");
            }

        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        throw new RuntimeException("Account number doesn't exist");
    }

    public long generateAccountNumber() {

        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select account_number from accounts order by account_number desc limit 1");

            if(resultSet.next()) {

                return resultSet.getInt("account_number")+1;
            }
            else {

                return 100100;
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        return 100100;
    }

    public boolean accountExist(String email) {

        String query  = "Select * from accounts where email = ?";

        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if( resultSet.next()) {

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
