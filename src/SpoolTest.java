import java.util.List;
import java.util.Map;

public class SpoolTest {
    static void check(String name, boolean passed) {
        System.out.println((passed ? "✅ " : "❌ ") + name);
    }

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        check("getSpool(0) -> null", warehouse.getSpool(0) == null);
        check("getSpool(7) -> null", warehouse.getSpool(7) == null);
        check("gramsByMaterial(\"PLA\") ожидаем 2800", approxEquals(warehouse.gramsByMaterial("PLA"), 2800, 0.001));
        check("gramsByMaterial(\"pla\") ожидаем 2800", approxEquals(warehouse.gramsByMaterial("pla"), 2800, 0.001));
        check("gramsByMaterial(\"ABS\") ожидаем 0", approxEquals(warehouse.gramsByMaterial("ABS"), 0, 0.001));
        check("totalUsed до списаний ожидаем 0", approxEquals(warehouse.totalUsed(), 0, 0.001));
        check("totalValue 4566,9", approxEquals(warehouse.totalValue(), 4566.9, 0.001));
        check("spendFilament(100) → true", warehouse.spendFilament(1,100));
        check("spendFilament(100) → totalUsed()", approxEquals(warehouse.totalUsed(), 100, 0.001));
        check("getSpool(1) и getSpool(6) — разные объекты", warehouse.getSpool(1) != warehouse.getSpool(6));
        check("списание с 1-й не затронуло 6-ю → остаток 850", approxEquals(warehouse.getSpool(6).getGramsLeft(), 850, 0.001));
        check("totalValue 4416,9", approxEquals(warehouse.totalValue(), 4416.9, 0.001));
        check("new Spool(PLA белый, 850, 1500).isLow() →  false", !(new Spool("PLA", "Белый", 850, 1500).isLow()));
        check("катушка на 60 г isLow() →  true", new Spool("PLA", "синий", 60, 1500).isLow());
        check("катушка на 100 г isLow() →  false", !(new Spool("PLA", "красный", 100, 1500).isLow()));
        Spool plaWhite = new Spool("Pla", "Белый", 100, 1500);
        check("1500 ₽/кг → costOf(50) 75 → true", approxEquals(plaWhite.costOf(50), 75.00, 0.001));
        check("costOf(50)остаток не изменился → true", approxEquals(plaWhite.getGramsLeft(), 100, 0.001));
        Spool plaRed = new Spool("PLA", "Красный", 850, 1500);
        check("spend(100) → true", plaRed.spend(100));
        check("spend(100) на 850 г → gramsUsed", approxEquals(plaRed.getGramsUsed(), 100, 0.001));
        check("неудачный spend(2000) больше остатка - false", !plaRed.spend(2000));
        check("неудачный spend(2000) → gramsUsed", approxEquals(plaRed.getGramsUsed(), 100, 0.001));
        check("PLA красный остаток 750г → true", approxEquals(plaRed.getGramsLeft(), 750, 0.001));
        Spool plaBlue = new Spool("PLA", "синий", 120.5, 1500);
        check("120,5 г → spend(200) ожидаем false", !plaBlue.spend(200));
        check("после операции остаток не изменился 120.5", approxEquals(plaBlue.getGramsLeft(), 120.5, 0.001));
        check("spend(0) → false, остаток не изменился", !plaBlue.spend(0));
        check("после операции остаток не изменился 120.5", approxEquals(plaBlue.getGramsLeft(), 120.5, 0.001));
        check("spend(-5)", !plaBlue.spend(-5));
        check("после операции остаток не изменился 120.5", approxEquals(plaBlue.getGramsLeft(), 120.5, 0.001));
        check("refill(1000)", new Spool("PLA", "синий", 100, 1500).refill(1000));
        check("refill(-10)", !(new Spool("PLA", "синий", 100, 1500).refill(-10)));
        Spool tpu = new Spool("TPU", "Прозрачный", 60, 2500);
        check("refill(1000) → true", tpu.refill(1000));
        check("после refill остаток 1060", approxEquals(tpu.getGramsLeft(), 1060, 0.001));
        Spool petg = new Spool("PETG", "Желтый", 120.5, 1800);
        check("новая катушка → gramsUsed ожидается 0", approxEquals(petg.getGramsUsed(), 0.00, 0.001));
        check("spend(120.5) — списать ровно всё → true", petg.spend(120.5));
        check("остаток 0", approxEquals(petg.getGramsLeft(), 0, 0.001));
        check("после этого isLow() → true", petg.isLow());
        check("getSpool(1).color", warehouse.getSpool(1).getColor().equals("Белый"));
        check("size() ожидаем 6", warehouse.size() == 6);
        check("getSpool(5).material", warehouse.getSpool(5).getMaterial().equals("PLA"));
        check("getSpool(2) == getSpool(2)", warehouse.getSpool(2) == warehouse.getSpool(2));
        check("toString() новой катушки PLA Белый 850 равен \"PLA Белый, 850,00 г\"", warehouse.getSpool(6).toString().equals("PLA Белый, 850,00 г"));
        check("Катушка с материалом \"Pla\" → getMaterial() равен \"PLA\"", plaWhite.getMaterial().equals("PLA"));
        check("Добавление катушки с ABS", warehouse.addSpool(new Spool("ABS", "Серый", 500, 1700)));
        check("Проверка size() после добавления 7ой катушки", warehouse.size() == 7);
        check("getSpool(7).getMaterial() == ABS", warehouse.getSpool(7).getMaterial().equals("ABS"));
        check("gramsByMaterial == 500", approxEquals(warehouse.gramsByMaterial("ABS"), 500, 0.001));
        check("totalValue 4416,9 + 850 = 5266,9", approxEquals(warehouse.totalValue(), 5266.9, 0.001));
        check("getSpool(8) -> null", warehouse.getSpool(8) == null);
        check("addSpool(null) == false", !warehouse.addSpool(null));
        check("Размер после неудачного добавления не изменился", warehouse.size() == 7);
        warehouse.addSpool(new Spool("abs", "Белый", 1000, 1700));
        check("totalValue 5266,9 + 1700 = 6966.9", approxEquals(warehouse.totalValue(), 6966.9, 0.001));
        check("проверка \"abs\" без учета регистра gramsByMaterial == 1500", approxEquals(warehouse.gramsByMaterial("ABS"), 1500, 0.001));
        //Тесты удаления катушки
        Warehouse warehouse1 = new Warehouse();
        check("Удалить катушку №1 --> true", warehouse1.removeSpool(1));
        check("size() --> 5", warehouse1.size() == 5);
        check("getSpool(1).getMaterial() --> PETG", warehouse1.getSpool(1).getMaterial().equals("PETG"));
        check("gramsByMaterial(\"PLA\") --> 1950", approxEquals(warehouse1.gramsByMaterial("PLA"), 1950, 0.001));
        check("Удалить катушку №0 --> false", !warehouse1.removeSpool(0));
        check("Удалить катушку №6 --> false", !warehouse1.removeSpool(6));
        check("Удалить катушку №1 из 5 --> true", warehouse1.removeSpool(1));
        check("Удалить катушку №1 из 4 --> true", warehouse1.removeSpool(1));
        check("Удалить катушку №1 из 3 --> true", warehouse1.removeSpool(1));
        check("Удалить катушку №1 из 2 --> true", warehouse1.removeSpool(1));
        check("Удалить катушку №1 из 1 --> true", warehouse1.removeSpool(1));
        check("size() --> 0", warehouse1.size() == 0);
        check("totalValue()", warehouse1.totalValue() == 0);
        check("totalUsed()", warehouse1.totalUsed() == 0);
        check("getSpool(1) -- > false", warehouse1.getSpool(1) == null);
        check("removeSpool(1) -- > false", !warehouse1.removeSpool(1));
        //Тесты удаления пустых катушек
        Warehouse warehouse2 = new Warehouse();
        check("countEmpty() --> 0", warehouse2.countEmpty() == 0);
        check("getSpool(1) spend 850 ", warehouse2.getSpool(1).spend(850));
        check("removeEmpty() == 1", warehouse2.removeEmpty() == 1);
        check("getSpool(1) материал PETG", "PETG".equals(warehouse2.getSpool(1).getMaterial()));
        warehouse2.addSpool(new Spool("abs", "Белый", 0, 1700));
        warehouse2.addSpool(new Spool("abs", "Белый", 0.0005, 1700));
        warehouse2.addSpool(new Spool("abs", "Белый", 1, 1700));
        check("остаток 0 ---> isEmpty true", warehouse2.getSpool(6).isEmpty());
        check("остаток 0.0005 ---> isEmpty true", warehouse2.getSpool(7).isEmpty());
        check("остаток 1 ---> isEmpty false", !warehouse2.getSpool(8).isEmpty());
        check("остаток 120.5 ---> isEmpty false", !warehouse2.getSpool(1).isEmpty());
        warehouse2.getSpool(1).spend(120.5);
        check("countEmpty() после списания --> 3", warehouse2.countEmpty() == 3);
        check("остаток после spend 0 ---> isEmpty true", warehouse2.getSpool(1).isEmpty());
        check("removeEmpty() --> 3", warehouse2.removeEmpty() == 3);
        check("size() --> 5", warehouse2.size() == 5);
        boolean hasEmptySpool = false;
        for (int i = 1; i <= warehouse2.size(); i++) {
            if (warehouse2.getSpool(i).isEmpty()) {
                hasEmptySpool = true;
                break;
            }
        }
        check("ни у одной катушки остаток не 0", !hasEmptySpool);
        check("removeEmpty() --> 0", warehouse2.removeEmpty() == 0);
        warehouse2.removeSpool(1);
        warehouse2.removeSpool(1);
        warehouse2.removeSpool(1);
        warehouse2.removeSpool(1);
        warehouse2.removeSpool(1);
        warehouse2.removeSpool(1);
        check("size() --> 0", warehouse2.size() == 0);
        check("removeEmpty() --> 0", warehouse2.removeEmpty() == 0);
        Warehouse warehouse3 = new Warehouse();
        check("Заканчивается у одной катушки", warehouse3.lowSpools().size() == 1);
        Map<Integer, Spool> lowWarehouse = warehouse3.lowSpools();
        check("Ключ 4 есть в lowSpools", lowWarehouse.containsKey(4));
        check("По ключу 4 лежит катушка №4 со склада", lowWarehouse.get(4) == warehouse3.getSpool(4));
        lowWarehouse.clear();
        check("Размер исходного списка не изменился", warehouse3.size() == 6);
        warehouse3.getSpool(1).spend(800);
        check("Заканчивается у двух катушек", warehouse3.lowSpools().size() == 2);
        check("На пустом складе не равно null", warehouse1.lowSpools() != null);
        check("warehouse1.lowSpools().isEmpty()", warehouse1.lowSpools().isEmpty());
        Warehouse warehouse4 = new Warehouse();
        check("materialReport(PLA) --> 2800", approxEquals(2800, warehouse4.materialReport().getOrDefault("PLA", -1.0), 0.001));
        check("materialReport(PETG) --> 120.5", approxEquals(120.5, warehouse4.materialReport().getOrDefault("PETG", -1.0), 0.001));
        check("materialReport(TPU) --> 60", approxEquals(60, warehouse4.materialReport().getOrDefault("TPU", -1.0), 0.001));
        check("materialReport на пустом складе", warehouse2.materialReport().isEmpty());
        warehouse4.materialReport().clear();
        check("materialReport(PLA) после clear() --> 2800", approxEquals(2800, warehouse4.materialReport().getOrDefault("PLA", -1.0), 0.001));
        check("materialReport(PETG) после clear() --> 120.5", approxEquals(120.5, warehouse4.materialReport().getOrDefault("PETG", -1.0), 0.001));
        check("materialReport(TPU) после clear() --> 60", approxEquals(60, warehouse4.materialReport().getOrDefault("TPU", -1.0), 0.001));
        warehouse4.getSpool(4).spend(60);
        check("materialReport(TPU) --> 0.00", approxEquals(0.00, warehouse4.materialReport().getOrDefault("TPU", -1.0), 0.001));
        check("Тест на количество ключей --> 3", warehouse4.materialReport().size() == 3);
        Warehouse warehouse5 = new Warehouse();
        warehouse5.getSpool(1).spend(800);
        warehouse5.getSpool(6).spend(800);
        Map<Integer, Spool> lowWarehouse5 = warehouse5.lowSpools();
        check("проверяем ключи --> 1", lowWarehouse5.containsKey(1));
        check("проверяем ключи --> 4", lowWarehouse5.containsKey(4));
        check("проверяем ключи --> 6", lowWarehouse5.containsKey(6));
        check("Проверяем размер lowWarehouse5 --> 3", lowWarehouse5.size() == 3);
        check("spoolCountByMaterial() PLA -- > 4", warehouse5.spoolCountByMaterial().getOrDefault("PLA", -1) == 4);
        check("spoolCountByMaterial() TPU -- > 1", warehouse5.spoolCountByMaterial().getOrDefault("TPU", -1) == 1);
        check("spoolCountByMaterial() PETG -- > 1", warehouse5.spoolCountByMaterial().getOrDefault("PETG", -1) == 1);
        check("Пустой склад --> пустая Map spoolCountByMaterial", warehouse2.spoolCountByMaterial().isEmpty());
        warehouse5.removeSpool(1);
        check("spoolCountByMaterial() PLA после удаления 1 катушки -- > 3", warehouse5.spoolCountByMaterial().getOrDefault("PLA", -1) == 3);
        check("Тест на количество ключей spoolCountByMaterial --> 3", warehouse4.spoolCountByMaterial().size() == 3);
        Warehouse warehouse6 = new Warehouse();
        check("порог 200 → contains PETG", warehouse6.materialsBelow(200).containsKey("PETG"));
        check("порог 200 → contains TPU", warehouse6.materialsBelow(200).containsKey("TPU"));
        check("порог 200 -> PETG и TPU, 2 ключа", warehouse6.materialsBelow(200).size() == 2);
        check("порог 61 -> TPU, 1 ключ", warehouse6.materialsBelow(61).size() == 1);
        check("порог 60 -> TPU, не докупаем", warehouse6.materialsBelow(60).isEmpty());
        check("порог 3000 → PLA, PETG и TPU, 3 ключа", warehouse6.materialsBelow(3000).size() == 3);
        check("порог 3000 → contains PETG", warehouse6.materialsBelow(3000).containsKey("PETG"));
        check("порог 3000 → contains TPU", warehouse6.materialsBelow(3000).containsKey("TPU"));
        check("порог 3000 → contains PLA", warehouse6.materialsBelow(3000).containsKey("PLA"));
        check("после вызова materialReport() по-прежнему 3 материала", warehouse6.materialReport().size() == 3);
        warehouse6.spendFilament(1,800);
        check("на  складе расход по PLA 800", approxEquals(800.00, warehouse6.materialUsedReport().getOrDefault("PLA",-1.00), 0.001));
        warehouse6.spendFilament(4,50);
        check("на  складе расход по TPU 50", approxEquals(50.00, warehouse6.materialUsedReport().getOrDefault("TPU",-1.00), 0.001));
        check("неудачное списание", !warehouse6.spendFilament(4,50));
        check("на новом складе расход по TPU 50 после неудачного списания", approxEquals(50.00, warehouse6.materialUsedReport().getOrDefault("TPU",-1.00), 0.001));
        check("Пустой склад --> пустая Map materialUsedReport",warehouse2.materialUsedReport().isEmpty());

