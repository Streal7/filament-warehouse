public class WriteOff {
    private static final double GRAMS_PER_KG = 1000;
    private final String material;
    private final String color;
    private final double grams;
    private final double pricePerKg;

    WriteOff(String material, String color, double grams, double pricePerKg) {
        this.material = material;
        this.color = color;
        this.grams = grams;
        this.pricePerKg = pricePerKg;
    }

    public double cost() {
        return grams * pricePerKg / GRAMS_PER_KG;
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
}
