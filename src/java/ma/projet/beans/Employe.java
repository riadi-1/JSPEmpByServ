/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.projet.beans;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Ouissal
 */
@Entity
public class Employe {
    @Id
    @GeneratedValue
    public int id;
    private String nom;
    private String prenom;
    private Date datenaissance;
    private String photo;
    @ManyToOne
    private Service service;
    @ManyToOne
    private Employe Chef;
    public Employe() {
    }

    public Employe(String nom, String prenom, Date datenaissance, String photo, Service service, Employe Chef) {
        this.nom = nom;
        this.prenom = prenom;
        this.datenaissance = datenaissance;
        this.photo = photo;
        this.service = service;
        this.Chef = Chef;
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

    public Date getDatenaissance() {
        return datenaissance;
    }

    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Employe getChef() {
        return Chef;
    }

    public void setChef(Employe Chef) {
        this.Chef = Chef;
    }

    @Override
    public String toString() {
        return "Employe{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", datenaissance=" + datenaissance + ", photo=" + photo + ", service=" + service +'}';
    }

   
    
    
}
