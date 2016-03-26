package org.mbds.tpt.maslow.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "proceduralPK")
public class Procedural {
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "id_utilisateur", column = @Column(name = "idUtilisateur")),
            @AttributeOverride(name = "id_procedural", column = @Column(name = "id_procedural"))
    })
    private ProceduralPK proceduralPK;

    @JoinColumn(name = "idUtilisateur", insertable = false, updatable = false)
    @ManyToOne
    private Utilisateur utilisateur;

    //A exécution immédiate par l'application
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = {
                    @JoinColumn(name = "id_utilisateur", nullable = false),
                    @JoinColumn(name = "id_procedural", nullable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "id_operation", nullable = false)
            })
    private List<Operation> operations;

    public Procedural() {
    }

    public Procedural(ProceduralPK proceduralPK) {
        this.proceduralPK = proceduralPK;

    }

    @JsonIgnore
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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

    public void addOperation(Operation operation) {
        this.operations.add(operation);
    }

    public Operation getOperation(int idOperation) {
        Operation res = null;

        for (Operation o : operations) {
            if (o.getId() == idOperation) {
                res = o;
                break;
            }
        }

        return res;
    }

    public void deleteOperation(int idOperation) {
        for (int i = 0; i <= operations.size(); i++) {
            Operation op = operations.get(i);
            if (op.getId() == idOperation) {
                op.setProcedural(null);
                operations.remove(i);
                break;
            }
        }
    }

    public void updateOperation(Operation o) {
        for (int i = 0; i <= operations.size(); i++) {
            if (operations.get(i).getId() == o.getId()) {
                operations.set(i, o);
                break;
            }
        }
    }
}
