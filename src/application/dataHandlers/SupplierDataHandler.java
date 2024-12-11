package application.dataHandlers;

import application.entity.ProductEntity;
import application.entity.ShopEntity;
import application.entity.UserEntity;
import application.serialize.Serialize;
import application.service.SupplierService;
import application.util.GenerateUniqueClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupplierDataHandler implements SupplierService {
    private List<ProductEntity> products = new ArrayList<>();
    private List<ShopEntity> shops = new ArrayList<>();
    private Serialize serialize;

    @Override
    public void addItem(UserEntity user, ProductEntity product) {
        serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/products.txt");

        if (products == null) {
            products = new ArrayList<>();
        }

        int id = GenerateUniqueClass.generateId(products,ProductEntity::getId);
        product.setId(id);
        products.add(product);
        serialize.serialize(products);
    }

    @Override
    public void addShop(UserEntity user, ShopEntity shop) {
        serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/products.txt");
        if (shops == null) {
            shops = new ArrayList<>();
        }
        int id = GenerateUniqueClass.generateId(shops,ShopEntity::getId);
        shop.setId(id);
        shops.add(shop);
        serialize.serialize(shops);
    }

    @Override
    public List<ShopEntity> getShopsInfo() {
        return List.of();
    }

    @Override
    public List<ProductEntity> getProductsInfo(UserEntity user) {
        serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/products.txt");
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
    public void updateItem(UserEntity user, ProductEntity updatedProduct) {
        Serialize serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/products.txt");
        List<ProductEntity> products = serialize.deserialize();

        if (products == null || products.isEmpty()) {
            System.out.println("Список товаров пуст.");
            return;
        }

        boolean updated = false;
        for (int i = 0; i < products.size(); i++) {
            ProductEntity existingProduct = products.get(i);

            if (existingProduct.getId() == updatedProduct.getId()) {
                products.set(i, updatedProduct);
                updated = true;
                break;
            }
        }

        if (updated) {
            serialize.serialize(products);
            System.out.println("Товар успешно обновлен: " + updatedProduct);
        } else {
            System.out.println("Товар с указанным ID не найден или не принадлежит текущему пользователю.");
        }
    }
}
