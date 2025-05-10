package IHM;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.*;

import java.util.List;
import java.util.stream.Collectors;

public class ViderCorbeilleView {

    private final MainApp mainApp;
    private final Stage stage;
    private final Menage menage;

    public ViderCorbeilleView(MainApp mainApp, Stage stage, Menage menage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.menage = menage;
    }

    public void afficher() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        Label titre = new Label("♻️ Vider une corbeille dans une poubelle compatible");
        titre.getStyleClass().add("titre");

        ComboBox<Integer> comboCorbeilles = new ComboBox<>();
        ComboBox<String> comboDechets = new ComboBox<>();
        ComboBox<Integer> comboPoubelles = new ComboBox<>();

        TextArea infoCorbeille = new TextArea();
        infoCorbeille.setEditable(false);
        infoCorbeille.setPromptText("Contenu de la corbeille sélectionnée...");

        TextField champQuantite = new TextField();
        champQuantite.setPromptText("Quantité (kg)");

        Label infoPoubelle = new Label();

        List<Corbeille> corbeilles = ServiceGestionCorbeille.consulterCorbeilles(menage);
        for (Corbeille c : corbeilles) {
            comboCorbeilles.getItems().add(c.getIdCorbeille());
        }

        comboCorbeilles.setOnAction(e -> {
            comboDechets.getItems().clear();
            infoCorbeille.clear();

            Integer idCorbeille = comboCorbeilles.getValue();
            if (idCorbeille != null) {
                List<Dechet> dechets = ServiceGestionDechet.recupererDechetsParCorbeille(idCorbeille);
                for (Dechet d : dechets) {
                    comboDechets.getItems().add(d.getIdentifiantDechet() + " - " + d.getTypeDechet());
                }

                String details = dechets.stream()
                        .map(d -> "ID " + d.getIdentifiantDechet() + " - " + d.getTypeDechet() + " (" + d.getPoids() + " kg)")
                        .collect(Collectors.joining("\n"));
                infoCorbeille.setText(details.isEmpty() ? "⚠️ Aucun déchet." : details);
            }
        });

        comboDechets.setOnAction(e -> {
            comboPoubelles.getItems().clear();
            infoPoubelle.setText("");

            String selection = comboDechets.getValue();
            if (selection != null && selection.contains(" - ")) {
                String typeDechet = selection.split(" - ")[1];

                List<Poubelle> poubellesCompatibles = ServiceGestionPoubelle.getToutesLesPoubelles().stream()
                        .filter(p -> p.getTypePoubelle().equalsIgnoreCase(typeDechet))
                        .collect(Collectors.toList());

                for (Poubelle p : poubellesCompatibles) {
                    comboPoubelles.getItems().add(p.getIdentifiantPoubelle());
                }
            }
        });

        comboPoubelles.setOnAction(e -> {
            Integer id = comboPoubelles.getValue();
            if (id != null) {
                Poubelle p = ServiceGestionPoubelle.getToutesLesPoubelles().stream()
                        .filter(pb -> pb.getIdentifiantPoubelle() == id)
                        .findFirst()
                        .orElse(null);
                if (p != null) {
                    infoPoubelle.setText("Type : " + p.getTypePoubelle() +
                            ", Capacité : " + p.getCapacite() + " kg, Actuel : " + p.getPoidsActuel() + " kg");
                }
            }
        });


        Button btnVider = new Button("Vider");
        btnVider.setOnAction(e -> {
            try {
                Integer idCorbeille = comboCorbeilles.getValue();
                String dechetSelectionne = comboDechets.getValue();
                Integer idPoubelle = comboPoubelles.getValue();
                double quantite = Double.parseDouble(champQuantite.getText().trim());

                if (idCorbeille == null || dechetSelectionne == null || idPoubelle == null || quantite <= 0) {
                    afficherAlerte("Erreur", "Veuillez remplir tous les champs correctement.");
                    return;
                }

                int idDechet = Integer.parseInt(dechetSelectionne.split(" - ")[0]);

                boolean success = ServiceGestionCorbeille.viderDansPoubelle(menage, idCorbeille, idDechet, idPoubelle, quantite);
                if (success) {
                    afficherConfirmation("✅ Déchet vidé avec succès !");
                    champQuantite.clear();
                    comboCorbeilles.getSelectionModel().clearSelection();
                    comboDechets.getItems().clear();
                    comboPoubelles.getItems().clear();
                    infoCorbeille.clear();
                    infoPoubelle.setText("");
                } else {
                    afficherAlerte("Erreur", "Impossible de vider ce déchet (vérifiez la quantité ou la compatibilité).");
                }

            } catch (Exception ex) {
                afficherAlerte("Erreur", "Veuillez vérifier les champs saisis.");
            }
        });

        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> mainApp.afficherMenuPrincipal(menage));

        root.getChildren().addAll(
                titre,
                new Label("Corbeille :"), comboCorbeilles,
                infoCorbeille,
                new Label("Déchet :"), comboDechets,
                new Label("Poubelle compatible :"), comboPoubelles,
                infoPoubelle,
                champQuantite,
                btnVider,
                btnRetour
        );

        Scene scene = new Scene(root, 500, 600);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Vider une Corbeille");
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
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
