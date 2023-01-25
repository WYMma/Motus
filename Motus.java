import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

abstract class Auxe {
    public abstract boolean testMot(String mot);
}
class Dictionnaire {
    private int tailleMot;
    private int nbMots;
    private final ArrayList<String> collectionMots;

    public Dictionnaire(int tailleMot) {
        this.tailleMot = tailleMot;
        this.nbMots = 0;
        this.collectionMots = new ArrayList<>();
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

    public void setTailleMot(int tailleMot) {
        this.tailleMot = tailleMot;
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
}

class Jeu extends Auxe{
    private static int nbEssais;
    private int nbEssaisRestants;
    private static int nbPartGagnees;
    private static int nbPartPerdues;
    private final String motCache;
    private String motTrouve;
    private final Dictionnaire dictionnaire;

    public Jeu(Dictionnaire dictionnaire, int nbEssais) {
        this.dictionnaire = dictionnaire;
        Jeu.nbEssais = nbEssais;
        this.nbEssaisRestants = nbEssais;
        motCache = choisirMot();
        motTrouve = "";
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
    @Override
    public boolean testMot(String mot) {
        if (mot.length() != dictionnaire.getTailleMot()) {
            return false;
        }
        if (!mot.matches("^[A-Z]*$")) {
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
public class Motus   {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (args.length < 2) {
            System.out.println("Arguments manquants !");
            return;
        }

        if (args[0].equals("config")) {
            int tailleMot = Integer.parseInt(args[1]);
            Dictionnaire dictionnaire = new Dictionnaire(tailleMot);
            while (true) {
                System.out.println("Que voulez-vous faire ? (recherche, ajout, suppression, quit)");
                String action = scanner.nextLine();
                if (action.equals("quit")) {
                    break;
                }
                if (action.equals("recherche")) {
                    System.out.println("Entrez le mot à rechercher :");
                    String mot = scanner.nextLine();
                    System.out.println(dictionnaire.rechercheMot(mot) ? "Mot trouvé !" : "Mot introuvable !");
                }
                if (action.equals("ajout")) {
                    System.out.println("Entrez le mot à ajouter :");
                    String mot = scanner.nextLine();
                    System.out.println(dictionnaire.ajoutMot(mot) ? "Mot ajouté !" : "Echec de l'ajout !");
                }
                if (action.equals("suppression")) {
                    System.out.println("Entrez le mot à supprimer :");
                    String mot = scanner.nextLine();
                    System.out.println(dictionnaire.supprimeMot(mot) ? "Mot supprimé !" : "Echec de la suppression !");
                }
            }
        } else if (args[0].equals("jeu")) {
            int tailleMot = Integer.parseInt(args[1]);
            Dictionnaire dictionnaire = new Dictionnaire(tailleMot);
            System.out.println("Entrez le nombre d'essais autorisés :");
            int nbEssais = scanner.nextInt();
            Jeu jeu = new Jeu(dictionnaire, nbEssais);
            while (true) {
                System.out.println("Nombre d'essais restants : " + jeu.getNbEssaisRestants());
                System.out.println("Mot : " + jeu.getMotTrouve());
                System.out.println("Entrez un mot :");
                String mot = scanner.next();
                if (jeu.testMot(mot)) {
                    System.out.println("Gagné !");
                }
            }
        }
    }
}