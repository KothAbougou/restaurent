package view.restaurateur;

import db.dao.StockDAO;
import db.model.Stock;
import view.Page;
import view.View;

public class StockManagerView extends View {

    protected static StockDAO stockDAO = StockDAO.getInstance();

    public StockManagerView() {
        super();
        this.actions = new String[]{
            "Retour à la Gestion générale"
        };

        this.commandes.put("ADD     ", "Ajouter un stock");
        this.commandes.put("SET <id>", "Modifier un stock");
        this.commandes.put("DEL <id>", "Supprimer un stock");
    }

    @Override
    protected void content() {
        this.showListStocks();
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
                    case "ADD" -> this.addNewStock();
                    case "DEL" -> this.deleteStock(Integer.parseInt(act[1]));
                    case "EDIT" -> this.setStock(Integer.parseInt(act[1]));
                    default -> USER_ACTION;
                };
            }
        }
    }

    private String setStock(int id) {
        Stock stock = stockDAO.getById(id);

        stock.setTypeProduit(this.input("Type du produit :"));
        stock.setProduit(this.input("Nouveau nom du produit :"));
        stock.setQte(Integer.parseInt(this.input("Ajout de quantité:")));

        stockDAO.save(stock);

        return USER_ACTION;
    }

    private String deleteStock(int id) {
        Stock stock = stockDAO.getById(id);
        stockDAO.delete(stock);
        System.out.println(stock.showFromStorage() + " a bien été supprimé");

        return USER_ACTION;
    }

    private String addNewStock() {
        Stock stock = stockDAO.create();

        stock.setTypeProduit(this.input("Type du produit :"));
        stock.setProduit(this.input("Nom du produit :"));
        stock.setQte(Integer.parseInt(this.input("Quantite de départ :")));

        stockDAO.save(stock);

        return USER_ACTION;
    }

    private void showListStocks() {
        System.out.println(String.format("\t%d produits en stock", stockDAO.getAll().size()));
        for(Stock t : stockDAO.getAll())
            System.out.println("\t" + t.showFromStorage());
    }
}
