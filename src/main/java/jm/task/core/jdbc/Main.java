package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();

        service.createUsersTable();

        service.saveUser("Vasia", "Pupkin", (byte)55);
        service.saveUser("Petia", "Dudkin", (byte)14);
        service.saveUser("Kostia", "Lolkin", (byte)21);
        service.saveUser("Tolia", "Popkin", (byte)24);

        List<User> users = service.getAllUsers();
        System.out.println(users);

        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
