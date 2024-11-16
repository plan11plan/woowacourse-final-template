package template.pattern.VO_TwoValues;

public class ProductQuantityRequest {
    private final String name;
    private final int quantity;

    public ProductQuantityRequest(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format("[%s-%d]", name, quantity);
    }
}
