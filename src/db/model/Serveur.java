package db.model;

public class Serveur extends Model{
    private String nom;
    private String prenom;

    public Serveur() {}
    public Serveur(int id, String... args)
    {
        this.id = id;
        this.nom = args[0];
        this.prenom = args[1];
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom()
    {
        return this.nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getFullName()
    {
        return String.format("%s %s", this.getPrenom(), this.getNom());
    }

    @Override
    public String toString()
    {
        return String.format("%s;%s", this.prenom, this.nom);
    }

    @Override
    public String show() {
        return String.format("Serveur : %s", this.getFullName());
    }

    @Override
    public String showFromStorage() {
        return String.format("%d. %s", this.id, this.getFullName());
    }


}
