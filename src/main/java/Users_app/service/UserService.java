package Users_app.service;

import Users_app.model.Role;
import Users_app.model.User;


import java.util.Collection;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void saveUser(User user);

    User getUserById(Long id);

    void deleteUserById(Long id);

    User findUserByUsername(String username);

    Role findRoleByName(String name);

    List<Role> getAllRoles();

    Role getRoleById(Long id);

    void updateUser(Long id, User userForm);

    User loadUserByUsername(String username);

    List<Role> getRolesByIds(Collection<Long> roleIds);
}