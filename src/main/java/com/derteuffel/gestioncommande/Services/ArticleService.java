package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.*;
import com.derteuffel.gestioncommande.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommandeRepository commandeRepository;
    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CaisseRepository caisseRepository;
    @Autowired
    private AddedProductRepository addedProductRepository;


    public List<Article> findAll(){
        try {
            return articleRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public <S extends Article> List<S> saveAll(List<S> iterable, Long commandeId) {

        try {
            Commande commande = commandeRepository.getOne(commandeId);
            for (int i = 0; i <= iterable.size(); i++) {
                iterable.get(i).setCommande(commande);
            }
            return articleRepository.saveAll(iterable);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public Article getOne(Long articleId) {
        try {
            return articleRepository.getOne(articleId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Article> findAllByCommande_CommandeId(Long commandeId) {
        try {
            return articleRepository.findAllByCommande_CommandeId(commandeId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Article> findAllByProductCode(String productCode) {
        try {
            return articleRepository.findAllByProductCode(productCode);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public  Article  save(Article s, Long commandId) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        try {
            s.setCodeArticle("ART"+simpleDateFormat.format(new Date())+(articleRepository.findAll().size()+1));
            Caisse caisse = caisseRepository.findByStatus(true);
            Product product = productRepository.findByProductCode(s.getName().split("_")[1]);
            System.out.println(s.getName().split("_")[1]);
            System.out.println(product.getName());
            if (product != null){
                System.out.println("Je suis ici : "+product.getName());
                s.setProductCode(product.getProductCode());
            }

            Mouvement mouvement = new Mouvement();
            mouvement.setType("ENTRER");
            mouvement.setCaisse(caisse);
            mouvement.setLibelle(s.getName());
            mouvement.setNumMouvement(mouvementRepository.findAllByCaisse_Id(caisse.getId()).size() + "/" + caisse.getMois() + "/" + caisse.getAnnee());
            Commande commande = commandeRepository.getOne(commandId);
            s.setCommande(commande);

            commande.setQuantity(articleRepository.findAllByCommande_CommandeId(commandId).size() + 1);
            if (s.getMonnaie().equals(EAMonaie.CDF.toString())) {
                s.setTotalCDF(s.getPrice() * s.getQuantity());
                s.setTotalUSD(s.getTotalCDF() / commande.getTauxJour());
                mouvement.setMontantFranc(s.getTotalCDF());
                mouvement.setMontantDollard(s.getTotalUSD());
            } else {
                s.setTotalUSD(s.getQuantity() * s.getPrice());
                s.setTotalCDF(s.getTotalUSD() * commande.getTauxJour());
                mouvement.setMontantDollard(s.getTotalUSD());
                mouvement.setMontantFranc(s.getTotalCDF());
            }

            caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() + mouvement.getMontantDollard());
            caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() + mouvement.getMontantFranc());
            caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() + mouvement.getMontantDollard());
            caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() + mouvement.getMontantFranc());
            mouvement.setSoldeFinFranc(caisse.getSoldeFinMoisFranc());
            mouvement.setSoldeFinDollard(caisse.getSoldeFinMoisDollard());

            commande.setAmountCDF(commande.getAmountCDF() + s.getTotalCDF());
            commande.setAmountUSD(commande.getAmountUSD() + s.getTotalUSD());
            commande.setNbreArticle(commande.getNbreArticle() + 1);
            commande.setQuantity(commande.getQuantity() + s.getQuantity());

            System.out.println("Test to print"+s.getCodeArticle());
            Article savedArticle = articleRepository.save(s);
            AddedProduct addedProduct = new AddedProduct();
            addedProduct.setValide(false);
            addedProduct.setActionType("RETRAIT");
            addedProduct.setDescription("Commande d'un produit depuis une commande");
            addedProduct.setDevise(s.getMonnaie());
            addedProduct.setQuantity(s.getQuantity());
            addedProduct.setTauxDuJour(1);
            addedProduct.setUnitCost(s.getPrice());
            addedProduct.setTotalCost(s.getPrice() * s.getQuantity());
            addedProduct.setAddedDate(sdf.format(new Date()));
            addedProduct.setProduct(product);
            addedProduct.setArticle(savedArticle);
            addedProductRepository.save(addedProduct);
            commandeRepository.save(commande);
            caisseRepository.save(caisse);
            mouvement.setArticleCode(savedArticle.getCodeArticle());
            mouvementRepository.save(mouvement);

            return savedArticle;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public Article update(Article article, Long id){
        try {
            Article existedArticle = articleRepository.getOne(id);
            Mouvement mouvement = mouvementRepository.findByArticleCode(article.getCodeArticle());
            AddedProduct addedProduct = addedProductRepository.findByArticle_ArticleId(existedArticle.getArticleId());
            addedProduct.setDevise(article.getMonnaie());
            addedProduct.setQuantity(article.getQuantity());
            addedProduct.setUnitCost(article.getPrice());
            addedProduct.setTotalCost(article.getPrice() * article.getQuantity());
            Commande commande = article.getCommande();
            addedProductRepository.save(addedProduct);
            Caisse caisse = mouvement.getCaisse();
            // Remove  first old mouvement value in caisse
            caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() - mouvement.getMontantDollard());
            caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() - mouvement.getMontantFranc());
            caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() - mouvement.getMontantDollard());
            caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() - mouvement.getMontantFranc());

            commande.setQuantity(commande.getQuantity() - existedArticle.getQuantity() + article.getQuantity());
            if (article.getPrice() != 0.0) {
                // Remove first old article value in commande
                commande.setAmountCDF(commande.getAmountCDF() + existedArticle.getTotalCDF());
                commande.setAmountUSD(commande.getAmountUSD() + existedArticle.getTotalUSD());
                commande.setNbreArticle(commande.getNbreArticle() - 1);
                commande.setQuantity(commande.getQuantity() + existedArticle.getQuantity());

                if (article.getMonnaie().equals(EAMonaie.CDF.toString())) {
                    existedArticle.setTotalCDF(article.getPrice() * article.getQuantity());
                    existedArticle.setTotalUSD(article.getTotalCDF() / commande.getTauxJour());
                    mouvement.setMontantFranc(existedArticle.getTotalCDF());
                    mouvement.setMontantDollard(existedArticle.getTotalUSD());
                } else {
                    existedArticle.setTotalUSD(article.getQuantity() * article.getPrice());
                    existedArticle.setTotalCDF(article.getTotalUSD() * commande.getTauxJour());
                    mouvement.setMontantDollard(existedArticle.getTotalUSD());
                    mouvement.setMontantFranc(existedArticle.getTotalCDF());
                }

                // Set new update value of article
                commande.setAmountCDF(commande.getAmountCDF() + existedArticle.getTotalCDF());
                commande.setAmountUSD(commande.getAmountUSD() + existedArticle.getTotalUSD());
                commande.setNbreArticle(commande.getNbreArticle() + 1);
                commande.setQuantity(commande.getQuantity() + existedArticle.getQuantity());
            }

            //Set new mouvement value in caisse
            caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() + mouvement.getMontantDollard());
            caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() + mouvement.getMontantFranc());
            caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() + mouvement.getMontantDollard());
            caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() + mouvement.getMontantFranc());

            mouvement.setSoldeFinFranc(caisse.getSoldeFinMoisFranc());
            mouvement.setSoldeFinDollard(caisse.getSoldeFinMoisDollard());
            Article savedArticle = articleRepository.save(existedArticle);
            commandeRepository.save(commande);
            caisseRepository.save(caisse);
            mouvementRepository.save(mouvement);
            return savedArticle;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Ooops, Une erreur est survenue");
        }
    }

    public long count() {
        return articleRepository.count();
    }

    public void deleteById(Long articleId) {
        try {
            articleRepository.deleteById(articleId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
