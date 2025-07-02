package com.example.SpringBootStudy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class BaseController {
/*    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "안녕하세요");
        return "index";
    }*/
}
