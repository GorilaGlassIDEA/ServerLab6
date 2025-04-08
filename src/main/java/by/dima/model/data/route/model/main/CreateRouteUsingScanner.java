package by.dima.model.data.route.model.main;

import by.dima.model.data.route.model.sub.Coordinates;
import by.dima.model.data.route.model.sub.LocationFrom;
import by.dima.model.data.route.model.sub.LocationTo;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class CreateRouteUsingScanner {
    private Scanner scanner;

    public CreateRouteUsingScanner() {
        scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
    }

    public Route createRoute(RouteBuildable routeBuildable, long id) {
        routeBuildable.setId(id);
        routeBuildable.setName(readRequiredLine("Route name: ", "Name shouldn't be empty or contain only spaces"));
        routeBuildable.setCoordinates(readCoordinates());
        routeBuildable.setLocationFrom(readLocationFrom());
        routeBuildable.setLocationTo(readLocationTo());
        routeBuildable.setDistance(readDistance());
        return routeBuildable.build();
    }

    private String readRequiredLine(String prompt, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input == null || input.trim().isEmpty()) {
                System.out.println(errorMessage);
            } else {
                return input;
            }
            if (!scanner.hasNextLine()) {
                System.out.println("\nEOF detected. Exiting...");
                System.exit(0);
            }
        }
    }

    private String readRequiredLineWithMaxLength(String prompt, String errorMessage, int maxLength) {
        while (true) {
            String input = readRequiredLine(prompt, errorMessage);
            if (input.length() > maxLength) {
                System.out.println("Length must be less than or equal to " + maxLength + ". Try again.");
            } else {
                return input;
            }
            if (!scanner.hasNextLine()) {
                System.out.println("\nEOF detected. Exiting...");
                System.exit(0);
            }
        }
    }

    private double parseDoubleWithCommaSupport(String input) {
        try {
            return Double.parseDouble(input.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Invalid number format");
        }
    }

    private float parseFloatWithCommaSupport(String input) {
        try {
            return Float.parseFloat(input.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Invalid number format");
        }
    }
    private Coordinates readCoordinates() {
        Coordinates coordinates = new Coordinates();
        while (true) {
            try {
                System.out.print("Coordinate X (int): ");
                String xInput = scanner.nextLine().trim();
                if (xInput.isEmpty()) {
                    System.out.println("Coordinate X cannot be empty");
                    continue;
                }
                try {
                    int x = Integer.parseInt(xInput);
                    coordinates.setX(x);
                } catch (NumberFormatException e) {
                    System.out.println("Coordinate X must be an integer. Try again.");
                    continue;
                }

                while (true) {
                    System.out.print("Coordinate Y (double, greater than -749): ");
                    String yInput = scanner.nextLine().trim();
                    if (yInput.isEmpty()) {
                        System.out.println("Coordinate Y cannot be empty");
                        continue;
                    }
                    try {
                        double y = parseDoubleWithCommaSupport(yInput);
                        if (y <= -749) {
                            System.out.println("Coordinate Y must be greater than -749. Try again.");
                            continue;
                        }
                        coordinates.setY(y);
                        return coordinates;
                    } catch (InputMismatchException e) {
                        System.out.println("Coordinate Y must be a number. Try again.");
                    }
                }

            } catch (Exception e) {
                System.out.println("Incorrect input! Try again.");
            }
            if (!scanner.hasNextLine()) {
                System.out.println("\nEOF detected. Exiting...");
                System.exit(0);
            }
        }
    }

    private LocationFrom readLocationFrom() {
        LocationFrom locationFrom = new LocationFrom();
        while (true) {
            try {
                System.out.print("LocationFrom X (double): ");
                String xInput = scanner.nextLine().trim();
                if (xInput.isEmpty()) {
                    System.out.println("LocationFrom X cannot be empty");
                    continue;
                }
                try {
                    double x = parseDoubleWithCommaSupport(xInput);
                    locationFrom.setX(x);
                } catch (InputMismatchException e) {
                    System.out.println("LocationFrom X must be a number. Try again.");
                    continue;
                }

                System.out.print("LocationFrom Y (float): ");
                String yInput = scanner.nextLine().trim();
                if (yInput.isEmpty()) {
                    System.out.println("LocationFrom Y cannot be empty");
                    continue;
                }
                try {
                    float y = parseFloatWithCommaSupport(yInput);
                    locationFrom.setY(y);
                } catch (InputMismatchException e) {
                    System.out.println("LocationFrom Y must be a number. Try again.");
                    continue;
                }

                String name = readRequiredLineWithMaxLength("LocationFrom Name: ",
                        "Name shouldn't be empty or contain only spaces", 690);
                locationFrom.setName(name);

                return locationFrom;

            } catch (Exception e) {
                System.out.println("Incorrect input! Try again.");
            }
            if (!scanner.hasNextLine()) {
                System.out.println("\nEOF detected. Exiting...");
                System.exit(0);
            }
        }
    }

    private LocationTo readLocationTo() {
        LocationTo locationTo = new LocationTo();
        while (true) {
            try {
                System.out.print("LocationTo X (double): ");
                String xInput = scanner.nextLine().trim();
                if (xInput.isEmpty()) {
                    System.out.println("LocationTo X cannot be empty");
                    continue;
                }
                try {
                    double x = parseDoubleWithCommaSupport(xInput);
                    locationTo.setX(x);
                } catch (InputMismatchException e) {
                    System.out.println("LocationTo X must be a number. Try again.");
                    continue;
                }

                System.out.print("LocationTo Y (double): ");
                String yInput = scanner.nextLine().trim();
                if (yInput.isEmpty()) {
                    System.out.println("LocationTo Y cannot be empty");
                    continue;
                }
                try {
                    double y = parseDoubleWithCommaSupport(yInput);
                    locationTo.setY(y);
                } catch (InputMismatchException e) {
                    System.out.println("LocationTo Y must be a number. Try again.");
                    continue;
                }

                String name = readRequiredLineWithMaxLength("LocationTo Name: ",
                        "Name shouldn't be empty or contain only spaces", 330);
                locationTo.setName(name);

                return locationTo;

            } catch (Exception e) {
                System.out.println("Incorrect input! Try again.");
            }
            if (!scanner.hasNextLine()) {
                System.out.println("\nEOF detected. Exiting...");
                System.exit(0);
            }
        }
    }

    private double readDistance() {
        while (true) {
            try {
                System.out.print("Route Distance: ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Distance cannot be empty");
                    continue;
                }
                try {
                    double distance = parseDoubleWithCommaSupport(input);
                    if (distance <= 0) {
                        System.out.println("Distance must be positive");
                        continue;
                    }
                    return distance;
                } catch (InputMismatchException e) {
                    System.out.println("Distance must be a positive number. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input! Try again.");
            }
            if (!scanner.hasNextLine()) {
                System.out.println("\nEOF detected. Exiting...");
                System.exit(0);
            }
        }
    }
}