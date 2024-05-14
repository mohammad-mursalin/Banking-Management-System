import java.sql.Connection;
import java.util.Scanner;

public class AccountManager {

    Scanner scanner;
    Connection connection;

    AccountManager(Connection connection, Scanner scanner) {

        this.connection = connection;
        this.scanner = scanner;
    }

    public void debitMoney(String accountNumber) {


    }

    public void creditMoney(String accountNumber) {

        
    }

    public void transferMoney(String accountNumber) {

        
    }

    public void checkBalance(String accountNumber) {

        
    }

}
