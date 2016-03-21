package org.mbds.tpt.maslow.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Map;

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
    @ManyToOne
    @JsonBackReference
    Appareil appareil;

    //Permet de faire la correspondance entre
    // le nom du paramètre envoyé par orchestra et
    // le nom de paramètre traité par l'appli
    @ElementCollection
    private Map<String, String> alias;


    public Appareil getAppareil() {
        return appareil;
    }

    public void setAppareil(Appareil appareil) {
        this.appareil = appareil;
    }

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

    public Map<String, String> getAlias() {
        return alias;
    }

    public void setAlias(Map<String, String> alias) {
        this.alias = alias;
    }
}
