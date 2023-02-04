package habsida.ygrit0s.springboot_security.controller;

import habsida.ygrit0s.springboot_security.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
	
	@GetMapping("/user/info")
	public String userPage(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("user", user);
		return "user/info";
	}


}
