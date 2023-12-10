package com.example.myapplication;

public class Etudiant {
    private String id;
    private String nom;
    private String prenom;
    private String classe;
    private boolean presence;

    public Etudiant(String id, String nom, String prenom, String classe, boolean presence) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.classe = classe;
        this.presence = presence;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getClasse() {
        return classe;
    }

    public boolean isPresence() {
        return presence;
    }
}
