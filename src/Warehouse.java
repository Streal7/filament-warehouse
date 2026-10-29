import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Warehouse {

    private final List<Spool> spools = new ArrayList<>();

    Warehouse() {
        spools.add(new Spool("PLA", "Белый", 850, 1500));
        spools.add(new Spool("PETG", "Черный", 120.5, 1800));
        spools.add(new Spool("PLA", "Красный", 1000, 1500));
        spools.add(new Spool("TPU", "Прозрачный", 60, 2500));
        spools.add(new Spool("PLA", "синий", 100, 1500));
        spools.add(new Spool("PLA", "Белый", 850, 1500));
    }


    double totalUsed() {
        double sum = 0;
        for (Spool spool : spools) {
            sum += spool.getGramsUsed();
        }
        return sum;
    }

    double gramsByMaterial(String material) {
        double sum = 0;
        String materialUpperCase = material.toUpperCase();
        for (Spool spool : spools) {
            if (spool.getMaterial().equals(materialUpperCase)) {
                sum += spool.getGramsLeft();
            }
        }
        return sum;
    }

    double totalValue() {
        double sum = 0;
        for (Spool spool : spools) {
            sum += spool.costOf(spool.getGramsLeft());
        }
        return sum;
    }

    Spool getSpool(int number) {
        if (number < 1 || number > spools.size()) {
            return null;
        }
        return spools.get(number - 1);
    }

    int size() {
        return spools.size();
    }

    boolean addSpool(Spool spool) {
        if (spool == null) {
            return false;
        }
        spools.add(spool);
        return true;
    }

    boolean removeSpool(int number) {
        if (number < 1 || number > spools.size()) {
            return false;
        }
        spools.remove(number - 1);
        return true;
    }

    int removeEmpty() {
        int counterEmptySpools = 0;
        Iterator<Spool> iterator = spools.iterator();
        while (iterator.hasNext()) {
            Spool spool = iterator.next();
            if (spool.isEmpty()) {
                iterator.remove();
                counterEmptySpools++;
            }
        }
        return counterEmptySpools;
    }

    }




