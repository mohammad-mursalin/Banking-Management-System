import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {

    Scanner scanner;
    Connection connection;

    AccountManager(Connection connection, Scanner scanner) {

        this.connection = connection;
        this.scanner = scanner;
    }

    public void debitMoney(long accountNumber) throws SQLException {
        
        try {

            if(accountNumber != 0) {

                System.out.println();
                System.out.println("Amount : ");
                double debitAmount = scanner.nextDouble();
                System.out.print("Security pin : ");
                String securityPin = scanner.next();
                scanner.nextLine();
    
                String query = "select balance from accounts where account_number = ? and security_pin = ?";

                connection.setAutoCommit(false);

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, accountNumber);
                preparedStatement.setString(2, securityPin);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {

                    double balance = resultSet.getDouble("balance");

                    if(debitAmount <= balance) {

                        String debitQuery = "update accounts set balance = - ? where account_number = ? and security_pin = ?";

                        PreparedStatement debitPreparedStatement = connection.prepareStatement(debitQuery);
                        debitPreparedStatement.setDouble(1, debitAmount);
                        debitPreparedStatement.setLong(2, accountNumber);
                        debitPreparedStatement.setString(3, securityPin);

                        int affectedRow = debitPreparedStatement.executeUpdate();

                        if( affectedRow > 0) {

                            System.out.println();
                            System.out.println("Balance " +debitAmount+ " debited successfully");

                            connection.commit();
                        }
                        else {

                            System.out.println();
                            System.out.println("Unsuccessfull debitation");

                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    }
                    else {

                        System.out.println();
                        System.out.println("Insufficent balance");

                    }
                }
                else {

                    System.out.println();
                    System.out.println("Invalid security pin");

                }

            
            }
            else {

                System.out.println();
                System.out.println("Invalid account number");

            }

        } catch (SQLException e) {
                    
            e.printStackTrace();
        }

        connection.setAutoCommit(true);
    }

    public void creditMoney(long accountNumber) throws SQLException {

        try {

            connection.setAutoCommit(false);

            if( accountNumber != 0) {

                System.out.println();
                System.out.print("Amount : ");
                double creditAmount = scanner.nextDouble();
                System.out.print("Enter security pin : ");
                String securityPin = scanner.next();
                scanner.nextLine();
                
                String query = "select * from accounts where account_number = ? and security_pin = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, accountNumber);
                preparedStatement.setString(2, securityPin);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {

                    String creditQuery = "update accounts set balance = + ? where account_number = ? and security_pin = ?";

                    PreparedStatement creditPreparedStatement = connection.prepareStatement(creditQuery);

                    creditPreparedStatement.setDouble(1, creditAmount);
                    creditPreparedStatement.setLong(2, accountNumber);
                    creditPreparedStatement.setString(3, securityPin);

                    int affectedRow = creditPreparedStatement.executeUpdate();

                    if(affectedRow > 0) {

                        System.out.println();
                        System.out.println("Balance "+creditAmount+ " credited successfully");

                        connection.commit();
                        connection.setAutoCommit(true);
                    }
                    else {

                        System.out.println();
                        System.out.println("Balance "+creditAmount+ " creditation unsuccessful");

                        connection.rollback();
                        connection.setAutoCommit(true);
                    }

                }
                else {

                    System.out.println();
                    System.out.println("Invalid security pin");
                }

            }
            else {

                System.out.println();
                System.out.println("Invalid account number");
            }
            
        } catch (SQLException e) {

            e.printStackTrace();
            
        }

        connection.setAutoCommit(true);
    }

    public void transferMoney(long senderAccountNumber) throws SQLException {

        try {

            connection.setAutoCommit(false);

            System.out.println();
            System.out.print("Enter reciever account number : ");
            long recieverAccountNumber = scanner.nextLong();
            System.out.print("Enter amount : ");
            double transferBalance = scanner.nextDouble();
            System.out.print("Enter security pin : ");
            String securityPin = scanner.nextLine();
            
            if(senderAccountNumber != 0 && recieverAccountNumber != 0) {

                String query = "select balance from accounts where account_number = ? and security_pin = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, senderAccountNumber);
                preparedStatement.setString(2, securityPin);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {

                    double senderBalance = resultSet.getDouble("balance");

                    if(transferBalance <= senderBalance) {

                        String debitQuery = "update accounts set balance = - ? where account_number = ? and security_pin = ?";
                        PreparedStatement debitPreparedStatement = connection.prepareStatement(debitQuery);
                        debitPreparedStatement.setDouble(1, transferBalance);
                        debitPreparedStatement.setLong(2, senderAccountNumber);
                        debitPreparedStatement.setString(3, securityPin);

                        int debitAccountAffectedRow = debitPreparedStatement.executeUpdate();

                        String creditQuery = "update accounts set balance = + ? where account_number = ? ";
                        PreparedStatement creditPreparedStatement = connection.prepareStatement(creditQuery);
                        debitPreparedStatement.setDouble(1, transferBalance);
                        debitPreparedStatement.setLong(2, recieverAccountNumber);

                        int creditAccountAffectedRow = creditPreparedStatement.executeUpdate();

                        if( debitAccountAffectedRow > 0 && creditAccountAffectedRow > 0) {

                            System.out.println();
                            System.out.println("Balance " +transferBalance+" transfered successfully");

                            connection.commit();
                            connection.setAutoCommit(true);
                        }
                        else {

                            System.out.println();
                            System.out.println("Balance transfer unsuccessful");

                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    }
                    else {

                        System.out.println();
                        System.out.println("Insufficient balance");
                    }
                }
                else {

                    System.out.println();
                    System.out.println("Invalid security pin");
                }
            }
            else {

                System.out.println();
                System.out.println("Invalid account number");
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }

        connection.setAutoCommit(true);
    }

    public void checkBalance(long accountNumber) {

        if(accountNumber != 0) {

            System.out.println();
            System.out.print("Enter security pin : ");
            String securityPin = scanner.next();
            scanner.nextLine();
            
            String query = "select balance from accounts where account_number = ? and security_pin = ?";

            try {

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, accountNumber);
                preparedStatement.setString(2, securityPin);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {

                    double balance = resultSet.getDouble("balance");

                    System.out.println();
                    System.out.println("Your current balance is " +balance);
                }
                else {

                    System.out.println();
                    System.out.println("Invalid security pin");
                }

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
