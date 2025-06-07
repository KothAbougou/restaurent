package view.client;

import db.dao.ClientDAO;
import db.dao.ReservationDAO;
import db.model.Reservation;
import db.model.Restaurent;
import view.Page;
import view.View;


import java.util.List;

public class PayerView extends View {

    private static final Restaurent restaurent = Restaurent.getInstance();
    private static final ReservationDAO reservationDAO = ReservationDAO.getInstance();
    private static final ClientDAO clientDAO = ClientDAO.getInstance();

    public PayerView()
    {
        this.actions = new String[]{
                "Retour à la page Client"
        };

        this.commandes.put("REFUND <amount>", "Payer toutes les factures d'un coup");
        this.commandes.put("PAY <id>", "\t\tPayer une facture");
        this.commandes.put("PAY ALL", "\t\tPayer toutes les factures d'un coup");
    }

    @Override
    protected void content() {
        System.out.println("\tVotre solde bancaire : " + restaurent.getClient().getSoldeBancaire() + " €");
        System.out.println("\tVos factures :");

        List<Reservation> reservations = reservationDAO.getAllByClientId(restaurent.getClient().getId());
        for(Reservation r : reservations.reversed())
            System.out.println("\t"+r.show().replace("Réservation : ", "").replace("en cours", "impayé"));
    }

    @Override
    protected void userAction() {
        String action = USER_ACTION;

        while(( isNumeric(action) && (Integer.parseInt(action) <= 0 || Integer.parseInt(action) > this.actions.length+1))
                || (!isNumeric(action) && action.isEmpty()))
        {
            action = this.input();

            if(isNumeric(action)) switch (Integer.parseInt(action))
            {
                case 1 -> View.changeTo(Page.CLIENT);
                default -> action = "";
            }
            else {
                String[] act = action.split(" ");
                String cmd = act[0];

                action = switch(cmd){
                    case "PAY" -> {
                        if(act[1].equals("ALL") || isNumeric(act[1].replace(",",".")))
                            this.payAddition(act[1]);
                        yield USER_ACTION;
                    }
                    case "REFUND" -> this.refundAccount(Float.parseFloat(act[1].replace(",", ".")));
                    default -> USER_ACTION;
                };
            }
        }
    }

    private String refundAccount(float amount) {
        restaurent.getClient().setSoldeBancaire(amount);
        clientDAO.save(restaurent.getClient());
        System.out.println("Votre compte a été crédité de : "+String.format("%.2f €", amount));

        return USER_ACTION;
    }

    private void payAddition(String param) {
        if(param.equals("ALL"))
        {
            List<Reservation> additions = reservationDAO.getClientUnpaidReservations(restaurent.getClient().getId());
            float facture = 0;

            for(Reservation addition : additions)
                facture -= addition.getPrix();

            if(restaurent.getClient().isAbleToPay(Math.abs(facture)))
            {
                restaurent.getClient().setSoldeBancaire(facture);
                clientDAO.save(restaurent.getClient());

                for(Reservation addition : additions) {
                    addition.setPaid(true);
                    reservationDAO.save(addition);
                }

                System.out.println("Toutes les additions ont bien été payées");

            }else System.out.println(restaurent.getClient().getFullName() + " : Solde bancaire insuffisant pour payer toutes les factures impayées.");


        } else if (isNumeric(param)) {
            Reservation addition = reservationDAO.getById(Integer.parseInt(param));
            float facture = -addition.getPrix();

            if(restaurent.getClient().isAbleToPay(Math.abs(facture)))
            {
                restaurent.getClient().setSoldeBancaire(facture);
                clientDAO.save(restaurent.getClient());
                addition.setPaid(true);
                reservationDAO.save(addition);

                System.out.println("L'addtion numéro " + addition.getId() + " a bien été payée.");

            }else System.out.println(restaurent.getClient().getFullName() + " : Solde bancaire insuffisant.");
        }
    }
}
