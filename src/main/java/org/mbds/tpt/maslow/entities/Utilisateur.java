package org.mbds.tpt.maslow.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by Gael on 17/02/2016.
 */
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @NotNull
    private String identifiant;

    @NotNull
    private String password;

    private String token;

    public Utilisateur() {
    }

    //immutable universally unique identifier
    public void generateToken() {
        token = UUID.randomUUID().toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        byte[] hashedPassword = null;

        try {
            hashedPassword = MessageDigest.getInstance("md5").digest(password.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            // Unlikely to happen
            System.out.println("MD5 n'est pas présent sur le système");
        }

        this.password = new String(hashedPassword);
    }

    @Override
    public String toString() {
        return "{" +
                "'id' : " + id + "," +
                "'nom': '" + nom + "'," +
                "'prenom': '" + prenom + "'," +
                "'identifiant': '" + identifiant + "'," +
                "'token': '" + token + "'" +
                "}";
    }
}
