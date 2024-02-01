package stage.perfectionnement.cni.municipalite.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.micrometer.common.lang.NonNull;
import stage.perfectionnement.cni.municipalite.model.Demande;
import stage.perfectionnement.cni.municipalite.model.Statut;
import stage.perfectionnement.cni.municipalite.repository.DemandeRepository;
import stage.perfectionnement.cni.municipalite.repository.StatutRepository;
import stage.perfectionnement.cni.municipalite.repository.Trace_DemandeRepository;
import stage.perfectionnement.cni.municipalite.repository.UtilisateursRepository;
import stage.perfectionnement.cni.municipalite.model.Trace_Demande;
import stage.perfectionnement.cni.municipalite.model.Utilisateur;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// @CrossOrigin(origins = "http://localhost:10533")
@RestController
@RequestMapping("/api/demandes")
public class DemandeController {

    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private Trace_DemandeRepository traceDemandeRepository;
    @Autowired
    private UtilisateursRepository utilisateurRepository;
    @Autowired
    private StatutRepository statutRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDemandeById(@PathVariable Long id) {
        try {

            Map<String, Object> demande = demandeRepository.findDemandeById(id);
            if ((!demande.isEmpty())) {

                return ResponseEntity.ok(demande);
            } else {
                return ResponseEntity.status(404).body(new Exception("Demande introuvable"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createDemande(@RequestBody Map<String, String> demande) {
        try {
            System.out.println(demande);
            // Save the utilisateur first
            Optional<Utilisateur> utilisateur = utilisateurRepository.findById(Long.parseLong(demande.get("id_agent")));
            if (!utilisateur.isPresent()) {
                return ResponseEntity.status(404).body(new Exception("Utilisateur introuvable"));
            }


            // Parse the date string into a Date object
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date = dateFormat.parse(demande.get("date_demande"));
            Date datedDeliberation = dateFormat.parse(demande.get("date_deliberation"));
            Date dateSignature = dateFormat.parse(demande.get("date_signature"));
            System.out.printf("here");

            // Save the demande
            Demande createdDemande = new Demande(demande.get("type_demande"),
                    Integer.parseInt(demande.get("annee_demande")),
                    date, Integer.parseInt(demande.get("num_demande")), utilisateur.get(), new Date(),
                    demande.get("objet"), datedDeliberation, demande.get("nature_demande"), demande.get("signature"),
                    dateSignature, demande.get("text").toString());
            createdDemande = demandeRepository.save(createdDemande);

            // Create a trace for the demande
            Trace_Demande trace = new Trace_Demande();
            trace.setDemande(createdDemande);

            // Save the statut first
            Optional<Statut> statut = statutRepository.findById((long) 1);
            if (!statut.isPresent()) {
                return ResponseEntity.status(404).body(new Exception("Statut introuvable"));
            }
            trace.setStatut(statut.get());

            trace.setDate_mise_a_jour(Date.from(new Date().toInstant()));
            trace.setRemarque("Demande créée");
            trace.setValidateur(null);
            // Set other trace properties

            // Save the trace
            traceDemandeRepository.save(trace);
            Map<String, Object> response = new HashMap<>();
            response.put("id_demande", createdDemande.getId());
            response.put("date_demande", createdDemande.getDate_demande());
            response.put("annee_demande", createdDemande.getAnnee_demande());
            response.put("statut", "En cours");
            response.put("type_demande", createdDemande.getType_demande());
            response.put("num_demande", createdDemande.getNum_demande());
            response.put("date_saisie", createdDemande.getDate_saisie());
            response.put("objet", createdDemande.getObjet());
            response.put("id_agent", createdDemande.getId_agent().getId());
            response.put("date_deliberation", createdDemande.getDate_deliberation());
            response.put("nature_demande", createdDemande.getNature_demande());
            response.put("signature", createdDemande.getSignature());
            response.put("date_signature", createdDemande.getDate_signature());
            response.put("text", createdDemande.getText());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteDemande(@NonNull @PathVariable Long id) {
//        try {
//            demandeRepository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("")
    public ResponseEntity<?> getDemandes(@RequestParam(required = false) String type,
            @RequestParam(required = false) String annee,
            @RequestParam(required = false) String num,
            @RequestParam(required = false) String date) {
        try {
            List<Map<String, Object>> demandes = demandeRepository.findDemandes();

//
//            if (num != null) {
//                // Filter demandes by num
//                demandes = demandes.stream()
//                        .filter(d -> String.valueOf(d.getNum_demande()).equals(num))
//                        .collect(Collectors.toList());
//            }
//            if (type != null) {
//                // Filter demandes by type
//                demandes = demandes.stream()
//                        .filter(d -> d.getType_demande().equals(type))
//                        .collect(Collectors.toList());
//            }
//
//            if (annee != null) {
//                // Filter demandes by annee
//
//                demandes = demandes.stream()
//                        .filter(d -> (String.valueOf(d.getAnnee_demande())).equals(annee))
//                        .collect(Collectors.toList());
//            }
//
//            if (date != null) {
//                // Filter demandes by date
//                demandes = demandes.stream()
//                        .filter(d -> {
//                            System.out.println(d.getDate_demande().toString());
//                            return d.getDate_demande().toString().equals(date);
//                        })
//                        .collect(Collectors.toList());
//            }

            return ResponseEntity.ok(demandes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateDemande(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        try {
            System.out.println(body);
            Map<String, Object> demandeUpdate = (Map<String, Object>) body.get("demande");
            // return new ResponseEntity<>("kk", HttpStatus.OK);
            Optional<Demande> optionalDemande = demandeRepository.findById(id);
            if (optionalDemande.isPresent()) {
                Demande demande = optionalDemande.get();

                // Verify the existence of validateur (utilisateur)
                Integer validateurId = (Integer) body.get("validateur");
                Optional<Utilisateur> optionalValidateur = utilisateurRepository.findById(validateurId.longValue());
                if (optionalValidateur.isPresent()) {
                    Utilisateur validateur = optionalValidateur.get();



                    Optional<Statut> statut = statutRepository
                            .findById(((Integer) ((Map<String, Object>) demandeUpdate.get("statut")).get("id_statut"))
                                    .longValue());
                    if (!statut.isPresent()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Exception("Statut introuvable!"));
                    }


//                    envoyer par agent
//                    if(statut.get().getId_statut() == 1 ){
//                        if (!validateur.getProfile().equals("agent")) {
//                            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                                    .body(new Exception("Tu na pas le droit de modifier ce demande!"));
//                        }                    }
//
//                    // envoyer par Validateur
//                    if(statut.get().getId_statut() == 3|| statut.get().getId_statut() == 4){
//                        if (!validateur.getProfile().equals("validateur")) {
//                            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                                    .body(new Exception("Tu na pas le droit de modifier ce demande!"));
//                        }
//                        traceDemande.setValidateur(validateur);
//                    }


                    // Create trace_demande and save it
                    Trace_Demande traceDemande = new Trace_Demande();
                    traceDemande.setDemande(demande);
                    traceDemande.setStatut(statut.get());
                    traceDemande.setRemarque((String) demandeUpdate.get("remarque"));
                    traceDemande.setDate_mise_a_jour(new Date());
                    traceDemande.setValidateur(validateur.getProfile().equals("validateur")?validateur:null);
                    traceDemandeRepository.save(traceDemande);
                    demande.setText(demandeUpdate.get("text").toString());
                    demande.setObjet(demandeUpdate.get("objet").toString());
                    demandeRepository.save(demande);

                    return ResponseEntity.status(HttpStatus.CREATED).body(traceDemande);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Exception("Validateur introuvable!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Exception("Demande introuvable!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }

    }

    @DeleteMapping("/{id}/annuler")
    public ResponseEntity<?> annulerDemande(@PathVariable Long id) {
        try {
            Optional<Demande> optionalDemande = demandeRepository.findById(id);
            if (optionalDemande.isPresent()) {
                Demande demande = optionalDemande.get();

                // Create trace_demande with statut number 6 and save it
                Optional<Statut> statut = statutRepository.findById(6L);
                if (!statut.isPresent()) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Exception("Statut introuvable!"));
                }
                Trace_Demande traceDemande = new Trace_Demande();
                traceDemande.setDemande(demande);
                traceDemande.setStatut(statut.get());
                traceDemande.setDate_mise_a_jour(new Date());
                traceDemande.setRemarque("Demande annulée");
                traceDemandeRepository.save(traceDemande);

                return ResponseEntity.status(204).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Exception("Demande introuvable!"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    // done
    @GetMapping("/agent/{agentId}")
    public ResponseEntity<?> findDemandesByAgentId(@PathVariable Long agentId) {
        try {
            List<Map<String, Object>> demandes = demandeRepository.findByAgentId(agentId);
            if (demandes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Exception("Aucune demande trouvée pour cet agent"));
            }
            return ResponseEntity.ok(demandes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/commune/{communeId}")
    public ResponseEntity<?> getDemandesbyCommuneId(@PathVariable int communeId) {
        try {
            List<Map<String, Object>> demandes = demandeRepository.findByCommune(communeId);
            if (demandes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Exception("Aucune demande trouvée pour cette commune"));
            }
            return ResponseEntity.ok(demandes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
