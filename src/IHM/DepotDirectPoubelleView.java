package IHM;
 
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.*;
 
import java.util.List;
 
public class DepotDirectPoubelleView {
 
    private final MainApp mainApp;
    private final Stage stage;
    private final Menage menage;
 
    public DepotDirectPoubelleView(MainApp mainApp, Stage stage, Menage menage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.menage = menage;
    }
 
    public void afficher() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
 
        Label titre = new Label("🚮 Dépôt direct dans une poubelle");
 
        ComboBox<String> comboTypesDechets = new ComboBox<>();
        ComboBox<Integer> comboPoubelles = new ComboBox<>();
        Label infoPoubelle = new Label();
        TextField champQuantite = new TextField();
        champQuantite.setPromptText("Quantité à jeter (kg)");
 
        // Chargement des types de déchets
        List<String> types = ServiceGestionDechet.recupererTousLesTypesDeDechets();
        comboTypesDechets.getItems().addAll(types);
        comboTypesDechets.setPromptText("Type de déchet");
 
        // Chargement des poubelles
        List<Poubelle> poubelles = ServiceGestionPoubelle.getToutesLesPoubelles();
        for (Poubelle p : poubelles) {
            comboPoubelles.getItems().add(p.getIdentifiantPoubelle());
        }
        comboPoubelles.setPromptText("Sélectionnez une poubelle");
 
        comboPoubelles.setOnAction(e -> {
            Integer id = comboPoubelles.getValue();
            if (id != null) {
                Poubelle p = poubelles.stream()
                        .filter(pb -> pb.getIdentifiantPoubelle() == id)
                        .findFirst()
                        .orElse(null);
                if (p != null) {
                    infoPoubelle.setText("Type : " + p.getTypePoubelle() +
                            " | Capacité : " + p.getCapacite() + "kg | Actuel : " + p.getPoidsActuel() + "kg");
                }
            }
        });
 
        Button btnValider = new Button("Déposer");
        Button btnRetour = new Button("Retour");
 
        btnValider.setOnAction(e -> {
            String typeDechet = comboTypesDechets.getValue();
            Integer idPoubelle = comboPoubelles.getValue();
            String quantiteTexte = champQuantite.getText().trim();
 
            if (typeDechet == null || idPoubelle == null || quantiteTexte.isEmpty()) {
                afficherAlerte("Champs manquants", "Veuillez remplir tous les champs.");
                return;
            }
 
            double quantite;
            try {
                quantite = Double.parseDouble(quantiteTexte);
                if (quantite <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                afficherAlerte("Quantité invalide", "Veuillez entrer une quantité valide (> 0).");
                return;
            }
 
            Poubelle poubelle = ServiceGestionPoubelle.recupererPoubelleParId(idPoubelle);
            if (poubelle == null) {
                afficherAlerte("Erreur", "Poubelle introuvable.");
                return;
            }
 
            // Vérification de compatibilité
            boolean estCompatible = ServiceGestionDechet.verifierTypeDeDechet(typeDechet, poubelle.getTypePoubelle());
 
            // Création du déchet
            Dechet dechet = new Dechet(-1, typeDechet, quantite, menage.getIdentifiant());
 
            // Tentative de dépôt même en cas de mauvais type
            boolean success = ServiceGestionDechet.deposerDechetDansPoubelle(dechet, poubelle, menage, quantite);
 
            if (success) {
                if (!estCompatible) {
                	menage.retirerPoints(2*quantite);
                    afficherConfirmation("⚠️ Mauvais type ! Le déchet a été jeté mais vous perdez " + quantite + " points.");
                } else {
                    afficherConfirmation("✅ Déchet jeté avec succès ! +" + quantite + " points.");
                }
 
                champQuantite.clear();
                comboTypesDechets.getSelectionModel().clearSelection();
                comboPoubelles.getSelectionModel().clearSelection();
                infoPoubelle.setText("");
            } else {
                afficherAlerte("Échec", "Capacité insuffisante pour ce déchet.");
            }
        });
 
        btnRetour.setOnAction(e -> mainApp.afficherMenuPrincipal(menage));
 
        root.getChildren().addAll(
                titre,
                new Label("Type de déchet :"), comboTypesDechets,
                new Label("Poubelle :"), comboPoubelles,
                infoPoubelle,
                champQuantite,
                btnValider,
                btnRetour
        );
 
        Scene scene = new Scene(root, 450, 450);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
 
        stage.setScene(scene);
        stage.setTitle("Dépôt direct");
        stage.show();
    }
 
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
 
    private void afficherConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}