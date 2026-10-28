public class Spool {
    private static final double LOW_THRESHOLD = 100.00;
    private static final double GRAMS_PER_KG = 1000.00;
    private static final double PRECISION_THRESHOLD = 0.001;
    private final String material;
    private final String color;
    private double gramsLeft;
    private final double pricePerKg;
    private double gramsUsed;

    Spool(String material, String color, double gramsLeft, double pricePerKg) {
        this.material = material.toUpperCase();
        this.color = color;
        this.gramsLeft = gramsLeft;
        this.pricePerKg = pricePerKg;
        gramsUsed = 0;
    }

    boolean isLow() {
        return (gramsLeft < LOW_THRESHOLD);
    }

    boolean isEmpty() {
        return gramsLeft < PRECISION_THRESHOLD;
    }

    double costOf(double grams) {
        return grams * pricePerKg / GRAMS_PER_KG;
    }

    boolean spend(double grams) {
        if ((gramsLeft < grams) || (grams <= 0)) {
            return false;
        }
        gramsLeft -= grams;
        gramsUsed += grams;
        return true;
    }

    boolean refill(double grams) {
        if (grams <= 0) {
            return false;
        }
        gramsLeft += grams;
        return true;
    }

    String toRow(int number) {
        return String.format("%4d | %-10s | %-11s |  %11.2f | %10.2f | %s", number, material, color, gramsLeft, pricePerKg, (isLow() ? "Заканчивается" : ""));
    }

    public String getMaterial() {
        return material;
    }

    public String getColor() {
        return color;
    }

    public double getGramsLeft() {
        return gramsLeft;
    }

    public double getGramsUsed() {
        return gramsUsed;
    }

    @Override
    public String toString() {
        return String.format("%s %s, %.2f г", getMaterial(), getColor(), getGramsLeft());
    }
}
