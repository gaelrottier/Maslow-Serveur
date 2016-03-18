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
    private int id;

    //Les appareils à surveiller par l'appli

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "watchlist_id")
    private List<Appareil> appareils;

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
}
