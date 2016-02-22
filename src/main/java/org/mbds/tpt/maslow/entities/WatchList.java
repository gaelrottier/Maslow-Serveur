package org.mbds.tpt.maslow.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
public class WatchList implements Serializable {

    @Id
    int id;

    //Les appareils à surveiller par l'appli
    @OneToMany(mappedBy = "watchlist")
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
