package it.opendelivey.demo;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Controllore {
    @RequestMapping("/index")
    public String Index() {
        return "redirect registrazione";
    }
    @RequestMapping("/login")
    public String Login() {
        return "registrazione";
    }
    @RequestMapping("/registrazione")
    public String Registrazione() {
        return "registrazione";
    }
    /*@PostMapping("/carello")
    public String Carrello() {
        return "registrazione";
    }
    @PostMapping("/account")
    public String Account () {
        return "registrazione";
    }
    @GetMapping("/ricerca")
    public String Ricarca () {
        return "registrazione";
    }
    @PostMapping("/salvati")
    public String Salvati () {
        return "registrazione";
    }
    @GetMapping("/ristorante")
    public String Ristorante () {
        return "registrazione";
    }
    @GetMapping("/homepage")
    public String Homepage() {
        return "registrazione";
    }*/
}
