package bci.work;

import bci.Creator;

public class DVD extends Work {

    /** DVD's director. */
    private final Creator _director;

    /** DVD's IGAC. */
    private final String _igac;

    public DVD(int workID, int workPrice, String title, Category category, int nCopies, String igac, Creator director) {
        super(workID, workPrice, title, category, nCopies); // chama o construtor de Work
        _director = director;
        _igac = igac;
    }


    public Creator getDirector() {
        return _director;
    }


    public String getIGAC() {
        return _igac;
    }


    @Override
    public boolean hasCreator(String creatorName) {
        return _director.getCreatorName().equalsIgnoreCase(creatorName);
    }

    @Override
    public boolean correspondsToTerm(String term) {
        term = term.toLowerCase();
        return super.correspondsToTerm(term) ||
               _director.getCreatorName().toLowerCase().contains(term);
    }


    @Override
    public String toString() {
        return getWorkID() + " - " + getAvailableCopies() + " de " + getNCopies() + " - DVD - "
                + getTitle() + " - " + getWorkPrice() +
               " - " + getCategory() + " - " + getDirector() +
               " - " + getIGAC();
    }
}
