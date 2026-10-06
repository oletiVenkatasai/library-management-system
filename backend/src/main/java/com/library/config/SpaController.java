package com.library.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {

    @RequestMapping(value = {
            "/",
            "/login",
            "/dashboard",
            "/books",
            "/authors",
            "/members",
            "/borrow/issue",
            "/borrowings",
            "/overdue"
    })
    public String forward() {
        return "forward:/index.html";
    }
}
