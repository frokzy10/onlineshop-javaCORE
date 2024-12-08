package application.dataHandlers;

import application.entity.ProductEntity;
import application.entity.ShopEntity;
import application.entity.UserEntity;
import application.serialize.Serialize;
import application.service.SupplierService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupplierDataHandler implements SupplierService {
    private List<ProductEntity> products = new ArrayList<>();
    private List<ShopEntity> shops = new ArrayList<>();
    private Serialize serialize;

    public SupplierDataHandler() {

    }

    @Override
    public void addItem(UserEntity user, ProductEntity product) {
        serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/products.txt");

        if(products == null){
            products = new ArrayList<>();
        }

        product.setSupplier(user);
        products.add(product);
        serialize.serialize(products);
    }

    @Override
    public void addShop(UserEntity user, ShopEntity shop) {

    }

    @Override
    public List<ShopEntity> getShopsInfo() {
        return List.of();
    }

    @Override
    public List<ProductEntity> getProductsInfo(UserEntity user) {
        Serialize serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/products.txt");
        List<ProductEntity> products = serialize.deserialize();

        if (products == null || products.isEmpty()) {
            System.out.println("Список пуст");
            return Collections.emptyList();
        }
        List<ProductEntity> userEntityList = products.stream()
                .filter(p -> p.getSupplier() != null && p.getSupplier().getEmail().equals(user.getEmail()))
                .distinct()
                .toList();

        if (userEntityList.isEmpty()) {
            System.out.println("У вас нет товаров");
            return Collections.emptyList();
        }

        return userEntityList;
    }

    @Override
    public void updateItem(UserEntity user, ProductEntity product) {

    }
}
