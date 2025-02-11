package Users_app.service;

import Users_app.model.Role;
import Users_app.model.User;
import Users_app.repository.RoleRepository;
import Users_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
            Role userRole = roleRepository.findByName("ROLE_USER");
            if (userRole == null) {
                userRole = new Role("ROLE_USER");
                roleRepository.save(userRole);
            }
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void updateUser(Long id, User userForm) {
        User existingUser = getUserById(id);
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }

        existingUser.setFirstName(userForm.getFirstName());
        existingUser.setLastName(userForm.getLastName());
        existingUser.setEmail(userForm.getEmail());
        existingUser.setAge(userForm.getAge());
        existingUser.setCity(userForm.getCity());

        userRepository.save(existingUser);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public List<Role> getRolesByIds(Collection<Long> roleIds) {
        return roleRepository.findAllByIdIn(roleIds);
    }
}