package stage.perfectionnement.cni.municipalite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import stage.perfectionnement.cni.municipalite.model.Demande;
import stage.perfectionnement.cni.municipalite.model.Trace_Demande;

@Repository
public interface Trace_DemandeRepository extends JpaRepository<Trace_Demande, Long> {
    // @Query("SELECT t FROM Trace_Demande t JOIN FETCH t.demande WHERE t.demande =
    // :dd")
    // @EntityGraph(value = "graph.TraceDemande", type = EntityGraphType.FETCH)
    List<Trace_Demande> findByDemande(Demande demande);

    @Query("SELECT t FROM Trace_Demande t WHERE t.demande = :dd ORDER BY t.date_mise_a_jour DESC")
    Optional<List<Trace_Demande>> getLatestTrace(@Param("dd") Demande dd);

}
