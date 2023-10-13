import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Contact {
    private String name;
    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber;
    }
}

class ContactManager {
    private ArrayList<Contact> contacts = new ArrayList<>();
    private String dataFile = "contacts.txt";
    private static final String DATA_PATH = "src/contacts.txt";

    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContactsToFile();
    }

    public void deleteContact(String name) {
        contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
        saveContactsToFile();
    }

    public Contact findContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public void updateContact(String name, String newPhoneNumber) {
        Contact contact = findContact(name);
        if (contact != null) {
            contact.setPhoneNumber(newPhoneNumber);
            saveContactsToFile();
            System.out.println("Contact updated successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    public void displayContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("Contacts:");
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    public void loadContactsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String phoneNumber = parts[1].trim();
                    Contact contact = new Contact(name, phoneNumber);
                    contacts.add(contact);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading contacts from file: " + e.getMessage());
        }
    }

    public void saveContactsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Contact contact : contacts) {
                writer.write(contact.getName() + ", " + contact.getPhoneNumber());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing contacts to file: " + e.getMessage());
        }
    }
}

public class ContactManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ContactManager contactManager = new ContactManager();
        contactManager.loadContactsFromFile();

        while (true) {
            System.out.println("Contact Management System Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. Delete Contact");
            System.out.println("3. Find Contact");
            System.out.println("4. Update Contact");
            System.out.println("5. Display Contacts");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.nextLine();
                    Contact newContact = new Contact(name, phoneNumber);
                    contactManager.addContact(newContact);
                    System.out.println("Contact added successfully.");
                    break;
                case 2:
                    System.out.print("Enter name to delete: ");
                    String nameToDelete = scanner.nextLine();
                    contactManager.deleteContact(nameToDelete);
                    System.out.println("Contact deleted successfully.");
                    break;
                case 3:
                    System.out.print("Enter name to find: ");
                    String nameToFind = scanner.nextLine();
                    Contact foundContact = contactManager.findContact(nameToFind);
                    if (foundContact != null) {
                        System.out.println("Contact found: " + foundContact);
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter name to update: ");
                    String nameToUpdate = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    contactManager.updateContact(nameToUpdate, newPhoneNumber);
                    break;
                case 5:
                    contactManager.displayContacts();
                    break;
                case 6:
                    System.out.println("Exiting Contact Management System.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
