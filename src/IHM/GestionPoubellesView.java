package IHM;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modele.*;

import java.util.List;

public class GestionPoubellesView {

    private final Stage stage;
    private final Menage menage;
    private final MainApp mainApp;
    private TableView<Poubelle> tablePoubelles;

    public GestionPoubellesView(MainApp mainApp, Stage stage, Menage menage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.menage = menage;
    }

    public void afficher() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label titre = new Label("🗑️ Gestion des Poubelles");
        titre.getStyleClass().add("titre");

        tablePoubelles = TableBuilder.creerTablePoubelles();
        chargerPoubelles();

        HBox boutons = new HBox(10);
        Button btnAjouter = new Button("Ajouter");
        Button btnSupprimer = new Button("Supprimer");
        Button btnVider = new Button("Vider");
        Button btnRetour = new Button("Retour");

        btnAjouter.setOnAction(e -> {
            if (estEmploye()) {
                new AjouterPoubelleDialog(stage, this::chargerPoubelles).afficher();
            } else {
                afficherAlerte("Accès refusé", "Seuls les employés peuvent ajouter une poubelle.");
            }
        });

        btnSupprimer.setOnAction(e -> {
            if (!estEmploye()) {
                afficherAlerte("Accès refusé", "Seuls les employés peuvent supprimer une poubelle.");
                return;
            }

            Poubelle selection = tablePoubelles.getSelectionModel().getSelectedItem();
            if (selection == null) {
                afficherAlerte("Erreur", "Veuillez sélectionner une poubelle à supprimer.");
                return;
            }

            boolean succes = ServiceGestionPoubelle.supprimerPoubelle(selection.getIdentifiantPoubelle());
            if (succes) {
                afficherConfirmation("✅ Poubelle supprimée avec succès.");
                chargerPoubelles();
            } else {
                afficherAlerte("Erreur", "Impossible de supprimer une poubelle non vide.");
            }
        });

        btnVider.setOnAction(e -> {
            if (!estEmploye()) {
                afficherAlerte("Accès refusé", "Seuls les employés peuvent vider une poubelle.");
                return;
            }

            Poubelle selection = tablePoubelles.getSelectionModel().getSelectedItem();
            if (selection == null) {
                afficherAlerte("Erreur", "Veuillez sélectionner une poubelle à vider.");
                return;
            }

            new ViderPoubelleDialog(selection, menage, this::chargerPoubelles).afficher();
        });

        btnRetour.setOnAction(e -> mainApp.afficherMenuPrincipal(menage));

        boutons.getChildren().addAll(btnAjouter, btnSupprimer, btnVider, btnRetour);
        root.getChildren().addAll(titre, tablePoubelles, boutons);

        Scene scene = new Scene(root, 700, 450);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Gestion des Poubelles");
        stage.show();
    }

    private void chargerPoubelles() {
        tablePoubelles.getItems().clear();

        if (!estEmploye()) {
            afficherAlerte("Accès refusé", "Vous n'avez pas l'autorisation de consulter toutes les poubelles.");
            return;
        }

        List<Poubelle> poubelles = ServiceGestionPoubelle.getToutesLesPoubelles();
        tablePoubelles.getItems().addAll(poubelles);
    }

    private boolean estEmploye() {
        return menage.getRole().equalsIgnoreCase("employé");
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
