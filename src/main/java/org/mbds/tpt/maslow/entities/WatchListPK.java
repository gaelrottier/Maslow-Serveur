package org.mbds.tpt.maslow.entities;

import org.mbds.tpt.maslow.dao.UtilisateurDao;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Gael on 25/03/2016.
 */
@Embeddable
public class WatchListPK implements Serializable {
    @Column(name = "id_watchlist")
    private int idWatchList;

    @Column(name = "id_utilisateur")
    private int idUtilisateur;

    public WatchListPK() {
    }

    public WatchListPK(int idWatchList, int idUtilisateur) {
        this.idWatchList = idWatchList;
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdWatchList() {
        return idWatchList;
    }

    public void setIdWatchList(int idWatchList) {
        this.idWatchList = idWatchList;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public static WatchListPK createInstance(int idWatchlist, String token, UtilisateurDao utilisateurDao) {
        return new WatchListPK(idWatchlist, utilisateurDao.findIdByToken(token));
    }
}
