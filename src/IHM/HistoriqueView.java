package IHM;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Menage;
import modele.ServiceGestionDechet;

import java.util.List;

public class HistoriqueView {

    private final Stage stage;
    private final Menage menage;
    private final MainApp mainApp;

    public HistoriqueView(MainApp mainApp, Stage stage, Menage menage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.menage = menage;
    }

    public void afficher() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titre = new Label("📜 Historique des dépôts");
        titre.getStyleClass().add("titre");

        ListView<String> listeHistorique = new ListView<>();

        List<String> historique = ServiceGestionDechet.consulterHistoriqueDepots();

        if (historique.isEmpty()) {
            listeHistorique.getItems().add("❌ Aucun dépôt enregistré.");
        } else {
            if (menage.getRole().equalsIgnoreCase("employé")) {
                listeHistorique.getItems().addAll(historique);
            } else {
                int identifiant = menage.getIdentifiant();
                for (String ligne : historique) {
                    if (ligne.contains("Utilisateur " + identifiant)) {
                        listeHistorique.getItems().add(ligne);
                    }
                }
                if (listeHistorique.getItems().isEmpty()) {
                    listeHistorique.getItems().add("ℹ️ Aucun dépôt enregistré pour votre ménage.");
                }
            }
        }

        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> mainApp.afficherMenuPrincipal(menage));

        root.getChildren().addAll(titre, listeHistorique, btnRetour);

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Historique des Dépôts");
        stage.show();
    }
}
