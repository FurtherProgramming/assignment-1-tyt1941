import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A system keeps a list of book titles that can be purchased,
 * in both physical and ebook form.
 */
public class BookStore {
    static final Book[] sBooks = Book.getAllBooks();
    // books in Shopping Cart
    static Book.Item sShoppingCart = null;

    public static void main(String[] args) {
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

    /**
     * Print option list and read input option.
     */
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

    /**
     * Add book to the shopping cart.
     */
    private static void addBookToShoppingCart(Scanner scanner) {
        System.out.print("Enter title to search for: ");
        String name = readStringFromScanner(scanner);
        List<Integer> indexes = new ArrayList<>();
        String prefix = name.toLowerCase();
        for (int i = 0; i < sBooks.length; i++) {
            if (sBooks[i].title.toLowerCase().startsWith(prefix)) {
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
            System.out.printf("%d. ", (i + 1));
            sBooks[indexes.get(i)].showTitleAuthor();
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
                if (sBooks[index].hasEbook) {
                    // has ebook, ask
                    System.out.print("Do you want to buy this as an ebook (yes or no):");
                    while (true) {
                        String useEbookOption = readStringFromScanner(scanner);
                        if (useEbookOption.equalsIgnoreCase("yes")) {
                            // add to shopping cart
                            Book.Item item = sBooks[index].toItem(true);//问题
                            sShoppingCart = item;
                            System.out.printf("\"%s\" has been added to your Cart%n", sBooks[index].title);
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
                int nowcopy = sBooks[index].copies;
                if (nowcopy <= 0) {
                    System.out.println("There are no physical copies of that book available!");
                } else {
                    sBooks[index].copies--;
                    // add to shopping cart
                    Book.Item item = sBooks[index].toItem(false);
                    sShoppingCart = item;
                    System.out.printf("\"%s\" has been added to your Cart%n", sBooks[index].title);
                }
                System.out.println();
                return;
            } else {
                System.out.println("Input invalid, please input again.");
                System.out.println();
            }
        }
    }

    /**
     * View the shopping cart.
     */
    private static void viewShoppingCart(Scanner scanner) {
        if (sShoppingCart == null) {
            System.out.println("There is nothing in your shopping cart!");
            System.out.println();
        } else {
            printShoppingCart();
            System.out.println();
        }
    }

    /**
     * Remove a book from shopping cart
     */
    private static void removeBookFromShoppingCart(Scanner scanner) {
        if (sShoppingCart == null) {
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
                } else if (option <= 1) {
                    int index = option - 1;
                    // when deleted item is physical, plus 1 to physical book num
                    if (!sShoppingCart.isEbook) {
                        sShoppingCart.book.copies++;
                    }
                    sShoppingCart = null;
                    System.out.println("Item removed from shopping Cart");
                    System.out.println();
                    return;
                } else {
                    System.out.print("Input invalid, please input again:");
                }
            }
        }
    }

    /**
     * Checkout.
     */
    private static void checkout() {
        if (sShoppingCart == null) {
            System.out.println("There is nothing in your shopping cart!");
            System.out.println();
        } else {
            printShoppingCart();
            double sum = sShoppingCart.price;
            sShoppingCart = null;
            System.out.printf("You have purchased items to the total value of $%.2f%n", sum);
            System.out.println("Thanks for shopping with Daintree!");
            System.out.println();
        }
    }

    /**
     * List all books in console.
     */
    private static void listAllBooks() {
        System.out.println("The following titles are available:");
        for (int i = 0; i < sBooks.length; i++) {
            System.out.printf("%d. ", (i + 1));
            sBooks[i].showFullInfo();
        }
        System.out.println();
    }

    /**
     * Read int option from scanner input.
     */
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

    /**
     * Read String option from scanner input.
     */
    private static String readStringFromScanner(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine();
            if (line.length() > 0) {
                return line;
            }
        }
    }

    /**
     * Print the books in Shopping Cart.
     */
    private static void printShoppingCart() {
        System.out.println("Your Shopping Cart contains the following:");
        System.out.printf("%d. ", 1);
        sShoppingCart.showItemInfo();
    }
}