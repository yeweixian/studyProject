package com.danger.study.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by PC-361 on 2016/10/9.
 */
@RestController
public class Controller {

    @RequestMapping("/")
    public String index() {
        return "Hello World!";
    }
}
