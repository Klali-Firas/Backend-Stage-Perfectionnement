package stage.perfectionnement.cni.municipalite.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import stage.perfectionnement.cni.municipalite.model.Commune;
import stage.perfectionnement.cni.municipalite.repository.CommuneRepository;
import stage.perfectionnement.cni.municipalite.repository.UtilisateursRepository;
import stage.perfectionnement.cni.municipalite.model.Utilisateur;

// @CrossOrigin(origins = "http://localhost:10533")
@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    UtilisateursRepository utilisateursRepository;
    @Autowired
    CommuneRepository communeRepository;

    @GetMapping("")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        try {
            List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();

            utilisateursRepository.findAll().forEach(utilisateurs::add);

            if (utilisateurs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUtilisateurById(@PathVariable("id") long id) {
        try{
        Optional<Utilisateur> utilisateurData = utilisateursRepository.findById(id);

        if (utilisateurData.isPresent()) {
            return new ResponseEntity<>(utilisateurData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Exception("Utilisatuer introuvable!"), HttpStatus.NOT_FOUND);
        }}catch(Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profile/{profile}")
    public ResponseEntity<?> getUtilisateurByProfile(@PathVariable("profile") String profile) {
        try {
            List<Utilisateur> utilisateurs = utilisateursRepository.findByProfile(profile);

            if (utilisateurs.isEmpty()) {
                return new ResponseEntity<>("Aucune utilisateur trouvé", HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createUtilisateur(@RequestBody Map<String, String> utilisateur) {
        try {
            int communeNum = Integer.parseInt(utilisateur.get("commune"));

            // Check if the Commune with the specified number exists
            Commune existingCommune = communeRepository.findByNum(communeNum);
            if (existingCommune == null) {
                // Commune not found, return an appropriate error response
                return new ResponseEntity<>("Commune with number " + communeNum + " not found.", HttpStatus.NOT_FOUND);
            }

            Utilisateur _utilisateur = utilisateursRepository.save(new Utilisateur(
                    utilisateur.get("nom"),
                    utilisateur.get("prenom"),
                    existingCommune,
                    utilisateur.get("profile"),
                    utilisateur.get("login"),
                    utilisateur.get("mdp")));

            return new ResponseEntity<>(_utilisateur, HttpStatus.CREATED);
        } catch (NumberFormatException e) {
            // Handle the case where the provided commune number is not a valid integer
            return new ResponseEntity<>("Invalid commune number format.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUtilisateur(@PathVariable("id") long id) {
        try {
            utilisateursRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/byCommune/{communeNum}")
    public List<Utilisateur> getUsersByCommune(@PathVariable Commune communeNum) {
        return utilisateursRepository.findByCommune(communeNum);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            // Check if login exists
            Optional<Utilisateur> utilisateurOptional = Optional
                    .ofNullable(utilisateursRepository.findByLogin(loginRequest.get("login")));
            if (utilisateurOptional.isEmpty()) {
                return ResponseEntity.status(404).body(new Exception("Utilisateur introuvable!"));
            }

            Utilisateur utilisateur = utilisateurOptional.get();

            // Check if mdp is correct
            if (!utilisateur.getMdp().equals(loginRequest.get("mdp"))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Exception("Mot de passe incorrect!"));
            }

            // Return utilisateur without password
            utilisateur.setMdp(null);
            return ResponseEntity.ok(utilisateur);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/agentsbycommune/{communeNum}")
    public ResponseEntity<?> getAgentsByCommune(@PathVariable int communeNum) {
        try {
            List<Utilisateur> agents = utilisateursRepository.findByProfileAndCommune("agent",
                    communeRepository.findByNum(communeNum));
            if (agents.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune Utilisateur trouvé!");
            }
            return ResponseEntity.ok(agents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
