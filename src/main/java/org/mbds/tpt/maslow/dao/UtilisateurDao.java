package org.mbds.tpt.maslow.dao;

import org.mbds.tpt.maslow.entities.Utilisateur;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Gael on 22/02/2016.
 */
@org.springframework.stereotype.Repository
public interface UtilisateurDao extends CrudRepository<Utilisateur, Integer> {
    Utilisateur findByPrenom(String prenom);
    Utilisateur findByNom(String nom);

}
