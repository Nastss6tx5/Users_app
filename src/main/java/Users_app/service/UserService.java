package Users_app.service;

import Users_app.model.Role;
import Users_app.model.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void saveUser(User user);

    User getUserById(Long id);

    void deleteUserById(Long id);

    User findUserByUsername(String username);

    Role findRoleByName(String name);

    List<Role> getAllRoles(); // Новый метод

    Role getRoleById(Long id); // Новый метод
}