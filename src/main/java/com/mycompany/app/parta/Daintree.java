import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A system keeps a list of book titles that can be purchased,
 * in both physical and ebook form.
 */

 public class Daintree {
     static String[] sBookTitles = {
            "Absolute Java",
            "JAVA: How to Program",
            "Computing Concepts with JAVA 8 Essentials",
            "Java Software Solutions",
            "Java Program Design"
     };
     static int[] sPhysicalCopies = {
         5, 0, 5, 5, 1
     };
     static boolean[] sHasEbooks = {
         true, true, false, false, true
     };
     static final double PricePhysical = 50;
     static final double PriceEbook = 8;

     static int sBookIndexInShoppingCart = -1;
     static boolean sBookIndexIsEbook = false;

     public static void main(String args[]) {
         System.out.println("Welcome to Daintree!");
         Scanner scanner = new Scanner(System.in);
          while (true) {
            int option = printChooseOption(scanner);
            System.out.println();
            switch (option) {
                case 0: {
                    System.out.println("Goodbye!");
                    return;
                }
                case 1: {
                    addBookToShoppingCart(scanner);
                    break;
                }
                case 2: {
                    viewShoppingCart(scanner);
                    break;
                }
                case 3: {
                    removeBookFromShoppingCart(scanner);
                    break;
                }
                case 4: {
                    checkout();
                    break;
                }
                case 5: {
                    listAllBooks();
                    break;
                }
                default: {
                    System.out.println("Sorry, that is an invalid option!");
                    System.out.println();
                    break;
                }
         }
     }
 }

// Print option list and read input option
private static int printChooseOption(Scanner scanner) {
        System.out.println("Choose an option:");
        System.out.println("1. Add a book to shopping cart");
        System.out.println("2. View shopping cart");
        System.out.println("3. Remove a book from shopping cart");
        System.out.println("4. Checkout");
        System.out.println("5. List all books");
        System.out.println("0. Quit");
        System.out.print("Please make a selection: ");
        return readIntFromScanner(scanner);
}

// Add book to the shopping cart
private static void addBookToShoppingCart(Scanner scanner) {
        System.out.print("Enter title to search for: ");
        String name = readStringFromScanner(scanner);
        List<Integer> indexes = new ArrayList<>();
        String prefix = name.toLowerCase();
        for (int i = 0; i < sBookTitles.length; i++) {
            if (sBookTitles[i].toLowerCase().startsWith(prefix)) {
                indexes.add(i);
                break;
            }
        }
        if (indexes.size() == 0) {
            System.out.println("There is no title starting with that");
            System.out.println();
            return;
        }
        System.out.println("The following title is a match:");
        for (int i = 0; i < indexes.size(); i++) {
            System.out.printf("%d. %s%n", (i + 1), sBookTitles[indexes.get(i)]);
        }
        System.out.println("0. Cancel");
        while (true) {
            System.out.print("What is your selection:");
            int option = readIntFromScanner(scanner);
            if (option == 0) {
                System.out.println();
                return;
            } else if (option <= indexes.size()) {
                int index = indexes.get(option - 1);
                double price = PricePhysical;
                if (sHasEbooks[index]) {
                    // has ebook, ask
                    System.out.print("Do you want to buy this as an ebook (yes or no):");
                    while (true) {
                        String useEbookOption = readStringFromScanner(scanner);
                        if (useEbookOption.equalsIgnoreCase("yes")) {
                            price = PriceEbook;
                            // restore old book in shopping cart
                            if (sBookIndexInShoppingCart >= 0) {
                                if (!sBookIndexIsEbook) {
                                    sPhysicalCopies[sBookIndexInShoppingCart]++;
                                }
                            }
                            // add to shopping cart
                            sBookIndexInShoppingCart = index;
                            sBookIndexIsEbook = true;
                            System.out.printf("\"%s\" has been added to your Cart%n", sBookTitles[index]);
                            System.out.println();
                            return;
                        } else if (useEbookOption.equalsIgnoreCase("no")) {
                            break;
                        } else {
                            System.out.println();
                            System.out.print("Input invalid, please input again:");
                        }
                    }
                }
                // no ebook or not buy ebook, check copy nums
                int nowcopy = sPhysicalCopies[index];
                if (nowcopy <= 0) {
                    System.out.println("There are no physical copies of that book available!");
                } else {
                    sPhysicalCopies[index]--;
                    // restore old book in shopping cart
                    if (sBookIndexInShoppingCart >= 0) {
                        if (!sBookIndexIsEbook) {
                            sPhysicalCopies[sBookIndexInShoppingCart]++;
                        }
                    }
                    // add to shopping cart
                    sBookIndexInShoppingCart = index;
                    sBookIndexIsEbook = false;
                    System.out.printf("\"%s\" has been added to your Cart%n", sBookTitles[index]);
                }
                System.out.println();
                return;
            } else {
                System.out.println("Input invalid, please input again.");
                System.out.println();
            }
        }
    }

    // View the shopping cart.
    
    private static void viewShoppingCart(Scanner scanner) {
        if (sBookIndexInShoppingCart < 0) {
            System.out.println("There is nothing in your shopping cart!");
            System.out.println();
        } else {
            printShoppingCart();
            System.out.println();
        }
    }

    //Remove a book from shopping cart
     
    private static void removeBookFromShoppingCart(Scanner scanner) {
        if (sBookIndexInShoppingCart < 0) {
            System.out.println("There is nothing in your shopping cart!");
            System.out.println();
        } else {
            printShoppingCart();
            System.out.println("0. Cancel");
            System.out.print("What do you want to remove:");
            while (true) {
                int option = readIntFromScanner(scanner);
                if (option == 0) {
                    System.out.println();
                    return;
                } else if (option == 1) {
                    // restore old book in shopping cart
                    if (!sBookIndexIsEbook) {
                        sPhysicalCopies[sBookIndexInShoppingCart]++;
                    }
                    sBookIndexInShoppingCart = -1;
                    System.out.println("Item removed from shopping Cart");
                    System.out.println();
                    return;
                } else {
                    System.out.print("Input invalid, please input again:");
                }
            }
        }
    }

    //Checkout.
     
    private static void checkout() {
        if (sBookIndexInShoppingCart < 0) {
            System.out.println("There is nothing in your shopping cart!");
            System.out.println();
        } else {
            printShoppingCart();
            double sum = sBookIndexIsEbook ? PriceEbook : PricePhysical;
            sBookIndexInShoppingCart = -1;
            System.out.printf("You have purchased items to the total value of $%.2f%n", sum);
            System.out.println("Thanks for shopping with Daintree!");
            System.out.println();
        }
    }

    // List all books in console.
    private static void listAllBooks() {
        System.out.println("The following titles are available:");
        for (int i = 0; i < sBookTitles.length; i++) {
            String ebook = sHasEbooks[i] ? "ebook available" : "no ebook";
            System.out.printf("%d. %s, %d copies, %s%n",
                    (i + 1), sBookTitles[i], sPhysicalCopies[i],
                    ebook);
        }
        System.out.println();
    }

    //Read int option from scanner input.

    private static int readIntFromScanner(Scanner scanner) {
        int option;
        while (true) {
            try {
                String line = scanner.nextLine();
                option = Integer.parseInt(line);
                break;
            } catch (Exception e) {
                System.out.println();
                System.out.print("Input invalid, please input again:");
            }
        }
        return option;
    }

    // Read String option from scanner input.
 
    private static String readStringFromScanner(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine();
            if (line.length() > 0) {
                return line;
            }
        }
    }

    // Print the books in Shopping Cart.

    private static void printShoppingCart() {
        System.out.println("Your Shopping Cart contains the following:");
        String type = sBookIndexIsEbook ? "ebook" : "physical";
        double price = sBookIndexIsEbook ? PriceEbook : PricePhysical;
        System.out.printf("1. %s (%s), $%.2f %n", sBookTitles[sBookIndexInShoppingCart], type, price);
    }
}