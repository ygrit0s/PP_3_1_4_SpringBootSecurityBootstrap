package habsida.ygrit0s.springboot_security.controller;

import habsida.ygrit0s.springboot_security.entity.User;
import habsida.ygrit0s.springboot_security.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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
	public String pageForAdmins(@ModelAttribute("user") User user,Model model, Principal principal) {
		Long id = userService.getByUsername(principal.getName()).getId();
		model.addAttribute("admin", userService.getUser(id));
		model.addAttribute("userList", userService.userList());
		model.addAttribute("roleList", roleService.roleList());
		return "admin";
	}

	@PostMapping("/admin/users/new")
	public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		model.addAttribute("roleList", roleService.roleList());
		if (bindingResult.hasErrors()) {
			return "admin";
		}
		if (!userService.addUser(user)) {
			bindingResult.addError(new ObjectError("", ""));
			return "admin";
		}
		return "redirect:/admin";
	}
	
	@GetMapping("/admin/users/edit/{id}")
	public String update(@PathVariable("id") long id, Model model) {
		model.addAttribute("user", userService.getUser(id));
		model.addAttribute("roleList", roleService.roleList());
		return "redirect:/admin";
	}

	@PatchMapping("/admin/users/edit/{id}")
	public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		model.addAttribute("roleList", roleService.roleList());
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/users/edit/{id}";
		} else {
			userService.updateUser(user);
			return "redirect:/admin";
		}
	}


	@DeleteMapping("/admin/users/delete/{id}")
	public String removeUser(@PathVariable("id") long id, Principal principal) {
		userService.removeUser(id, principal);
		return "redirect:/admin";
	}
}
