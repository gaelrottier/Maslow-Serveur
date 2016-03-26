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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Appareil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //Nom affiché par l'appli
    private String nom;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Evenement> evenements = new ArrayList<>();


    @ManyToMany(mappedBy = "appareils")
    private List<WatchList> watchlists = new ArrayList<>();

    @JsonIgnore
    public List<WatchList> getWatchlists() {
        return watchlists;
    }

    public void setWatchlists(List<WatchList> watchlists) {
        this.watchlists = watchlists;
    }

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

    public void updateEvenement(Evenement e) {
        for (int i = 0; i <= evenements.size(); i++) {
            if (evenements.get(i).getId() == e.getId()) {
                evenements.set(i, e);
                break;
            }
        }
    }

    public Evenement getEvenement(int idEvenement) {
        Evenement res = null;
        for (Evenement e : evenements) {
            if (e.getId() == idEvenement) {
                res = e;
                break;
            }
        }
        return res;
    }

    public void deleteEvenement(int idEvenement) {
        for (int i = 0; i <= evenements.size(); i++) {
            Evenement e = evenements.get(i);
            if (e.getId() == idEvenement) {
                evenements.remove(i);
                break;
            }
        }
    }
}
