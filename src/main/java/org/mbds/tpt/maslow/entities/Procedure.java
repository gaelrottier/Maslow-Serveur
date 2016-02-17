package org.mbds.tpt.maslow.entities;

import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
public class Procedure {

    //Id recu via la lampe, l'activité, ou le tag NFC
    int idProcedure;

    //Permet d'associer le même id de procédure à plusieurs utilisateurs
    int idUtilisateur;

    //A exécution immédiate par l'application
    List<Operation> operations;
}
