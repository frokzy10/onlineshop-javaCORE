package application.entity;



import java.io.Serializable;
import java.util.List;

public class ShopEntity implements Serializable {
    private int id;
    private String name;
    private String address;
    private List<ProductEntity> productEntityList;

    public ShopEntity(String name, String address, List<ProductEntity> productEntityList) {
        this.name = name;
        this.address = address;
        this.productEntityList = productEntityList;
    }

    public List<ProductEntity> getProductEntityList() {
        return productEntityList;
    }

    public void setProductEntityList(List<ProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void addProduct(ProductEntity productEntity){
        this.productEntityList.add(productEntity);
    }

    @Override
    public String toString() {
        return "ShopEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
