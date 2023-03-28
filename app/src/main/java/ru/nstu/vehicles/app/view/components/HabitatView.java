package ru.nstu.vehicles.app.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import ru.nstu.vehicles.app.model.dto.VehicleDto;

import java.util.List;
import java.util.Objects;

public class HabitatView extends Region implements IHabitatView {
    private final static Image unknownSprite = new Image(Objects.requireNonNull(
            HabitatView.class.getResourceAsStream("/ru/nstu/vehicles/app/view/images/unknown-vehicle.jpg")));
    private final static Image motorbikeSprite = new Image(Objects.requireNonNull(
            HabitatView.class.getResourceAsStream("/ru/nstu/vehicles/app/view/images/motorbike-vehicle.jpg")));
    private final static Image automobileSprite = new Image(Objects.requireNonNull(
            HabitatView.class.getResourceAsStream("/ru/nstu/vehicles/app/view/images/automobile-vehicle.jpg")));

    @Override
    public void update(List<VehicleDto> vehicles) {
        deleteAll();
        vehicles.forEach(vehicle -> {
            ImageView iv = new ImageView(switch (vehicle.type()) {
                case MOTORBIKE -> motorbikeSprite;
                case AUTOMOBILE -> automobileSprite;
                default -> unknownSprite;
            });

            iv.setFitHeight(100);
            iv.setFitWidth(100);
            iv.setX(vehicle.x());
            iv.setY(vehicle.y());

            getChildren().add(iv);
        });
    }

    @Override
    public void deleteAll() {
        getChildren().clear();
    }


}
