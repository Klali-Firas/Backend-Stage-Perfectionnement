package stage.perfectionnement.cni.municipalite.model;

import jakarta.persistence.*;

@Entity
@Table(name = "statut")
public class Statut {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_statut;

    @Column(name = "label")
    private String label;

    // @OneToMany(mappedBy = "statut")
    // private List<Trace_Demande> tarce_demande;

    // Constructor without id_statut
    public Statut(String label) {
        this.label = label;
        // this.tarce_demande = tarce_demande;
    }

    // Empty constructor
    public Statut() {
    }

    // Getters and Setters
    public Long getId_statut() {
        return id_statut;
    }

    public void setId_statut(Long id_statut) {
        this.id_statut = id_statut;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // public List<Trace_Demande> getTarce_demande() {
    // return tarce_demande;
    // }

    // public void setTarce_demande(List<Trace_Demande> tarce_demande) {
    // this.tarce_demande = tarce_demande;
    // }
}
