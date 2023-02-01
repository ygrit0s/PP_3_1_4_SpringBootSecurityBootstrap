package habsida.ygrit0s.springboot_security.configs;

import habsida.ygrit0s.springboot_security.entity.Role;
import habsida.ygrit0s.springboot_security.entity.User;
import habsida.ygrit0s.springboot_security.service.RoleService;
import habsida.ygrit0s.springboot_security.service.UserService;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;

@Component
public class DbInit {
	private final UserService userService;
	private final RoleService roleService;

	public DbInit(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@PostConstruct
	public void initDbUsers() {

		if (roleService.roleList().isEmpty()) {
			roleService.addRole(new Role("ROLE_USER"));
			roleService.addRole(new Role("ROLE_ADMIN"));
		}

		if (userService.userList().isEmpty()) {
			User admin = new User();
			admin.setId(1L);
			admin.setName("Admin");
			admin.setSurname("AA");
			admin.setAge((byte) 37);
			admin.setUsername("admin@mail.dev");
			admin.setPassword("admin");
			admin.setRoles(new HashSet<>(List.of(new Role(2L,"ROLE_ADMIN"))));
			userService.updateUser(admin);

			User user = new User();
			user.setId(2L);
			user.setName("User");
			user.setSurname("UU");
			user.setAge((byte) 27);
			user.setUsername("user@mail.dev");
			user.setPassword("user");
			user.setRoles(new HashSet<>());
			userService.updateUser(user);
		}
	}
}