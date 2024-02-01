package stage.perfectionnement.cni.municipalite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import stage.perfectionnement.cni.municipalite.model.Statut;

public interface StatutRepository extends JpaRepository<Statut, Long> {

}
