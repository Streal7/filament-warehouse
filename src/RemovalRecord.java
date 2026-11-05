public class RemovalRecord {
    private static final double GRAMS_PER_KG = 1000;
    private final String material;
    private final String color;
    private final double grams;
    private final double pricePerKg;
    private final RemovalReason reason;

    RemovalRecord(String material, String color, double grams, double pricePerKg, RemovalReason reason) {
        this.material = material;
        this.color = color;
        this.grams = grams;
        this.pricePerKg = pricePerKg;
        this.reason = reason;
    }

    public double lossCost() {
        if (reason.isLoss()) {
            return grams * pricePerKg / GRAMS_PER_KG;
        }
        return 0.00;
    }

    public String getMaterial() {
        return material;
    }

    public String getColor() {
        return color;
    }

    public double getGrams() {
        return grams;
    }

    public double getPricePerKg() {
        return pricePerKg;
    }

    public RemovalReason getReason() {
        return reason;
    }
}
