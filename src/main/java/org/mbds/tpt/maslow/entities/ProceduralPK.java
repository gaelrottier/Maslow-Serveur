package org.mbds.tpt.maslow.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Gael on 22/02/2016.
 */
@Embeddable
public class ProceduralPK implements Serializable {
    private int idProcedure;
    private int idUtilisateur;

    public ProceduralPK() {
    }

    public ProceduralPK(int idProcedure, int idUtilisateur) {
        this.idProcedure = idProcedure;
        this.idUtilisateur = idUtilisateur;
    }

    public int getIdProcedure() {
        return idProcedure;
    }

    public void setIdProcedure(int idProcedure) {
        this.idProcedure = idProcedure;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public int hashCode() {
        return idProcedure + idUtilisateur;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProceduralPK) {
            ProceduralPK ppk = (ProceduralPK) obj;
            return ppk.idUtilisateur == this.idUtilisateur && ppk.idProcedure == idProcedure;
        }

        return false;
    }
}
