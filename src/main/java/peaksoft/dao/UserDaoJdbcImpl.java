package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDaoJdbcImpl implements UserDao {


    private final Connection connection;

    public UserDaoJdbcImpl() {
        connection = new Util().getConnection();

    }

    public void createUsersTable() {

        String query = """
                create table if not exists users(
                id serial primary key,
                name varchar not null,
                last_name varchar not null ,
                age smallint not null);
                """;
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            System.out.println();
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {

        String sql = "drop table users;";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Table is drop successfully...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = """
                 insert into users (name, last_name, age) values(?,?,?)
                 """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String query = "delete from users where id = ?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String query = "select * from users;";
        List<User> allUsers = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        String query = "truncate table users;";
        try(Statement statement = connection.createStatement()){
            statement.execute(query);
            System.out.println("You have successfully deleted all users in users table");
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }
    public boolean existsByFirstName(String firstName) {
        // eger databasede parametrine kelgen firstnamege okshosh adam bar bolso
        // anda true kaitarsyn
        // jok bolso anda false kaitarsyn.
        String query = "select * from users where name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}