package org.mbds.tpt.maslow.dao;

import org.mbds.tpt.maslow.entities.Utilisateur;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Gael on 22/02/2016.
 */
@Transactional
public interface UtilisateurDao extends CrudRepository<Utilisateur, Long> {
    Utilisateur findByNom(String nom);
}
