package org.mbds.tpt.maslow.dao;

import org.mbds.tpt.maslow.entities.WatchList;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Gael on 24/02/2016.
 */
@Transactional
@org.springframework.stereotype.Repository
public interface WatchListDao extends CrudRepository<WatchList, Integer> {
}
