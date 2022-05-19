package com.example.komandor.controller;

import com.example.komandor.model.BasketProductDto;
import com.example.komandor.model.ProductDto;
import com.example.komandor.service.GoodService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@FxmlView(value = "/ui/MainController.fxml")
@RequiredArgsConstructor
public class MainController {
    private final GoodService goodService;

    private final ObservableList<BasketProductDto> basketProductDtoObservableList = FXCollections.observableArrayList();

    @FXML
    public Text header;

    @FXML
    public TextField searchBox;

    @FXML
    public TableView<BasketProductDto> basketTable;

    @FXML
    public TableColumn<String, BasketProductDto> basketGoodId;

    @FXML
    public TableColumn<String, BasketProductDto> basketName;

    @FXML
    public TableColumn<Integer, BasketProductDto> basketCount;

    @FXML
    public TableColumn<String, BasketProductDto> basketPrice;

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

        basketGoodId.setCellValueFactory(new PropertyValueFactory<>("BasketGoodId"));
        basketName.setCellValueFactory(new PropertyValueFactory<>("BasketName"));
        basketCount.setCellValueFactory(new PropertyValueFactory<>("BasketCount"));
        basketPrice.setCellValueFactory(new PropertyValueFactory<>("BasketPrice"));

        basketTable.setItems(basketProductDtoObservableList);
    }

    private ObservableList<ProductDto> filterList(List<ProductDto> productDtos, String searchText) {
        List<ProductDto> filteredList = productDtos.stream().filter(e -> searchFindsProduct(e, searchText)).collect(Collectors.toList());

        return FXCollections.observableList(filteredList);
    }

    private boolean searchFindsProduct(ProductDto productDto, String searchText) {
        return (productDto.getName().toLowerCase().contains(searchText.toLowerCase()));
    }

    public void addBasketRow(ActionEvent actionEvent) {
        ProductDto selectedItem = productTable.getSelectionModel().getSelectedItem();
        Optional<BasketProductDto> optionalBasketProductDto = basketProductDtoObservableList.stream()
                .filter(e -> e.getBasketGoodId().equals(selectedItem.getGoodId())).findFirst();

        if (optionalBasketProductDto.isPresent()) {
            optionalBasketProductDto.ifPresent(e -> e.setBasketCount(e.getBasketCount() + 1));
            basketTable.refresh();
            return;
        }

        basketProductDtoObservableList.add(BasketProductDto.builder()
                .basketCount(new SimpleIntegerProperty(1))
                .basketGoodId(new SimpleStringProperty(selectedItem.getGoodId()))
                .basketName(new SimpleStringProperty(selectedItem.getName()))
                .basketPrice(new SimpleStringProperty(selectedItem.getPrice()))
                .basketTotalPrice(new SimpleStringProperty(selectedItem.getPrice())).build());

        basketTable.refresh();
    }
}