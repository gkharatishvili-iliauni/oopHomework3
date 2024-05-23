package lms;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LMS implements Serializable {
    private static final long serialVersionUID = 1L;

    List<Book> books = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public boolean removeBook(Book book) {
        return books.remove(book);
    }

    public boolean borrowBook(Book book, Student student) {
        for (Book b : books) {
            if (b.equals(book)) {
                if (!b.isBorrowed()) {
                    b.setBorrowed(true);
                    b.setBorrowedBy(student);
                    System.out.println(student.getName() + " borrowed \"" + book.getTitle() + "\" successfully.");
                    return true;
                } else {
                    System.out.println("Sorry, \"" + book.getTitle() + "\" is already borrowed by another student.");
                    return false;
                }
            }
        }
        System.out.println("Sorry, the book \"" + book.getTitle() + "\" is not available in the library.");
        return false;
    }

    public boolean returnBook(Book book) {
        for (Book b : books) {
            if (b.equals(book)) {
                if (b.isBorrowed()) {
                    b.setBorrowed(false);
                    b.setBorrowedBy(null);
                    System.out.println("Book \"" + book.getTitle() + "\" returned successfully.");
                    return true;
                } else {
                    System.out.println("Book \"" + book.getTitle() + "\" was not borrowed.");
                    return false;
                }
            }
        }
        System.out.println("Book \"" + book.getTitle() + "\" is not available in the library.");
        return false;
    }

    public void saveState(String filePath) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(this);
            System.out.println("Library state saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving library state: " + e.getMessage());
        }
    }

    public static LMS loadState(String filePath) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            Object obj = inputStream.readObject();
            if (obj instanceof LMS) {
                System.out.println("Library state loaded successfully.");
                return (LMS) obj;
            } else {
                throw new ClassNotFoundException("Invalid class in file: " + filePath);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading library state from " + filePath + ": " + e.getMessage());
            return null;
        }
    }
}
