package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.CaisseService;
import com.derteuffel.gestioncommande.entities.Caisse;
import com.derteuffel.gestioncommande.entities.Mouvement;
import com.derteuffel.gestioncommande.repositories.CaisseRepository;
import com.derteuffel.gestioncommande.repositories.MouvementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/caisse")
public class CaisseController {

    @Autowired
    private CaisseService caisseService;

    @Autowired
    private CaisseRepository caisseRepository;

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
        SimpleDateFormat sdf =new  SimpleDateFormat("yyyy-MM-dd");
        mouvement.setCreatedDate(sdf.format(new Date()));
        mouvement.setSoldeFinDollard(caisse.getSoldeFinMoisDollard());
        mouvement.setSoldeFinFranc(caisse.getSoldeFinMoisFranc());
        mouvement.setNumMouvement(mouvementRepository.findAllByCaisse_Id(caisse.getId()).size()+"/"+caisse.getMois()+"/"+caisse.getAnnee());
        mouvementRepository.save(mouvement);
        caisseService.update(caisse);
        return "redirect:/caisse/details/"+caisse.getId();

    }

    @GetMapping("/endMonth/{id}")
     public String endMonth(@PathVariable Long id){
        Caisse caisse = caisseService.getOne(id);
        caisse.setStatus(false);
        caisseService.update(caisse);
        Caisse caisse1 = new Caisse();
        caisse1.setStatus(true);
        caisse1.setMouvementMensuelFranc(0.0);
        caisse1.setMouvementMensuelDollard(0.0);
        caisse1.setSoldeDebutMoisFranc(caisse.getSoldeFinMoisFranc());
        caisse1.setSoldeDebutMoisDollard(caisse.getSoldeFinMoisDollard());
        caisse1.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc());
        caisse1.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard());
        caisseService.save(caisse1);

        return "redirect:/caisse/details/"+caisse1.getId();
     }


     @GetMapping("/mouvement/update/{id}")
     public String updateFormMouvement(@PathVariable Long id, Model model){

        Mouvement mouvement = mouvementRepository.getOne(id);
        model.addAttribute("mouvement", mouvement);

        return "caisse/mouvementU";
     }

     @PostMapping("/mouvement/update/{id}")
     public String updateMouvement(Mouvement mouvement, @PathVariable Long id, String taux, String devise,
                                   String montant, RedirectAttributes redirectAttributes){
        Mouvement existedMouvement = mouvementRepository.getOne(id);

        Caisse caisse = existedMouvement.getCaisse();
         existedMouvement.setType(mouvement.getType());
         existedMouvement.setLibelle(mouvement.getLibelle());
        if (!devise.isEmpty() && devise.equals("CDF")){
            if (!taux.isEmpty() && !montant.isEmpty()) {

            if (mouvement.getType().equals("ENTRER")){

                    caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() - existedMouvement.getMontantDollard() + (Double.parseDouble(montant) / Integer.parseInt(taux)));
                    caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() - existedMouvement.getMontantFranc() + Double.parseDouble(montant));
                    caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() - existedMouvement.getMontantFranc() + Double.parseDouble(montant));
                    caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() - existedMouvement.getMontantDollard() + (Double.parseDouble(montant) / Integer.parseInt(taux)));

            }else {
                    caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() + existedMouvement.getMontantDollard() - (Double.parseDouble(montant) / Integer.parseInt(taux)));
                    caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() + existedMouvement.getMontantFranc() - Double.parseDouble(montant));
                    caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() + existedMouvement.getMontantFranc() - Double.parseDouble(montant));
                    caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() + existedMouvement.getMontantDollard() - (Double.parseDouble(montant) / Integer.parseInt(taux)));

            }

            existedMouvement.setMontantFranc(Double.parseDouble(montant));
            existedMouvement.setMontantDollard(Double.parseDouble(montant)/Integer.parseInt(taux));
            existedMouvement.setSoldeFinDollard(caisse.getSoldeFinMoisDollard());
            existedMouvement.setSoldeFinFranc(caisse.getSoldeFinMoisFranc());
            }else {
                System.out.println("same value");
            }
        }else if (!devise.isEmpty() && devise.equals("USD")){
            if (!taux.isEmpty() && !montant.isEmpty()) {
                if (mouvement.getType().equals("ENTRER")) {
                    System.out.println("Je suis dedans ");
                    System.out.println(caisse.getMois());
                    System.out.println(caisse.getMouvementMensuelDollard());
                    System.out.println(existedMouvement.getMontantDollard());
                    System.out.println(montant);
                    caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() - existedMouvement.getMontantDollard() + (Double.parseDouble(montant)));
                    caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() - existedMouvement.getMontantFranc() + (Double.parseDouble(montant) * Integer.parseInt(taux)));
                    caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() - existedMouvement.getMontantFranc() + (Double.parseDouble(montant)) * Integer.parseInt(taux));
                    caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() - existedMouvement.getMontantDollard() + (Double.parseDouble(montant)));
                } else {
                    caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() + existedMouvement.getMontantDollard() - Double.parseDouble(montant));
                    caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() + existedMouvement.getMontantFranc() - (Double.parseDouble(montant) * Integer.parseInt(taux)));
                    caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() + existedMouvement.getMontantFranc() - (Double.parseDouble(montant) * Integer.parseInt(taux)));
                    caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() + existedMouvement.getMontantDollard() - Double.parseDouble(montant));
                }

                existedMouvement.setMontantFranc(Double.parseDouble(montant) * Integer.parseInt(taux));
                existedMouvement.setMontantDollard(Double.parseDouble(montant));
                existedMouvement.setSoldeFinDollard(caisse.getSoldeFinMoisDollard());
                existedMouvement.setSoldeFinFranc(caisse.getSoldeFinMoisFranc());
            }else {
                System.out.println("same value");
            }
        }else {
           redirectAttributes.addFlashAttribute("message", "Les elements ne sont pas reuniepour faire cette mise a jours");
           return "redirect:/caisse/mouvement/update/"+existedMouvement.getId();
        }

        caisseRepository.save(caisse);
        mouvementRepository.save(existedMouvement);
        return "redirect:/caisse/details/"+caisse.getId();

     }

     @PostMapping("/mouvement/search/{id}")
         public String searchByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date searchDate, @PathVariable Long id,
                                Model model){

         System.out.println(searchDate);
        Caisse caisse = caisseRepository.getOne(id);
        Double montantTotalDollars = 0.0;
        String goodDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        goodDate = sdf.format(searchDate);
        Double montantTotalFranc = 0.0;
        List<Mouvement> mouvements = mouvementRepository.findAllByCreatedDateAndCaisse_Id(goodDate, caisse.getId());
        for (Mouvement mouvement : mouvements){
            if (mouvement.getType().equals("ENTRER")){
                montantTotalDollars =+ mouvement.getMontantDollard();
                montantTotalFranc =+ mouvement.getMontantFranc();
            }else {
                montantTotalDollars =- mouvement.getMontantDollard();
                montantTotalFranc =- mouvement.getMontantFranc();
            }
        }

         System.out.println(montantTotalDollars);
         System.out.println(montantTotalFranc);
        model.addAttribute("lists", mouvements);
        model.addAttribute("caisse", caisse);
        model.addAttribute("date", searchDate);
        model.addAttribute("soldeDollars", montantTotalDollars);
        model.addAttribute("soldeFranc", montantTotalFranc);
        return "caisse/search";
     }
}
