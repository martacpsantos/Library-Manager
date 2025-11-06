package bci.work;

import bci.Creator;
import java.util.ArrayList;
import java.util.List;

public class Book extends Work {
    
    /** Book's creators. */
    private final List<Creator> _creators = new ArrayList<>();

    /** Book's ISBN. */
    private final String _isbn;

    public Book(int workID, int workPrice, String title, Category category, int nCopies, String isbn, List <Creator> creators) {
        super(workID, workPrice, title, category, nCopies);
        _isbn = isbn;
        _creators.addAll(creators);
    }


    public List<Creator> getCreators() {
        return _creators;
    }


    public String getISBN() {
        return _isbn;
    }


    public void addCreator(Creator c) {
        _creators.add(c);
    }


    @Override
    public boolean hasCreator(String creatorName) {
        return _creators.stream()
            .anyMatch(c -> c.getCreatorName().equalsIgnoreCase(creatorName));
    }


    @Override
    public boolean correspondsToTerm(String term) {
        term = term.toLowerCase();
        if (super.correspondsToTerm(term)) {
            return true;
        }
        for (Creator c : _creators) {
            if (c.getCreatorName().toLowerCase().contains(term)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String creatorsStr = String.join("; ",
            _creators.stream().map(Creator::getCreatorName).toList());

        return getWorkID() + " - " + getAvailableCopies() + " de " + getNCopies() +
            " - Livro - " + getTitle() + " - " + getWorkPrice() +
            " - " + getCategory() + " - " + creatorsStr + " - " + getISBN();
    }
}
