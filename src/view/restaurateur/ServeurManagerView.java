package view.restaurateur;

import db.dao.ServeurDAO;
import db.model.Serveur;
import view.Page;
import view.View;

public class ServeurManagerView extends View {

    protected static ServeurDAO serveurDAO = ServeurDAO.getInstance();

    public ServeurManagerView() {
        super();
        this.actions = new String[]{
            "Retour à la Gestion générale"
        };

        this.commandes.put("ADD     ", "Ajouter un serveur");
        this.commandes.put("SET <id>", "Modifier un serveur");
        this.commandes.put("DEL <id>", "Supprimer un serveur");
    }

    @Override
    protected void content() {
        this.showListServeurs();
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
                    case "ADD" -> this.addNewSereveur();
                    case "DEL" -> this.deleteSereveur(Integer.parseInt(act[1]));
                    case "EDIT" -> this.setSereveur(Integer.parseInt(act[1]));
                    default -> USER_ACTION;
                };
            }
        }
    }

    private String setSereveur(int id) {
        Serveur serveur = serveurDAO.getById(id);

        System.out.println(serveur.showFromStorage());
        serveur.setNom(this.input("Nouveau nom :"));
        serveur.setPrenom(this.input("Nouveau prénom :"));
        serveurDAO.save(serveur);

        System.out.println("Le serveur a bien été modifié");

        return USER_ACTION;
    }

    private String deleteSereveur(int id) {
        Serveur serveur = serveurDAO.getById(id);
        serveurDAO.delete(serveur);
        System.out.println(serveur.showFromStorage() + " a bien été supprimé");

        return USER_ACTION;
    }

    private String addNewSereveur() {
        Serveur serveur = serveurDAO.create();

        serveur.setNom(this.input("Nom :"));
        serveur.setPrenom(this.input("Prenom :"));
        serveurDAO.save(serveur);

        System.out.println("Nouveau serveur ajouté");


        return USER_ACTION;
    }

    private void showListServeurs() {
        System.out.println(String.format("\t%d Serveurs enregistrés", serveurDAO.getAll().size()));
        for(Serveur s : serveurDAO.getAll())
            System.out.println("\t" + s.showFromStorage());
    }
}
