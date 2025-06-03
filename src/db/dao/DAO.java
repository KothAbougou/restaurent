package db.dao;

import db.model.Client;
import db.model.Model;
import db.model.Serveur;

import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;

public abstract class DAO {

    protected static final String EMPTY_LINE = "NULL";
    protected String storage;

    /**
     * Création d'un model
     */
    abstract public Model create();

    /**
     * Sauvegarder un model
     * @param model
     */
    public void save(Model model)
    {
        if(model.isStored()) this.rewrite(model);

        else {
            List<String> content = new ArrayList<>();
            content.add(model.toString());
            this.write(content);
        }
    }

    public Model flush(Model model) {
        try {
            this.save(model);
            return this.getById((int) Files.lines(Path.of(this.storage)).count() - 1);
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Récupérer un model
     * @param id
     */
    abstract public Model getById(int id);

    /**
     * Récupérer tous les articles
     */
    abstract public List<?> getAll();

    /**
     * Supprimer un articles
     */
    public void delete(Model model){
        this.remove(model);
    }

    /**
     * Lire dans le storage
     */
    protected List<String[]> read()
    {
        Path path = Paths.get(this.storage);

        List<String[]> lignes = new ArrayList<>();

        try {
            for(String l :  Files.readAllLines(path))
                lignes.add(l.split(";"));

            return lignes;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Ecrire dans le storage
     * @param content
     */
    protected void write(List<String> content)
    {
        try {
            String joined = String.join(System.lineSeparator(), content) + System.lineSeparator();
            Files.write(Paths.get(this.storage), joined.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    /**
     * Modifier une ligne du fichier
     */
    protected void rewrite(Model model)
    {
        if(model.isStored()) {
            Path path = Paths.get(this.storage);

            try {
                List<String> lignes = Files.readAllLines(path);

                int numeroLigne = model.getId();
                if (lignes.size() > numeroLigne) {
                    lignes.set(numeroLigne, model.toString());
                }

                Files.write(path, lignes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Supprimer une ligne
     * 
     */
    protected void remove(Model model)
    {
        if(model.isStored())
        {
            Path path = Paths.get(this.storage);

            try {
                List<String> lignes = Files.readAllLines(path);

                int numeroLigne = model.getId();
                if (lignes.size() > numeroLigne) {
                    lignes.set(numeroLigne, EMPTY_LINE);
                }

                Files.write(path, lignes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
