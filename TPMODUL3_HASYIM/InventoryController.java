import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class InventoryController {
    @FXML private TableView<Album> tableView;
    @FXML private TableColumn<Album, String> colAlbumName;
    @FXML private TableColumn<Album, String> colArtist;
    @FXML private TableColumn<Album, Integer> colTotal;
    @FXML private TableColumn<Album, Integer> colAvailable;
    @FXML private TableColumn<Album, Integer> colRented;
    @FXML private TextField tfAlbumName, tfArtist, tfTotal, tfRented;

    private final ObservableList<Album> albumList = FXCollections.observableArrayList();

    public void initialize() {
        colAlbumName.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAlbumName()));
        colArtist.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getArtist()));
        colTotal.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getTotal()).asObject());
        colAvailable.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getAvailable()).asObject());
        colRented.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getRented()).asObject());

        tableView.setItems(albumList);
    }

    public void handleAdd() {
        try {
            String name = tfAlbumName.getText();
            String artist = tfArtist.getText();
            int total = Integer.parseInt(tfTotal.getText());
            int rented = Integer.parseInt(tfRented.getText());

            if (rented > total) {
                showError("Jumlah rented tidak boleh melebihi total album.");
                return;
            }

            int available = total - rented;
            albumList.add(new Album(name, artist, total, available));
            showAlert("Album " + name + " berhasil ditambahkan.");
            clearFields();
        } catch (Exception e) {
            showError("Silahkan cek data yang anda masukan!");
        }
    }

    public void handleDelete() {
        Album selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            albumList.remove(selected);
            showAlert("Album berhasil dihapus.");
        } else {
            showError("Pilih album terlebih dahulu.");
        }
    }

    public void handleUpdate() {
        Album selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                String name = tfAlbumName.getText();
                String artist = tfArtist.getText();
                int total = Integer.parseInt(tfTotal.getText());
                int rented = Integer.parseInt(tfRented.getText());

                if (rented > total) {
                    showError("Jumlah rented tidak boleh melebihi total album.");
                    return;
                }

                int available = total - rented;
                selected.setAlbumName(name);
                selected.setArtist(artist);
                selected.setTotal(total);
                selected.setAvailable(available);

                tableView.refresh();
                showAlert("Album berhasil diperbarui.");
            } catch (Exception e) {
                showError("Input tidak valid!");
            }
        } else {
            showError("Pilih album terlebih dahulu.");
        }
    }

    public void handleRent() {
        Album selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getAvailable() > 0) {
            selected.setAvailable(selected.getAvailable() - 1);
            tableView.refresh();
            showAlert("Album berhasil disewa.");
        } else {
            showError("Album tidak tersedia untuk disewa.");
        }
    }

    public void handleRowClick(MouseEvent event) {
        Album selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            tfAlbumName.setText(selected.getAlbumName());
            tfArtist.setText(selected.getArtist());
            tfTotal.setText(String.valueOf(selected.getTotal()));
            tfRented.setText(String.valueOf(selected.getRented()));
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(message);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Terjadi Kesalahan");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        tfAlbumName.clear();
        tfArtist.clear();
        tfTotal.clear();
        tfRented.clear();
    }
}
