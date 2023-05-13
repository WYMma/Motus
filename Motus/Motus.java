import java.util.*;

public class Motus {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (args.length < 2) {
            System.out.println("Arguments manquants !");
            return;
        }
        ArrayList<String> collection = new ArrayList<>(Integer.parseInt(args[1]));
        Scanner collect = new Scanner(Objects.requireNonNull(Motus.class.getClassLoader().getResourceAsStream("Dictionnaires/d" + Integer.parseInt(args[1]) + ".txt")));
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