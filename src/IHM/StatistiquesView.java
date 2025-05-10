package IHM;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modele.Menage;
import modele.Poubelle;
import modele.ServiceGestionPoubelle;
import modele.ServiceStatistiques;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class StatistiquesView {

    private final MainApp mainApp;
    private final Stage stage;
    private final Menage menage;

    public StatistiquesView(MainApp mainApp, Stage stage, Menage menage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.menage = menage;
    }

    public void afficher() {
        if (!menage.getRole().equalsIgnoreCase("employé")) {
            afficherErreur("Accès refusé", "Seuls les employés ont accès aux statistiques.");
            return;
        }

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titre = new Label("📈 Statistiques du système");
        titre.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label statsGenerales = new Label("📊 Statistiques globales :");
        TextArea zoneStats = new TextArea();
        zoneStats.setEditable(false);
        zoneStats.setText(ServiceStatistiques.genererRapport());

        Label labelTypes = new Label("📦 Quantité totale par type de déchet :");
        BarChart<String, Number> barTypes = creerBarChart("Type", "Quantité (kg)", ServiceStatistiques.getQuantiteDechetsParType());

        Label labelVidages = new Label("🧾 Vidages par poubelle :");
        BarChart<String, Number> barVidages = creerBarChart("ID Poubelle", "Nombre", ServiceStatistiques.getNbVidagesParPoubelle());

        Label labelDepots = new Label("📅 Déchets déposés par jour :");
        PieChart pieParJour = creerPieChart(ServiceStatistiques.getDepotsParJour());

        Label labelTop = new Label("🏆 Top 3 des types de déchets les plus jetés :");
        BarChart<String, Number> barTop = creerBarChart("Type", "Quantité (kg)", ServiceStatistiques.getTop3TypesDechets());

        Label titrePoubelles = new Label("🟠 Poubelles à surveiller (≥ 80%) :");
        ListView<String> listePoubelles = new ListView<>();
        List<Poubelle> aSurveiller = ServiceGestionPoubelle.getPoubellesBientotPleines();
        if (aSurveiller.isEmpty()) {
            listePoubelles.getItems().add("✅ Aucune poubelle proche de la saturation.");
        } else {
            for (Poubelle p : aSurveiller) {
                listePoubelles.getItems().add("Poubelle ID " + p.getIdentifiantPoubelle()
                        + " (" + p.getVille() + ", " + p.getQuartier() + ") : "
                        + p.getPoidsActuel() + "/" + p.getCapacite() + " kg ("
                        + String.format("%.0f", 100.0 * p.getPoidsActuel() / p.getCapacite()) + "%)");
            }
        }

        Button btnExporter = new Button("📤 Exporter en CSV");
        Button btnImporter = new Button("📥 Importer un CSV");
        Button btnTXT = new Button("📄 Générer le bilan (TXT)");

        btnExporter.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exporter les données en CSV");
            fileChooser.setInitialFileName("export_tri.csv");
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                boolean success = ServiceStatistiques.exporterBaseEnCSV(file.getAbsolutePath());
                afficherInformation(success ? "✅ Export CSV réussi." : "❌ Erreur pendant l'export.");
            }
        });

        btnImporter.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Importer un fichier CSV");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                boolean success = ServiceStatistiques.importerBaseDepuisCSV(file.getAbsolutePath());
                afficherInformation(success ? "✅ Import CSV réussi." : "❌ Erreur lors de l'import.");
            }
        });

        btnTXT.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exporter un bilan au format .txt");
            fileChooser.setInitialFileName("bilan_tri_selectif.txt");
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                boolean success = ServiceStatistiques.genererBilanTxt(file.getAbsolutePath());
                afficherInformation(success ? "✅ Bilan TXT généré avec succès." : "❌ Erreur lors de la génération du fichier.");
            }
        });

        HBox boutons = new HBox(10, btnExporter, btnImporter, btnTXT);
        boutons.setPadding(new Insets(10, 0, 0, 0));

        Button retour = new Button("Retour");
        retour.setOnAction(e -> mainApp.afficherMenuPrincipal(menage));

        root.getChildren().addAll(
                titre,
                statsGenerales, zoneStats,
                labelTypes, barTypes,
                labelVidages, barVidages,
                labelDepots, pieParJour,
                labelTop, barTop,
                titrePoubelles, listePoubelles,
                boutons,
                retour
        );

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setPadding(new Insets(10));

        Scene scene = new Scene(scroll, 800, 700);
        stage.setScene(scene);
        stage.setTitle("Statistiques");
        stage.show();
    }

    private BarChart<String, Number> creerBarChart(String xLabel, String yLabel, Map<String, Number> data) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        data.forEach((k, v) -> serie.getData().add(new XYChart.Data<>(k, v)));
        chart.getData().add(serie);
        chart.setLegendVisible(false);
        chart.setPrefHeight(300);
        return chart;
    }

    private PieChart creerPieChart(Map<String, Number> data) {
        PieChart chart = new PieChart();
        data.forEach((k, v) -> chart.getData().add(new PieChart.Data(k, v.doubleValue())));
        chart.setLegendVisible(true);
        chart.setPrefHeight(300);
        return chart;
    }

    private void afficherInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
