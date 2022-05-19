package com.example.komandor.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketProductDto {

    private SimpleStringProperty basketGoodId;

    private SimpleStringProperty basketName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BasketProductDto that = (BasketProductDto) o;

        return basketGoodId.equals(that.basketGoodId);
    }

    @Override
    public int hashCode() {
        return basketGoodId.hashCode();
    }

    private SimpleStringProperty basketPrice;

    private SimpleIntegerProperty basketCount;

    private SimpleStringProperty basketTotalPrice;

    public String getBasketGoodId() {
        return basketGoodId.get();
    }

    public void setBasketGoodId(String basketGoodId) {
        this.basketGoodId.set(basketGoodId);
    }

    public SimpleStringProperty basketGoodIdProperty() {
        return basketGoodId;
    }

    public String getBasketName() {
        return basketName.get();
    }

    public void setBasketName(String basketName) {
        this.basketName.set(basketName);
    }

    public SimpleStringProperty basketNameProperty() {
        return basketName;
    }

    public String getBasketPrice() {
        return basketPrice.get();
    }

    public void setBasketPrice(String basketPrice) {
        this.basketPrice.set(basketPrice);
    }

    public SimpleStringProperty basketPriceProperty() {
        return basketPrice;
    }

    public int getBasketCount() {
        return basketCount.get();
    }

    public void setBasketCount(int basketCount) {
        this.basketCount.set(basketCount);
    }

    public SimpleIntegerProperty basketCountProperty() {
        return basketCount;
    }

    public String getBasketTotalPrice() {
        return basketTotalPrice.get();
    }

    public void setBasketTotalPrice(String basketTotalPrice) {
        this.basketTotalPrice.set(basketTotalPrice);
    }

    public SimpleStringProperty basketTotalPriceProperty() {
        return basketTotalPrice;
    }
}
