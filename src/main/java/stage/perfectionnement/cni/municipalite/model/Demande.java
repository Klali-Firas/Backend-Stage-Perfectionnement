package stage.perfectionnement.cni.municipalite.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "demande")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_demande;

    @Column(name = "type_demande")
    private String type_demande;

    @Column(name = "annee_demande")
    private int annee_demande;

    @Column(name = "date_demande")
    private Date date_demande;

    @Column(name = "num_demande")
    private int num_demande;

    @ManyToOne
    @JoinColumn(name = "id_agent", referencedColumnName = "id_utilisateur")
    private Utilisateur id_agent;

    @Column(name = "date_saisie")
    private Date date_saisie;

    @Column(name = "objet")
    private String objet;

    // @OneToMany(mappedBy = "demande", fetch = FetchType.LAZY, cascade =
    // CascadeType.ALL)
    // private List<Trace_Demande> traceDemandes;

    @Column(name = "date_deliberation")
    private Date date_deliberation;

    @Column(name = "nature_demande")
    private String nature_demande;

    @Column(name = "signature")
    private String signature;

    @Column(name = "text", columnDefinition = "TEXT", length = 4000)
    private String text;

    @Column(name = "date_signature")
    private Date date_signature;

    public Demande() {
        // Default constructor
    }

    public Demande(String type_demande, int annee_demande, Date date_demande, int num_demande, Utilisateur id_agent,
            Date date_saisie, String objet, Date date_deliberation, String nature_demande, String signature,
            Date date_signature, String text) {
        this.type_demande = type_demande;
        this.annee_demande = annee_demande;
        this.date_demande = date_demande;
        this.num_demande = num_demande;
        this.id_agent = id_agent;
        this.date_saisie = date_saisie;
        this.objet = objet;
        this.date_deliberation = date_deliberation;
        this.nature_demande = nature_demande;
        this.signature = signature;
        this.date_signature = date_signature;
        this.text = text;
    }

    public Utilisateur getId_agent() {
        return id_agent;
    }

    public void setId_agent(Utilisateur id_agent) {
        this.id_agent = id_agent;
    }

    public Long getId() {
        return id_demande;
    }

    public String getType_demande() {
        return type_demande;
    }

    public void setType_demande(String type_demande) {
        this.type_demande = type_demande;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAnnee_demande() {
        return annee_demande;
    }

    public void setAnnee_demande(int annee_demande) {
        this.annee_demande = annee_demande;
    }

    public Date getDate_demande() {
        return date_demande;
    }

    public void setDate_demande(Date date_demande) {
        this.date_demande = date_demande;
    }

    public int getNum_demande() {
        return num_demande;
    }

    public void setNum_demande(int num_demande) {
        this.num_demande = num_demande;
    }

    public Date getDate_deliberation() {
        return date_deliberation;
    }

    public void setDate_deliberation(Date date_deliberation) {
        this.date_deliberation = date_deliberation;
    }

    public String getNature_demande() {
        return nature_demande;
    }

    public void setNature_demande(String nature_demande) {
        this.nature_demande = nature_demande;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Date getDate_signature() {
        return date_signature;
    }

    public void setDate_signature(Date date_signature) {
        this.date_signature = date_signature;
    }

    public Date getDate_saisie() {
        return date_saisie;
    }

    public void setDate_saisie(Date date_saisie) {
        this.date_saisie = date_saisie;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

}
