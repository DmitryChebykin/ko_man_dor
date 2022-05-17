package com.example.komandor.model;

import javafx.beans.property.SimpleStringProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private SimpleStringProperty goodId;

    private SimpleStringProperty name;

    private SimpleStringProperty description;

    private SimpleStringProperty price;

    public String getGoodId() {
        return goodId.get();
    }

    public SimpleStringProperty goodIdProperty() {
        return goodId;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }
}
