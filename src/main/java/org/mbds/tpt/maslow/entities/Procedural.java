package org.mbds.tpt.maslow.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
public class Procedural implements Serializable {

    //Id recu via la lampe, l'activité, ou le tag NFC
    @EmbeddedId
    ProceduralPK idProcedure;

    //A exécution immédiate par l'application
    @OneToMany(mappedBy = "procedural")
    List<Operation> operations;

    public Procedural() {
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}
