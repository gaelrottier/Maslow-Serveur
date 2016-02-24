package org.mbds.tpt.maslow.dao;

import org.mbds.tpt.maslow.entities.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Gael on 22/02/2016.
 */
@org.springframework.stereotype.Repository
public interface UtilisateurDao extends CrudRepository<Utilisateur, Integer> {
    Utilisateur findByPrenom(String prenom);

    Utilisateur findByNom(String nom);

    @Query("SELECT case WHEN count(u) > 0 THEN 'true' ELSE 'false' END FROM Utilisateur u WHERE u.token = ?1")
    boolean existsWithToken(String token);
}
