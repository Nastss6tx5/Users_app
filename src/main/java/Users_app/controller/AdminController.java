package Users_app.controller;

import Users_app.model.Role;
import Users_app.model.User;
import Users_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User admin = userService.findUserByUsername(username);

        model.addAttribute("admin", admin);
        return "admin";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("user") User userForm,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userForm);
            return "admin/edit";
        }

        try {
            userService.updateUser(id, userForm);
            return "redirect:/admin/users?success=userUpdated";
        } catch (IllegalArgumentException e) {
            return "redirect:/admin/users?error=userNotFound";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/editRoles/{id}")
    public String editUserRolesForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        List<Role> allRoles = userService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "admin/editRoles";
    }

    @PostMapping("/editRoles/{id}")
    public String updateUserRoles(@PathVariable Long id,
                                  @RequestParam(value = "roles", required = false) List<Long> roleIds,
                                  Model model) {
        User user = userService.getUserById(id);

        if (CollectionUtils.isEmpty(roleIds)) {
            model.addAttribute("errorMessage", "User has no roles");
            model.addAttribute("user", user);
            model.addAttribute("allRoles", userService.getAllRoles());
            return "admin/editRoles";
        }

        Set<Role> roles = new HashSet<>(userService.getRolesByIds(roleIds));
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin/users";
    }
}