        WriteOff writeOff = new WriteOff("PLA", "Белый", 800, 1500);

        WriteOff writeOff2 = new WriteOff("PETG", "Черный", 120.5, 1800);
        check("writeOff.getMaterial() --> PLA", "PLA".equals(writeOff.getMaterial()));
        check("writeOff.getColor() --> Белый", "Белый".equals(writeOff.getColor()));
        check("writeOff.getGrams() --> 800.00", approxEquals(800.00, writeOff.getGrams(),0.001));
        check("writeOff.getPricePerKg() --> 1500", approxEquals(1500.00,writeOff.getPricePerKg(),0.001));
        check("writeOff.cost(800,1500) --> 1200", approxEquals(writeOff.cost(), 1200, 0.001));
        check("writeOff2.cost() --> 216.9", approxEquals(216.9,writeOff2.cost(),0.001));
        Warehouse warehouse7 = new Warehouse();
        check("spendFilament(1, 800) --> true", warehouse7.spendFilament(1, 800));
        List<WriteOff> records = warehouse7.getSpendRecords();
        check("в журнале одна запись", records.size() == 1);
        check("records.getMaterial = PLA", records.size() == 1 && "PLA".equals(records.get(0).getMaterial()));
        check("records.getColor = Белый", records.size() == 1 && "Белый".equals(records.get(0).getColor()));
        check("records.getGrams = 800", records.size() == 1 && approxEquals(800.00,records.get(0).getGrams(), 0.001));
        check("records.getPricePerKG = 1500", records.size() == 1 && approxEquals(1500.00, records.get(0).getPricePerKg(), 0.001));
        check("spendFilament(1, 800) --> false", !warehouse7.spendFilament(1, 800));
        check("журнал не изменился", warehouse7.getSpendRecords().size() == 1);
        check("списание было именно по катушке 1", approxEquals(warehouse7.getSpool(1).getGramsLeft(),50.00,0.001));
        records.clear();
        check("clear() не меняет журнал", warehouse7.getSpendRecords().size() == 1);
        Warehouse warehouse8 = new Warehouse();
        check("warehouse8.spendFilament(1,800) --> true", warehouse8.spendFilament(1,800));
        check("Удаление катушки --> true",warehouse8.removeSpool(1));
        check("warehouse8.materialUsedReport(PLA) --> 800", approxEquals(800.00, warehouse8.materialUsedReport().getOrDefault("PLA",-1.00), 0.001));
        check("warehouse8.totalUsed(PLA) --> 800", approxEquals(800.00, warehouse8.totalUsed(), 0.001));
        check("Списаний нет отчет пуст",new Warehouse().materialUsedReport().isEmpty());


        Warehouse warehouse9 = new Warehouse();
        check("mostUsedMaterial списаний нет --> null", warehouse9.mostUsedMaterial() == null);
        warehouse9.spendFilament(2,100);
        warehouse9.spendFilament(1,50);
        warehouse9.spendFilament(4,55);
        check("mostUsedMaterial --> PETG", "PETG".equals(warehouse9.mostUsedMaterial()));
        warehouse9.spendFilament(1,50);
        check("mostUsedMaterial() первый по алфавиту из двух одинаковых --> PETG", "PETG".equals(warehouse9.mostUsedMaterial()));
    }

    private static boolean approxEquals(double a, double b, double v) {
        return Math.abs(a - b) <= v;
    }

}
