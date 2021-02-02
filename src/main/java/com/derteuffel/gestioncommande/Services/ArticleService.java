package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.*;
import com.derteuffel.gestioncommande.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private CaisseRepository caisseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public List<Article> findAll(){
        return articleRepository.findAll();
    }

    public <S extends Article> List<S> saveAll(List<S> iterable, Long commandeId) {

        Commande commande = commandeRepository.getOne(commandeId);
        for(int i=0; i<=iterable.size();i++){
            iterable.get(i).setCommande(commande);
        }
        return articleRepository.saveAll(iterable);
    }

    public Article getOne(Long articleId) {
        return articleRepository.getOne(articleId);
    }

    public List<Article> findAllByCommande_CommandeId(Long commandeId) {
        return articleRepository.findAllByCommande_CommandeId(commandeId);
    }

    public  Article  save(Article s, Long commandId) {

        Caisse caisse = caisseRepository.findByStatus(true);
        Mouvement mouvement = new Mouvement();
        mouvement.setType("ENTRER");
        mouvement.setCaisse(caisse);
        mouvement.setLibelle(s.getName());
        mouvement.setNumMouvement(mouvementRepository.findAllByCaisse_Id(caisse.getId()).size()+"/"+caisse.getMois()+"/"+caisse.getAnnee());
        Commande commande = commandeRepository.getOne(commandId);
        s.setCommande(commande);
        commande.setQuantity(articleRepository.findAllByCommande_CommandeId(commandId).size()+1);
        if (s.getMonnaie().equals(EAMonaie.CDF.toString())){
            s.setTotalCDF(s.getPrice() * s.getQuantity());
            s.setTotalUSD(s.getTotalCDF() / commande.getTauxJour());
            mouvement.setMontantFranc(s.getTotalCDF());
            mouvement.setMontantDollard(s.getTotalUSD());
        }else {
            s.setTotalUSD(s.getQuantity() * s.getPrice());
            s.setTotalCDF(s.getTotalUSD() * commande.getTauxJour());
            mouvement.setMontantDollard(s.getTotalUSD());
            mouvement.setMontantFranc(s.getTotalCDF());
        }

        caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard()+mouvement.getMontantDollard());
        caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc()+mouvement.getMontantFranc());
        caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard()+mouvement.getMontantDollard());
        caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc()+mouvement.getMontantFranc());
        mouvement.setSoldeFinFranc(caisse.getSoldeFinMoisFranc());
        mouvement.setSoldeFinDollard(caisse.getSoldeFinMoisDollard());

        commande.setAmountCDF(commande.getAmountCDF() + s.getTotalCDF());
        commande.setAmountUSD(commande.getAmountUSD() + s.getTotalUSD());
        commande.setNbreArticle(commande.getNbreArticle()+1);
        commandeRepository.save(commande);
        caisseRepository.save(caisse);
        mouvementRepository.save(mouvement);

        return articleRepository.save(s);
    }

    public long count() {
        return articleRepository.count();
    }

    public void deleteById(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    public List<Article> findAllByType(String type) {
        return articleRepository.findAllByType(type);
    }

    public List<Article> findAllByCategory(String category) {
        return articleRepository.findAllByCategory(category);
    }
}
