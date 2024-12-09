package application.entity;



import java.io.Serializable;

public class ProductEntity implements Serializable {

    private int id;
    private String name;
    private String description;
    private double price;
    private UserEntity supplier;


    public ProductEntity(String name, String description, double price, UserEntity supplier) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.supplier = supplier;
    }

    public ProductEntity() {

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UserEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(UserEntity supplier) {
        this.supplier = supplier;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", supplier=" + supplier +
                '}';
    }
}