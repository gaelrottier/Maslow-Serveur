package org.mbds.tpt.maslow.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "watchListPK")
public class WatchList {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "idUtilisateur", column = @Column(name = "id_utilisateur")),
            @AttributeOverride(name = "idWatchList", column = @Column(name = "id_watchlist"))
    })
    private WatchListPK watchListPK;

    @JoinColumn(name = "id_utilisateur", insertable = false, updatable = false)
    @ManyToOne
    private Utilisateur utilisateur;

    //Les appareils à surveiller par l'appli
    @JoinTable(
            joinColumns = {
                    @JoinColumn(name = "id_utilisateur", nullable = false),
                    @JoinColumn(name = "id_watchlist", nullable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "id_appareil", nullable = false)
            })
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Appareil> appareils = new ArrayList<>();

    public WatchList() {
    }

    public WatchList(WatchListPK watchListPK, List<Appareil> appareils) {
        this.watchListPK = watchListPK;
        this.appareils = appareils;
    }

    public WatchListPK getWatchListPK() {
        return watchListPK;
    }

    public void setWatchListPK(WatchListPK watchListPK) {
        this.watchListPK = watchListPK;
    }

    @JsonIgnore
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Appareil> getAppareils() {
        return appareils;
    }

    public void setAppareils(List<Appareil> appareils) {
        this.appareils = appareils;
    }

    public void updateAppareil(Appareil a) {
        for (int i = 0; i <= appareils.size(); i++) {
            if (appareils.get(i).getId() == a.getId()) {
                appareils.set(i, a);
                break;
            }
        }
    }

    public Appareil getAppareil(int idAppareil) {
        Appareil res = null;
        for (Appareil a : appareils) {
            if (a.getId() == idAppareil) {
                res = a;
                break;
            }
        }
        return res;
    }

    public boolean deleteAppareil(int idAppareil) {

        boolean result = false;

        for (int i = 0; i <= appareils.size(); i++) {
            Appareil a = appareils.get(i);
            if (a.getId() == idAppareil) {
                a.getWatchlists().remove(this);
                appareils.remove(i);
                result = true;
                break;
            }
        }

        return result;
    }
}
