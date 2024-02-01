package stage.perfectionnement.cni.municipalite.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import stage.perfectionnement.cni.municipalite.model.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Long> {



   @Query("SELECT t.demande.date_saisie as date_saisie, t.demande.text as text, t.remarque as remarque, t.statut as statut, t.demande.id_demande as id_demande ,t.demande.nature_demande as nature_demande,t.demande.signature as signature, t.demande.date_signature as date_signature, t.demande.date_deliberation as date_deliberation, t.demande.annee_demande as annee_demande, t.demande.date_demande as date_demande, t.demande.num_demande as num_demande, t.demande.id_agent.id_utilisateur as id_agent, t.demande.objet as objet ,t.demande.type_demande as type_demande  FROM Trace_Demande t where t.demande.id_agent.id_utilisateur = :agentId and t.date_mise_a_jour=(SELECT tt.date_mise_a_jour FROM Trace_Demande tt where tt.demande= t.demande order by tt.date_mise_a_jour desc limit 1) order by t.date_mise_a_jour desc")
    List<Map<String, Object>> findByAgentId(@Param("agentId") Long agentId);

    @Query("SELECT t.demande.date_saisie as date_saisie, t.demande.text as text, t.remarque as remarque, t.statut as statut, t.demande.id_demande as id_demande ,t.demande.nature_demande as nature_demande,t.demande.signature as signature, t.demande.date_signature as date_signature, t.demande.date_deliberation as date_deliberation, t.demande.annee_demande as annee_demande, t.demande.date_demande as date_demande, t.demande.num_demande as num_demande, t.demande.id_agent.id_utilisateur as id_agent, t.demande.objet as objet ,t.demande.type_demande as type_demande  FROM Trace_Demande t where t.demande.id_agent.commune.num=:communeId and t.date_mise_a_jour=(SELECT tt.date_mise_a_jour FROM Trace_Demande tt where tt.demande= t.demande order by tt.date_mise_a_jour desc limit 1) order by t.date_mise_a_jour desc")
    List<Map<String, Object>> findByCommune(@Param("communeId") int CommuneId);

    @Query("SELECT t.demande.date_saisie as date_saisie, t.demande.text as text, t.remarque as remarque, t.statut as statut, t.demande.id_demande as id_demande ,t.demande.nature_demande as nature_demande,t.demande.signature as signature, t.demande.date_signature as date_signature, t.demande.date_deliberation as date_deliberation, t.demande.annee_demande as annee_demande, t.demande.date_demande as date_demande, t.demande.num_demande as num_demande, t.demande.id_agent.id_utilisateur as id_agent, t.demande.objet as objet ,t.demande.type_demande as type_demande  FROM Trace_Demande t where t.demande.id_demande = :Id and t.date_mise_a_jour=(SELECT tt.date_mise_a_jour FROM Trace_Demande tt where tt.demande= t.demande order by tt.date_mise_a_jour desc limit 1) order by t.date_mise_a_jour desc")
    Map<String, Object> findDemandeById(@Param("Id") long Id);

    @Query("SELECT t.demande.date_saisie as date_saisie, t.demande.text as text, t.remarque as remarque, t.statut as statut, t.demande.id_demande as id_demande ,t.demande.nature_demande as nature_demande,t.demande.signature as signature, t.demande.date_signature as date_signature, t.demande.date_deliberation as date_deliberation, t.demande.annee_demande as annee_demande, t.demande.date_demande as date_demande, t.demande.num_demande as num_demande, t.demande.id_agent.id_utilisateur as id_agent, t.demande.objet as objet ,t.demande.type_demande as type_demande  FROM Trace_Demande t where  t.date_mise_a_jour=(SELECT tt.date_mise_a_jour FROM Trace_Demande tt where tt.demande= t.demande order by tt.date_mise_a_jour desc limit 1) order by t.date_mise_a_jour desc")
    List<Map<String, Object>> findDemandes();

}