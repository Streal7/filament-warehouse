
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            int cmd = readIntInRange(scanner, "Выберите пункт", 0, 9);
            if (cmd == 1) {
                printStock(warehouse);
            } else if (cmd == 2) {
                handleSpend(scanner, warehouse);
            } else if (cmd == 3) {
                handleCostOf(scanner, warehouse);
            } else if (cmd == 4) {
                handleRefill(scanner, warehouse);
            } else if (cmd == 5) {
                System.out.printf("Общая стоимость склада: %.2f р.%n", warehouse.totalValue());
            } else if (cmd == 6) {
                handleGramsByMaterial(scanner, warehouse);
            } else if (cmd == 7) {
                handleGramsUsed(warehouse);
            } else if (cmd == 8) {
                handleAddSpool(scanner, warehouse);
            } else if (cmd == 9) {
                handleRemoveSpool(scanner, warehouse);
            } else if (cmd == 0) {
                System.out.println("Выход из программы");
                break;
            }

        }
    }

    static String readNonEmptyLine(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
        }
    }

    static boolean readYesNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.println(prompt + " Введите да или нет.");
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.equals("да") || line.equals("yes")) {
                return true;
            } else if (line.equals("нет") || line.equals("no")) {
                return false;
            }
        }
    }

    static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            int number = -1;
            System.out.println(prompt);
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.next();
                System.out.println("Нужно ввести число");
                continue;
            }
            if (number >= min && number <= max) {
                return number;
            } else {
                System.out.println("Число должно быть в границах от " + min + " до " + max);
            }
        }
    }

    static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            double number = -1;
            System.out.println(prompt);
            if (scanner.hasNextDouble()) {
                number = scanner.nextDouble();
                scanner.nextLine();
            } else {
                scanner.next();
                System.out.println("Нужно ввести число");
                continue;
            }
            if (number > 0) {
                return number;
            } else {
                System.out.println("Число должно быть больше 0");
            }
        }
    }

    static void handleSpend(Scanner scanner, Warehouse warehouse) {
        if (warehouse.size() == 0) {
            System.out.println("Склад пуст");
            return;
        }
        int number = Main.readIntInRange(scanner, "Введите номер катушки", 1, warehouse.size());
        double weight = Main.readPositiveDouble(scanner, "Вес модели, г");
        Spool spool = warehouse.getSpool(number);
        boolean ok = spool.spend(weight);

        if (ok) {
            System.out.printf("Списано. Осталось %.2f г, стоимость печати %.2f ₽%n",
                    spool.getGramsLeft(), spool.costOf(weight));
        } else {
            System.out.printf("Недостаточно пластика: есть %.2f г, нужно %.2f г%n",
                    spool.getGramsLeft(), weight);
        }
    }

    static void handleCostOf(Scanner scanner, Warehouse warehouse) {
        if (warehouse.size() == 0) {
            System.out.println("Склад пуст");
            return;
        }
        int number = Main.readIntInRange(scanner, "Номер катушки", 1, warehouse.size());
        double weight = Main.readPositiveDouble(scanner, "Вес модели, г");
        Spool spool = warehouse.getSpool(number);
        System.out.printf("Стоимость печати: %.2f ₽%n", spool.costOf(weight));
    }

    static void handleRefill(Scanner scanner, Warehouse warehouse) {
        if (warehouse.size() == 0) {
            System.out.println("Склад пуст");
            return;
        }
        int number = Main.readIntInRange(scanner, "Номер катушки", 1, warehouse.size());
        double weight = Main.readPositiveDouble(scanner, "Вес, который требуется добавить, г");
        Spool spool = warehouse.getSpool(number);
        boolean ok = spool.refill(weight);
        if (ok) {
            System.out.printf("Катушка пополнена, на %.2f граммов, теперь вес катушки %.2f%n", weight, spool.getGramsLeft());
        } else {
            System.out.println("Катушка не пополнена, введены некорректные данные для пополнения");
        }
    }

    static void handleGramsByMaterial(Scanner scanner, Warehouse warehouse) {
        if (warehouse.size() == 0) {
            System.out.println("Склад пуст");
            return;
        }
        System.out.println("Введите наименование материала для поиска остатков");
        String targetMaterial = scanner.nextLine();
        System.out.printf("Всего на складе %.2f граммов %s%n", warehouse.gramsByMaterial(targetMaterial), targetMaterial);
    }

    static void handleGramsUsed(Warehouse warehouse) {
        System.out.printf("Пластика израсходовано %.2f грамм%n", warehouse.totalUsed());
    }

    static void handleAddSpool(Scanner scanner, Warehouse warehouse) {
        String materialSpool = readNonEmptyLine(scanner, "Введите материал новой катушки");
        String colorSpool = readNonEmptyLine(scanner, "Введите цвет новой катушки");
        double gramsSpool = readPositiveDouble(scanner, "Введите вес новой катушки");
        double pricePerKgSpool = readPositiveDouble(scanner, "Введите стоимость за кг новой катушки");
        warehouse.addSpool(new Spool(materialSpool, colorSpool, gramsSpool, pricePerKgSpool));
        System.out.printf("Катушка: %s добавлена%n", warehouse.getSpool(warehouse.size()).toString());
    }

    static void handleRemoveSpool(Scanner scanner, Warehouse warehouse) {
        if (warehouse.size() == 0) {
            System.out.println("Склад пуст");
            return;
        }
        int removeNumber = readIntInRange(scanner, "Введите номер катушки для удаления", 1, warehouse.size());
        boolean isToDelete = readYesNo(scanner, (String.format("Вы уверены, что хотите удалить катушку %s?", warehouse.getSpool(removeNumber).toString())));
        if (isToDelete) {
            warehouse.removeSpool(removeNumber);
            printStock(warehouse);
        } else {
            System.out.println("Удаление отменено");
        }
    }

    static void printMenu() {
        System.out.println("====Меню склада====");
        System.out.println("1. Показать склад");
        System.out.println("2. Списать печать");
        System.out.println("3. Рассчитать стоимость печати (без списания)");
        System.out.println("4. Пополнить катушку");
        System.out.println("5. Стоимость всего склада");
        System.out.println("6. Остаток по материалу");
        System.out.println("7. Пластика израсходовано");
        System.out.println("8. Добавить катушку");
        System.out.println("9. Удалить катушку");
        System.out.println("0. Выход");

    }


    static void printStock(Warehouse warehouse) {
        System.out.printf("%4s | %-10s | %-11s |  %11s | %10s | %s%n", "№п/п", "Материал", "Цвет", "Вес (грамм)", "Цена", "Предупреждения");
        System.out.println("*".repeat(80));
        for (int i = 1; i <= warehouse.size(); i++) {
            System.out.println(warehouse.getSpool(i).toRow(i));
        }
    }
}
