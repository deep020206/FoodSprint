import java.util.*;

public class FoodSprintApp {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, Double> order = new LinkedHashMap<>();
    static List<Double> individualTotals = new ArrayList<>();
    static List<Double> individualDiscounts = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("+---------------------------------------------------------+");
        System.out.println("|                    Welcome to FoodSprint!               |");
        System.out.println("+---------------------------------------------------------+");

        boolean moreOrders = true;

        while (moreOrders) {
            System.out.println("\n+---------------------------------------------------------+");
            System.out.println("|                 Choose a Restaurant                     |");
            System.out.println("+---------------------------------------------------------+");
            System.out.println("| 1. Pizza Palace (Veg & Non-Veg)                         |");
            System.out.println("| 2. Burger Barn (Non-Veg & Beverages)                    |");
            System.out.println("| 3. Pasta House (Veg)                                    |");
            System.out.println("| 4. Finish and Generate Bill                             |");
            System.out.println("+---------------------------------------------------------+");
            System.out.print("Enter the restaurant number: ");
            int restaurantChoice = scanner.nextInt();

            switch (restaurantChoice) {
                case 1:
                    displayAdvertisement("Pizza Palace");
                    showMenuCategories("Pizza Palace", new String[]{"Veg", "Non-Veg"});
                    break;
                case 2:
                    displayAdvertisement("Burger Barn");
                    showMenuCategories("Burger Barn", new String[]{"Non-Veg", "Beverages"});
                    break;
                case 3:
                    displayAdvertisement("Pasta House");
                    showMenuCategories("Pasta House", new String[]{"Veg"});
                    break;
                case 4:
                    generateBill();
                    processPayment();
                    moreOrders = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid restaurant.");
            }
        }
    }

    static void displayAdvertisement(String hotelName) {
        System.out.println("\n+---------------------------------------------------------+");
        System.out.println("|                *** Special Offer! ***                   |");
        System.out.println("|    Order from " + hotelName + " and get amazing discounts!    |");
        System.out.println("+---------------------------------------------------------+");
    }

    static void showMenuCategories(String hotelName, String[] categories) {
        System.out.println("\n+---------------------------------------------------------+");
        System.out.println("|       Select a category for " + hotelName + "                 |");
        System.out.println("+---------------------------------------------------------+");
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("| %d. %s                                              |\n", (i + 1), categories[i]);
        }
        System.out.println("+---------------------------------------------------------+");
        System.out.print("Enter category number: ");
        int categoryChoice = scanner.nextInt();

        switch (hotelName) {
            case "Pizza Palace":
                if (categoryChoice == 1) {
                    handleRestaurantMenu(hotelName, new String[]{"Margherita Pizza", "Veggie Burger", "Veg Pasta"},
                            new double[]{300.0, 250.0, 220.0});
                } else {
                    handleRestaurantMenu(hotelName, new String[]{"Chicken Pizza", "Pepperoni Pizza"},
                            new double[]{350.0, 400.0});
                }
                break;
            case "Burger Barn":
                if (categoryChoice == 1) {
                    handleRestaurantMenu(hotelName, new String[]{"Chicken Burger", "Beef Burger"},
                            new double[]{350.0, 400.0});
                } else {
                    handleBeverageMenu(hotelName, new String[]{"Fries", "Coke"},
                            new double[]{100.0, 50.0});
                }
                break;
            case "Pasta House":
                handleRestaurantMenu(hotelName, new String[]{"Paneer Tikka", "Veg Wrap", "Veggie Salad"},
                        new double[]{200.0, 150.0, 120.0});
                break;
            default:
                System.out.println("Invalid hotel selection.");
        }
    }

    static void handleBeverageMenu(String hotelName, String[] items, double[] prices) {
        boolean ordering = true;
        double currentTotal = 0.0;

        while (ordering) {
            System.out.println("\n+---------------------------------------------------------+");
            System.out.println("|              Beverage Menu for " + hotelName + "               |");
            System.out.println("+---------------------------------------------------------+");
            for (int i = 0; i < items.length; i++) {
                System.out.printf("| %d. %s - %.2f Rupees                                    |\n", (i + 1), items[i], prices[i]);
            }
            System.out.println("| 0. Go Back to Category Selection                        |");
            System.out.println("+---------------------------------------------------------+");
            System.out.print("Enter the beverage item number to order (0 to go back): ");
            int itemChoice = scanner.nextInt();

            if (itemChoice == 0) {
                return; // Go back to the category selection
            } else if (itemChoice > 0 && itemChoice <= items.length) {
                System.out.print("Enter the quantity: ");
                int quantity = scanner.nextInt();
                String selectedItem = items[itemChoice - 1];
                double price = prices[itemChoice - 1] * quantity;
                order.put(selectedItem + " x " + quantity, price);
                currentTotal += price;

                System.out.println("\n+---------------------------------------------------------+");
                System.out.println("| Your current selection:                                 |");
                for (String orderedItem : order.keySet()) {
                    System.out.printf("| %s - %.2f Rupees                                        |\n", orderedItem, order.get(orderedItem));
                }
                System.out.printf("| Current total for %s: %.2f Rupees                        |\n", hotelName, currentTotal);
                System.out.println("+---------------------------------------------------------+");
            } else {
                System.out.println("Invalid item choice.");
            }
        }
    }

    static void handleRestaurantMenu(String hotelName, String[] items, double[] prices) {
        boolean ordering = true;
        double currentTotal = 0.0;

        while (ordering) {
            System.out.println("\n+---------------------------------------------------------+");
            System.out.println("|                  Menu for " + hotelName + "                  |");
            System.out.println("+---------------------------------------------------------+");
            for (int i = 0; i < items.length; i++) {
                System.out.printf("| %d. %s - %.2f Rupees                                    |\n", (i + 1), items[i], prices[i]);
            }
            System.out.println("| 0. Back to Category Selection                           |");
            System.out.println("| 5. Delete an Item                                       |");
            System.out.println("+---------------------------------------------------------+");
            System.out.print("Enter the item number to order (0 to finish, 5 to delete an item): ");
            int itemChoice = scanner.nextInt();

            if (itemChoice == 0) {
                individualTotals.add(currentTotal);
                individualDiscounts.add(calculateDiscount(currentTotal, hotelName));
                return; // Go back to the main menu
            } else if (itemChoice == 5) {
                deleteItem();
                continue; // Show options again after deletion
            } else if (itemChoice > 0 && itemChoice <= items.length) {
                System.out.print("Enter the quantity: ");
                int quantity = scanner.nextInt();
                String selectedItem = items[itemChoice - 1];
                double price = prices[itemChoice - 1] * quantity;
                order.put(selectedItem + " x " + quantity, price);
                currentTotal += price;

                System.out.println("\n+---------------------------------------------------------+");
                System.out.println("| Your current selection:                                 |");
                for (String orderedItem : order.keySet()) {
                    System.out.printf("| %s - %.2f Rupees                                        |\n", orderedItem, order.get(orderedItem));
                }
                System.out.printf("| Current total for %s: %.2f Rupees                        |\n", hotelName, currentTotal);
                System.out.println("+---------------------------------------------------------+");
                
                System.out.println("\n+---------------------------------------------------------+");
                System.out.println("| What would you like to do next?                         |");
                System.out.println("| 1. Add more items                                       |");
                System.out.println("| 2. Delete an item                                       |");
                System.out.println("| 3. Go back to select another hotel                      |");
                System.out.println("| 4. Finish and Generate Bill                             |");
                System.out.println("+---------------------------------------------------------+");
                int action = scanner.nextInt();

                switch (action) {
                    case 1:
                        continue; // Add more items
                    case 2:
                        deleteItem();
                        break;
                    case 3:
                        individualTotals.add(currentTotal);
                        individualDiscounts.add(calculateDiscount(currentTotal, hotelName));
                        return; // Go back to select another hotel
                    case 4:
                        individualTotals.add(currentTotal);
                        individualDiscounts.add(calculateDiscount(currentTotal, hotelName));
                        generateBill();
                        ordering = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Invalid item choice.");
            }
        }
    }

    static void deleteItem() {
        System.out.println("\n+---------------------------------------------------------+");
        System.out.println("| Your current selection:                                 |");
        List<String> keys = new ArrayList<>(order.keySet());
        int count = 1;
        for (String item : keys) {
            System.out.printf("| %d. %s - %.2f Rupees                                    |\n", count++, item, order.get(item));
        }
        System.out.println("+---------------------------------------------------------+");
        System.out.print("Enter the item number to delete: ");
        int deleteChoice = scanner.nextInt();
        if (deleteChoice > 0 && deleteChoice <= keys.size()) {
            order.remove(keys.get(deleteChoice - 1));
            System.out.println("Item deleted successfully.");
        } else {
            System.out.println("Invalid selection.");
        }
    }

    static double calculateDiscount(double total, String hotelName) {
        double discount = hotelName.equals("Pizza Palace") ? 0.10 : hotelName.equals("Burger Barn") ? 0.15 : 0.05;
        return total * discount;
    }

    static void generateBill() {
        System.out.println("\n+---------------------------------------------------------+");
        System.out.println("|                   Final Bill                            |");
        System.out.println("+---------------------------------------------------------+");
        double finalTotal = 0.0;
        for (String orderedItem : order.keySet()) {
            System.out.printf("| %s - %.2f Rupees                                        |\n", orderedItem, order.get(orderedItem));
            finalTotal += order.get(orderedItem);
        }
        double discount = individualDiscounts.stream().mapToDouble(Double::doubleValue).sum();
        System.out.printf("| Total before discount: %.2f Rupees                      |\n", finalTotal);
        System.out.printf("| Discount: %.2f Rupees                                    |\n", discount);
        System.out.printf("| Total after discount: %.2f Rupees                        |\n", finalTotal - discount);
        System.out.println("+---------------------------------------------------------+");
        System.out.println("| Thank you for using FoodSprint!                         |");
        System.out.println("+---------------------------------------------------------+");
    }

    static void processPayment() {
        System.out.println("\n+---------------------------------------------------------+");
        System.out.println("|                Select a Payment Method                  |");
        System.out.println("+---------------------------------------------------------+");
        System.out.println("| 1. Credit Card                                          |");
        System.out.println("| 2. Debit Card                                           |");
        System.out.println("| 3. Net Banking                                          |");
        System.out.println("| 4. UPI                                                  |");
        System.out.println("| 5. Cash on Delivery                                     |");
        System.out.println("+---------------------------------------------------------+");
        System.out.print("Enter your payment method: ");
        int paymentChoice = scanner.nextInt();

        switch (paymentChoice) {
            case 1:
                System.out.println("Processing Credit Card payment...");
                break;
            case 2:
                System.out.println("Processing Debit Card payment...");
                break;
            case 3:
                System.out.println("Processing Net Banking payment...");
                break;
            case 4:
                System.out.println("Processing UPI payment...");
                break;
            case 5:
                System.out.println("Payment selected: Cash on Delivery.");
                break;
            default:
                System.out.println("Invalid payment method.");
        }

        System.out.println("Payment processed successfully. Thank you for using FoodSprint!");
        System.out.println("+---------------------------------------------------------+");
    }
}
