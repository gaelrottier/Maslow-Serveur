package org.mbds.tpt.maslow.entities;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
public class WatchList{

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    //Les appareils à surveiller par l'appli

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "watchlist_id")
    List<Appareil> appareils;

    public WatchList() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Appareil> getAppareils() {
        return appareils;
    }

    public void setAppareils(List<Appareil> appareils) {
        this.appareils = appareils;
    }
}
