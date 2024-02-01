package stage.perfectionnement.cni.municipalite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stage.perfectionnement.cni.municipalite.model.Commune;

public interface CommuneRepository extends JpaRepository<Commune, Integer> {
    Commune findByNum(int num);
}
