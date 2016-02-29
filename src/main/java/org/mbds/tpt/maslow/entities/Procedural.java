package org.mbds.tpt.maslow.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
public class Procedural {

    @EmbeddedId
    ProceduralPK proceduralPK;

    //A exécution immédiate par l'application
    @OneToMany(mappedBy = "procedural", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<Operation> operations;

    public Procedural() {
    }

    public Procedural(ProceduralPK proceduralPK) {
        this.proceduralPK = proceduralPK;
    }

    public ProceduralPK getProceduralPK() {
        return proceduralPK;
    }

    public void setProceduralPK(ProceduralPK idProcedure) {
        this.proceduralPK = idProcedure;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }


}
