package view;

public enum Page {

    TEST("test"),
    ACCUEIL("Bienvenue au Restaurent de Marcelle et Koth!"),

    CLIENT("Je suis Client"),
    RESERVER_TABLE("Réserver une table"),
    PAYER("Payer mes réservations"),
    ENREGISTREMENT_CLIENT("Enregistrement"),

    RESTAURATEUR("Je suis Restaurateur"),
    TABLE_MANAGER("Gestion des tables"),
    STOCK_MANAGER("Gestion des stocks"),
    MENU_MANAGER("Gestion des menus"),
    SERVEURS_MANAGER("Gestion des serveurs");



    private String name;

    Page(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
