package IHM;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modele.Poubelle;

public class TableBuilder {

    public static TableView<Poubelle> creerTablePoubelles() {
        TableView<Poubelle> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList());

        TableColumn<Poubelle, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("identifiantPoubelle"));

        TableColumn<Poubelle, String> colVille = new TableColumn<>("Ville");
        colVille.setCellValueFactory(new PropertyValueFactory<>("ville"));

        TableColumn<Poubelle, String> colQuartier = new TableColumn<>("Quartier");
        colQuartier.setCellValueFactory(new PropertyValueFactory<>("quartier"));

        TableColumn<Poubelle, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("typePoubelle"));

        TableColumn<Poubelle, Integer> colCapacite = new TableColumn<>("Capacité");
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));

        TableColumn<Poubelle, Double> colPoids = new TableColumn<>("Poids Actuel");
        colPoids.setCellValueFactory(new PropertyValueFactory<>("poidsActuel"));

        table.getColumns().addAll(colId, colVille, colQuartier, colType, colCapacite, colPoids);
        return table;
    }
}
