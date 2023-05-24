package com.smartcontactmanager.smartcontact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartcontactmanager.smartcontact.Dao.UserRepo;
import com.smartcontactmanager.smartcontact.entities.UserEntity;
import com.smartcontactmanager.smartcontact.helper.message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder PasswordEncoder;

    @RequestMapping(value = { "/home", "/" })
    public String homeController() {

        return "home";
    }


    @RequestMapping("/singup")
    public String singupController(Model model) {

        model.addAttribute("user", new UserEntity());

        return "singup";
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public String singupProcessController(@Valid @ModelAttribute("user") UserEntity user, BindingResult result,
            @RequestParam(value = "agrement", defaultValue = "false") boolean agree, Model model,
            HttpSession session) {

        try {

            if (!agree) {
//                System.out.println("you have not agreed tearm and conditon");

                throw new Exception("you have not agreed tearm and conditon");
            }

            if (result.hasErrors()) {
//                System.out.println(result.toString());
                model.addAttribute("user", user);
                return "singup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImage("default.png");
            user.setPassword(PasswordEncoder.encode(user.getPassword()));
            System.out.println(agree + " agrement");
//            System.out.println(user);

            userRepo.save(user);

            model.addAttribute("user", new UserEntity());
            model.addAttribute("result", result);
            session.setAttribute("message", new message("Successfully registerd", "alert-success"));

            return "singup";
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

            model.addAttribute("user", user);

            session.setAttribute("message", new message("somethong went worng " + e.getMessage(), "alert-danger"));

            model.addAttribute("session", session);
            return "singup";
        }

    }

    @GetMapping(value = "/signin")
    public String loginController() {
  
        return "login";
    }
    

}
