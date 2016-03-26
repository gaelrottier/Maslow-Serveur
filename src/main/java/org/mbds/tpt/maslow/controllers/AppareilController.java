package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.AppareilDao;
import org.mbds.tpt.maslow.dao.UtilisateurDao;
import org.mbds.tpt.maslow.dao.WatchListDao;
import org.mbds.tpt.maslow.entities.Appareil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Zac on 27/02/2016.
 */
@RestController
@RequestMapping(value = "/a")
public class AppareilController {

    @Autowired
    private AppareilDao appareilDao;

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    private WatchListDao watchListDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createAppareil(@RequestBody Appareil appareil,
                                            @RequestParam String token) {
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {
            if (appareil.getId() == 0) {

                response = new ResponseEntity<>(appareilDao.save(appareil), HttpStatus.CREATED);
            } else {
                response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAppareil(@RequestBody Appareil appareil, @RequestParam String token) {

        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            if (appareil.getId() != 0) {
                try {
                    response = new ResponseEntity<>(appareilDao.save(appareil), HttpStatus.OK);
                } catch (DataAccessException e) {
                    response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                response = new ResponseEntity<>("L'appareil n'existe pas...", HttpStatus.NOT_FOUND);
            }


        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = "/{idAppareil}/", method = RequestMethod.GET)
    public ResponseEntity<?> getAppareil(@PathVariable int idAppareil, @RequestParam String token) {
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            Appareil appareil = appareilDao.findOne(idAppareil);

            if (appareil != null) {
                response = new ResponseEntity<Object>(appareil, HttpStatus.OK);
            } else {
                response = new ResponseEntity<Object>("L'appareil " + idAppareil + " n'existe pas...", HttpStatus.NOT_FOUND);
            }
        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = "/{idAppareil}/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAppareil(@PathVariable int idAppareil, @RequestParam String token) {
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            try {
                appareilDao.delete(idAppareil);
                response = new ResponseEntity<>("Appareil supprimé", HttpStatus.OK);

            } catch (Exception e) {
                response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            response = new ResponseEntity<Object>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }
}
