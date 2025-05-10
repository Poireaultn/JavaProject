package IHM;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.*;

import java.util.List;

public class AchatBonsView {

    private final Stage stage;
    private final Menage menage;
    private final MainApp mainApp;

    public AchatBonsView(MainApp mainApp, Stage stage, Menage menage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.menage = menage;
    }

    public void afficher() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titre = new Label("🛍️ Achat de bons d'achat");
        titre.getStyleClass().add("titre");

        Label points = new Label("Points disponibles : " + menage.getPointsFidelite());

        ListView<String> listeBons = new ListView<>();
        ListView<String> listeAchetes = new ListView<>();
        TextField champIdBon = new TextField();
        champIdBon.setPromptText("ID du bon à acheter");

        Button btnAcheter = new Button("Acheter");
        Button btnAfficherAchetes = new Button("📄 Mes bons achetés");
        Button btnRetour = new Button("Retour");

        List<BonAchat> bonsDisponibles = ServiceGestionBons.getBonsDisponibles();
        if (bonsDisponibles.isEmpty()) {
            listeBons.getItems().add("Aucun bon disponible pour le moment.");
        } else {
            for (BonAchat bon : bonsDisponibles) {
                listeBons.getItems().add(
                    "ID: " + bon.getIdentifiantBon()
                    + " | Catégorie: " + bon.getCategorieProduit()
                    + " | Valeur: " + bon.getPrix() + "€"
                    + " | Coût: " + bon.getCoutPoints() + " pts"
                    + " | Expire le: " + bon.getDateExpiration()
                );
            }
        }

        btnAcheter.setOnAction(e -> {
            try {
                int id = Integer.parseInt(champIdBon.getText().trim());

                BonAchat bonAchat = bonsDisponibles.stream()
                        .filter(b -> b.getIdentifiantBon() == id)
                        .findFirst()
                        .orElse(null);

                if (bonAchat == null) {
                    afficherAlerte("❌ Erreur", "ID de bon invalide.");
                    return;
                }

                boolean achatReussi = ServiceGestionBons.acheterBon(menage, id);
                if (achatReussi) {
                    afficherAlerte("✅ Succès", "Bon acheté ! Nouveaux points : " + menage.getPointsFidelite());
                    points.setText("Points disponibles : " + menage.getPointsFidelite());
                    champIdBon.clear();
                } else {
                    afficherAlerte("❌ Erreur", "Achat échoué (points insuffisants ?).");
                }

            } catch (NumberFormatException ex) {
                afficherAlerte("❌ Erreur", "Veuillez entrer un ID valide.");
            }
        });

        btnAfficherAchetes.setOnAction(e -> {
            listeAchetes.getItems().clear();
            List<String> bonsAchetes = ServiceGestionBons.getBonsAchetesParMenage(menage);
            if (bonsAchetes.isEmpty()) {
                listeAchetes.getItems().add("Aucun bon acheté.");
            } else {
                listeAchetes.getItems().addAll(bonsAchetes);
            }
        });

        btnRetour.setOnAction(e -> mainApp.afficherMenuPrincipal(menage));

        root.getChildren().addAll(
                titre, points,
                new Label("📋 Bons disponibles :"), listeBons,
                champIdBon, btnAcheter,
                btnAfficherAchetes,
                new Label("🎫 Mes bons achetés :"), listeAchetes,
                btnRetour
        );

        Scene scene = new Scene(root, 700, 600);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Bons d'achat");
        stage.show();
    }

    private void afficherAlerte(String titre, String contenu) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}
