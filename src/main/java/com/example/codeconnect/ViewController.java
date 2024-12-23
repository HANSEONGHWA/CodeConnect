package com.example.codeconnect;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/posts/{id}")
    public String postDetail(@PathVariable("id") String id){
        return "post_detail";
    }
}
