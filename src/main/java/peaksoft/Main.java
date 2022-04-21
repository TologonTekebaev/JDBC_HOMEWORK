package peaksoft;

import peaksoft.dao.UserDaoJdbcImpl;
import peaksoft.model.User;
import peaksoft.service.UserServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scannerInt = new Scanner(System.in);
    public static void main(String[] args) {

        UserDaoJdbcImpl userDaoJdbc = new UserDaoJdbcImpl();
        List<User> allUsers = userDaoJdbc.getAllUsers();

        while (true) {
            commands();
            int commandNum = scannerInt.nextInt();
            if (commandNum == 1) {
                userDaoJdbc.createUsersTable();
            } else if (commandNum == 2) {
                userDaoJdbc.saveUser("Almaz", "Janybekov", (byte) 18);
                userDaoJdbc.saveUser("Ulaan", "Kubanychbekov", (byte) 27);
                userDaoJdbc.saveUser("Azat", "Ibraev", (byte) 31);
                System.out.println("");
            } else if (commandNum == 3) {
                 allUsers.forEach(System.out::println);
            } else if (commandNum == 4) {
                System.out.println("Please, write your ID");
                long id = scannerInt.nextInt();
                userDaoJdbc.removeUserById(id);
            } else if (commandNum == 5) {
                userDaoJdbc.cleanUsersTable();
            } else if (commandNum == 6) {
                Scanner scanner1 = new Scanner(System.in);
                System.out.println("Please, write user name!");
                String firstName = scanner1.nextLine();
                System.out.println(userDaoJdbc.existsByFirstName(firstName));
            } else if (commandNum == 7) {
                userDaoJdbc.dropUsersTable();
            }
        }
    }

    public static void commands(){
            System.out.println("----------Commands---------");
            System.out.println("Press 1 to create table");
            System.out.println("Press 2 to add users to database");
            System.out.println("Press 3 to get all users");
            System.out.println("Press 4 to delete value by id");
            System.out.println("Press 5 to delete values of table");
            System.out.println("Press 6 to find with name");
            System.out.println("Press 7 to drop the table");
            System.out.println("----------------------------");
    }
}
