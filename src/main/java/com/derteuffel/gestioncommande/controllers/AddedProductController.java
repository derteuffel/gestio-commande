package com.derteuffel.gestioncommande.controllers;

import com.derteuffel.gestioncommande.Services.ArticleService;
import com.derteuffel.gestioncommande.Services.CaisseService;
import com.derteuffel.gestioncommande.Services.CommandeService;
import com.derteuffel.gestioncommande.Services.ProductService;
import com.derteuffel.gestioncommande.entities.*;
import com.derteuffel.gestioncommande.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/added")
public class AddedProductController {

    @Autowired
    private AddedProductRepository addedProductRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private CaisseRepository caisseRepository;

    @Autowired
    private MouvementRepository mouvementRepository;

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");


    @PostMapping("/update/save/{productId}")
    public String saveAjout(AddedProduct addedProduct, @PathVariable Long productId){

        Product product = productService.getOne(productId);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        addedProduct.setAddedDate(sdf.format(new Date()));
        addedProduct.setActionType("AJOUT");
        addedProduct.setProduct(product);
        addedProduct.setValide(false);
        addedProductRepository.save(addedProduct);

        return "redirect:/produit/detail/"+product.getProductId();
    }
    @PostMapping("/update/save/retrait/{productId}")
    public String saveRetrait(AddedProduct addedProduct, @PathVariable Long productId){

        Product product = productService.getOne(productId);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        addedProduct.setAddedDate(sdf.format(new Date()));
        addedProduct.setActionType("RETRAIT");
        addedProduct.setValide(false);

        addedProduct.setProduct(product);
        addedProductRepository.save(addedProduct);

        return "redirect:/produit/detail/"+product.getProductId();
    }
    @GetMapping("/update/activate/{articleId}")
    public String updateAction(@PathVariable Long articleId){

        Article article = articleRepository.getOne(articleId);
        AddedProduct addedProduct = addedProductRepository.findByArticle_ArticleId(article.getArticleId());

        Product product = addedProduct.getProduct();
        Caisse caisse = caisseRepository.findByMoisAndAnneeAndStatus(monthFormat.format(new Date()), yearFormat.format(new Date()), true);
        addedProduct.setValide(true);


        article.setActivate(true);

            product.setQuantity(product.getQuantity() - addedProduct.getQuantity());

            Mouvement boutiqueMouvement = new Mouvement();
            boutiqueMouvement.setType("ENTRER");
            boutiqueMouvement.setMilieu("BOUTIQUE");
            boutiqueMouvement.setLibelle("Achat article "+addedProduct.getProduct().getName());
            if (addedProduct.getDevise().equals(EAMonaie.CDF.toString())) {
                boutiqueMouvement.setMontantFranc(addedProduct.getTotalCost());
                boutiqueMouvement.setMontantDollard(addedProduct.getTotalCost() / addedProduct.getTauxDuJour());
            } else {
                boutiqueMouvement.setMontantDollard(addedProduct.getTotalCost());
                boutiqueMouvement.setMontantFranc(addedProduct.getTotalCost() * addedProduct.getTauxDuJour());
            }

            if (caisse != null){
                boutiqueMouvement.setCaisse(caisse);
                boutiqueMouvement.setNumMouvement("MVM" + caisse.getMois() + "" + caisse.getAnnee()+""+(mouvementRepository.findAllByCaisse_Id(caisse.getId()).size()+1));

                caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() + boutiqueMouvement.getMontantDollard());
                caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() + boutiqueMouvement.getMontantFranc());
                caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() + boutiqueMouvement.getMontantDollard());
                caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() + boutiqueMouvement.getMontantFranc());
                boutiqueMouvement.setSoldeFinFranc(caisse.getSoldeFinMoisFranc());
                boutiqueMouvement.setSoldeFinDollard(caisse.getSoldeFinMoisDollard());
                caisseRepository.save(caisse);
            }else {
                Caisse caisse1 = new Caisse();
                caisse1.setAnnee(yearFormat.format(new Date()));
                caisse1.setMois(monthFormat.format(new Date()));

                caisse.setSoldeFinMoisDollard(boutiqueMouvement.getMontantDollard());
                caisse.setSoldeFinMoisFranc(boutiqueMouvement.getMontantFranc());
                caisse.setMouvementMensuelDollard( boutiqueMouvement.getMontantDollard());
                caisse.setMouvementMensuelFranc( boutiqueMouvement.getMontantFranc());
                caisse1.setStatus(true);
                caisseRepository.save(caisse1);
                boutiqueMouvement.setSoldeFinFranc(caisse1.getSoldeFinMoisFranc());
                boutiqueMouvement.setSoldeFinDollard(caisse1.getSoldeFinMoisDollard());
                boutiqueMouvement.setCaisse(caisse1);
                boutiqueMouvement.setNumMouvement("MVM" + caisse.getMois() + "" + caisse.getAnnee()+""+(mouvementRepository.findAllByCaisse_Id(caisse.getId()).size()+1));
            }
            mouvementRepository.save(boutiqueMouvement);



            articleRepository.save(article);
        productRepository.save(product);
        addedProductRepository.save(addedProduct);

        return "redirect:/produit/detail/"+product.getProductId();
    }

    @PostMapping("/update/activate/caisse/{articleId}")
    public String updateActionCaisse(@PathVariable Long articleId, AddedProduct incomingAddedProduct){

        Article article = articleRepository.getOne(articleId);
        AddedProduct addedProduct = addedProductRepository.findByArticle_ArticleId(article.getArticleId());
        Product product = addedProduct.getProduct();
        Caisse caisse = caisseRepository.findByMoisAndAnneeAndStatus(monthFormat.format(new Date()), yearFormat.format(new Date()), true);
        addedProduct.setCaisseValidation(true);
        addedProduct.setValidationCode("CA"+(addedProductRepository.findAll().size()+1));
        addedProduct.setValidationComment(incomingAddedProduct.getValidationComment());

        article.setActivate(true);
        Mouvement boutiqueMouvement = new Mouvement();
        boutiqueMouvement.setType("SORTIE");
        boutiqueMouvement.setMilieu("BOUTIQUE");
        boutiqueMouvement.setLibelle("Achat article "+addedProduct.getProduct().getName());

            if (article != null){

                if (article.getMonnaie().equals(EAMonaie.CDF.toString())) {
                    boutiqueMouvement.setMontantFranc(article.getTotalBuyedCDF());
                    boutiqueMouvement.setMontantDollard(article.getTotalBuyedUSD());
                } else {
                    boutiqueMouvement.setMontantDollard(article.getTotalBuyedUSD());
                    boutiqueMouvement.setMontantFranc(article.getTotalBuyedCDF());
                }

            }else{

                if (addedProduct.getDevise().equals(EAMonaie.CDF.toString())) {
                    boutiqueMouvement.setMontantFranc(addedProduct.getTotalCost());
                    boutiqueMouvement.setMontantDollard(addedProduct.getTotalCost() / addedProduct.getTauxDuJour());
                } else {
                    boutiqueMouvement.setMontantDollard(addedProduct.getTotalCost());
                    boutiqueMouvement.setMontantFranc(addedProduct.getTotalCost() * addedProduct.getTauxDuJour());
                }

            }

            if (caisse != null){
            boutiqueMouvement.setCaisse(caisse);
            boutiqueMouvement.setNumMouvement("MVM" + caisse.getMois() + "" + caisse.getAnnee()+""+(mouvementRepository.findAllByCaisse_Id(caisse.getId()).size()+1));

            caisse.setSoldeFinMoisDollard(caisse.getSoldeFinMoisDollard() + boutiqueMouvement.getMontantDollard());
            caisse.setSoldeFinMoisFranc(caisse.getSoldeFinMoisFranc() + boutiqueMouvement.getMontantFranc());
            caisse.setMouvementMensuelDollard(caisse.getMouvementMensuelDollard() + boutiqueMouvement.getMontantDollard());
            caisse.setMouvementMensuelFranc(caisse.getMouvementMensuelFranc() + boutiqueMouvement.getMontantFranc());
            boutiqueMouvement.setSoldeFinFranc(caisse.getSoldeFinMoisFranc());
            boutiqueMouvement.setSoldeFinDollard(caisse.getSoldeFinMoisDollard());
            caisseRepository.save(caisse);
        }else {
            Caisse caisse1 = new Caisse();
            caisse1.setAnnee(yearFormat.format(new Date()));
            caisse1.setMois(monthFormat.format(new Date()));

            caisse.setSoldeFinMoisDollard(boutiqueMouvement.getMontantDollard());
            caisse.setSoldeFinMoisFranc(boutiqueMouvement.getMontantFranc());
            caisse.setMouvementMensuelDollard( boutiqueMouvement.getMontantDollard());
            caisse.setMouvementMensuelFranc( boutiqueMouvement.getMontantFranc());
            caisse1.setStatus(true);
            caisseRepository.save(caisse1);
            boutiqueMouvement.setSoldeFinFranc(caisse1.getSoldeFinMoisFranc());
            boutiqueMouvement.setSoldeFinDollard(caisse1.getSoldeFinMoisDollard());
            boutiqueMouvement.setCaisse(caisse1);
            boutiqueMouvement.setNumMouvement("MVM" + caisse.getMois() + "" + caisse.getAnnee()+""+(mouvementRepository.findAllByCaisse_Id(caisse.getId()).size()+1));
        }

            articleRepository.save(article);
            addedProductRepository.save(addedProduct);
            mouvementRepository.save(boutiqueMouvement);

        return "redirect:/commande/details/"+article.getCommande().getCommandeId();
    }



}
