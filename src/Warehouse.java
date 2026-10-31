import java.util.*;

public class Warehouse {

    private final List<Spool> spools = new ArrayList<>();
    private final List<WriteOff> spendRecords = new ArrayList<>();

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
        for (WriteOff record : spendRecords) {
            sum += record.getGrams();
        }
        return sum;
    }

    double gramsByMaterial(String material) {
        return materialReport().getOrDefault(material.toUpperCase(), 0.00);
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

    int countEmpty() {
        int counterEmptySpools = 0;
        Iterator<Spool> iterator = spools.iterator();
        while (iterator.hasNext()) {
            Spool spool = iterator.next();
            if (spool.isEmpty()) {
                counterEmptySpools++;
            }
        }
        return counterEmptySpools;
    }

    Map<Integer, Spool> lowSpools() {
        Map<Integer, Spool> result = new TreeMap<>();
        for (int i = 1; i <= spools.size(); i++) {
            if (spools.get(i - 1).isLow()) {
                result.put(i, spools.get(i - 1));
            }
        }
        return result;
    }

    Map<String, Double> materialReport() {
        Map<String, Double> result = new TreeMap<>();
        for (Spool spool : spools) {
            String material = spool.getMaterial();
            double current = result.getOrDefault(material, 0.0);
            result.put(material, current + spool.getGramsLeft());
        }
        return result;
    }

    Map<String, Double> materialUsedReport() {
        Map<String, Double> result = new TreeMap<>();
        for (WriteOff record : spendRecords) {
            String material = record.getMaterial();
            double current = result.getOrDefault(material, 0.0);
            result.put(material, current + record.getGrams());
        }
        return result;
    }

    Map<String, Double> materialsBelow(double minGrams) {
        Map<String, Double> report = materialReport();
        Iterator<Map.Entry<String, Double>> iterator = report.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Double> entry = iterator.next();
            if (entry.getValue() >= minGrams) {
                iterator.remove();
            }
        }
        return report;
    }

    Map<String, Integer> spoolCountByMaterial() {
        Map<String, Integer> spoolCount = new HashMap<>();
        for (Spool spool : spools) {
            String material = spool.getMaterial();
            int count = spoolCount.getOrDefault(material, 0);
            spoolCount.put(material, count + 1);
        }
        return spoolCount;
    }

    boolean spendFilament(int number, double grams){
        Spool spool = getSpool(number);
        if (spool == null){
            return false;
        }
        if (!spool.spend(grams)){
            return false;
        }
        spendRecords.add(new WriteOff(spool.getMaterial(),spool.getColor(), grams, spool.getPricePerKg()));
        return true;
    }

    public List<WriteOff> getSpendRecords() {
        List<WriteOff> spendRecordsCopy = new ArrayList<>(spendRecords);
        return spendRecordsCopy;
    }
}





