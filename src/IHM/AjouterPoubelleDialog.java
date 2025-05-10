package IHM;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modele.ServiceGestionDechet;
import modele.ServiceGestionPoubelle;

public class AjouterPoubelleDialog {

    private final Stage dialog;
    private final Runnable callbackChargement;

    public AjouterPoubelleDialog(Stage parent, Runnable callbackChargement) {
        this.dialog = new Stage(); 
        this.callbackChargement = callbackChargement;
    }

    public void afficher() {
        dialog.setTitle("Ajouter une Poubelle");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField champVille = new TextField();
        TextField champQuartier = new TextField();
        ComboBox<String> comboTypeDechet = new ComboBox<>();
        TextField champCapacite = new TextField();


        ServiceGestionDechet.recupererTousLesTypesDeDechets()
                .forEach(comboTypeDechet.getItems()::add);

        grid.add(new Label("Ville :"), 0, 0);
        grid.add(champVille, 1, 0);
        grid.add(new Label("Quartier :"), 0, 1);
        grid.add(champQuartier, 1, 1);
        grid.add(new Label("Type de déchet :"), 0, 2);
        grid.add(comboTypeDechet, 1, 2);
        grid.add(new Label("Capacité (kg) :"), 0, 3);
        grid.add(champCapacite, 1, 3);

        Button btnValider = new Button("Ajouter");
        Button btnAnnuler = new Button("Annuler");

        grid.add(btnValider, 0, 4);
        grid.add(btnAnnuler, 1, 4);

        btnValider.setOnAction(e -> {
            String ville = champVille.getText().trim();
            String quartier = champQuartier.getText().trim();
            String typeDechet = comboTypeDechet.getValue();
            String capaciteStr = champCapacite.getText().trim();

            if (ville.isEmpty() || quartier.isEmpty() || typeDechet == null || capaciteStr.isEmpty()) {
                afficherAlerte("Champs manquants", "Veuillez remplir tous les champs.");
                return;
            }

            try {
                int capacite = Integer.parseInt(capaciteStr);

                boolean success = ServiceGestionPoubelle.ajouterPoubelle(ville, quartier, typeDechet, capacite);
                if (success) {
                    afficherAlerte("Succès", "✅ Poubelle ajoutée avec succès.");
                    dialog.close();
                    if (callbackChargement != null) {
                        callbackChargement.run();
                    }
                } else {
                    afficherAlerte("Erreur", "❌ Échec de l'ajout de la poubelle.");
                }

            } catch (NumberFormatException ex) {
                afficherAlerte("Erreur", "⚠️ Capacité invalide. Veuillez entrer un nombre.");
            }
        });

        btnAnnuler.setOnAction(e -> dialog.close());

        Scene scene = new Scene(grid, 400, 250);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        dialog.setScene(scene);
        dialog.show();
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
