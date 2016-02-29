
package org.mbds.tpt.maslow.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String idOrchestra;

    @JoinColumns({
            @JoinColumn(name = "id_procedural_fk", referencedColumnName = "idUtilisateur"),
            @JoinColumn(name = "id_utilisateur_fk", referencedColumnName = "idProcedural")
    })
    @ManyToOne
    @JsonBackReference
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
