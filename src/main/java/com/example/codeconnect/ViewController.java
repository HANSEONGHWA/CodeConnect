package com.example.codeconnect;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String post(){
        return "post";
    }

    @GetMapping("/posts")
    public String postCreate(){
        return "post_form";
    }
}
