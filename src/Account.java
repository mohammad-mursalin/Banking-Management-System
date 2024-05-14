import java.sql.Connection;
import java.util.Scanner;

public class Account {

    Scanner scanner;
    Connection connection;

    Account(Connection connection, Scanner scanner) {

        this.connection = connection;
        this.scanner = scanner;
    }

    public void openAccount(String email) {

        
    }

    public String getAccountNumber(String email) {

        return null;
    }

    public boolean accountExist(String email) {

        return false;
    }

}
