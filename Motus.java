import java.util.*;

class Dictionnaire {
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
class Jeu {
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
public class Motus {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (args.length < 2) {
            System.out.println("Arguments manquants !");
            return;
        }
        ArrayList<String> collection = new ArrayList<>(Integer.parseInt(args[1]));
        Scanner collect = new Scanner(Objects.requireNonNull(Motus.class.getClassLoader().getResourceAsStream("d" + Integer.parseInt(args[1]) + ".txt")));
        while(collect.hasNextLine()) {
            collection.add(collect.nextLine());
        }
        collect.close();
        if (args[0].equalsIgnoreCase("config")) {
            int tailleMot = Integer.parseInt(args[1]);
            String config = "oui";
            Dictionnaire dictionnaire = new Dictionnaire(tailleMot, collection);
            System.out.println("Bienvenue dans le mode Configuration");
            while (true) {
                if (config.equalsIgnoreCase("non")) {
                    break;
                } else if (config.equalsIgnoreCase("oui")){
                    System.out.println("""
                        Que voulez-vous faire ?\s
                        -------------------------
                        1. recherche
                        2. ajout
                        3. suppression
                        4. afficher
                        5. quit
                        -------------------------
                        Entrez votre choix ?""");
                    String action = scanner.next();
                    switch (action){
                        case "quit" -> {
                        }
                        case "recherche" -> {
                            System.out.println("Entrez le mot à rechercher :");
                            String mot = scanner.next();
                            System.out.println(dictionnaire.rechercheMot(mot) ? "Mot trouvé !" : "Mot introuvable !");
                        }
                        case "ajout" -> {
                            System.out.println("Entrez le mot à ajouter :");
                            String mot = scanner.next();
                            System.out.println(dictionnaire.ajoutMot(mot) ? "Mot ajouté !" : "Echec de l'ajout !");
                        }
                        case "suppression" -> {
                            System.out.println("Entrez le mot à supprimer :");
                            String mot = scanner.next();
                            System.out.println(dictionnaire.supprimeMot(mot) ? "Mot supprimé !" : "Echec de la suppression !");
                        }
                        case "afficher" -> System.out.println(dictionnaire);
                        default -> System.out.println("Choix invalide !!!!");
                    }
                    System.out.println("Voulez-vous continuer à configurer? (oui/non)");
                    config = scanner.next();
                }
            }
        } else if (args[0].equalsIgnoreCase("jeu")) {
            int tailleMot = Integer.parseInt(args[1]);
            String game = "oui";
            Dictionnaire dictionnaire = new Dictionnaire(tailleMot, collection);
            Jeu jeu = new Jeu(dictionnaire);
            System.out.println("//------- Bienvenue dans Jeux Motus -------//");
            while (true) {
                if (game.equalsIgnoreCase("non")) {
                    System.out.println("//------- JEUX FINIE-------//" + "\n" + "Nombre des parties gagnées: " + Jeu.getNbPartGagnees() + "\n" + "Nombre des parties pérdues: " + Jeu.getNbPartPerdues());
                    break;
                } else if (game.equalsIgnoreCase("oui")){
                    System.out.println("--- DEBUT PARTIE---");
                    System.out.println("Entrez le nombre d'essais autorisés :");
                    int nbEssais = scanner.nextInt();
                    Jeu.setNbEssais(nbEssais);
                    jeu.setNbEssaisRestants(nbEssais);
                    System.out.println("Le mot à rechercher est composer de "+dictionnaire.getTailleMot()+" lettres");
                    while (true) {
                        if (jeu.getNbEssaisRestants() > 0) {
                            System.out.println("-------");
                            System.out.println("Nombre d'essais restants : " + jeu.getNbEssaisRestants());
                            System.out.println("Mot : " + jeu.getMotTrouve());
                            System.out.println("Entrez un mot :");
                            String mot = scanner.next();
                            if (jeu.testMot(mot)) {
                                System.out.println("-------" + "\n" + "Gagné !" + "\n" + "Le mot caché été: "+ jeu.getMotCache() + "\n" + "Voulez-vous jouer de nouveau ? (oui/non)");

                                game = scanner.next();
                                break;
                            }
                        }else {
                            System.out.println("-------" + "\n" + "Perude !" + "\n" + "Le mot caché été: "+ jeu.getMotCache() + "\n" + "Voulez-vous jouer de nouveau ? (oui/non)");
                            game = scanner.next();
                            break;
                        }
                }
                }
            }
            }
        }
    }