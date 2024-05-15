import java.sql.Connection;
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

        
    }

}
