package IHM;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.*;

import java.util.List;

public class DepotDechetView {

    private final Stage stage;
    private final Menage menage;
    private final MainApp mainApp;

    public DepotDechetView(MainApp mainApp, Stage stage, Menage menage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.menage = menage;
    }

    public void afficher() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titre = new Label("🗑️ Dépôt de déchets");
        titre.getStyleClass().add("titre");

        ComboBox<Integer> comboCorbeille = new ComboBox<>();
        ComboBox<String> comboTypeDechet = new ComboBox<>();
        TextField champPoids = new TextField();
        champPoids.setPromptText("Poids (kg)");

        List<Corbeille> corbeilles = ServiceGestionCorbeille.consulterCorbeilles(menage);
        for (Corbeille c : corbeilles) {
            comboCorbeille.getItems().add(c.getIdCorbeille());
        }
        comboCorbeille.setPromptText("Sélectionnez une corbeille");

        // Charger les types de déchets
        List<String> types = ServiceGestionDechet.recupererTousLesTypesDeDechets();
        comboTypeDechet.getItems().addAll(types);
        comboTypeDechet.setPromptText("Sélectionnez un type de déchet");

        Button btnAjouter = new Button("Ajouter un déchet");
        Button btnRetour = new Button("Retour");

        btnAjouter.setOnAction(e -> {
            try {
                Integer idCorbeille = comboCorbeille.getValue();
                String typeDechet = comboTypeDechet.getValue();
                double poids = Double.parseDouble(champPoids.getText().trim());

                if (idCorbeille == null || typeDechet == null || poids <= 0) {
                    afficherAlerte("Erreur", "Veuillez remplir tous les champs correctement.");
                    return;
                }

                Corbeille corbeille = corbeilles.stream()
                        .filter(c -> c.getIdCorbeille() == idCorbeille)
                        .findFirst()
                        .orElse(null);

                if (corbeille == null) {
                    afficherAlerte("Erreur", "Corbeille introuvable.");
                    return;
                }

                boolean reussi = ServiceGestionDechet.ajouterDechetDansCorbeille(corbeille, typeDechet, poids);

                if (reussi) {
                    afficherConfirmation("✅ Déchet ajouté à la corbeille.");
                    champPoids.clear();
                } else {
                    afficherAlerte("Erreur", "❌ Impossible d'ajouter le déchet.");
                }

            } catch (NumberFormatException ex) {
                afficherAlerte("Erreur", "Le poids doit être un nombre valide.");
            }
        });

        btnRetour.setOnAction(e -> mainApp.afficherMenuPrincipal(menage));

        root.getChildren().addAll(
                titre,
                new Label("Corbeille :"), comboCorbeille,
                new Label("Type de déchet :"), comboTypeDechet,
                champPoids,
                btnAjouter,
                btnRetour
        );

        Scene scene = new Scene(root, 400, 330);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        String css = getClass().getResource("style.css") != null ?
                getClass().getResource("style.css").toExternalForm() : null;
        if (css != null) {
            scene.getStylesheets().add(css);
        }

        stage.setScene(scene);
        stage.setTitle("Dépôt de Déchets");
        stage.show();
    }

    private void afficherConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
