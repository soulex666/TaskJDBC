package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Util {
    private final ResourceBundle resource;

    public Util(String fileConfigName) {
        resource = ResourceBundle.getBundle(fileConfigName);
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(
                resource.getString("db.url"),
                resource.getString("db.user"),
                resource.getString("db.password"));
    }
}
