package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.AppareilDao;
import org.mbds.tpt.maslow.dao.UtilisateurDao;
import org.mbds.tpt.maslow.dao.WatchListDao;
import org.mbds.tpt.maslow.entities.Appareil;
import org.mbds.tpt.maslow.entities.WatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Zac on 27/02/2016.
 */
@RestController
@RequestMapping(value = "/w/{idWatchList}/a")
public class AppareilController {

    @Autowired
    private AppareilDao appareilDao;

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    private WatchListDao watchListDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> addAppareil(@PathVariable int idWatchList, @RequestBody Appareil appareil,
                                         @RequestParam String token){
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            WatchList watchList = watchListDao.findOne(idWatchList);

            if (watchList != null){
                watchList.getAppareils().add(appareil);
                response = new ResponseEntity<Object>(watchListDao.save(watchList),HttpStatus.CREATED);
            }else {
                response = new ResponseEntity<Object>("WatchList "+idWatchList+" Introuvable ...", HttpStatus.NOT_FOUND);
            }

        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> majAppareil(@PathVariable int idWatchList, @RequestBody Appareil appareil,
                                         @RequestParam String token){
        ResponseEntity<?> response;
        if (utilisateurDao.existsWithToken(token)) {

            WatchList watchList = watchListDao.findOne(idWatchList);

            if (watchList != null){
                int idAppareil = appareil.getId();
                if (watchList.getAppareil(idAppareil) != null){
                    watchList.updateAppareil(appareil);
                    response = new ResponseEntity<Object>(watchListDao.save(watchList), HttpStatus.OK) ;
                    //dans la table evenement ==> duplication avec ancienne reference pointée a null ==> trouver moyen nettoyage
                }else{
                    response = new ResponseEntity<Object>("L'appreil "+idAppareil+" n'existe pas dans la WatchList "+idWatchList+ "...", HttpStatus.NOT_FOUND);
                }

            }else {
                response = new ResponseEntity<Object>("WatchList "+idWatchList+" Introuvable ...", HttpStatus.NOT_FOUND);
            }

        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = "/{idAppareil}/", method = RequestMethod.GET)
    public ResponseEntity<?> getAppareil(@PathVariable int idWatchList, @PathVariable int idAppareil,
                                         @RequestParam String token){
        ResponseEntity<?> response;
        WatchList watchList;
        if (utilisateurDao.existsWithToken(token)){
            if ((watchList=watchListDao.findOne(idWatchList)) != null){
                if (watchList.getAppareil(idAppareil) != null){
                    response = new ResponseEntity<Object>(watchList.getAppareil(idAppareil), HttpStatus.OK) ;
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

    @RequestMapping(value = "/{idAppareil}/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAppareil(@PathVariable int idWatchList, @PathVariable int idAppareil,@RequestParam String token){
        ResponseEntity<?> response;
        if (utilisateurDao.existsWithToken(token)) {
            WatchList watchList;
            if ((watchList=watchListDao.findOne(idWatchList)) != null) {
                int index = 0;
                boolean trouve = false;
                List<Appareil> appareils = watchList.getAppareils();
                for (Appareil appareil1 : watchList.getAppareils()) {
                    if ((appareil1.getId() == idAppareil)) {
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
                    response = new ResponseEntity<>(watchListDao.save(watchList),HttpStatus.OK);
                } else {
                    response = new ResponseEntity<>("Appareil "+idAppareil+" introuvable dans la watchlist "+idWatchList+" ...", HttpStatus.NOT_FOUND);
                }

            } else {
                response = new ResponseEntity<>("WatchList introuvable...", HttpStatus.NOT_FOUND);
            }
        } else {
            response = new ResponseEntity<Object>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }
        return response;
    }
}
