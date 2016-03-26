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
@RequestMapping("/u/{idUtilisateur}/p/{idProcedural}")
public class ProceduralController {

    @Autowired
    private ProceduralDao proceduralDao;

    @Autowired
    private UtilisateurDao utilisateurDao;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    public ResponseEntity<?> createProcedural(@PathVariable int idUtilisateur, @PathVariable int idProcedural,
                                              @RequestBody Procedural procedural, @RequestParam String token) {
        ResponseEntity<?> response;

        try {
            if (utilisateurDao.existsWithIdAndToken(idUtilisateur, token) || utilisateurDao.isAdmin(token)) {
                procedural.setProceduralPK(new ProceduralPK(idUtilisateur, idProcedural));
                response = new ResponseEntity<>(proceduralDao.save(procedural), HttpStatus.CREATED);
            } else {
                response = new ResponseEntity<>("Token erroné ou non autorisé", HttpStatus.UNAUTHORIZED);
            }
        } catch (ConstraintViolationException e) {
            response = new ResponseEntity<>("Les paramètres ne sont pas bons.", HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ResponseEntity<?> readProcedural(@PathVariable int idUtilisateur, @PathVariable int idProcedural, @RequestParam String token) {
        ResponseEntity<?> response;

        try {

            if (utilisateurDao.existsWithIdAndToken(idUtilisateur, token) || utilisateurDao.isAdmin(token)) {
                response = new ResponseEntity<>(proceduralDao.findOne(new ProceduralPK(idUtilisateur, idProcedural)), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
            }

        } catch (NullPointerException e) {
            response = new ResponseEntity<>("La procédure demandée n'existe pas.", HttpStatus.NOT_FOUND);
        }

        return response;
    }

}
