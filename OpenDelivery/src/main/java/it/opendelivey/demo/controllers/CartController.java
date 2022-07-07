package it.opendelivey.demo.controllers;

import it.opendelivey.demo.Repo.RepoIndirizzoUtente;
import it.opendelivey.demo.Repo.RepoOrdine;
import it.opendelivey.demo.Repo.RepoRecordOrdine;
import it.opendelivey.demo.model.IndirizzoUtente;
import it.opendelivey.demo.model.Ordine;
import it.opendelivey.demo.model.OrdineRecord;
import it.opendelivey.demo.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Controller
public class CartController {

    @Autowired
    RepoOrdine repoOrdineDao;

    @Autowired
    RepoRecordOrdine repoRecordOrdineDao;

    @Autowired
    RepoIndirizzoUtente repoIndirizzoUtenteDao;

    @GetMapping("/cart")
    public String getCart(
            Model model,
            HttpSession session
    ){
        //TODO: aggiungi utente dal database

        Utente utente = (Utente) session.getAttribute("loggedUser");
        if(utente == null) return "login";
        Ordine ordine = repoOrdineDao.findByUtente(utente);
        if(ordine == null) return "homepage";
        Set<OrdineRecord> carrello = ordine.getPiatti();
        ArrayList<IndirizzoUtente> indirizziUtente = repoIndirizzoUtenteDao.findByUtente(utente);

        model.addAttribute("utente", utente);
        model.addAttribute("carrello", carrello);
        model.addAttribute("items", 0);
        model.addAttribute("indirizziUtente", indirizziUtente);

        return "cart";
    }

    @PostMapping("/cart/delete")
    public String cartDelete(
            HttpSession session,
            Model model,
            @RequestParam("recordId") int recordId
    ){

        Utente utente = (Utente) session.getAttribute("loggedUser");
        if(utente == null) return "login";

        Optional<OrdineRecord> record = repoRecordOrdineDao.findById(recordId);
        if(record.isEmpty()) return "redirect:cart";

        repoRecordOrdineDao.deleteById(recordId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String cartRemove(
            HttpSession session,
            Model model,
            @RequestParam("recordId") int recordId
    ){

        Utente utente = (Utente) session.getAttribute("loggedUser");
        if(utente == null) return "login";

        Optional<OrdineRecord> record = repoRecordOrdineDao.findById(recordId);
        if(record.isEmpty()) return "redirect:cart";

        if(record.get().getAmount() < 2){
            repoRecordOrdineDao.deleteById(recordId);
            return "redirect:/cart";
        }

        record.get().setAmount(
                record.get().getAmount()-1
        );
        repoRecordOrdineDao.save(record.get());
        return "redirect:/cart";
    }

    @PostMapping("/cart/add")
    public String cartAdd(
            HttpSession session,
            Model model,
            @RequestParam("recordId") int recordId
    ){

        Utente utente = (Utente) session.getAttribute("loggedUser");
        if(utente == null) return "login";

        Optional<OrdineRecord> record = repoRecordOrdineDao.findById(recordId);
        if(record.isEmpty()) return "redirect:cart";

        record.get().setAmount(
                record.get().getAmount()+1
        );
        repoRecordOrdineDao.save(record.get());
        return "redirect:/cart";
    }

    @PostMapping("/cart/newAddress")
    public String newAddress(
            HttpSession session,
            IndirizzoUtente indirizzo
    ){
        Utente utente = (Utente) session.getAttribute("loggedUser");
        if(utente == null) return "login";
        indirizzo.setUtente(utente);

        ArrayList<IndirizzoUtente> indirizziUtente = repoIndirizzoUtenteDao.findByUtente(utente);
        for(IndirizzoUtente temp: indirizziUtente){
            if(temp.equals(indirizzo)) return "redirect:/cart";
        }


        repoIndirizzoUtenteDao.save(indirizzo);
        return "redirect:/cart";
    }
}
