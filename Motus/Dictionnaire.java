import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Dictionnaire {
    private final int tailleMot;
    private int nbMots;
    private final ArrayList<String> collectionMots;
    public Dictionnaire(int tailleMot, ArrayList<String> collectionMots) {
        this.tailleMot = tailleMot;
        this.collectionMots = collectionMots;
        this.nbMots = collectionMots.size();
    }
    public int getTailleMot() {
        return tailleMot;
    }
    public int getNbMots() {
        return nbMots;
    }
    public ArrayList<String> getCollectionMots() {
        return collectionMots;
    }
    public boolean rechercheMot(String mot) {
        return collectionMots.contains(mot);
    }
    public boolean ajoutMot(String mot) {
        if(mot.length() != tailleMot) {
            return false;
        }
        if(!mot.matches("^[A-Z]*$")) {
            return false;
        }
        if(rechercheMot(mot)) {
            return false;
        }

        collectionMots.add(mot);
        Collections.sort(collectionMots);
        nbMots++;
        return true;
    }
    public boolean supprimeMot(String mot) {
        if(!rechercheMot(mot)) {
            return false;
        }
        collectionMots.remove(mot);
        nbMots--;
        return true;
    }
    public String toString(){
        String msg;
        msg = "Le nombre des mots est: " + getNbMots() +"\n" + "La taille du mot est: " + getTailleMot() +"\n" + "La liste des mots est: " +"\n" + Arrays.toString(Arrays.copyOf(collectionMots.toArray(), collectionMots.size(), String[].class)) +"\n";
        return msg;
    }
}
