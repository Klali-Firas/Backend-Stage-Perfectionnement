package stage.perfectionnement.cni.municipalite.model;

import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Entity
@Table(name = "commune")

public class Commune implements Serializable {
    @Id
    @Column(name = "num")
    private int num;

    @Column(name = "nom")
    private String nom;

    // Define the relationship with the Utilisateur entity
    // @OneToMany(mappedBy = "commune", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY)
    // private List<Utilisateur> utilisateur;

    public Commune() {
    }

    public Commune(int num, String nom) {
        this.num = num;
        this.nom = nom;
        // this.utilisateur = utilisateur;
    }

    public int getNum() {
        return num;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // public List<Utilisateur> getUtilisateur() {
    // return utilisateur;
    // }

    // public void setUtilisateur(List<Utilisateur> utilisateur) {
    // this.utilisateur = utilisateur;
    // }
}
