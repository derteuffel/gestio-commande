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

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");


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
            return articleRepository.findAllByCommande_CommandeIdOrderByArticleIdDesc(commandeId);
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

    public  void  save(Article s, Long commandId, String buyedPrice) {


        try {
            s.setCodeArticle("ART"+simpleDateFormat.format(new Date())+(articleRepository.findAll().size()+1));
            Caisse caisse = caisseRepository.findByMoisAndAnneeAndStatus(monthFormat.format(new Date()), yearFormat.format(new Date()), true);

            AddedProduct existAddedProduct = addedProductRepository.getOne(Long.parseLong(buyedPrice.split("_")[1]));
            s.setBuyedPrice(Double.parseDouble(buyedPrice.split("_")[0]));
            Product product = existAddedProduct.getProduct();
            System.out.println();
            System.out.println(product.getName());
            if (product != null){
                System.out.println("Je suis ici : "+product.getName());
                s.setProductCode(product.getProductCode());
            }

            Commande commande = commandeRepository.getOne(commandId);
            s.setCommande(commande);
            s.setMonnaie(existAddedProduct.getDevise());
            s.setBuyedPrice(existAddedProduct.getUnitCost());
            s.setActivate(false);

            if (existAddedProduct.getDevise().equals(EAMonaie.CDF.name())){
                s.setTotalBuyedCDF(s.getBuyedPrice() * s.getQuantity());
                s.setTotalBuyedUSD((s.getBuyedPrice() / commande.getTauxJour()) * s.getQuantity());
            }else {
                s.setTotalBuyedUSD(s.getBuyedPrice() * s.getQuantity());
                s.setTotalBuyedCDF((s.getBuyedPrice() * commande.getTauxJour()) * s.getQuantity());
            }

            commande.setQuantity(articleRepository.findAllByCommande_CommandeIdOrderByArticleIdDesc(commandId).size() + 1);

            Mouvement mouvement = new Mouvement();
            mouvement.setType("ENTRER");
            mouvement.setMilieu("IMPRIMERIE");
            mouvement.setLibelle(s.getName());
            if (s.getMonnaie().equals(EAMonaie.CDF.toString())) {
                s.setTotalSellCDF(s.getSellPrice() * s.getQuantity());
                s.setTotalSellUSD(s.getTotalSellCDF() / commande.getTauxJour());
                mouvement.setMontantFranc(s.getTotalSellCDF());
                mouvement.setMontantDollard(s.getTotalSellUSD());
            } else {
                s.setTotalSellUSD(s.getQuantity() * s.getSellPrice());
                s.setTotalSellCDF(s.getTotalSellUSD() * commande.getTauxJour());
                mouvement.setMontantDollard(s.getTotalSellUSD());
                mouvement.setMontantFranc(s.getTotalSellCDF());
            }


            commande.setAmountCDF(commande.getAmountCDF() + s.getTotalSellCDF());
            commande.setAmountUSD(commande.getAmountUSD() + s.getTotalSellUSD());
            commande.setNbreArticle(commande.getNbreArticle() + 1);
            commande.setQuantity(commande.getQuantity() + s.getQuantity());

            System.out.println("Test to print" + s.getCodeArticle());
            Article savedArticle = articleRepository.save(s);
            mouvement.setArticleCode(savedArticle.getCodeArticle());
            AddedProduct addedProduct = new AddedProduct();
            addedProduct.setValide(false);
            addedProduct.setActionType("RETRAIT");
            addedProduct.setDescription("Commande d'un produit depuis une commande");
            addedProduct.setDevise(s.getMonnaie());
            addedProduct.setQuantity(s.getQuantity());
            addedProduct.setTauxDuJour(1);
            addedProduct.setUnitCost(s.getBuyedPrice());
            addedProduct.setTotalCost(s.getBuyedPrice() * s.getQuantity());
            addedProduct.setAddedDate(sdf.format(new Date()));
            addedProduct.setProduct(product);
            addedProduct.setArticle(savedArticle);
            addedProductRepository.save(addedProduct);

            commandeRepository.save(commande);

            if (caisse != null) {


                mouvement.setCaisse(caisse);
                mouvement.setNumMouvement(("MVM" + caisse.getMois() + "" + caisse.getAnnee()+""+(mouvementRepository.findAllByCaisse_Id(caisse.getId()).size()+1)) + "/" + caisse.getMois() + "/" + caisse.getAnnee());

                caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() + mouvement.getMontantDollard());
                caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() + mouvement.getMontantFranc());
                caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() + mouvement.getMontantDollard());
                caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() + mouvement.getMontantFranc());
                mouvement.setSoldeFinFranc(caisse.getSoldeFinMoisFranc());
                mouvement.setSoldeFinDollard(caisse.getSoldeFinMoisDollard());
                caisseRepository.save(caisse);



            }else {
                Caisse caisse1 = new Caisse();
                caisse1.setAnnee(yearFormat.format(new Date()));
                caisse1.setMois(monthFormat.format(new Date()));
                caisse1.setSoldeFinMoisDollard(mouvement.getMontantDollard());
                caisse1.setSoldeFinMoisFranc(mouvement.getMontantFranc());
                caisse1.setMouvementMensuelDollard( mouvement.getMontantDollard());
                caisse1.setMouvementMensuelFranc( mouvement.getMontantFranc());
                caisse1.setStatus(true);
                caisseRepository.save(caisse1);
                mouvement.setSoldeFinFranc(caisse1.getSoldeFinMoisFranc());
                mouvement.setSoldeFinDollard(caisse1.getSoldeFinMoisDollard());
                mouvement.setCaisse(caisse1);
                mouvement.setNumMouvement("MVM" + caisse1.getMois() + "" + caisse1.getAnnee()+""+(mouvementRepository.findAllByCaisse_Id(caisse1.getId()).size()+1));
            }

            mouvementRepository.save(mouvement);

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
            Commande commande = article.getCommande();
            existedArticle.setDescription(article.getDescription());
            existedArticle.setQuantity(article.getQuantity());
            existedArticle.setSellPrice(article.getSellPrice());
            existedArticle.setDescription(article.getDescription());
            existedArticle.setName(article.getName());

            if (addedProduct != null){
                existedArticle.setMonnaie(addedProduct.getDevise());
                existedArticle.setBuyedPrice(addedProduct.getUnitCost());
                if (addedProduct.getDevise().equals(EAMonaie.CDF.name())){
                    existedArticle.setTotalBuyedCDF(existedArticle.getBuyedPrice() * article.getQuantity());
                    existedArticle.setTotalBuyedUSD((existedArticle.getBuyedPrice() / commande.getTauxJour()) * article.getQuantity());
                }else {
                    existedArticle.setTotalBuyedUSD(existedArticle.getBuyedPrice() * article.getQuantity());
                    existedArticle.setTotalBuyedCDF((existedArticle.getBuyedPrice() * commande.getTauxJour()) * article.getQuantity());
                }
            }else {
                existedArticle.setMonnaie(article.getMonnaie());
                existedArticle.setBuyedPrice(article.getBuyedPrice());
                if (article.getMonnaie().equals(EAMonaie.CDF.name())){
                    existedArticle.setTotalBuyedCDF(existedArticle.getBuyedPrice() * article.getQuantity());
                    existedArticle.setTotalBuyedUSD((existedArticle.getBuyedPrice() / commande.getTauxJour()) * article.getQuantity());
                }else {
                    existedArticle.setTotalBuyedUSD(existedArticle.getBuyedPrice() * article.getQuantity());
                    existedArticle.setTotalBuyedCDF((existedArticle.getBuyedPrice() * commande.getTauxJour()) * article.getQuantity());
                }
            }

            if (mouvement != null) {
                Caisse caisse = mouvement.getCaisse();
                // Remove  first old mouvement value in caisse
                caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() - mouvement.getMontantDollard());
                caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() - mouvement.getMontantFranc());
                caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() - mouvement.getMontantDollard());
                caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() - mouvement.getMontantFranc());

                commande.setQuantity(commande.getQuantity() - existedArticle.getQuantity() + article.getQuantity());
                if (article.getBuyedPrice() != 0.0) {
                    // Remove first old article value in commande
                    commande.setAmountCDF(commande.getAmountCDF() + existedArticle.getTotalBuyedCDF());
                    commande.setAmountUSD(commande.getAmountUSD() + existedArticle.getTotalBuyedUSD());
                    commande.setNbreArticle(commande.getNbreArticle() - 1);
                    commande.setQuantity(commande.getQuantity() + existedArticle.getQuantity());

                    if (article.getMonnaie().equals(EAMonaie.CDF.toString())) {
                        existedArticle.setTotalBuyedCDF(article.getBuyedPrice() * article.getQuantity());
                        existedArticle.setTotalBuyedUSD(article.getTotalBuyedCDF() / commande.getTauxJour());
                        mouvement.setMontantFranc(existedArticle.getTotalBuyedCDF());
                        mouvement.setMontantDollard(existedArticle.getTotalBuyedUSD());
                    } else {
                        existedArticle.setTotalBuyedUSD(article.getQuantity() * article.getBuyedPrice());
                        existedArticle.setTotalBuyedCDF(article.getTotalBuyedUSD() * commande.getTauxJour());
                        mouvement.setMontantDollard(existedArticle.getTotalBuyedUSD());
                        mouvement.setMontantFranc(existedArticle.getTotalBuyedCDF());
                    }

                    // Set new update value of article
                    commande.setAmountCDF(commande.getAmountCDF() + existedArticle.getTotalBuyedCDF());
                    commande.setAmountUSD(commande.getAmountUSD() + existedArticle.getTotalBuyedUSD());
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
                commandeRepository.save(commande);
                caisseRepository.save(caisse);
                mouvementRepository.save(mouvement);
            }
            return articleRepository.save(existedArticle);
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
