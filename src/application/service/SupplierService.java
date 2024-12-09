package application.service;

import application.entity.ProductEntity;
import application.entity.ShopEntity;
import application.entity.UserEntity;

import java.util.List;

public interface SupplierService<T> {
    void addItem(UserEntity user, ProductEntity product);
    void addShop(UserEntity user, ShopEntity shop);
    List<ShopEntity> getShopsInfo();
    List<ProductEntity> getProductsInfo(UserEntity user);
    void updateItem(UserEntity user, ProductEntity product);
}
