package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.*;
import org.mbds.tpt.maslow.entities.Appareil;
import org.mbds.tpt.maslow.entities.Evenement;
import org.mbds.tpt.maslow.entities.WatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gael on 17/02/2016.
 */
@RestController
@RequestMapping("/w")
public class WatchListController {

    @Autowired
    WatchListDao watchListDao;

    @Autowired
    UtilisateurDao utilisateurDao;

    @Autowired
    AppareilDao appareilDao;

    @Autowired
    EvenementDao evenementDao;

    @Autowired
    ProceduralDao proceduralDao;

    @Autowired
    OperationDao operationDao;


    //CREATION WATCHLIST PARAM JSON

    //A SUPPRIMER PAR LA SUITE, ON NE PEUT PAS CREER DE WATCHLIST
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> init() {
        WatchList watchList1 = new WatchList();

        List<Evenement> evenementsArduino = new ArrayList<>();

        Evenement evenementArduinoOn = new Evenement();
        evenementArduinoOn.setIdOrchestra("aWGnLYQKqBnpsxJwm");

        Map<String, String> aliasArduinoOn = new HashMap<>();
        aliasArduinoOn.put("nom", "on");
        aliasArduinoOn.put("nomOrchestra", "switchOn");
        evenementArduinoOn.setAlias(aliasArduinoOn);

        Evenement evenementArduinoOff = new Evenement();
        evenementArduinoOff.setIdOrchestra("AaMyshZoJyn9Ng33D");

        Map<String, String> aliasArduinoOff = new HashMap<>();
        aliasArduinoOff.put("nom", "off");
        aliasArduinoOff.put("nomOrchestra", "switchOff");
        evenementArduinoOff.setAlias(aliasArduinoOff);

        evenementsArduino.add(evenementArduinoOn);
        evenementsArduino.add(evenementArduinoOff);

        Appareil arduino = new Appareil();
        arduino.setEvenements(evenementsArduino);
        arduino.setNom("Arduino");
        arduino.setWatchlist(watchList1);


        List<Evenement> evenementsChauffage = new ArrayList<>();

        Evenement evenementChauffageOn = new Evenement();
        evenementChauffageOn.setIdOrchestra("XHLQQ5yvhNNmv7Rad");

        Map<String, String> aliasChauffageOn = new HashMap<>();
        aliasChauffageOn.put("nom", "on");
        aliasChauffageOn.put("nomOrchestra", "switchOn");

        evenementChauffageOn.setAlias(aliasChauffageOn);

        Evenement evenementChauffageOff = new Evenement();
        evenementChauffageOff.setIdOrchestra("kmvdPAgvFZ7dhRk6N");

        Map<String, String> aliasChauffageOff = new HashMap<>();
        aliasChauffageOff.put("nom", "off");
        aliasChauffageOff.put("nomOrchestra", "switchOff");
        evenementChauffageOff.setAlias(aliasChauffageOff);


        Evenement evenementPriseChauffageOn = new Evenement();
        evenementPriseChauffageOn.setIdOrchestra("6DXJvDjpbxjYeDwJT");

        Map<String, String> aliasPriseChauffageOn = new HashMap<>();
        aliasPriseChauffageOn.put("nom", "on");
        aliasPriseChauffageOn.put("parameterKey", "state");
        aliasPriseChauffageOn.put("valeur", "1");
        evenementPriseChauffageOn.setAlias(aliasPriseChauffageOn);

        Evenement evenementPriseChauffageOff = new Evenement();
        evenementPriseChauffageOff.setIdOrchestra("6DXJvDjpbxjYeDwJT");

        Map<String, String> aliasPriseChauffageOff = new HashMap<>();
        aliasPriseChauffageOff.put("nom", "off");
        aliasPriseChauffageOff.put("parameterKey", "state");
        aliasPriseChauffageOff.put("valeur", "0");
        evenementPriseChauffageOff.setAlias(aliasPriseChauffageOff);

        evenementsChauffage.add(evenementChauffageOn);
        evenementsChauffage.add(evenementChauffageOff);
        evenementsChauffage.add(evenementPriseChauffageOn);
        evenementsChauffage.add(evenementPriseChauffageOff);

        Appareil chauffage = new Appareil();
        chauffage.setEvenements(evenementsChauffage);
        chauffage.setNom("Chauffage");
        chauffage.setWatchlist(watchList1);

        List<Appareil> appareils = new ArrayList<>();
        appareils.add(arduino);
        appareils.add(chauffage);

        watchList1.setAppareils(appareils);
        watchList1.setId(1);

        return new ResponseEntity<>(watchListDao.save(watchList1), HttpStatus.CREATED);

    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> majWatchList(@RequestBody WatchList watchlist, @RequestParam String token) {
        try {

            if (utilisateurDao.existsWithToken(token)) {

                if (watchListDao.findOne(watchlist.getId()) != null) {
                    return new ResponseEntity<Object>(watchListDao.save(watchlist), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("La WatchList " + watchlist.getId() + " est Vide...", HttpStatus.NOT_FOUND);
                }

            } else {
                throw new IllegalAccessException("Le token est erroné");
            }

        } catch (NullPointerException e) {
            return new ResponseEntity<>("La WatchList " + watchlist.getId() + " est Vide...", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> readWatchList(@RequestParam String token) {
        try {

            if (utilisateurDao.existsWithToken(token)) {

                return new ResponseEntity<>((List<WatchList>) watchListDao.findAll(), HttpStatus.OK);

            } else {
                throw new IllegalAccessException("Le token est erroné");
            }

        } catch (NullPointerException e) {
            return new ResponseEntity<>("La WatchList  est Vide...", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ResponseEntity<?> readWatchList(@PathVariable int id, @RequestParam String token) {
        try {
            if (utilisateurDao.existsWithToken(token)) {
                return new ResponseEntity<>(watchListDao.findOne(id), HttpStatus.OK);

            } else {
                throw new IllegalAccessException("Le token est erroné");
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("La WatchList " + id + " demandée n'existe pas.", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWatchList(@PathVariable int id, @RequestParam String token) {
        try {

            if (utilisateurDao.existsWithToken(token)) {
                watchListDao.delete(id);
                return new ResponseEntity<String>(" WATCHLIST " + id + " DELETED...", HttpStatus.OK);


            } else {
                throw new IllegalAccessException("Le token est erroné");
            }

        } catch (NullPointerException e) {
            return new ResponseEntity<>("La WatchList demandée n'existe pas.", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
