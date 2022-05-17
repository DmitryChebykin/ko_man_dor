package com.example.komandor.controller;

import com.example.komandor.model.ProductDto;
import com.example.komandor.service.GoodService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@FxmlView(value = "/ui/MainController.fxml")
@RequiredArgsConstructor
public class MainController {
    private final GoodService goodService;

    @FXML
    public Text header;

    @FXML
    public TextField searchBox;

    @FXML
    private TableView<ProductDto> productTable;

    @FXML
    private TableColumn<String, ProductDto> description;

    @FXML
    private TableColumn<String, ProductDto> name;

    @FXML
    private TableColumn<String, ProductDto> price;

    @FXML
    private TableColumn<String, ProductDto> goodId;

    @FXML
    public void initialize() {
        List<ProductDto> productDtoList = goodService.getGoodInfoList().stream().map(e -> ProductDto.builder().goodId(new SimpleStringProperty(e.getId())).name(new SimpleStringProperty(e.getName())).description(new SimpleStringProperty(e.getDescription())).price(new SimpleStringProperty(e.getPrice())).build()).collect(Collectors.toList());

        FilteredList<ProductDto> filterItems = new FilteredList<>(FXCollections.observableArrayList(productDtoList));

        goodId.setCellValueFactory(new PropertyValueFactory<>("GoodId"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        productTable.setItems(filterItems);

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> productTable.setItems(filterList(productDtoList, newValue.toLowerCase())));
    }

    private ObservableList<ProductDto> filterList(List<ProductDto> productDtos, String searchText) {
        List<ProductDto> filteredList = productDtos.stream().filter(e -> searchFindsProduct(e, searchText)).collect(Collectors.toList());

        return FXCollections.observableList(filteredList);
    }

    private boolean searchFindsProduct(ProductDto productDto, String searchText) {
        return (productDto.getName().toLowerCase().contains(searchText.toLowerCase()));
    }
}