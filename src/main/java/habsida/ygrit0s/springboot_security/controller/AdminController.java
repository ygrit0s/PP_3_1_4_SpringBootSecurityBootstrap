package habsida.ygrit0s.springboot_security.controller;

import habsida.ygrit0s.springboot_security.entity.User;
import habsida.ygrit0s.springboot_security.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class AdminController {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public AdminController(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping("/admin")
	public String pageForAdmins(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("userList", userService.userList());
		model.addAttribute("roleList", roleService.roleList());
		return "admin/users";
	}

	@GetMapping("/admin/new")
	public String add(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("roleList", roleService.roleList());
		return "admin/new";
	}

	@PostMapping("/admin/new")
	public String addUser(@ModelAttribute("user") User user, RedirectAttributes ra, Model model) {
		if (!userService.addUser(user)) {
			ra.addFlashAttribute("error", "");
			model.addAttribute("roleList", roleService.roleList());
			return "admin/new";
		}
		return "redirect:/admin";
	}
	

	@PutMapping("/admin/edit/{id}")
	public String updateUser(@ModelAttribute("user") User user) {
		userService.updateUser(user);
		return "redirect:/admin";
	}


	@DeleteMapping("/admin/delete/{id}")
	public String removeUser(@PathVariable("id") long id, Principal principal) {
		userService.removeUser(id, principal);
		return "redirect:/admin";
	}
}
