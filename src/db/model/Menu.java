package db.model;

import db.dao.StockDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Menu extends Model{

    private String nom;
    private float prix;
    private List<Integer> idProduits;

    private static final StockDAO stockDAO = StockDAO.getInstance();

    public Menu(){
        this.idProduits = new ArrayList<>();
    }
    public Menu(int id, String... args)
    {
        this.id = id;
        this.nom = args[0];
        this.prix = Float.parseFloat(args[1].replace(",", "."));

        this.idProduits = new ArrayList<>();
        for(int i=2; i < args.length; i++)
            this.idProduits.add(Integer.parseInt(args[i]));
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public List<Stock> getProduits()
    {
        List<Stock> produits = new ArrayList<>();

        for(int idProduit : this.idProduits)
            produits.add(stockDAO.getById(idProduit));

        return produits;
    }

    public void addProduit(Stock produit)
    {
        this.idProduits.add(produit.getId());
    }

    public void removeProduit(Stock produit)
    {
        this.idProduits.remove(produit.getId());
    }

    @Override
    public String toString()
    {
        return String.format("%s;%.2f;%s", this.nom, this.prix, this.idProduits.stream().map(String::valueOf).collect(Collectors.joining(";")));
    }

    @Override
    public String show() {
        return String.format("Menu : %s, %.2f €", this.nom, this.prix);
    }

    @Override
    public String showFromStorage() {
        return String.format("%d. %s %.2f €", this.getId(), this.nom, this.prix);
    }
}
