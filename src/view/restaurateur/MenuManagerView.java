package view.restaurateur;

import db.dao.MenuDAO;
import db.dao.StockDAO;
import db.model.Menu;
import db.model.Stock;
import view.Page;
import view.View;

import java.sql.SQLOutput;

public class MenuManagerView extends View {

    protected static MenuDAO menuDAO = MenuDAO.getInstance();
    protected static StockDAO stockDAO = StockDAO.getInstance();

    public MenuManagerView() {
        super();
        this.actions = new String[]{
            "Retour à la Gestion générale"
        };

        this.commandes.put("GET <id>", "Voir un menu");
        this.commandes.put("ADD     ", "Ajouter un menu");
        this.commandes.put("SET <id>", "Modifier un menu");
        this.commandes.put("DEL <id>", "Supprimer un menu");
    }

    @Override
    protected void content() {
        this.showListMenus();
    }

    @Override
    protected final void userAction() {
        String action = USER_ACTION;

        while(( isNumeric(action) && (Integer.parseInt(action) <= 0 || Integer.parseInt(action) > this.actions.length+1))
                || (!isNumeric(action) && action.isEmpty()))
        {
            action = this.input();

            if(isNumeric(action)) switch (Integer.parseInt(action))
            {
                case 1 -> View.changeTo(Page.RESTAURATEUR);
                default -> action = "";
            }
            else {
                String[] act = action.split(" ");
                String cmd = act[0];

                action = switch(cmd){
                    case "GET" -> this.getMenu(Integer.parseInt(act[1]));
                    case "ADD" -> this.addNewMenu();
                    case "DEL" -> this.deleteMenu(Integer.parseInt(act[1]));
                    case "EDIT" -> this.setMenu(Integer.parseInt(act[1]));
                    default -> USER_ACTION;
                };
            }
        }
    }

    private String getMenu(int id) {
        Menu menu = menuDAO.getById(id);
        System.out.println(menu.showFromStorage());
        System.out.println("Composition :");
        for(Stock produit : menu.getProduits())
            System.out.println("- " + produit.commercialName());

        return USER_ACTION;
    }

    private String setMenu(int id) {
        Menu menu = menuDAO.getById(id);

        menu.setNom(this.input("Nouveau nom du menu :"));
        System.out.println("Composition actuelle du menu :");
        for(Stock stock : menu.getProduits())
            System.out.println(stock.showFromStorage());

        String removeProduits = this.input("Supprimer des produits du menu [ex: 1 2 7 ... ou N] ?");
        if(!removeProduits.isEmpty() || !removeProduits.equals(INPUT_NO))
            for(String c : removeProduits.split(" "))
                menu.removeProduit(stockDAO.getById(Integer.parseInt(c)));

        System.out.println("Tous les produits existants :");
        for(Stock stock : stockDAO.getAll())
            System.out.println(stock.showFromStorage());

        String addProduits = this.input("Ajouter des produits du menu [ex: 1 2 7 ... ou N] ?");
        if(!addProduits.isEmpty())
            for(String c : addProduits.split(" "))
                menu.addProduit(stockDAO.getById(Integer.parseInt(c)));

        menu.setPrix(Float.parseFloat(this.input("Prix du menu:")));

        menuDAO.save(menu);

        return USER_ACTION;
    }

    private String deleteMenu(int id) {
        Menu menu = menuDAO.getById(id);
        menuDAO.delete(menu);
        System.out.println(menu.showFromStorage() + " a bien été supprimé");

        return USER_ACTION;
    }

    private String addNewMenu() {
        Menu menu = menuDAO.create();

        menu.setNom(this.input("Nom du menu :"));
        for(Stock stock : stockDAO.getAll())
            System.out.println(stock.showFromStorage());

        String composition = this.input("Composition du menu [ex: 1 2 7 ...] :");
        if(!composition.isEmpty())
            for(String c : composition.split(" "))
                menu.addProduit(stockDAO.getById(Integer.parseInt(c)));

        menu.setPrix(Float.parseFloat(this.input("Prix du menu:")));

        menuDAO.save(menu);

        return USER_ACTION;
    }

    private void showListMenus() {
        System.out.printf("\t%n", menuDAO.getAll().size());
        for(Menu menu : menuDAO.getAll())
            System.out.println("\t" + menu.showFromStorage());
    }
}
