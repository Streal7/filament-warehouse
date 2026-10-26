public class Warehouse {
    private Spool[] spools;

    Warehouse() {
        spools = new Spool[]{new Spool("PLA", "Белый", 850, 1500),
                new Spool("PETG", "Черный", 120.5, 1800),
                new Spool("PLA", "Красный", 1000, 1500),
                new Spool("TPU", "Прозрачный", 60, 2500),
                new Spool("PLA", "синий", 100, 1500),
                new Spool("PLA", "Белый", 850, 1500)
        };
    }


    int size() {
        return spools.length;
    }

    Spool getSpool(int number) {
        if (number <= 0 || number > spools.length){
            return null;
        }
            return spools[number - 1];
    }

    double totalUsed() {
        double sum = 0;
        for (int i = 0; i < spools.length; i++) {
            sum += spools[i].getGramsUsed();
        }
        return sum;
    }

    double gramsByMaterial(String material) {
        double sum = 0;
        String materialUpperCase = material.toUpperCase();
        for (int i = 0; i < spools.length; i++) {
            if (spools[i].getMaterial().equals(materialUpperCase)) {
                sum += spools[i].getGramsLeft();
            }
        }
        return sum;
    }

    double totalValue() {
        double sum = 0;
        for (int i = 0; i < spools.length; i++) {
            sum += spools[i].costOf(spools[i].getGramsLeft());
        }
        return sum;
    }
}




