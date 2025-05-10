package IHM;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.*;

import java.util.List;

public class ViderPoubelleDialog {

    private final Stage dialog;
    private final Menage menage;
    private final Runnable callbackChargement;
    private final Poubelle poubelle;

    public ViderPoubelleDialog(Poubelle poubelle, Menage menage, Runnable callbackChargement) {
        this.poubelle = poubelle;
        this.menage = menage;
        this.callbackChargement = callbackChargement;
        this.dialog = new Stage();
    }

    public void afficher() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titre = new Label("♻️ Vider la poubelle sélectionnée");
        titre.getStyleClass().add("titre");

        Label infoPoubelle = new Label("ID : " + poubelle.getIdentifiantPoubelle() +
                " | Type : " + poubelle.getTypePoubelle() +
                " | Contenu actuel : " + poubelle.getPoidsActuel() + " kg");

        ComboBox<String> comboCentres = new ComboBox<>();
        comboCentres.setPromptText("Sélectionnez un centre de tri");

        List<CentreDeTri> centres = ServiceGestionPoubelle.recupererCentresDeTri();
        for (CentreDeTri centre : centres) {
            comboCentres.getItems().add(centre.getIdentifiantCentre() + " - " + centre.getNom());
        }

        Button btnVider = new Button("Vider");
        Button btnAnnuler = new Button("Annuler");

        btnVider.setOnAction(e -> {
            if (!menage.getRole().equalsIgnoreCase("employé")) {
                afficherAlerte("Accès refusé", "Seuls les employés peuvent effectuer cette opération.");
                return;
            }

            String selection = comboCentres.getValue();
            if (selection == null || !selection.contains(" - ")) {
                afficherAlerte("Erreur", "Veuillez sélectionner un centre de tri valide.");
                return;
            }

            try {
                int idCentre = Integer.parseInt(selection.split(" - ")[0].trim());
                boolean success = ServiceGestionPoubelle.viderPoubelleDansCentre(poubelle.getIdentifiantPoubelle(), idCentre, menage);

                if (success) {
                    afficherAlerte("Succès", "✅ Poubelle vidée avec succès !");
                    if (callbackChargement != null) callbackChargement.run();
                    dialog.close();
                } else {
                    afficherAlerte("Erreur", "❌ Impossible de vider cette poubelle.");
                }

            } catch (NumberFormatException ex) {
                afficherAlerte("Erreur", "Format invalide pour le centre de tri.");
            }
        });

        btnAnnuler.setOnAction(e -> dialog.close());

        root.getChildren().addAll(
                titre,
                infoPoubelle,
                new Label("Centre de tri :"),
                comboCentres,
                btnVider,
                btnAnnuler
        );

        Scene scene = new Scene(root, 420, 300);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        dialog.setScene(scene);
        dialog.setTitle("Vider une Poubelle");
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
