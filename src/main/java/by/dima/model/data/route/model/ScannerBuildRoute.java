package by.dima.model.data.route.model;

import by.dima.model.data.route.model.main.Route;
import by.dima.model.data.route.model.sub.Coordinates;
import by.dima.model.data.route.model.sub.LocationFrom;
import by.dima.model.data.route.model.sub.LocationTo;

import java.util.Scanner;

public class ScannerBuildRoute {
    private final RouteBuilder builder;
    private final Scanner scanner;

    public ScannerBuildRoute() {
        this.builder = new RouteBuilder();
        scanner = new Scanner(System.in);
    }

    public Route build(Long id) {
        builder.setName(readName());
        builder.setCoordinates(readCoordinates());
        builder.setFrom(readLocationFrom());
        builder.setTo(readLocationTo());
        builder.setDistance(readDistance());
        builder.setId(id);
        return builder.build();
    }

    public String readName() {
        System.out.println("Введите имя:");
        while (true) {
            String line = scanner.nextLine();
            if (line.isBlank()) {
                System.out.println("Имя не может быть пустой строкой! Попробуйте ещё раз:");
            } else {
                return line.strip();
            }
        }
    }

    public int readInt(String prompt) {
        System.out.println(prompt);
        while (true) {
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число! Попробуйте ещё раз:");
            }
        }
    }

    public double readDouble(String prompt) {
        System.out.println(prompt);
        while (true) {
            String line = scanner.nextLine();
            try {
                return Double.parseDouble(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число с точкой! Попробуйте ещё раз:");
            }
        }
    }

    public Float readFloat(String prompt) {
        System.out.println(prompt);
        while (true) {
            String line = scanner.nextLine();
            try {
                return Float.parseFloat(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите дробное число (float)! Попробуйте ещё раз:");
            }
        }
    }

    public Coordinates readCoordinates() {
        int x;
        while (true) {
            System.out.println("Введите координату X (целое число):");
            String line = scanner.nextLine();
            try {
                x = Integer.parseInt(line.trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное целое число!");
            }
        }

        Double y;
        while (true) {
            System.out.println("Введите координату Y (дробное число больше -749):");
            String line = scanner.nextLine();
            try {
                y = Double.parseDouble(line.trim());
                if (y > -749) {
                    break;
                } else {
                    System.out.println("Ошибка: Y должно быть больше -749!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число!");
            }
        }

        return new Coordinates(x, y);
    }

    public LocationFrom readLocationFrom() {
        double x;
        while (true) {
            System.out.println("Введите LocationFrom X (дробное число):");
            String line = scanner.nextLine();
            try {
                x = Double.parseDouble(line.trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число!");
            }
        }

        Float y;
        while (true) {
            System.out.println("Введите LocationFrom Y (дробное число):");
            String line = scanner.nextLine();
            try {
                y = Float.parseFloat(line.trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число!");
            }
        }

        String name;
        while (true) {
            System.out.println("Введите имя LocationFrom:");
            name = scanner.nextLine().strip();
            if (name.isEmpty()) {
                System.out.println("Имя не может быть пустым! Попробуйте снова:");
            } else if (name.length() > 690) {
                System.out.println("Имя слишком длинное! Не более 690 символов:");
            } else {
                break;
            }
        }

        return new LocationFrom(x, y, name);
    }

    public LocationTo readLocationTo() {
        Double x;
        while (true) {
            System.out.println("Введите LocationTo X (дробное число):");
            String line = scanner.nextLine();
            try {
                x = Double.parseDouble(line.trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число!");
            }
        }

        Double y;
        while (true) {
            System.out.println("Введите LocationTo Y (дробное число):");
            String line = scanner.nextLine();
            try {
                y = Double.parseDouble(line.trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число!");
            }
        }

        String name;
        while (true) {
            System.out.println("Введите имя LocationTo:");
            name = scanner.nextLine().strip();
            if (name.isEmpty()) {
                System.out.println("Имя не может быть пустым! Попробуйте снова:");
            } else if (name.length() > 330) {
                System.out.println("Имя слишком длинное! Не более 330 символов:");
            } else {
                break;
            }
        }

        return new LocationTo(x, y, name);
    }

    public double readDistance() {
        double distance;
        while (true) {
            distance = readDouble("Введите расстояние (положительное число):");
            if (distance > 0) {
                break;
            }
            System.out.println("Ошибка: расстояние должно быть положительным! Попробуйте ещё раз:");
        }
        return distance;

    }
}
