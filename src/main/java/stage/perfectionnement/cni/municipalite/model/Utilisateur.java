package stage.perfectionnement.cni.municipalite.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "utilisateurs")
// @JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_utilisateur;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commune", referencedColumnName = "num")
    @JsonIgnoreProperties({ "hibernateLazyInitializer" })
    private Commune commune;

    @Column(name = "profile")
    private String profile;

    // @OneToMany(mappedBy = "id_agent")
    // private List<Demande> demandes;

    @Column(name = "login")
    private String login;

    @JsonIgnore
    @Column(name = "mdp")
    private String mdp;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, Commune commune, String profile, String login, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.commune = commune;
        this.profile = profile;
        this.login = login;
        this.mdp = mdp;
    }

    public Long getId() {
        return id_utilisateur;
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

    @Transactional
    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

}
