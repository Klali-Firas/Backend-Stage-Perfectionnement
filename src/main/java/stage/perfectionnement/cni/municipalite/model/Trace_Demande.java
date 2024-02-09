package stage.perfectionnement.cni.municipalite.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

class Trace_DemandeId implements Serializable {
    private Demande demande;
    private Date date_mise_a_jour;

    public Trace_DemandeId() {
    }

    public Trace_DemandeId(Demande demande, Date date_mise_a_jour) {
        this.demande = demande;
        this.date_mise_a_jour = date_mise_a_jour;
    }
}

@Entity
@Table(name = "trace_demande")
@IdClass(Trace_DemandeId.class)

public class Trace_Demande implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "demande")
    private Demande demande;

    @Id
    @Column(name = "date_mise_a_jour")
    private Date date_mise_a_jour;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "validateur", referencedColumnName = "id_utilisateur")
    private Utilisateur validateur;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "statut", referencedColumnName = "id_statut")
    @JsonIgnoreProperties({ "hibernateLazyInitializer" })
    private Statut statut;

    @Column(name = "remarque")
    private String remarque;

    // Constructor
    public Trace_Demande(Demande demande, Date date_mise_a_jour, Utilisateur validateur, Statut statut,
            String remarque) {
        this.demande = demande;
        this.date_mise_a_jour = date_mise_a_jour;
        this.validateur = validateur;
        this.statut = statut;
        this.remarque = remarque;
    }

    // Empty constructor
    public Trace_Demande() {
    }

    // Getters and Setters
    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Date getDate_mise_a_jour() {
        return date_mise_a_jour;
    }

    public void setDate_mise_a_jour(Date date_mise_a_jour) {
        this.date_mise_a_jour = date_mise_a_jour;
    }

    public Utilisateur getValidateur() {
        return validateur;
    }

    public void setValidateur(Utilisateur validateur) {
        this.validateur = validateur;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

}
