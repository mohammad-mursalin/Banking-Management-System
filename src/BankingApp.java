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
            Account account = new Account(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);

            String email;
            long accountNumber;
            
            while(true) {

                System.out.println();
                System.out.println("Banking Management App");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println();
                System.out.print("Chose your option : ");
                int choice = scanner.nextInt();

                switch(choice) {

                    case 1 : user.register();
                             break;

                    case 2 : email = user.login();

                            if(email != null) {

                                System.out.println();
                                System.out.println("User logged in");

                                if(!(account.accountExist(email))) {

                                    System.out.println();
                                    System.out.println("1. Open a new bank account");
                                    System.out.println("2. Exit");
                                    System.out.println();
                                    System.out.print("chose option : ");
                                    int choice2 = scanner.nextInt();

                                    if(choice2 == 1) {

                                        account.openAccount(email);
                                    }
                                    else {

                                        break;
                                    }
                                }
                                
                                int choice3 = 0;

                                accountNumber = account.getAccountNumber(email);

                                while(choice != 5) {

                                    System.out.println();
                                    System.out.print("1. Debit money");
                                    System.out.print("2. Credit money");
                                    System.out.print("3. Transfer money");
                                    System.out.print("4. Check balance");
                                    System.out.print("5. Log out");
                                    System.out.println();
                                    System.out.print("chose option : ");
                                    choice3 = scanner.nextInt();

                                    switch( choice3 ) {

                                        case 1 : accountManager.debitMoney(accountNumber);
                                                 break;

                                        case 2 : accountManager.creditMoney(accountNumber);
                                                 break;

                                        case 3 : accountManager.transferMoney(accountNumber);
                                                 break;

                                        case 4 : accountManager.checkBalance(accountNumber);
                                                 break;

                                        case 5 : logout();
                                                 break;

                                        default : System.out.println();
                                                  System.out.println("Please enter valid choice");
                            
                                    }
                                }
                            }
                            else {

                                System.out.println("Invalid email or password");
                            }

                            break;

                    case 3 : exit();

                    default : System.out.println();
                              System.out.println("Please enter valid choice");
                }
            }

        } catch (SQLException e) {
            
            System.out.println(e.getMessage());
        }
    }

    public static void exit() {

    }

    public static void logout() {


    }

}
