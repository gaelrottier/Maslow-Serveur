package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.AppareilDao;
import org.mbds.tpt.maslow.dao.EvenementDao;
import org.mbds.tpt.maslow.dao.UtilisateurDao;
import org.mbds.tpt.maslow.dao.WatchListDao;
import org.mbds.tpt.maslow.entities.Appareil;
import org.mbds.tpt.maslow.entities.Evenement;
import org.mbds.tpt.maslow.entities.WatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Zac on 27/02/2016.
 */
@RestController
@RequestMapping(value = "/w/{idWatchlist}/a/{idAppareil}/e")
public class EvenementController {

    @Autowired
    private EvenementDao evenementDao;

    @Autowired
    private AppareilDao appareilDao;

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    public WatchListDao watchListDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> addEvenement(@PathVariable("idWatchlist") int idWatchList, @PathVariable("idAppareil") int idAppareil,
                                          @RequestBody Evenement evenement, @RequestParam String token){
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            WatchList watchList = watchListDao.findOne(idWatchList);

            if (watchList != null){
                Appareil appareil;
               if ((appareil=watchList.getAppareil(idAppareil)) != null){
                  appareil.getEvenements().add(evenement);
                  response = new ResponseEntity<Object>(appareilDao.save(appareil),HttpStatus.CREATED);
               }else{
                   response = new ResponseEntity<Object>("L'appareil "+idAppareil+" est introuvable", HttpStatus.NOT_FOUND);
               }

            }else {
                response = new ResponseEntity<Object>("WatchList "+idWatchList+" Introuvable ...", HttpStatus.NOT_FOUND);
            }

        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> majEvenement(@PathVariable("idWatchlist") int idWatchList, @PathVariable("idAppareil") int idAppareil,
                                          @RequestBody Evenement evenement, @RequestParam String token){
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            WatchList watchList = watchListDao.findOne(idWatchList);

            if (watchList != null){
                Appareil appareil;
                if ((appareil=watchList.getAppareil(idAppareil)) != null){
                    try {
                        appareil.updateEvenement(evenement);
                        response = new ResponseEntity<Object>(appareilDao.save(appareil),HttpStatus.CREATED);
                    }catch (IndexOutOfBoundsException e){
                        response = new ResponseEntity<Object>("L'evenement "+evenement.getId()+" de l'appareil "+appareil.getId()+" de la watchList "+idWatchList+" est introuvable ...", HttpStatus.NOT_FOUND);
                    }

                }else{
                    response = new ResponseEntity<Object>("L'appareil "+idAppareil+" est introuvable", HttpStatus.NOT_FOUND);
                }

            }else {
                response = new ResponseEntity<Object>("WatchList "+idWatchList+" Introuvable ...", HttpStatus.NOT_FOUND);
            }

        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = "/{idEvenement}/", method = RequestMethod.GET)
    public ResponseEntity<?> getAppareil(@PathVariable("idWatchlist") int idWatchList, @PathVariable("idAppareil") int idAppareil,
                                         @PathVariable("idEvenement") int idEvenement,@RequestParam String token){
        ResponseEntity<?> response;
        WatchList watchList;
        if (utilisateurDao.existsWithToken(token)){
            if ((watchList=watchListDao.findOne(idWatchList)) != null){
                Appareil appareil;
                if ((appareil=watchList.getAppareil(idAppareil)) != null){
                    try {
                        response = new ResponseEntity<Object>(watchList.getAppareil(idAppareil).getEvenement(idEvenement), HttpStatus.OK);
                    }catch (Exception e){
                        response = new ResponseEntity<Object>("L'evenement "+idEvenement+" de l'appareil "+appareil.getId()+" de la watchList "+idWatchList+" est introuvable ...", HttpStatus.NOT_FOUND);
                    }
                }else{
                    response = new ResponseEntity<Object>("L'appreil "+idAppareil+" n'existe pas dans la WatchList "+idWatchList+ "...", HttpStatus.NOT_FOUND);
                }
            }else {
                response = new ResponseEntity<Object>("WatchList "+idWatchList+" Introuvable ...", HttpStatus.NOT_FOUND);
            }
        }else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getAppareil(@PathVariable("idWatchlist") int idWatchList, @PathVariable("idAppareil") int idAppareil,
                                         @RequestParam String token){
        ResponseEntity<?> response;
        WatchList watchList;
        if (utilisateurDao.existsWithToken(token)){
            if ((watchList=watchListDao.findOne(idWatchList)) != null){
                Appareil appareil;
                if ((appareil=watchList.getAppareil(idAppareil)) != null){
                    try {
                        response = new ResponseEntity<Object>(watchList.getAppareil(idAppareil).getEvenements(), HttpStatus.OK);
                    }catch (Exception e){
                        response = new ResponseEntity<Object>("La liste d'évènements de l'appareil "+appareil.getId()+" de la watchList "+idWatchList+" est vide ...", HttpStatus.NOT_FOUND);
                    }
                }else{
                    response = new ResponseEntity<Object>("L'appreil "+idAppareil+" n'existe pas dans la WatchList "+idWatchList+ "...", HttpStatus.NOT_FOUND);
                }
            }else {
                response = new ResponseEntity<Object>("WatchList "+idWatchList+" Introuvable ...", HttpStatus.NOT_FOUND);
            }
        }else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    @RequestMapping(value = "/{idEvenement}/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAppareil(@PathVariable("idWatchlist") int idWatchList, @PathVariable("idAppareil") int idAppareil,
                                            @PathVariable("idEvenement") int idEvenement,@RequestParam String token){
        ResponseEntity<?> response;
        WatchList watchList;
        if (utilisateurDao.existsWithToken(token)){
            if ((watchList=watchListDao.findOne(idWatchList)) != null){
                Appareil appareil;
                if ((appareil=watchList.getAppareil(idAppareil)) != null){
                    try {
                        appareil.deleteEvenement(idEvenement);
                        evenementDao.delete(idEvenement);
                        response = new ResponseEntity<Object>(watchListDao.save(watchList), HttpStatus.OK);
                    }catch (Exception e){
                        response = new ResponseEntity<Object>("L'evenement "+idEvenement+" de l'appareil "+appareil.getId()+" de la watchList "+idWatchList+" est introuvable ...", HttpStatus.NOT_FOUND);
                    }
                }else{
                    response = new ResponseEntity<Object>("L'appreil "+idAppareil+" n'existe pas dans la WatchList "+idWatchList+ "...", HttpStatus.NOT_FOUND);
                }
            }else {
                response = new ResponseEntity<Object>("WatchList "+idWatchList+" Introuvable ...", HttpStatus.NOT_FOUND);
            }
        }else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }
        return response;
    }
}
