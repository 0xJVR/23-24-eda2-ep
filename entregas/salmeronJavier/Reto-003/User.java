import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private Library library;

    public User(Library library) {
        this.library = library;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n ~~~| Library Management System |~~~");
            System.out.println("  1. Add Document");
            System.out.println("  2. Delete Document");
            System.out.println("  3. Update Document");
            System.out.println("  4. Search Documents");
            System.out.println("  5. Exit");
            String choice = UserIO.getInput(" Choose an option: ");

            switch (choice) {
                case "1":
                    addDocument();
                    break;
                case "2":
                    deleteDocumentByTitle();
                    break;
                case "3":
                    updateDocument();
                    break;
                case "4":
                    searchDocuments();
                    break;
                case "5":
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void addDocument() {
        String title = UserIO.getInput("Enter document title: ");

        String authorsStr = UserIO.getInput("Enter authors (comma-separated): ");
        List<String> authors = new ArrayList<>(Arrays.asList(authorsStr.split("\\s*,\\s*")));

        int year = Integer.parseInt(UserIO.getInput("Enter publishing year: "));

        String type = UserIO.getInput("Enter document type: ");

        String keywordsStr = UserIO.getInput("Enter keywords (comma-separated): ");
        List<String> keywords = new ArrayList<>(Arrays.asList(keywordsStr.split("\\s*,\\s*")));

        Document newDocument = new Document(title, authors, year, type);
        for (String keyword : keywords) {
            newDocument.addKeyword(keyword);
        }
        library.addDocument(newDocument);
        System.out.println("Document added successfully.");
    }

    private void deleteDocumentByTitle() {
        String title = UserIO.getInput("Enter the title of the document to delete: ");
        library.deleteDocument(title);
        System.out.println("Document deleted.");
    }

    private void updateDocument() {
        String title = UserIO.getInput("Enter the title of the document you want to update: ");
        Document existingDocument = library.searchDocumentByTitle(title);
        if (existingDocument == null) {
            System.out.println("Document not found.");
            return;
        }

        String newTitle = UserIO.getInput("Enter new document title (or press Enter to keep '" + existingDocument.getTitle() + "''): ");
        if (newTitle.isEmpty()) {
            newTitle = existingDocument.getTitle();
        }

        String authorsStr = UserIO.getInput("Enter new authors (comma-separated, or press Enter to keep existing): ");
        List<String> authors = authorsStr.isEmpty() ? existingDocument.getAuthors() : new ArrayList<>(Arrays.asList(authorsStr.split("\\s*,\\s*")));

        String yearStr = UserIO.getInput("Enter new publishing year (or press Enter to keep '" + existingDocument.getPublishingYear() + "''): ");
        int year = yearStr.isEmpty() ? existingDocument.getPublishingYear() : Integer.parseInt(yearStr);

        String type = UserIO.getInput("Enter new document type (or press Enter to keep '" + existingDocument.getType() + "''): ");
        if (type.isEmpty()) {
            type = existingDocument.getType();
        }

        String keywordsStr = UserIO.getInput("Enter new keywords (comma-separated, or press Enter to keep existing): ");
        List<String> keywords = keywordsStr.isEmpty() ? existingDocument.getKeyWords() : new ArrayList<>(Arrays.asList(keywordsStr.split("\\s*,\\s*")));
    
        Document updatedDocument = new Document(newTitle, authors, year, type);
        for (String keyword : keywords) {
            updatedDocument.addKeyword(keyword);
        }
        
        library.updateDocument(title, updatedDocument);
        System.out.println("Document updated successfully.");
    }
    
    private void searchDocuments() {
        List<Document> searchResults;
        String searchType = UserIO.getInput("Search by (title/author/year/type/keyword): ");
        switch (searchType.toLowerCase()) {
            case "title":
                String title = UserIO.getInput("Enter title: ");
                Document document = library.searchDocumentByTitle(title);
                if (document != null) {
                    System.out.println(" Document found: " + document);
                    System.out.println(" - Title: " + document.getTitle());
                    System.out.println(" - Authors: " + String.join(", ", document.getAuthors()));
                    System.out.println(" - Publishing Year: " + document.getPublishingYear());
                    System.out.println(" - Type: " + document.getType());
                    System.out.println(" - Keywords: " + String.join(", ", document.getKeyWords()));
                } else {
                    System.out.println("No document found with the title '" + title + "'");
                }
                break;
            case "author":
                String author = UserIO.getInput("Enter author: ");
                searchResults = library.searchByAuthor(author);
                for (Document doc : searchResults) {
                    System.out.println("Found: " + doc.getTitle() + " By: " + String.join(", ", doc.getAuthors()));
                }
                break;
            case "year":
                int year = UserIO.getInt("Enter year: ");
                searchResults = library.searchByYear(year);
                for (Document doc : searchResults) {
                    System.out.println("Found: " + doc.getTitle() + " By: " + String.join(", ", doc.getAuthors()));
                }
                break;
            case "type":
                String type = UserIO.getInput("Enter document type: ");
                searchResults = library.searchByType(type);
                for (Document doc : searchResults) {
                    System.out.println("Found: " + doc.getTitle() + " By: " + String.join(", ", doc.getAuthors()));
                }
                break;
            case "keyword":
                String keyword = UserIO.getInput("Enter keyword: ");
                searchResults = library.searchByKeyword(keyword);
                for (Document doc : searchResults) {
                    System.out.println("Found: " + doc.getTitle() + " By: " + String.join(", ", doc.getAuthors()));
                }
                break;
            default:
                System.out.println("Invalid search type.");
                break;
        }
    }
    
}
