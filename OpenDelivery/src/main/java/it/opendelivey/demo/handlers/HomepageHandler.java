package it.opendelivey.demo.handlers;

import it.opendelivey.demo.model.Piatto;
import it.opendelivey.demo.model.Ristorante;

public class HomepageHandler extends  Handler{


    public HomepageHandler(String operation, int id, String password) {
        super(operation, id, password);
    }

    //seleziono l'operazione e la eseguo
    public Object exec(){
        switch (getOperation()){
            case "piatto":
                return piattoConsigliato();

            case "ristorante":
                return ristoranteConsigliato();

            case "categorie":
                return categories();

            default:
                return -1;
        }
    }

    //Ritorna la lista delle offerte da far vedere all utente


    //Ritorna la lista delle categorie
    private String[] categories() {
        //TODO: pull data from database
        return new String[]{"pizza", "vegetariano", "vegano"};
    }

    //Ritorna i dati di un ristorante scelto dall'algoritmo per essere consigliato
    private Ristorante ristoranteConsigliato(){
        //TODO: algoritmo di selezione ristorante
        return Ristorante.ristoranteSample();
    }

    //Ritorna i dati di un piatto scelto dall'algoritmo per essere consigliato
    private Piatto piattoConsigliato(){
        //TODO: algoritmo di selezione piatto
        return Piatto.piattoSample();
    }
}
