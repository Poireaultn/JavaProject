package IHM;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modele.Menage;
import modele.ServiceGestionVidage;

import java.util.List;

public class GestionVidagesView {

    private final Stage stage;
    private final Menage menage;
    private final MainApp mainApp;

    public GestionVidagesView(MainApp mainApp, Stage stage, Menage menage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.menage = menage;
    }

    public void afficher() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label titre = new Label("♻️ Gestion des Vidages");
        titre.getStyleClass().add("titre");

        Button btnConsulter = new Button("📄 Consulter mes vidages");
        Button btnAjouter = new Button("➕ Ajouter un nouveau vidage");
        Button btnRetour = new Button("Retour");

        TextArea zoneAffichage = new TextArea();
        zoneAffichage.setEditable(false);
        zoneAffichage.setPrefHeight(200);

        btnConsulter.setOnAction(e -> afficherVidages(zoneAffichage));
        btnAjouter.setOnAction(e -> afficherFormulaireAjout(zoneAffichage));
        btnRetour.setOnAction(e -> mainApp.afficherMenuPrincipal(menage));

        root.getChildren().addAll(titre, btnConsulter, btnAjouter, zoneAffichage, btnRetour);

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Gestion des Vidages");
        stage.show();
    }

    private void afficherVidages(TextArea zoneAffichage) {
        zoneAffichage.clear();
        List<String> vidages = ServiceGestionVidage.consulterVidages(menage);

        if (vidages.isEmpty()) {
            zoneAffichage.setText("⚠️ Aucun vidage trouvé.");
        } else {
            zoneAffichage.setText(String.join("\n", vidages));
        }
    }

    private void afficherFormulaireAjout(TextArea zoneAffichage) {
        Stage popup = new Stage();
        popup.setTitle("Ajouter un vidage");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        TextField champQuantite = new TextField();
        champQuantite.setPromptText("Quantité en kg");

        TextField champType = new TextField();
        champType.setPromptText("Type de déchet");

        Button btnValider = new Button("Valider");
        Button btnAnnuler = new Button("Annuler");
        Label message = new Label();

        HBox actions = new HBox(10, btnValider, btnAnnuler);

        btnValider.setOnAction(e -> {
            try {
                int quantite = Integer.parseInt(champQuantite.getText().trim());
                String type = champType.getText().trim();

                if (type.isEmpty() || quantite <= 0) {
                    message.setText("⚠️ Veuillez saisir un type et une quantité valide.");
                    return;
                }

                boolean ok = ServiceGestionVidage.ajouterVidage(menage, quantite, type);
                if (ok) {
                    message.setText("✅ Vidage ajouté !");
                    popup.close();
                    afficherVidages(zoneAffichage);
                } else {
                    message.setText("❌ Échec de l'ajout du vidage.");
                }

            } catch (NumberFormatException ex) {
                message.setText("⚠️ Quantité invalide (entier attendu).");
            }
        });

        btnAnnuler.setOnAction(e -> popup.close());

        layout.getChildren().addAll(
                new Label("Quantité :"), champQuantite,
                new Label("Type de déchet :"), champType,
                actions,
                message
        );

        Scene scene = new Scene(layout, 320, 250);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        popup.setScene(scene);
        popup.show();
    }
}
