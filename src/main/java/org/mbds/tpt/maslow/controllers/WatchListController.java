package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.AppareilDao;
import org.mbds.tpt.maslow.dao.UtilisateurDao;
import org.mbds.tpt.maslow.dao.WatchListDao;
import org.mbds.tpt.maslow.entities.Appareil;
import org.mbds.tpt.maslow.entities.WatchList;
import org.mbds.tpt.maslow.entities.WatchListPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
@RestController
public class WatchListController {

    @Autowired
    WatchListDao watchListDao;

    @Autowired
    UtilisateurDao utilisateurDao;

    @Autowired
    AppareilDao appareilDao;

    @RequestMapping(value = "/u/{idUtilisateur}/w/{idWatchlist}/", method = RequestMethod.POST)
    public ResponseEntity<?> createWatchlist(@RequestParam String token, @PathVariable int idUtilisateur, @PathVariable int idWatchlist) {
        ResponseEntity<?> response;

        try {

            if (utilisateurDao.existsWithIdAndToken(idUtilisateur, token) || utilisateurDao.isAdmin(token)) {
                WatchList watchList = new WatchList();
                watchList.setWatchListPK(WatchListPK.createInstance(idWatchlist, token, utilisateurDao));

                response = new ResponseEntity<>(watchListDao.save(watchList), HttpStatus.CREATED);
            } else {
                response = new ResponseEntity<>("Token erroné ou non autorisé", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @RequestMapping(value = "/u/{idUtilisateur}/w/{idWatchlist}/", method = RequestMethod.PUT)
    public ResponseEntity<?> updateWatchList(@RequestBody WatchList watchlist, @PathVariable int idUtilisateur, @PathVariable int idWatchlist, @RequestParam String token) {
        ResponseEntity<?> response;

        try {

            if ((utilisateurDao.existsWithIdAndToken(idUtilisateur, token) || utilisateurDao.isAdmin(token))
                    && watchlist.getWatchListPK() == null) {

                WatchListPK watchlistPk = WatchListPK.createInstance(idWatchlist, token, utilisateurDao);

                if (watchListDao.findOne(watchlistPk) != null) {
                    watchlist.setWatchListPK(watchlistPk);
                    response = new ResponseEntity<Object>(watchListDao.save(watchlist), HttpStatus.OK);
                } else {
                    response = new ResponseEntity<>("La WatchList n'existe pas", HttpStatus.NOT_FOUND);
                }

            } else {
                response = new ResponseEntity<>("Token erroné ou non autorisé", HttpStatus.UNAUTHORIZED);
            }

        } catch (NullPointerException e) {
            response = new ResponseEntity<>("La WatchList n'existe pas", HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @RequestMapping(value = "/w/", method = RequestMethod.GET)
    public ResponseEntity<?> readAllWatchList(@RequestParam String token) {
        if (utilisateurDao.isAdmin(token)) {
            return new ResponseEntity<>((List<WatchList>) watchListDao.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Vous n'êtes pas autorisé à faire ça", HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/u/{idUtilisateur}/w/{idWatchlist}/", method = RequestMethod.GET)
    public ResponseEntity<?> readWatchList(@PathVariable int idUtilisateur, @PathVariable int idWatchlist, @RequestParam String token) {
        ResponseEntity<?> response;

        try {
            if (utilisateurDao.existsWithIdAndToken(idUtilisateur, token) || utilisateurDao.isAdmin(token)) {
                WatchList watchList = watchListDao.findOne(WatchListPK.createInstance(idWatchlist, token, utilisateurDao));
                if (watchList == null) {
                    response = new ResponseEntity<>("La WatchList demandée n'existe pas.", HttpStatus.NOT_FOUND);

                } else {
                    response = new ResponseEntity<>(watchList, HttpStatus.OK);
                }
            } else {
                response = new ResponseEntity<>("Token erroné ou non autorisé", HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            response = new ResponseEntity<>("La WatchList demandée n'existe pas.", HttpStatus.NOT_FOUND);
        }

        return response;
    }


    @RequestMapping(value = "/u/{idUtilisateur}/w/{idWatchlist}/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteWatchList(@PathVariable int idUtilisateur, @PathVariable int idWatchlist, @RequestParam String token) {
        ResponseEntity<?> response;

        try {

            if (utilisateurDao.existsWithIdAndToken(idUtilisateur, token) || utilisateurDao.isAdmin(token)) {
                watchListDao.delete(WatchListPK.createInstance(idWatchlist, token, utilisateurDao));
                response = new ResponseEntity<>("Watchlist supprimée", HttpStatus.OK);


            } else {
                response = new ResponseEntity<>("Token erroné ou non autorisé", HttpStatus.UNAUTHORIZED);
            }

        } catch (NullPointerException e) {
            response = new ResponseEntity<>("La WatchList demandée n'existe pas", HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @RequestMapping(value = "/u/{idUtilisateur}/w/{idWatchlist}/a/{idAppareil}/", method = RequestMethod.PUT)
    public ResponseEntity<?> addAppareil(@PathVariable int idUtilisateur, @PathVariable int idWatchlist,
                                         @PathVariable int idAppareil, @RequestParam String token) {
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithIdAndToken(idUtilisateur, token) || utilisateurDao.isAdmin(token)) {

            WatchList watchList = watchListDao.findOne(WatchListPK.createInstance(idWatchlist, token, utilisateurDao));

            Appareil appareil = appareilDao.findOne(idAppareil);

            if (watchList != null && appareil != null) {
                watchList.getAppareils().add(appareilDao.findOne(idAppareil));
                response = new ResponseEntity<>(watchListDao.save(watchList), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = "/u/{idUtilisateur}/w/{idWatchlist}/a/{idAppareil}/", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeAppareil(@PathVariable int idUtilisateur, @PathVariable int idWatchlist,
                                            @PathVariable int idAppareil, @RequestParam String token) {
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithIdAndToken(idUtilisateur, token) || utilisateurDao.isAdmin(token)) {

            WatchList watchList = watchListDao.findOne(WatchListPK.createInstance(idWatchlist, token, utilisateurDao));

            if (watchList != null && watchList.deleteAppareil(idAppareil)) {
                response = new ResponseEntity<>(watchListDao.save(watchList), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }
}
