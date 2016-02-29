package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.ProceduralDao;
import org.mbds.tpt.maslow.dao.UtilisateurDao;
import org.mbds.tpt.maslow.entities.Procedural;
import org.mbds.tpt.maslow.entities.ProceduralPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

/**
 * Created by Gael on 17/02/2016.
 */
@RestController
@RequestMapping("/u/{idUtilisateur}/p")
public class ProceduralController {

    @Autowired
    private ProceduralDao proceduralDao;

    @Autowired
    private UtilisateurDao utilisateurDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createProcedural(@RequestBody Procedural procedural, @RequestParam String token) {
        try {
            if (utilisateurDao.existsWithToken(token)) {
                return new ResponseEntity<>(proceduralDao.save(procedural), HttpStatus.CREATED);
            } else {
                throw new IllegalAccessException("Le token est erroné");
            }
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>("Les paramètres ne sont pas bons.", HttpStatus.BAD_REQUEST);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProcedural(@RequestBody Procedural procedural, @RequestParam String token) {
        try {

            if (utilisateurDao.existsWithToken(token)) {
                return new ResponseEntity<>(proceduralDao.save(procedural), HttpStatus.OK);
            } else {
                throw new IllegalAccessException("Le token est erroné");
            }

        } catch (ConstraintViolationException | IllegalArgumentException e) {
            return new ResponseEntity<>("Les paramètres ne sont pas bons.", HttpStatus.BAD_REQUEST);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{idProcedural}", method = RequestMethod.GET)
    public ResponseEntity<?> readProcedure(@PathVariable int idUtilisateur, @PathVariable int idProcedural, @RequestParam String token) {
        try {

            if (utilisateurDao.existsWithToken(token)) {
                return new ResponseEntity<>(proceduralDao.findOne(new ProceduralPK(idUtilisateur, idProcedural)), HttpStatus.OK);
            } else {
                throw new IllegalAccessException("Le token est erroné");
            }

        } catch (NullPointerException e) {
            return new ResponseEntity<>("La procédure demandée n'existe pas.", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
