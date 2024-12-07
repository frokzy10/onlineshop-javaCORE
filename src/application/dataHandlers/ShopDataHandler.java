package application.dataHandlers;

import application.entity.ProductEntity;
import application.entity.ShopEntity;
import application.serialize.Serialize;

import java.util.ArrayList;
import java.util.List;

public class ShopDataHandler {
    List<ProductEntity> products = List.of();

    private List<ShopEntity> shopEntityList = new ArrayList<>(
            List.of(
                    new ShopEntity("Пятерочка", "Ул.Кулиева", products),
                    new ShopEntity("Азия", "Пр.Чуй", products)
            )
    );

    public ShopEntity addShop(ShopEntity shopEntity) {
        Serialize serialize = new Serialize("/Users/nurdinbakytbekov/Desktop/shops.txt");

        shopEntityList = serialize.deserialize();
        if (shopEntityList.isEmpty()) {
            shopEntityList = new ArrayList<>();
        }

        serialize.serialize(shopEntityList);
        return shopEntity;
    }

}