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
        check("spend(100) → true", warehouse.getSpool(1).spend(100));
        check("getSpool(1).spend(100) → totalUsed()", approxEquals(warehouse.totalUsed(), 100, 0.001));
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
    }

    private static boolean approxEquals(double a, double b, double v) {
        return Math.abs(a - b) <= v;
    }

}
