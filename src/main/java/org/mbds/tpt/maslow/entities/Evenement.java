package org.mbds.tpt.maslow.entities;

import java.util.Map;

/**
 * Created by Gael on 17/02/2016.
 */
public class Evenement {
    int id;

    String idOrchestra;

    //Permet de faire la correspondance entre
    // le nom du paramètre envoyé par orchestra et
    // le nom de paramètre traité par l'appli
    Map<String, String> alias;
}
