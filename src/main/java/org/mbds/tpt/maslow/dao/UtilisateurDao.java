package org.mbds.tpt.maslow.dao;

import org.mbds.tpt.maslow.entities.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Gael on 22/02/2016.
 */
@org.springframework.stereotype.Repository
public interface UtilisateurDao extends CrudRepository<Utilisateur, Integer> {
    Utilisateur findByIdentifiantAndPassword(String identifiant, String password);

    @Query("SELECT case WHEN count(u) > 0 THEN 'true' ELSE 'false' END FROM Utilisateur u WHERE u.token = ?1")
    boolean existsWithToken(String token);

    @Query("SELECT case WHEN count(u) > 0 THEN 'true' ELSE 'false' END FROM Utilisateur u WHERE u.identifiant = ?1")
    boolean exists(String identifiant);

    @Query("SELECT case WHEN count(u) > 0 THEN 'true' ELSE 'false' END FROM  Utilisateur u WHERE u.token = ?1 AND u.identifiant = 'admin'")
    boolean isAdmin(String token);

    @Query("SELECT u.id FROM Utilisateur u WHERE u.token = ?1")
    Integer findIdByToken(String token);

    @Query("SELECT case WHEN count (u) > 0 THEN 'true' ELSE 'false' END FROM  Utilisateur u WHERE u.id = ?1 AND u.token = ?2")
    boolean existsWithIdAndToken(Integer idUtilisateur, String token);
}
