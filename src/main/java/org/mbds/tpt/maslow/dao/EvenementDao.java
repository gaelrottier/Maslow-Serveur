package org.mbds.tpt.maslow.dao;

import org.mbds.tpt.maslow.entities.Evenement;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Gael on 23/02/2016.
 */
@Transactional
@org.springframework.stereotype.Repository
public interface EvenementDao extends CrudRepository<Evenement, Integer> {
}
