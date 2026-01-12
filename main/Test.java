package main;

import services.*;
import CLASSES.*;
import db.Connexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Test {

    public static void main(String[] args) throws SQLException {
    	
        Scanner scanner = new Scanner(System.in);
        GestionProduit gestionProduit = new GestionProduit();
        GestionFournisseur gestionFournisseur = new GestionFournisseur();
        GestionLot gestionLot = new GestionLot();
        GestionVente gestionVente = new GestionVente();
        GestionContrat gestionContrat = new GestionContrat();
        GestionTableauxDeBord gestionTableauDeBord = new GestionTableauxDeBord();
        gestionProduit.creerTableProduits();
        gestionFournisseur.creerTableFournisseurs(); 
        gestionLot.creerTableLots();               
        gestionVente.creerTableVentes();
        gestionContrat.creerTableContrats();
        while (true) {
            System.out.println("\n*** MENU PRINCIPAL ***");
            System.out.println("1. Gérer les Produits");
            System.out.println("2. Gérer les Fournisseurs");
            System.out.println("3. Gérer les Lots");
            System.out.println("4. Gérer les Ventes");
            System.out.println("5. Gérer les Contrats");
            System.out.println("6. Voir les Tableaux de Bord");
            System.out.println("7. Quitter");
            System.out.print("Votre choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> gererProduits(gestionProduit, scanner);
                case "2" -> gererFournisseurs(gestionFournisseur, scanner);
                case "3" -> gererLots(gestionLot, scanner);
                case "4" -> gererVentes(gestionVente, scanner);
                case "5" -> gererContrats(gestionContrat, scanner);
                case "6" -> afficherTableauxDeBord(gestionTableauDeBord);
                case "7" -> {
                    System.out.println("Au revoir !");
                    System.exit(0);
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private static void gererProduits(GestionProduit gestionProduit, Scanner scanner) {
        System.out.println("\n*** GESTION DES PRODUITS ***");
        System.out.println("1. Ajouter un produit");
        System.out.println("2. Lister les produits");
        System.out.print("Votre choix : ");
        String choix = scanner.nextLine();

        switch (choix) {
            case "1" -> {
                System.out.print("ID du produit (saisir un nombre entier) : ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Nom du produit : ");
                String nom = scanner.nextLine();
                System.out.print("Description : ");
                String description = scanner.nextLine();
                System.out.print("Catégorie : ");
                String categorie = scanner.nextLine();
                System.out.print("Prix de vente : ");
                double prixVente = Double.parseDouble(scanner.nextLine());

                Produit produit = new Produit(id,nom, description, categorie, prixVente);
                gestionProduit.ajouterProduit(produit);
            }
            case "2" -> {
                List<Produit> produits = gestionProduit.Display();
                if (produits.isEmpty()) {
                    System.out.println("Aucun produit trouvé.");
                } else {
                    produits.forEach(p -> System.out.println(
                            "ID : " + p.getId() +
                            ", Nom : " + p.getNom() +
                            ",Description"+p.getDescription()+
                            ", Catégorie : " + p.getCategorie()+
                            ",Prix de Vente "+p.getPrixVente()
                    ));
                }
            }
            default -> System.out.println("Choix invalide.");
        }
    }

    private static void gererFournisseurs(GestionFournisseur gestionFournisseur, Scanner scanner) throws SQLException {
        System.out.println("\n*** GESTION DES FOURNISSEURS ***");
        System.out.println("1. Ajouter un fournisseur");
        System.out.println("2. Lister les fournisseurs");
        System.out.print("Votre choix : ");
        String choix = scanner.nextLine();

        switch (choix) {
            case "1" -> {
                System.out.print("ID du Fournisseur (saisir un nombre entier) : ");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.print("Nom : ");
                String nom = scanner.nextLine();
                System.out.print("Numéro SIRET : ");
                String siret = scanner.nextLine();
                System.out.print("Adresse : ");
                String adresse = scanner.nextLine();
                System.out.print("Email : ");
                String email = scanner.nextLine();
                System.out.print("Téléphone : ");
                String telephone = scanner.nextLine();

                Fournisseur fournisseur = new Fournisseur(id , nom, siret, adresse, email, telephone, true);
                gestionFournisseur.ajouterFournisseur(fournisseur);
            }
            case "2" -> {
                List<Fournisseur> fournisseurs = gestionFournisseur.displayTable();
                if (fournisseurs.isEmpty()) {
                    System.out.println("Aucun fournisseur trouvé.");
                } else {
                    fournisseurs.forEach(f -> System.out.println(
                            "ID : " + f.getId() +
                            ", Nom : " + f.getNom() +
                            ",numero Siret:"+f.getNumeroSiret()+
                            ",adresse :"+ f.getAdresse()+
                            ",Email"+f.getEmail()+
                            ", Telephone"+f.getTelephone()+
                            ", Actif : " + (f.isActif() ? "Oui" : "Non")
                    ));
                }
            }
            default -> System.out.println("Choix invalide.");
        }
    }

    private static void gererLots(GestionLot gestionLot, Scanner scanner) throws SQLException {
        System.out.println("\n*** GESTION DES LOTS ***");
        System.out.println("1. Ajouter un lot");
        System.out.println("2. Lister les lots");
        System.out.print("Votre choix : ");
        String choix = scanner.nextLine();

        switch (choix) {
            case "1" -> {
                try {
                    System.out.print("ID du Produit  : ");
                    int produitId = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID du fournisseur : ");
                    int fournisseurId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Quantité : ");
                    int quantite = Integer.parseInt(scanner.nextLine());
                    System.out.print("Prix d'achat : ");
                    double prixAchat = Double.parseDouble(scanner.nextLine());
                    System.out.print("Date d'achat (yyyy-MM-dd, laissez vide pour la date du jour) : ");
                    String dateAchatInput = scanner.nextLine();
                    LocalDate dateAchat = dateAchatInput.isBlank() ? LocalDate.now() : LocalDate.parse(dateAchatInput);
                    System.out.print("Date de péremption (yyyy-MM-dd) : ");
                    String datePeremption = scanner.nextLine();

                    Lot lot = new Lot(produitId, fournisseurId, quantite, prixAchat, dateAchat, datePeremption);
                    gestionLot.ajouterLot(lot);
                } catch (NumberFormatException e) {
                    System.err.println("Erreur : Veuillez entrer des nombres valides.");
                } catch (Exception e) {
                    System.err.println("Erreur inattendue : " + e.getMessage());
                }
            }
            case "2" -> {
                List<Lot> lots = gestionLot.displayTable();
                if (lots.isEmpty()) {
                    System.out.println("Aucun lot trouvé.");
                } else {
                    lots.forEach(l -> System.out.println(
                            "ID : " + l.getIdLot() +
                            ", Produit ID : " + l.getIdProduit() +
                            ", Fournisseur ID : " + l.getIdFournisseur()+
                            ", Quantité : " + l.getQuantite() +
                            ", Prix d'achat : " + l.getPrixAchat() +
                            ", Date d'achat : " + l.getDateAchat() +
                            ", Date de péremption : " + l.getDatePeremption()
                    ));
                }
            }
            default -> System.out.println("Choix invalide.");
        }
    }

    private static void gererVentes(GestionVente gestionVente, Scanner scanner) {
        System.out.println("\n*** GESTION DES VENTES ***");
        try {
            System.out.print("ID du produit : ");
            int produitId = Integer.parseInt(scanner.nextLine());
            System.out.print("ID du lot : ");
            int lotId = Integer.parseInt(scanner.nextLine());
            System.out.print("Quantité : ");
            int quantite = Integer.parseInt(scanner.nextLine());
            System.out.print("Prix de vente : ");
            double prixVente = Double.parseDouble(scanner.nextLine());

            Vente vente = new Vente(produitId, lotId, quantite, prixVente, java.time.LocalDateTime.now());
            gestionVente.ajouterVente(vente);
        } catch (NumberFormatException e) {
            System.err.println("Erreur : Veuillez entrer des nombres valides.");
        } catch (Exception e) {
            System.err.println("Erreur inattendue : " + e.getMessage());
        }
    }

    private static void gererContrats(GestionContrat gestionContrat, Scanner scanner) {
        System.out.println("\n*** GESTION DES CONTRATS ***");
        System.out.println("1. Ajouter un contrat");
        System.out.println("2. Lister les contrats");
        System.out.println("3. Lister les contrats actifs");
        System.out.println("4. Geler un contrat");
        System.out.println("5. Activer un contrat");
        System.out.print("Votre choix : ");
        String choix = scanner.nextLine();

        switch (choix) {
            case "1" -> {
                try {
                    System.out.print("ID du contrat: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID du fournisseur : ");
                    int fournisseurId = Integer.parseInt(scanner.nextLine());

                    System.out.print("ID du produit : ");
                    int produitId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Quantité minimale : ");
                    int quantiteMin = Integer.parseInt(scanner.nextLine());

                    System.out.print("Prix fixe : ");
                    double prixFixe = Double.parseDouble(scanner.nextLine());
                    System.out.print("Date debut  (yyyy-MM-dd) : ");
                    String dateDebut = scanner.nextLine();
                    System.out.print("Date fin (yyyy-MM-dd) : ");
                    String dateFin= scanner.nextLine();                   
                    Contrat contrat = new Contrat(id ,fournisseurId, produitId,  prixFixe, true,quantiteMin, dateDebut, dateFin);
                    gestionContrat.ajouterContrat(contrat);
                    System.out.println("Contrat ajouté avec succès !");
                } catch (NumberFormatException e) {
                    System.out.println("Entrée invalide. Veuillez entrer des valeurs numériques correctes.");
                }
            }
            case "2" -> {
               gestionContrat.afficherContrats();            }
            case "3" -> {
               gestionContrat.afficherContratsActifs();
            }
            case "4" -> {
             
                System.out.print("ID du contrat à geler : ");
                int id = Integer.parseInt(scanner.nextLine());
                gestionContrat.gelerContrat(id);
            }
            case "5" -> {
           
                System.out.print("ID du contrat à activer : ");
                int id = Integer.parseInt(scanner.nextLine());
                gestionContrat.activerContrat(id);
            }
            default -> System.out.println("Choix invalide.");
        }
    }


    private static void afficherTableauxDeBord(GestionTableauxDeBord gestionTableauDeBord) {
        System.out.println("\n*** TABLEAUX DE BORD ***");
        System.out.println("\n--- Ventes Quotidiennes ---");
        gestionTableauDeBord.obtenirResultatsDuJour().forEach(System.out::println);

        System.out.println("\n--- Top Vente ---");
        gestionTableauDeBord.obtenirTop10Ventes().forEach(System.out::println);

        System.out.println("\n--- Lots Proches de la Péremption ---");
        gestionTableauDeBord.obtenirLotsProchesPeremption().forEach(System.out::println);
    }

    private static LocalDate lireDate(Scanner scanner, String message) {
        while (true) {
            try {
                System.out.print(message);
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.err.println("Erreur : La date doit être au format yyyy-MM-dd. Veuillez réessayer.");
            }
        }
    }
}
