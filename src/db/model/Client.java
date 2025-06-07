package db.model;

public class Client extends Model{

    private String prenom;
    private String nom;
    private float soldeBancaire;

    public Client(){}
    public Client(int id, String... args){
        this.id = id;
        this.prenom = args[0];
        this.nom = args[1];
        this.soldeBancaire = Float.parseFloat(args[2].replace(",","."));
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFullName()
    {
        return String.format("%s %s", this.getPrenom(), this.getNom());
    }

    public float getSoldeBancaire() {
        return this.soldeBancaire;
    }

    public void setSoldeBancaire(float delta)
    {
        this.soldeBancaire += delta;
    }

    public boolean isAbleToPay(float amount)
    {
        return this.soldeBancaire >= amount;
    }

    @Override
    public String toString()
    {
        return String.format("%s;%s;%.2f", this.prenom, this.nom, this.soldeBancaire);
    }

    @Override
    public String show() {
        return String.format("Client : %s", this.getFullName());
    }

    @Override
    public String showFromStorage() {
        return String.format("%d. %s %.2f €", this.getId(), this.getFullName(), this.getSoldeBancaire());
    }
}
