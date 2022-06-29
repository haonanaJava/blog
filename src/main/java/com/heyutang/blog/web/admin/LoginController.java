package com.heyutang.blog.web.admin;

import com.heyutang.blog.po.User;
import com.heyutang.blog.service.serviceImpl.UserServiceImpl;
import com.heyutang.blog.utils.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    private HashMap<String, String> verifyCodes = new HashMap<>();

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String codeKey,
                        @RequestParam String codeStr,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);

        if (user != null && verifyCodes.get(codeKey).equalsIgnoreCase(codeStr)) {
            System.out.println("verify success");
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/admin";
        } else if (user == null) {
            attributes.addFlashAttribute("message", "用户名和密码错误");
            return "redirect:/admin";
        } else if (!verifyCodes.get(codeKey).equalsIgnoreCase(codeStr)) {
            attributes.addFlashAttribute("message", "verify success");
            return "redirect:/admin";
        }
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "admin/login";
    }

    @GetMapping(value = "/getCode")
    public void getImageCode(HttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam String codeKey
    ) {
        try {
            // 设置response
            response.setContentType("image/jpeg");
            response.setDateHeader("Expire", 0);
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");

            // 响应验证码
            String imageCode = ImageCodeUtil.getImageCode(request, response, codeKey);
            verifyCodes.put(codeKey, imageCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
