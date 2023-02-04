package habsida.ygrit0s.springboot_security.controller;

import habsida.ygrit0s.springboot_security.entity.User;
import habsida.ygrit0s.springboot_security.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
	public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		model.addAttribute("roleList", roleService.roleList());
		if (bindingResult.hasErrors()) {
			return "admin/new";
		}
		if (!userService.addUser(user)) {
			bindingResult.addError(new ObjectError("", ""));
			return "admin/new";
		}
		return "redirect:/admin";
	}
	
	@GetMapping("/admin/edit/{id}")
	public String update(@PathVariable("id") long id, Model model) {
		model.addAttribute("user", userService.getUser(id));
		model.addAttribute("roleList", roleService.roleList());
		return "redirect:/admin";
	}

	@PutMapping("/admin/edit/{id}")
	public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		model.addAttribute("roleList", roleService.roleList());
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/edit/{id}";
		} else {
			userService.updateUser(user);
			return "redirect:/admin";
		}
	}


	@DeleteMapping("/admin/delete/{id}")
	public String removeUser(@PathVariable("id") long id, Principal principal) {
		userService.removeUser(id, principal);
		return "redirect:/admin";
	}
}
