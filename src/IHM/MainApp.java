package IHM;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Menage;
import modele.ServiceUtilisateur;

import java.net.URL;

public class MainApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Système de Tri Sélectif - Connexion");
        afficherEcranConnexion();
    }

    void afficherEcranConnexion() {
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20;");

        Label titre = new Label("Connexion au système");
        titre.getStyleClass().add("titre");

        TextField champIdentifiant = new TextField();
        champIdentifiant.setPromptText("Identifiant");

        PasswordField champMotDePasse = new PasswordField();
        champMotDePasse.setPromptText("Mot de passe");

        Button boutonConnexion = new Button("Connexion");
        Button boutonInfos = new Button("ℹ️ Infos sur le tri sélectif");
        boutonInfos.setOnAction(e -> new VisualisationView(this, primaryStage).afficher());

        Label messageErreur = new Label();
        messageErreur.getStyleClass().add("erreur");

        boutonConnexion.setOnAction(e -> {
            String login = champIdentifiant.getText().trim();
            String mdp = champMotDePasse.getText().trim();

            Menage menage = ServiceUtilisateur.connexion(login, mdp);
            if (menage != null) {
                afficherMenuPrincipal(menage);
            } else {
                messageErreur.setText("Identifiants incorrects.");
            }
        });

        root.getChildren().addAll(
                titre,
                champIdentifiant,
                champMotDePasse,
                boutonConnexion,
                boutonInfos,
                messageErreur
        );

        Scene scene = new Scene(root, 400, 250);

        URL cssURL = MainApp.class.getResource("/css/style.css");
        if (cssURL != null) {
            scene.getStylesheets().add(cssURL.toExternalForm());
        } else {
            System.out.println("⚠️ CSS introuvable : /css/style.css");
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void afficherMenuPrincipal(Menage menage) {
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20;");

        Label bienvenue = new Label("Bienvenue " + menage.getIdentifiantConnexion() + " - Rôle : " + menage.getRole());
        bienvenue.getStyleClass().add("titre");

        Button boutonDepotDechet = new Button("Déposer un déchet");
        Button boutonCorbeilles = new Button("Gérer mes corbeilles");
        Button boutonDepotDirect = new Button("Jeter directement");
        Button boutonAchatBons = new Button("Acheter des bons");
        Button boutonViderCorbeille = new Button("Vider une corbeille");
        Button boutonDeconnexion = new Button("Déconnexion");
        Button boutonHistorique = new Button("Consulter l'historique");
        Button boutonGestionPoubelles = new Button("Gérer les poubelles");
        Button boutonStatistiques = new Button("📊 Statistiques");

        boutonDepotDechet.setOnAction(e -> new DepotDechetView(this, primaryStage, menage).afficher());
        boutonCorbeilles.setOnAction(e -> new GestionCorbeillesView(this, primaryStage, menage).afficher());
        boutonDepotDirect.setOnAction(e -> new DepotDirectPoubelleView(this, primaryStage, menage).afficher());
        boutonAchatBons.setOnAction(e -> new AchatBonsView(this, primaryStage, menage).afficher());
        boutonViderCorbeille.setOnAction(e -> new ViderCorbeilleView(this, primaryStage, menage).afficher());
        boutonDeconnexion.setOnAction(e -> afficherEcranConnexion());

        if (menage.getRole().equalsIgnoreCase("employé")) {
            boutonHistorique.setOnAction(e -> new HistoriqueView(this, primaryStage, menage).afficher());
            boutonGestionPoubelles.setOnAction(e -> new GestionPoubellesView(this, primaryStage, menage).afficher());
            boutonStatistiques.setOnAction(e -> new StatistiquesView(this, primaryStage, menage).afficher());
        }

        root.getChildren().addAll(
                bienvenue,
                boutonDepotDechet,
                boutonDepotDirect,
                boutonCorbeilles,
                boutonAchatBons,
                boutonViderCorbeille
        );

        if (menage.getRole().equalsIgnoreCase("employé")) {
            root.getChildren().addAll(
                    boutonHistorique,
                    boutonGestionPoubelles,
                    boutonStatistiques
            );
        }

        root.getChildren().add(boutonDeconnexion);

        Scene scene = new Scene(root, 500, 450);

        URL cssURL = MainApp.class.getResource("/css/style.css");
        if (cssURL != null) {
            scene.getStylesheets().add(cssURL.toExternalForm());
        } else {
            System.out.println("⚠️ CSS introuvable : /css/style.css");
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
