package ru.nstu.vehicles.app.view.components;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Pair;
import ru.nstu.vehicles.app.dto.DownloadVehiclesRequestDto;
import ru.nstu.vehicles.app.model.service.IServerUpdaterService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.stream.Collectors;

public class ConnectionListView extends TableView<Pair<InetSocketAddress, Boolean>> {
    private  IServerUpdaterService serverUpdaterService;
    private ObservableMap<InetSocketAddress, Boolean> addresses;

    public ConnectionListView() {
        TableColumn<Pair<InetSocketAddress, Boolean>, String> addressTableColumn = new TableColumn<>();
        addressTableColumn.setCellValueFactory((cellDataFeatures) ->
                new SimpleStringProperty(cellDataFeatures.getValue().getKey().toString()));

        TableColumn<Pair<InetSocketAddress, Boolean>, Button> loadVehiclesButtonTableColumn = new TableColumn<>();
        loadVehiclesButtonTableColumn.setCellValueFactory((cellDataFeatures) -> {
            if (!cellDataFeatures.getValue().getValue())
                return new SimpleObjectProperty<>();
            Button button = new Button("load");

            button.setOnAction(event -> {
                try {
                    this.serverUpdaterService.send(new DownloadVehiclesRequestDto(
                            cellDataFeatures.getValue().getKey()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            return new SimpleObjectProperty<>(button);
        });


        getColumns().add(addressTableColumn);
        getColumns().add(loadVehiclesButtonTableColumn);
    }

    public void bindConnectionMap(ObservableMap<InetSocketAddress, Boolean> addresses) {
        this.addresses = addresses;

        ObservableList<Pair<InetSocketAddress, Boolean>> keys = FXCollections.observableArrayList(
                addresses.entrySet().stream()
                        .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList())
        );

        this.addresses.addListener((MapChangeListener<InetSocketAddress, Boolean>) change -> {
            if (change.wasRemoved())
                keys.removeIf(pair -> pair.getKey().equals(change.getKey()));
            if (change.wasAdded())
                keys.add(new Pair<>(change.getKey(), change.getValueAdded()));
            System.out.println("keys=" + keys);
        });

        setItems(keys);

//        ObservableList<InetSocketAddress> keys = FXCollections.observableArrayList(addresses.keySet());
//
//        this.addresses.addListener((MapChangeListener<InetSocketAddress, Boolean>) change -> {
//            if (change.wasRemoved())
//                keys.remove(change.getKey());
//            if (change.wasAdded())
//                keys.add(change.getKey());
//            System.out.println("keys=" + keys);
//        });
//
//        setItems(keys);
    }

    public void setServerUpdaterService(IServerUpdaterService serverUpdaterService) {
        this.serverUpdaterService = serverUpdaterService;
    }
}
