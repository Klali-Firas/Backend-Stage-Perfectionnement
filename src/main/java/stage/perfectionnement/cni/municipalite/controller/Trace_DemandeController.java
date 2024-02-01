package stage.perfectionnement.cni.municipalite.controller;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import stage.perfectionnement.cni.municipalite.repository.DemandeRepository;
import stage.perfectionnement.cni.municipalite.repository.Trace_DemandeRepository;
import stage.perfectionnement.cni.municipalite.model.Trace_Demande;
import stage.perfectionnement.cni.municipalite.model.Demande;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/trace")
public class Trace_DemandeController {
    @Autowired
    Trace_DemandeRepository trace_DemandeRepository;
    @Autowired
    DemandeRepository demandeRepository;

    @GetMapping("/{demandeId}")
    @Transactional
    public ResponseEntity<?> getTraceByDemande(@PathVariable long demandeId) {
        try {
            Optional<Demande> demande = demandeRepository.findById(demandeId);
            if (demande.isPresent()) {
                List<Trace_Demande> traceList = trace_DemandeRepository.findByDemande(demande.get());

                List<Map<String, Object>> result = new ArrayList<>();
                for (Trace_Demande trace : traceList) {
                    Map<String, Object> traceMap = new HashMap<>();
                    traceMap.put("date_mise_a_jour", trace.getDate_mise_a_jour());
                    traceMap.put("demande", trace.getDemande().getId());
                    traceMap.put("remarque", trace.getRemarque());
                    traceMap.put("validateur", trace.getValidateur() != null ? trace.getValidateur().getId() : null);
                    traceMap.put("statut", trace.getStatut().getLabel());
                    result.add(traceMap);
                }

                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
