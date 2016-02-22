package org.mbds.tpt.maslow.controllers;

import org.mbds.tpt.maslow.dao.UtilisateurDao;
import org.mbds.tpt.maslow.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Gael on 22/02/2016.
 */
@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @RequestMapping("/create")
    public String create(String nom, String prenom) {
        String id = "";

        try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setIdentifiant("");
            utilisateur.setPassword("");
            utilisateurDao.save(utilisateur);
            id = String.valueOf(utilisateur.getId());
        } catch (Exception e) {
            return "user not found";
        }

        return "user created successfully, id : " + id;
    }
}
