package IHM;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.*;

import java.util.List;

public class GestionCorbeillesView {

    private final Stage stage;
    private final Menage menage;
    private final MainApp mainApp;
    private ListView<Integer> listeCorbeilles;
    private ListView<String> listeDechets;

    public GestionCorbeillesView(MainApp mainApp, Stage stage, Menage menage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.menage = menage;
    }

    public void afficher() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titre = new Label("🧺 Gestion des Corbeilles");
        titre.getStyleClass().add("titre");

        listeCorbeilles = new ListView<>();
        listeDechets = new ListView<>();
        chargerCorbeilles();

        listeCorbeilles.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                chargerDechetsPourCorbeille(newVal);
            } else {
                listeDechets.getItems().clear();
            }
        });

        Button btnAjouter = new Button("Ajouter une corbeille");
        Button btnSupprimer = new Button("Supprimer la corbeille sélectionnée");
        Button btnRetour = new Button("Retour");

        btnAjouter.setOnAction(e -> {
            boolean ajout = ServiceGestionCorbeille.ajouterCorbeille(menage);
            if (ajout) {
                afficherConfirmation("✅ Corbeille ajoutée !");
                chargerCorbeilles();
            } else {
                afficherAlerte("Erreur", "Impossible d’ajouter une corbeille.");
            }
        });

        btnSupprimer.setOnAction(e -> {
            Integer id = listeCorbeilles.getSelectionModel().getSelectedItem();
            if (id == null) {
                afficherAlerte("Erreur", "Veuillez sélectionner une corbeille à supprimer.");
                return;
            }

            boolean suppression = ServiceGestionCorbeille.supprimerCorbeille(menage, id);
            if (suppression) {
                afficherConfirmation("✅ Corbeille supprimée.");
                chargerCorbeilles();
                listeDechets.getItems().clear();
            } else {
                afficherAlerte("Erreur", "Impossible de supprimer cette corbeille (elle contient peut-être des déchets).");
            }
        });

        btnRetour.setOnAction(e -> mainApp.afficherMenuPrincipal(menage));

        root.getChildren().addAll(
                titre,
                new Label("Mes Corbeilles :"), listeCorbeilles,
                new Label("Contenu de la corbeille sélectionnée :"), listeDechets,
                btnAjouter, btnSupprimer, btnRetour
        );

        Scene scene = new Scene(root, 500, 450);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Gestion des Corbeilles");
        stage.show();
    }

    private void chargerCorbeilles() {
        listeCorbeilles.getItems().clear();
        List<Corbeille> corbeilles = ServiceGestionCorbeille.consulterCorbeilles(menage);
        for (Corbeille c : corbeilles) {
            listeCorbeilles.getItems().add(c.getIdCorbeille());
        }
    }

    private void chargerDechetsPourCorbeille(int idCorbeille) {
        listeDechets.getItems().clear();
        List<Dechet> dechets = ServiceGestionDechet.recupererDechetsParCorbeille(idCorbeille);
        if (dechets.isEmpty()) {
            listeDechets.getItems().add("⚠️ Aucun déchet dans cette corbeille.");
        } else {
            for (Dechet d : dechets) {
                listeDechets.getItems().add("ID: " + d.getIdentifiantDechet() +
                        " | Type: " + d.getTypeDechet() +
                        " | Poids: " + d.getPoids() + " kg");
            }
        }
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
