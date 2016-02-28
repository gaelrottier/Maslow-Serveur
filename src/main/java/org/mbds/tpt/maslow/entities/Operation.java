
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

    @MapsId("idProcedural")
    @JoinColumns(value = {
            @JoinColumn(name = "idProcedural_fk", referencedColumnName = "idProcedural"),
            @JoinColumn(name = "idUtilisateur_fk", referencedColumnName = "idUtilisateur")
    })
    @ManyToOne
    Procedural procedural;

    //les parametres à envoyer avec l'id
    String parametres;

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

    public String getParametres() {
        return parametres;
    }

    public void setParametres(String parametres) {
        this.parametres = parametres;
    }
}
