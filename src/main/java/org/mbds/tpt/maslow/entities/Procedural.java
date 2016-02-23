package org.mbds.tpt.maslow.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
public class Procedural {

    //Id recu via la lampe, l'activité, ou le tag NFC
    @EmbeddedId
    ProceduralPK idProcedure;

    //A exécution immédiate par l'application
    @OneToMany(mappedBy = "procedural")
    List<Operation> operations;

    public Procedural() {
    }

    public Procedural(ProceduralPK idProcedure) {
        this.idProcedure = idProcedure;
    }

    public ProceduralPK getIdProcedure() {
        return idProcedure;
    }

    public void setIdProcedure(ProceduralPK idProcedure) {
        this.idProcedure = idProcedure;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
