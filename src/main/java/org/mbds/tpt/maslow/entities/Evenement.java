package org.mbds.tpt.maslow.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String idOrchestra;
    //
//    @ManyToOne
//    Appareil appareil;

    //Permet de faire la correspondance entre
    // le nom du paramètre envoyé par orchestra et
    // le nom de paramètre traité par l'appli
    private String alias;

    public Evenement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdOrchestra() {
        return idOrchestra;
    }

    public void setIdOrchestra(String idOrchestra) {
        this.idOrchestra = idOrchestra;
    }

//    public Appareil getAppareil() {
//        return appareil;
//    }
//
//    public void setAppareil(Appareil appareil) {
//        this.appareil = appareil;
//    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
