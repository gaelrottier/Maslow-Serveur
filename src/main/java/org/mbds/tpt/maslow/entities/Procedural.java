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
    ProceduralPK idProcedural;

    //A exécution immédiate par l'application
    @OneToMany(mappedBy = "procedural")
    List<Operation> operations;

    public Procedural() {
    }

    public Procedural(ProceduralPK idProcedural) {
        this.idProcedural = idProcedural;
    }

    public ProceduralPK getIdProcedural() {
        return idProcedural;
    }

    public void setIdProcedural(ProceduralPK idProcedure) {
        this.idProcedural = idProcedure;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }


    @Override
    public String toString() {
        String proceduralToString = "{'idProcedural': " +
                "{'idProcedural': " + idProcedural.getIdProcedural() + "," +
                "'idUtilisateur': " + idProcedural.getIdUtilisateur() + "" +
                "}," +
                "'operations': [";
        for (int i = 0; i <= operations.size() - 1; i++) {
            Operation o = operations.get(i);

            proceduralToString += "" +
                    "{'id': " + o.getId() + "," +
                    "'idOrchestra': '" + o.getIdOrchestra() + "'," +
                    "'parametres': " + o.getParametres() + "}";
            if (i != operations.size() - 1) {
                proceduralToString += ",";
            }
        }

        proceduralToString += "]}";

        return proceduralToString;
    }
}
