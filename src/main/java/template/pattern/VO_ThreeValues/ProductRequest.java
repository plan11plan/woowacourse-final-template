package template.pattern.VO_ThreeValues;

public class ProductRequest {
    private final String name;
    private final int price;
    private final int quantity;

    public ProductRequest(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("[%s,%d,%d]", name, price, quantity);
    }
}
