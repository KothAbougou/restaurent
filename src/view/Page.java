package view;

public enum Page {

    TEST("test"),
    ACCUEIL("Bienvenue au Restaurent de Marcelle et Koth!"),
    CLIENT("Je suis Client"),
    RESTAURATEUR("Je suis Restaurateur");

    private String name;

    Page(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
