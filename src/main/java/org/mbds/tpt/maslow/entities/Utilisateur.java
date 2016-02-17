package org.mbds.tpt.maslow.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by Gael on 17/02/2016.
 */
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    List<Procedure> procedures;

    public Utilisateur() {
    }
}
