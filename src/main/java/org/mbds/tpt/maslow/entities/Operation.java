package org.mbds.tpt.maslow.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
public class Operation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String idOrchestra;

    @MapsId("idProcedure")
    @JoinColumns({
            @JoinColumn(name = "idProcedure_fk", referencedColumnName = "idProcedure"),
            @JoinColumn(name = "idUtilisateur_fk", referencedColumnName = "idUtilisateur")
    })
    @ManyToOne
    Procedural procedural;

    //les paramètres à envoyer avec l'id
    String paramètres;

    public Operation() {
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

    public Procedural getProcedural() {
        return procedural;
    }

    public void setProcedural(Procedural procedural) {
        this.procedural = procedural;
    }

    public String getParamètres() {
        return paramètres;
    }

    public void setParamètres(String paramètres) {
        this.paramètres = paramètres;
    }
}
