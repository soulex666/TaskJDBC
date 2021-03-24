package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private static final String TRUNCATE_TABLE = "TRUNCATE TABLE users";
    private static final String CREATE_TABLE =
            "CREATE TABLE users " +
                    "(" +
                    "    id        BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "    name      VARCHAR(100) NOT NULL, " +
                    "    last_name VARCHAR(100) NOT NULL, " +
                    "    age       TINYINT          NOT NULL " +
                    ")";
    private static final String SAVE =
            "INSERT INTO users (name, last_name, age) " +
                    "VALUES (?, ?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL = "SELECT  * FROM users;";

    private Connection connection = null;

    public UserDaoJDBCImpl() {
        Util util = new Util("config");
        try {
            connection = util.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE);
            statement.executeUpdate(CREATE_TABLE);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> entities = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    String name = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    Byte age = resultSet.getByte(4);

                    User user = new User();

                    user.setId(id);
                    user.setName(name);
                    user.setLastName(lastName);
                    user.setAge(age);

                    entities.add(user);
                }
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return entities;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(TRUNCATE_TABLE);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
