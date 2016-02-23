package org.mbds.tpt.maslow.controllers;

import org.json.JSONObject;
import org.mbds.tpt.maslow.dao.UtilisateurDao;
import org.mbds.tpt.maslow.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gael on 22/02/2016.
 */
@RestController
@RequestMapping("/u")
public class UtilisateurController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<String> createUtilisateur(@RequestBody Utilisateur utilisateur, HttpServletRequest request) {
        JSONObject json = new JSONObject();

        //Si l'utilisateur ne se crée pas, un HTTP 500 est levé
        Utilisateur createdUtilisateur = utilisateurDao.save(utilisateur);

        json.put("href", request.getRequestURL().append(createdUtilisateur.getId()));
        json.put("resource", createdUtilisateur);

        return new ResponseEntity<>(json.toString(), HttpStatus.CREATED);
    }
}
