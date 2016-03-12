package org.mbds.tpt.maslow.controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mbds.tpt.maslow.dao.*;
import org.mbds.tpt.maslow.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> createWatchList(@PathVariable int id, @RequestBody WatchList watchlist, @RequestParam String token){
        try {

            if (utilisateurDao.existsWithToken(token)) {

                watchlist.setId(id);
                return new ResponseEntity<>(watchListDao.save(watchlist), HttpStatus.OK);

            } else {
                throw new IllegalAccessException("Le token est erroné");
            }

        } catch (NullPointerException e) {
            return new ResponseEntity<>("La WatchList "+id+" est Vide...", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> majWatchList(@RequestBody WatchList watchlist, @RequestParam String token){
        try {

            if (utilisateurDao.existsWithToken(token)) {

                if (watchListDao.findOne(watchlist.getId()) != null){
                    return new ResponseEntity<Object>(watchListDao.save(watchlist), HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("La WatchList "+watchlist.getId()+" est Vide...", HttpStatus.NOT_FOUND);
                }

            } else {
                throw new IllegalAccessException("Le token est erroné");
            }

        } catch (NullPointerException e) {
            return new ResponseEntity<>("La WatchList "+watchlist.getId()+" est Vide...", HttpStatus.NOT_FOUND);
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


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> readWatchList(@PathVariable int id, @RequestParam String token) {
        try {
            if (utilisateurDao.existsWithToken(token)) {
                return new ResponseEntity<>(watchListDao.findOne(id), HttpStatus.OK);

            } else {
                throw new IllegalAccessException("Le token est erroné");
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("La WatchList "+id+" demandée n'existe pas.", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWatchList(@PathVariable int id, @RequestParam String token) {
        try {

            if (utilisateurDao.existsWithToken(token)) {
                watchListDao.delete(id);
                return new ResponseEntity<String>(" WATCHLIST "+id+" DELETED...", HttpStatus.OK);


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
