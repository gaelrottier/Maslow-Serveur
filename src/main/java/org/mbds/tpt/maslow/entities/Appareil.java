package org.mbds.tpt.maslow.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
public class Appareil implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    //Nom affiché par l'appli
    String nom;

    @OneToMany(mappedBy = "appareil")
    List<Evenement> evenements;

    @ManyToOne
    WatchList watchlist;

    public Appareil() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    public WatchList getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(WatchList watchlist) {
        this.watchlist = watchlist;
    }
}
