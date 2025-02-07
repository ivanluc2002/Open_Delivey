package it.opendelivey.demo.controllers;

import it.opendelivey.demo.Repo.RepoAllergie;
import it.opendelivey.demo.Repo.RepoPiatto;
import it.opendelivey.demo.Repo.RepoRistorante;
import it.opendelivey.demo.Repo.RepoUtente;
import it.opendelivey.demo.model.Allergie;
import it.opendelivey.demo.model.Piatto;
import it.opendelivey.demo.model.Ristorante;
import it.opendelivey.demo.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class SearchController {

    @Autowired
    RepoUtente repoUtenteDao;

    @Autowired
    RepoRistorante repoRistoranteDao;

    @Autowired
    RepoPiatto repoPiattoDao;

    @Autowired
    private RepoAllergie repoAllergieDao;

    @GetMapping("/search")
    public String search(){


        return "ricerca";
    }

    @GetMapping("/search/query")
    public String searchParam(
            @RequestParam("q") String ricerca,
            Model model,
            HttpSession session
    ){
        //prendo i dati :)
        Utente utente = (Utente) session.getAttribute("loggedUser");
        if(utente == null) return "redirect:/login";
        ArrayList<Ristorante> ristoranti = repoRistoranteDao.findByNomeContains(ricerca);
        ArrayList<Piatto> prodotti = repoPiattoDao.findByNomeContains(ricerca);

        ArrayList<Allergie> allergieUtente = repoAllergieDao.findByUtenti(utente);

        //filtro i prodotti che contengono allergeni che provocano reazioni all'utente
            if(allergieUtente != null)
                for(Allergie a:allergieUtente)
                    prodotti.removeIf(c->c.hasAllergia(a));

        model.addAttribute("ristoranti", ristoranti);
        model.addAttribute("prodotti", prodotti);
        return "ricerca";
    }
}
