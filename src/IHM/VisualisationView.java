package IHM;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class VisualisationView {

    private final Stage stage;
    private final MainApp mainApp;

    public VisualisationView(MainApp mainApp, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
    }

    public void afficher() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titre = new Label("♻️ Bienvenue dans le module de visualisation du tri sélectif !");
        titre.getStyleClass().add("titre");

        Label intro = new Label("Voici quelques informations utiles sur le tri sélectif :");
        intro.getStyleClass().add("sous-titre");

        List<String> typesDechets = Arrays.asList(
                "🟡 Plastique : bouteilles, flacons, emballages plastiques",
                "🔵 Papier : journaux, magazines, cartons",
                "🟢 Verre : bouteilles, pots, bocaux (sans bouchon)",
                "⚫ Métal : canettes, boîtes de conserve",
                "❌ Déchets interdits : piles, médicaments, déchets toxiques"
        );
        VBox liste = new VBox(5);
        for (String type : typesDechets) {
            Label ligne = new Label("• " + type);
            ligne.getStyleClass().add("info-ligne");
            liste.getChildren().add(ligne);
        }

        Label astuces = new Label("🧠 Astuces de tri :");
        astuces.getStyleClass().add("sous-titre");

        List<String> conseils = Arrays.asList(
                "✔️ Videz les emballages avant de les jeter.",
                "✔️ Évitez les sacs plastiques dans la poubelle de tri.",
                "✔️ Ne lavez pas les emballages, il suffit qu'ils soient bien vidés.",
                "✔️ Regardez les pictogrammes de tri sur les emballages."
        );
        VBox blocConseils = new VBox(5);
        for (String conseil : conseils) {
            Label label = new Label("→ " + conseil);
            label.getStyleClass().add("info-conseil");
            blocConseils.getChildren().add(label);
        }

        Button retour = new Button("Retour");
        retour.setOnAction(e -> mainApp.afficherEcranConnexion());

        root.getChildren().addAll(titre, intro, liste, astuces, blocConseils, retour);

        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Informations sur le tri");
        stage.show();
    }
}
