package stage.perfectionnement.cni.municipalite.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import stage.perfectionnement.cni.municipalite.model.Commune;
import stage.perfectionnement.cni.municipalite.model.Utilisateur;

public interface UtilisateursRepository extends JpaRepository<Utilisateur, Long> {

    List<Utilisateur> findByNomContaining(String nom);

    Utilisateur findByLogin(String Login);

    List<Utilisateur> findByProfile(String Profile);

    List<Utilisateur> findByCommune(Commune commune);

    List<Utilisateur> findByProfileAndCommune(String string, Commune communeNum);

}