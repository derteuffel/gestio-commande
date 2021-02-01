package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CaisseService;
import com.derteuffel.gestioncommande.entities.Caisse;
import com.derteuffel.gestioncommande.entities.Mouvement;
import com.derteuffel.gestioncommande.repositories.MouvementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/caisse")
public class CaisseController {

    @Autowired
    private CaisseService caisseService;

    @Autowired
    private MouvementRepository mouvementRepository;

    @GetMapping("/lists")
    public String findAll(Model model){
        List<Caisse> caisses = caisseService.findAll();
        if (caisses.isEmpty()){
            Caisse newCaisse = new Caisse();
            newCaisse.setMouvementMensuelDollard(0.0);
            newCaisse.setMouvementMensuelFranc(0.0);
            newCaisse.setSoldeDebutMoisDollard(0.0);
            newCaisse.setSoldeDebutMoisFranc(0.0);
            newCaisse.setSoldeFinMoisDollard(0.0);
            newCaisse.setSoldeFinMoisFranc(0.0);
            newCaisse.setStatus(true);
            caisseService.save(newCaisse);
        }

        model.addAttribute("lists", caisses);
        return "caisse/lists";
    }


    @GetMapping("/details/{id}")
    public String getCaisse(@PathVariable Long id, Model model){

        Caisse caisse = caisseService.getOne(id);
        List<Mouvement> mouvements = mouvementRepository.findAllByCaisse_Id(id);
        model.addAttribute("caisse",caisse);
        model.addAttribute("lists",mouvements);
        model.addAttribute("mouvement",new Mouvement());
        return "caisse/detail";

    }

    @PostMapping("/mouvement/save/{id}")
    public String saveMouvement(@PathVariable Long id, Mouvement mouvement, Model model, String montant, String taux, String devise){

        Caisse caisse = caisseService.getOne(id);

        if (devise.equals("CDF")){
            mouvement.setMontantFranc(Double.parseDouble(montant));
            mouvement.setMontantDollard(Double.parseDouble(montant)/Integer.parseInt(taux));

        }else {
            mouvement.setMontantDollard(Double.parseDouble(montant));
            mouvement.setMontantFranc(Double.parseDouble(montant)*Integer.parseInt(taux));
        }

        if (mouvement.getType().equals("ENTRER")) {
            caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() + mouvement.getMontantFranc());
            caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() + mouvement.getMontantDollard());
            caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() + mouvement.getMontantFranc());
            caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() + mouvement.getMontantDollard());
        }else {
            caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() - mouvement.getMontantFranc());
            caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() - mouvement.getMontantDollard());
            caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() - mouvement.getMontantFranc());
            caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() - mouvement.getMontantDollard());
        }

        mouvement.setCaisse(caisse);
        mouvement.setSoldeFinDollard(caisse.getSoldeFinMoisDollard());
        mouvement.setSoldeFinFranc(caisse.getSoldeFinMoisFranc());
        mouvement.setNumMouvement(mouvementRepository.findAllByCaisse_Id(caisse.getId()).size()+"/"+caisse.getMois()+"/"+caisse.getAnnee());
        mouvementRepository.save(mouvement);
        caisseService.update(caisse);
        return "redirect:/caisse/details/"+caisse.getId();

    }
}
