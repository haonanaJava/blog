package com.heyutang.blog.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

public class ArchivesController {

    @GetMapping("/archives/{id}")
    public String Archive(@PathVariable Long id) {
        return "archives";
    }

}
