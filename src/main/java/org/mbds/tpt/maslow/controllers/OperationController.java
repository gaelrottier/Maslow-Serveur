package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.ProceduralDao;
import org.mbds.tpt.maslow.dao.UtilisateurDao;
import org.mbds.tpt.maslow.entities.Operation;
import org.mbds.tpt.maslow.entities.Procedural;
import org.mbds.tpt.maslow.entities.ProceduralPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Zac on 27/02/2016.
 */
@RestController
@RequestMapping(value = "/u/{idUtilisateur}/p/{idProcedural}/o")
public class OperationController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    private ProceduralDao proceduralDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createOperation(@PathVariable int idUtilisateur, @PathVariable int idProcedural,
                                             @RequestBody Operation operation, @RequestParam String token) {

        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            Procedural p = proceduralDao.findOne(new ProceduralPK(idUtilisateur, idProcedural));

            p.addOperation(operation);

            response = new ResponseEntity<>(proceduralDao.save(p), HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = "/{idOperation}", method = RequestMethod.GET)
    public ResponseEntity<?> readOperation(@PathVariable int idUtilisateur, @PathVariable int idProcedural,
                                           @PathVariable int idOperation, @RequestParam String token) {
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            Operation o = proceduralDao.findOne(new ProceduralPK(idUtilisateur, idProcedural)).getOperation(idOperation);
            if (o == null) {
                response = new ResponseEntity<>("L'opération n'existe pas pour la procédure demandée", HttpStatus.NOT_FOUND);
            } else {
                response = new ResponseEntity<>(o, HttpStatus.OK);
            }

        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.PUT)
    public ResponseEntity<?> updateOperation(@PathVariable int idUtilisateur, @PathVariable int idProcedural,
                                             @RequestBody Operation operation, @RequestParam String token) {
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {

            int idOperation = operation.getId();
            Procedural p = proceduralDao.findOne(new ProceduralPK(idUtilisateur, idProcedural));

            if (p.getOperation(idOperation) == null)
                response = new ResponseEntity<>("L'opération n'existe pas pour la procédure demandée", HttpStatus.NOT_FOUND);
            else {
                p.deleteOperation(idOperation);
                p.addOperation(operation);
                response = new ResponseEntity<>(proceduralDao.save(p), HttpStatus.OK);
            }

        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOperation(@PathVariable int idUtilisateur, @PathVariable int idProcedural,
                                             @PathVariable int idOperation, @RequestParam String token) {
        ResponseEntity<?> response;

        if (utilisateurDao.existsWithToken(token)) {
            Procedural p = proceduralDao.findOne(new ProceduralPK(idUtilisateur, idProcedural));

            p.deleteOperation(idOperation);

            response = new ResponseEntity<>(proceduralDao.save(p), HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<>("Le token est erroné", HttpStatus.UNAUTHORIZED);
        }

        return response;
    }
}
