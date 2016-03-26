package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.AppareilDao;
import org.mbds.tpt.maslow.dao.EvenementDao;
import org.mbds.tpt.maslow.dao.UtilisateurDao;
import org.mbds.tpt.maslow.entities.Appareil;
import org.mbds.tpt.maslow.entities.Evenement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Zac on 27/02/2016.
 */
@RestController
@RequestMapping(value = "/a/{idAppareil}/e")
public class EvenementController {

    @Autowired
    private EvenementDao evenementDao;

    @Autowired
    private AppareilDao appareilDao;

    @Autowired
    private UtilisateurDao utilisateurDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> addEvenement(@PathVariable int idAppareil,
                                          @RequestBody Evenement evenement, @RequestParam String token) {
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            Appareil appareil = appareilDao.findOne(idAppareil);

            if (appareil != null) {

                evenement.setAppareil(appareil);

                appareil.getEvenements().add(evenement);

                response = new ResponseEntity<Object>(appareilDao.save(appareil), HttpStatus.CREATED);

            } else {
                response = new ResponseEntity<Object>("L'appareil " + idAppareil + " est introuvable", HttpStatus.NOT_FOUND);
            }
        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> majEvenement(@PathVariable int idAppareil,
                                          @RequestBody Evenement evenement, @RequestParam String token) {
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            Appareil appareil = appareilDao.findOne(idAppareil);

            if (appareil != null) {
                try {
                    appareil.updateEvenement(evenement);
                    response = new ResponseEntity<>(appareilDao.save(appareil), HttpStatus.CREATED);
                } catch (DataAccessException e) {
                    response = new ResponseEntity<>("L'evenement " + evenement.getId() + " de l'appareil " + appareil.getId() + "est introuvable ...", HttpStatus.NOT_FOUND);
                }

            } else {
                response = new ResponseEntity<Object>("L'appareil " + idAppareil + " est introuvable", HttpStatus.NOT_FOUND);
            }

        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = "/{idEvenement}/", method = RequestMethod.GET)
    public ResponseEntity<?> getEvenement(@PathVariable int idAppareil,
                                          @PathVariable int idEvenement, @RequestParam String token) {
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            Appareil appareil = appareilDao.findOne(idAppareil);

            if (appareil != null) {
                try {
                    response = new ResponseEntity<>(appareil.getEvenement(idEvenement), HttpStatus.OK);
                } catch (Exception e) {
                    response = new ResponseEntity<>("L'evenement " + idEvenement + " de l'appareil " + appareil.getId() + " est introuvable ...", HttpStatus.NOT_FOUND);
                }
            } else {
                response = new ResponseEntity<>("L'appareil " + idAppareil + " n'existe pas...", HttpStatus.NOT_FOUND);
            }
        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> getEvenements(@PathVariable int idAppareil,
                                           @RequestParam String token) {
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            Appareil appareil = appareilDao.findOne(idAppareil);

            if (appareil != null) {
                try {
                    response = new ResponseEntity<>(appareil.getEvenements(), HttpStatus.OK);
                } catch (Exception e) {
                    response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                response = new ResponseEntity<Object>("L'appreil " + idAppareil + " n'existe pas...", HttpStatus.NOT_FOUND);
            }
        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    @RequestMapping(value = "/{idEvenement}/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEvenement(@PathVariable int idAppareil,
                                             @PathVariable int idEvenement, @RequestParam String token) {
        ResponseEntity<?> response;
        if (utilisateurDao.existsWithToken(token)) {

            Appareil appareil = appareilDao.findOne(idAppareil);

            if (appareil != null) {
                try {
                    appareil.deleteEvenement(idEvenement);
                    evenementDao.delete(idEvenement);
                    response = new ResponseEntity<Object>(appareilDao.save(appareil), HttpStatus.OK);
                } catch (Exception e) {
                    response = new ResponseEntity<Object>("L'evenement " + idEvenement + " de l'appareil " + appareil.getId() + " est introuvable ...", HttpStatus.NOT_FOUND);
                }
            } else {
                response = new ResponseEntity<Object>("L'appreil " + idAppareil + " n'existe pas...", HttpStatus.NOT_FOUND);
            }
        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }
}
