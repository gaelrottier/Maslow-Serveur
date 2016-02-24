package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.UtilisateurDao;
import org.mbds.tpt.maslow.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

/**
 * Created by Gael on 22/02/2016.
 */
@RestController
@RequestMapping("/u")
public class UtilisateurController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<String> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            utilisateur.generateToken();

            return new ResponseEntity<>(utilisateurDao.save(utilisateur).toString(), HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>("Les paramètres ne sont pas bons.", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUtilisateur(@RequestBody Utilisateur utilisateur, @RequestParam String token) {
        try {
            // Si id = 0 (automatique), il n'est pas renseigné,
            //  c'est donc une requête de création,
            //  ce qu'on ne veut pas dans la méthode PUT
            if (utilisateur.getId() == 0) {
                throw new IllegalArgumentException("L'id doit être renseigné");
            }

            if (utilisateurDao.existsWithToken(token)) {
                return new ResponseEntity<>(utilisateurDao.save(utilisateur).toString(), HttpStatus.OK);
            } else {
                throw new IllegalAccessException("Le token est erroné");
            }

        } catch (ConstraintViolationException | IllegalArgumentException e) {
            return new ResponseEntity<>("Les paramètres ne sont pas bons.", HttpStatus.BAD_REQUEST);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> readUtilisateur(@PathVariable("id") int id, @RequestParam String token) {
        try {

            if (utilisateurDao.existsWithToken(token)) {
                return new ResponseEntity<>(utilisateurDao.findOne(id).toString(), HttpStatus.OK);
            } else {
                throw new IllegalAccessException("Le token est erroné");
            }

        } catch (NullPointerException e) {
            return new ResponseEntity<>("L'utilisateur demandé n'existe pas.", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
