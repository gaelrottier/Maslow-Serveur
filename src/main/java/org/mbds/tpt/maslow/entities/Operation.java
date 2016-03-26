
package org.mbds.tpt.maslow.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Map;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String idOrchestra;

    @JoinColumns({
            @JoinColumn(name = "id_utilisateur_fk", referencedColumnName = "idUtilisateur"),
            @JoinColumn(name = "id_procedural_fk", referencedColumnName = "idProcedural")
    })
    @ManyToOne
    Procedural procedural;

    //les parametres à envoyer avec l'id
    @ElementCollection
    private Map<String, String> params;

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

    @JsonIgnore
    public Procedural getProcedural() {
        return procedural;
    }

    public void setProcedural(Procedural procedural) {
        this.procedural = procedural;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> alias) {
        this.params = alias;
    }
}
