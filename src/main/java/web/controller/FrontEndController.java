package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontEndController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/funcionarios")
    public String funcionarios() {
        return "funcionarios";
    }

    @RequestMapping("/restaurantes")
    public String restaurantes() {
        return "restaurantes";
    }
}
