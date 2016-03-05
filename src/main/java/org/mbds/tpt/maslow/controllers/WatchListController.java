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


    //MAJ APPAREIL 'ida' (existante) DANS UNE WATCHLIST 'idl'
    @RequestMapping(value = "/{idl}/a/{ida}", method = RequestMethod.PUT)
    public ResponseEntity<?> majListAppareilToWatchList(@PathVariable("idl") int idl, @PathVariable("ida") int ida, @RequestBody Appareil appareil,
                                                        @RequestParam String token) {
        try {
            if (utilisateurDao.existsWithToken(token)) {

                if (watchListDao.findOne(idl) != null) {
                    //on a pas la reference bidirectionnelle avec appareil dans notre mapping pour une recherche plus propre
                    int index = 0;
                    for (Appareil appareil1 : watchListDao.findOne(idl).getAppareils()) {
                        if ((appareil1.getId() == ida)) {
                            //maj appareil
                            watchListDao.findOne(idl).getAppareils().set(index, appareilDao.save(appareil));
                            return new ResponseEntity<>(watchListDao.findOne(idl), HttpStatus.OK);
                        }
                        index++;
                    }
                        return new ResponseEntity<>("Appareil "+ida+" introuvable dans la watchlist "+idl+" ...", HttpStatus.NOT_FOUND);
                } else {
                    return new ResponseEntity<>("WatchList introuvable...", HttpStatus.NOT_FOUND);
                }
            } else {
                throw new IllegalAccessException("Le token est erroné");
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("La WatchList est Vide...", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    //CREATION APPAREIL DANS UNE WATCHLIST 'idl' -- dans le body serai mieux ne pas specifier les id(appareil, evenement) deja gerés par le system
    //au risque de devoir effectuer un operation en plus de maj pour cause de liste evenement vide en cas de conflit d'id avc appareil deja existant
    @RequestMapping(value = "/{idl}/a", method = RequestMethod.POST)
    public ResponseEntity<?> majListAppareilToWatchList(@PathVariable("idl") int idl,  @RequestBody Appareil appareil,
                                                        @RequestParam String token) {
        try {
            if (utilisateurDao.existsWithToken(token)) {
                WatchList watchList;
                if ((watchList=watchListDao.findOne(idl)) != null) {
                    //insertion nouveau appareil
                    List<Appareil> appareils = watchList.getAppareils();
                    appareils.add(appareil);
                    watchList.setAppareils(appareils);
                    return new ResponseEntity<>(watchListDao.save(watchList), HttpStatus.OK);

                } else {
                    return new ResponseEntity<>("WatchList "+idl+" introuvable...", HttpStatus.NOT_FOUND);
                }
            } else {
                throw new IllegalAccessException("Le token est erroné");
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("La WatchList "+idl+" est Vide...", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    //SUPPRESSION APPAREIL 'ida' DANS UNE WATCHLIST 'idl'
    @RequestMapping(value = "/{idl}/a/{ida}", method = RequestMethod.DELETE)
    public ResponseEntity<?> supprimeAppareilToWatchList(@PathVariable("idl") int idl, @PathVariable("ida") int ida, @RequestParam String token) {
        try {
            if (utilisateurDao.existsWithToken(token)) {
                WatchList watchList;
                if ((watchList=watchListDao.findOne(idl)) != null) {
                    //on a pas la reference bidirectionnelle avec appareil dans notre mapping pour une recherche plus propre
                    int index = 0;
                    boolean trouve = false;
                    List<Appareil> appareils = watchList.getAppareils();
                    for (Appareil appareil1 : watchList.getAppareils()) {
                        if ((appareil1.getId() == ida)) {
                            trouve=true;
                            break;
                        }
                        index++;
                    }
                    if(trouve){
                        Appareil app = appareils.remove(index);
                        //suppression de l'appareil
                        appareilDao.delete(app);
                        watchList.setAppareils(appareils);
                        return new ResponseEntity<>(watchListDao.save(watchList),HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("Appareil "+ida+" introuvable dans la watchlist "+idl+" ...", HttpStatus.NOT_FOUND);
                    }

                } else {
                    return new ResponseEntity<>("WatchList introuvable...", HttpStatus.NOT_FOUND);
                }
            } else {
                throw new IllegalAccessException("Le token est erroné");
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("La WatchList est Vide...", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    //CREATION WATCHLIST PARAM JSON
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> createWatchList(@PathVariable int id, @RequestBody WatchList watchlistStr, @RequestParam String token){
        try {

            if (utilisateurDao.existsWithToken(token)) {

                watchlistStr.setId(id);
                return new ResponseEntity<>(watchListDao.save(watchlistStr), HttpStatus.OK);

            } else {
                throw new IllegalAccessException("Le token est erroné");
            }

        } catch (NullPointerException e) {
            return new ResponseEntity<>("La WatchList "+id+" est Vide...", HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>("La WatchList est Vide...", HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>("La WatchList demandée n'existe pas.", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWatchList(@PathVariable int id, @RequestParam String token) {
        try {

            if (utilisateurDao.existsWithToken(token)) {
                watchListDao.delete(id);
                return new ResponseEntity<String>("DELETED...", HttpStatus.OK);


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
