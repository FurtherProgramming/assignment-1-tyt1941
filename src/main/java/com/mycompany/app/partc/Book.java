public class Book {
    public final String title;
    public final String author;
    public int copies;
    public final boolean hasEbook;

    public Book(String title, String author, int copies, boolean hasEbook) {
        this.title = title;
        this.author = author;
        this.copies = copies;
        this.hasEbook = hasEbook;
    }

    /**
     * Generate all Books
     *
     * @return Book[]
     */
    public static Book[] getAllBooks() {
        Book[] books = new Book[5];
        books[0] = new Book("Absolute Java", "Savitch", 5, true);
        books[1] = new Book("Java: How to Program", "Deitel and Deitel", 0, true);
        books[2] = new Book("Computing Concepts with JAVA 8 Essentials", "Horstman", 5, false);
        books[3] = new Book("Java Software Solutions", "Lewis and Loftus", 5, false);
        books[4] = new Book("Java Program Design", "Cohoon and Davidson", 1, true);
        return books;
    }

    /**
     * Print title and author to the console
     */
    public void showTitleAuthor() {
        System.out.printf("%s -- %s%n", title, author);
    }

    /**
     * Show full info of the book
     */
    public void showFullInfo() {
        String ebook = hasEbook ? "ebook available" : "no ebook";
        System.out.printf("%s -- %s, %d copies, %s%n", title, author, copies, ebook);
    }

    /**
     * Generate an Item object to store in the Shopping Cart
     *
     * @param isEbook choose ebook or not
     * @return Item
     */
    public Item toItem(boolean isEbook) {
        return new Item(this, isEbook);
    }

    public static class Item {
        static final double PricePhysical = 50;
        static final double PriceEbook = 8;

        public final Book book;
        public final boolean isEbook;
        public final double price;

        public Item(Book book, boolean isEbook) {
            this.book = book;
            this.isEbook = isEbook;
            this.price = isEbook ? PriceEbook : PricePhysical;
        }

        /**
         * Print Item info to the console
         */
        public void showItemInfo() {
            String ebook = isEbook ? "ebook" : "physical";
            System.out.printf("%s -- %s, (%s), $%.2f%n",
                    book.title, book.author, ebook, price);
        }
    }
}