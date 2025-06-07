package view.restaurateur;

import db.dao.ClientDAO;
import db.dao.ReservationDAO;
import db.dao.ServeurDAO;
import db.dao.TableDAO;
import db.model.Client;
import db.model.Reservation;
import db.model.Serveur;
import db.model.Table;
import observer.ClientBillingObserver;
import view.Page;
import view.View;

public class TableManagerView extends View {

    protected static TableDAO tableDAO = TableDAO.getInstance();
    protected static ServeurDAO serveurDAO = ServeurDAO.getInstance();
    protected static ReservationDAO reservationDAO = ReservationDAO.getInstance();
    protected static ClientDAO clientDAO = ClientDAO.getInstance();

    public TableManagerView() {
        super();
        this.actions = new String[]{
            "Retour à la Gestion générale"
        };

        this.commandes.put("ADD     ", "Ajouter une table");
        this.commandes.put("SET <id>", "Modifier une table");
        this.commandes.put("DEL <id>", "Supprimer une table");
        this.commandes.put("BILL <id>", "Facturer une table");
        this.commandes.put("ABOUT <id>", "A propos d'une table");
    }

    @Override
    protected void content() {
        this.showListTables();
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
                    case "ADD" -> this.addNewTable();
                    case "DEL" -> this.deleteTable(Integer.parseInt(act[1]));
                    case "EDIT" -> this.setTable(Integer.parseInt(act[1]));
                    case "BILL" -> this.billTable(Integer.parseInt(act[1]));
                    case "ABOUT" -> this.aboutTable(Integer.parseInt(act[1]));
                    default -> USER_ACTION;
                };
            }
        }
    }

    private String aboutTable(int idTable) {
        Table table = tableDAO.getById(idTable);
        System.out.println(table.show());
        System.out.println("Chiffre d'affaires de la table : " + String.format("%.2f €", reservationDAO.getTableCA(idTable)));
        for(Reservation reservation : reservationDAO.getAllByIdTable(idTable))
            System.out.println(reservation.show().replace("Réservation : ", "Facture : ").replace("en cours", "à facturer"));

        return USER_ACTION;
    }

    private String billTable(int idTable) {
        Table table = tableDAO.getById(idTable);

        table.clearBillingObservers();

        for(Reservation reservation : table.getAllReservations()) {
            if(!reservation.isPaid()) {
                table.addBillingObserver(new ClientBillingObserver(
                        reservation.getClient(),
                        reservation
                ));
            }
        }

        table.notifyBillingObservers();

        return USER_ACTION;
    }

    private String setTable(int id) {
        Table table = tableDAO.create();

        table.setNbPlace(Integer.parseInt(this.input("Nouveau nombre de place :")));

        Serveur serveur = switch(this.input("Affecter un nouveau serveur [y/N/?] ?"))
        {
            case INPUT_YES -> serveurDAO.getById(Integer.parseInt(this.input("Id d'un nouveau serveur :")));

            case INPUT_QUESTION -> {
                for(Serveur s : serveurDAO.getAll())
                    System.out.println(s.showFromStorage());
                yield serveurDAO.getById(Integer.parseInt(this.input("Id d'un nouveau serveur :")));
            }

            default -> table.getServeur();

        };

        if(serveur != null)
        {
            table.setServeur(serveur);
            System.out.println(table.getServeur().getFullName() + " a été affecté à la nouvelle table.");
        }

        tableDAO.save(table);

        System.out.println("Une nouvelle table ajoutée");


        return USER_ACTION;
    }

    private String deleteTable(int id) {
        Table table = tableDAO.getById(id);
        tableDAO.delete(table);
        System.out.println(table.showFromStorage() + " a bien été supprimé");

        return USER_ACTION;
    }

    private String addNewTable() {
        Table table = tableDAO.create();

        table.setNbPlace(Integer.parseInt(this.input("Nombre de place :")));

        Serveur serveur = switch(this.input("Affecter un serveur [y/N/?] ?"))
        {
            case INPUT_YES -> serveurDAO.getById(Integer.parseInt(this.input("Id d'un serveur :")));

            case INPUT_QUESTION -> {
                for(Serveur s : serveurDAO.getAll())
                    System.out.println(s.showFromStorage());
                yield serveurDAO.getById(Integer.parseInt(this.input("Id d'un serveur :")));
            }

            case INPUT_NO -> null;

            default -> null;

        };

        if(serveur != null)
        {
            table.setServeur(serveur);
            System.out.println(table.getServeur().getFullName() + " a été affecté à une nouvelle table");
        }

        tableDAO.save(table);
        System.out.println("Une nouvelle table a été ajoutée");
        return USER_ACTION;
    }

    private void showListTables() {
        System.out.println(String.format("\t%d Tables existantes\n\tCapacité totale: %d personnes", tableDAO.getAll().size(), tableDAO.getCapaciteTotale()));
        for(Table t : tableDAO.getAll())
            System.out.println("\t"+t.show() + (t.isFree() ? "": String.format(" [%.2f € à facturer]",t.toBeBilled())));
    }
}
