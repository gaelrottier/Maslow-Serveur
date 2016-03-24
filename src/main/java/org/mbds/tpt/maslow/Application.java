package org.mbds.tpt.maslow;


import org.mbds.tpt.maslow.dao.UtilisateurDao;
import org.mbds.tpt.maslow.dao.WatchListDao;
import org.mbds.tpt.maslow.entities.Appareil;
import org.mbds.tpt.maslow.entities.Evenement;
import org.mbds.tpt.maslow.entities.Utilisateur;
import org.mbds.tpt.maslow.entities.WatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application {

    @Autowired
    private WatchListDao watchListDao;

    @Autowired
    private UtilisateurDao utilisateurDao;

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        context.getBean(Application.class).init();
    }

    public void init() {

        if (!utilisateurDao.exists("admin")) {
            Utilisateur admin = new Utilisateur();

            admin.setPrenom("Admin");
            admin.setNom("Admin");
            admin.generateToken();
            admin.setIdentifiant("admin");
            admin.setPassword("admin");

            utilisateurDao.save(admin);
        }

        if (!watchListDao.exists(1)) {
            ////////////////////
            // Création de la première Watchlist,
            //  correspondant à la vue ListeLumiereActivity
            ////////////////////

            WatchList watchList1 = new WatchList();

//        List<Evenement> evenementsArduino = new ArrayList<>();
//
//        Evenement evenementArduinoOn = new Evenement();
//        evenementArduinoOn.setIdOrchestra("aWGnLYQKqBnpsxJwm");
//
//        Map<String, String> aliasArduinoOn = new HashMap<>();
//        aliasArduinoOn.put("nom", "on");
//        aliasArduinoOn.put("nomOrchestra", "switchOn");
//        evenementArduinoOn.setAlias(aliasArduinoOn);
//
//        Evenement evenementArduinoOff = new Evenement();
//        evenementArduinoOff.setIdOrchestra("AaMyshZoJyn9Ng33D");
//
//        Map<String, String> aliasArduinoOff = new HashMap<>();
//        aliasArduinoOff.put("nom", "off");
//        aliasArduinoOff.put("nomOrchestra", "switchOff");
//        evenementArduinoOff.setAlias(aliasArduinoOff);
//
//        evenementsArduino.add(evenementArduinoOn);
//        evenementsArduino.add(evenementArduinoOff);
//
//        Appareil arduino = new Appareil();
//        arduino.setEvenements(evenementsArduino);
//        arduino.setNom("Arduino");
//        arduino.setWatchlist(watchList1);

            ////////////////////
            // Création des évènements de la prise connectée
            ////////////////////

            List<Evenement> evenementsPrise = new ArrayList<>();

            ////////////////////
            // Allumage de la prise
            ////////////////////
            Evenement evenementPriseOn = new Evenement();
            evenementPriseOn.setIdOrchestra("XHLQQ5yvhNNmv7Rad");

            ////////////////////
            // Alias faisant le lien entre les paramètres
            //  Orchestra et les paramètres attendus par l'appli
            ////////////////////
            Map<String, String> aliasPriseOn = new HashMap<>();
            aliasPriseOn.put("nom", "on");
            aliasPriseOn.put("nomOrchestra", "switchOn");

            evenementPriseOn.setAlias(aliasPriseOn);

            ////////////////////
            // Exctinction de la prise
            ////////////////////
            Evenement evenementPriseOff = new Evenement();
            evenementPriseOff.setIdOrchestra("kmvdPAgvFZ7dhRk6N");

            ////////////////////
            // Alias
            ////////////////////
            Map<String, String> aliasPriseOff = new HashMap<>();
            aliasPriseOff.put("nom", "off");
            aliasPriseOff.put("nomOrchestra", "switchOff");
            evenementPriseOff.setAlias(aliasPriseOff);


            ////////////////////
            // La prise envoie 2 commandes différentes pour
            //  signaler son état
            ////////////////////
            Evenement evenementPriseOn2 = new Evenement();
            evenementPriseOn2.setIdOrchestra("6DXJvDjpbxjYeDwJT");

            ////////////////////
            // Alias
            ////////////////////
            Map<String, String> aliasPriseOn2 = new HashMap<>();
            aliasPriseOn2.put("nom", "on");
            aliasPriseOn2.put("parameterKey", "state");
            aliasPriseOn2.put("valeur", "1");
            evenementPriseOn2.setAlias(aliasPriseOn2);

            ////////////////////
            // Exctinction de la prise
            ////////////////////
            Evenement evenementPriseOff2 = new Evenement();
            evenementPriseOff2.setIdOrchestra("6DXJvDjpbxjYeDwJT");

            ////////////////////
            // Alias
            ////////////////////
            Map<String, String> aliasPriseOff2 = new HashMap<>();
            aliasPriseOff2.put("nom", "off");
            aliasPriseOff2.put("parameterKey", "state");
            aliasPriseOff2.put("valeur", "0");
            evenementPriseOff2.setAlias(aliasPriseOff2);


            ////////////////////
            // Ajout des évènements à la liste des évènements
            //  de la prise
            ////////////////////
            evenementsPrise.add(evenementPriseOn);
            evenementsPrise.add(evenementPriseOff);
            evenementsPrise.add(evenementPriseOn2);
            evenementsPrise.add(evenementPriseOff2);

            ////////////////////
            // Création de la prise
            ////////////////////
            Appareil prise = new Appareil();
            prise.setEvenements(evenementsPrise);
            prise.setNom("Prise électrique");
            prise.setWatchlist(watchList1);


            ////////////////////
            // Création des évènements d'une éolienne
            ////////////////////

            List<Evenement> evenementsEolienne = new ArrayList<>();

            ////////////////////
            // Allumage de l'éolienne
            ////////////////////
            Evenement evenementEolienneOn = new Evenement();
            evenementEolienneOn.setIdOrchestra("NgyodFMBFChomRuY7");

            ////////////////////
            // Alias faisant le lien entre les paramètres
            //  Orchestra et les paramètres attendus par l'appli
            ////////////////////
            Map<String, String> aliasEolienneOn = new HashMap<>();
            aliasEolienneOn.put("nom", "on");
            aliasEolienneOn.put("nomOrchestra", "switchOn");

            evenementEolienneOn.setAlias(aliasEolienneOn);

            ////////////////////
            // Exctinction de l'éolienne
            ////////////////////
            Evenement evenementEolienneOff = new Evenement();
            evenementEolienneOff.setIdOrchestra("A798oWWjGGcGjfTLQ");

            ////////////////////
            // Alias faisant le lien entre les paramètres
            //  Orchestra et les paramètres attendus par l'appli
            ////////////////////
            Map<String, String> aliasEolienneOff = new HashMap<>();
            aliasEolienneOff.put("nom", "off");
            aliasEolienneOff.put("nomOrchestra", "switchOff");

            evenementEolienneOff.setAlias(aliasEolienneOff);

            evenementsEolienne.add(evenementEolienneOn);
            evenementsEolienne.add(evenementEolienneOff);


            ////////////////////
            // Création de l'eolienne
            ////////////////////
            Appareil eolienne = new Appareil();
            eolienne.setEvenements(evenementsEolienne);
            eolienne.setNom("Eolienne");
            eolienne.setWatchlist(watchList1);


            ////////////////////
            // Création de la liste des appareils de la watchlist
            ////////////////////
            List<Appareil> appareils = new ArrayList<>();
//        appareils.add(arduino);
            appareils.add(prise);
            appareils.add(eolienne);

            ////////////////////
            // Mise en place de la watchlist
            ////////////////////
            watchList1.setAppareils(appareils);
            watchList1.setId(1);

            watchListDao.save(watchList1);
        }

        if (!watchListDao.exists(2)) {

        }

    }
}