import java.util.ArrayList;
import java.util.Random;

public class Jeu {
    private static int nbEssais;
    private int nbEssaisRestants;
    private static int nbPartGagnees;
    private static int nbPartPerdues;
    private final String motCache;
    private String motTrouve;
    private final Dictionnaire dictionnaire;
    public Jeu(Dictionnaire dictionnaire) {
        this.dictionnaire = dictionnaire;
        this.motCache = choisirMot();
        this.motTrouve = "";
        for (int i = 0; i < motCache.length(); i++) {
            motTrouve += "*";
        }
    }
    public static int getNbEssais() {
        return nbEssais;
    }
    public int getNbEssaisRestants() {
        return nbEssaisRestants;
    }
    public static int getNbPartGagnees() {
        return nbPartGagnees;
    }
    public static int getNbPartPerdues() {
        return nbPartPerdues;
    }
    public String getMotCache() {
        return motCache;
    }
    public String getMotTrouve() {
        return motTrouve;
    }
    public static void setNbEssais(int nbEssais) {
        Jeu.nbEssais = nbEssais;
    }
    public void setNbEssaisRestants(int nbEssaisRestants) {
        this.nbEssaisRestants = nbEssaisRestants;
    }
    private String choisirMot() {
        Random rand = new Random();
        ArrayList<String> mots = dictionnaire.getCollectionMots();
        return mots.get(rand.nextInt(mots.size()));
    }
    public boolean testMot(String mot) {
        if (mot.length() != dictionnaire.getTailleMot()) {
            nbEssaisRestants--;
            if (nbEssaisRestants == 0) {
                nbPartPerdues++;
            }
            return false;
        }
        if (!mot.matches("^[A-Z]*$")) {
            nbEssaisRestants--;
            if (nbEssaisRestants == 0) {
                nbPartPerdues++;
            }
            return false;
        }
        nbEssaisRestants--;
        if (mot.equals(motCache)) {
            nbPartGagnees++;
            return true;
        } else {
            String newMotTrouve = "";
            for (int i = 0; i < motCache.length(); i++) {
                if (mot.charAt(i) == motCache.charAt(i)) {
                    newMotTrouve += mot.charAt(i);
                } else {
                    newMotTrouve += motTrouve.charAt(i);
                }
            }
            motTrouve = newMotTrouve;
            if (nbEssaisRestants == 0) {
                nbPartPerdues++;
            }
            return false;
        }
    }
}
