package bci.work;

import java.io.Serializable;

public class Category implements Serializable{

    @java.io.Serial
    private static final long serialVersionUID = 202510042130L;

    @Override
    public String toString() {
        return "Categoria";
    }

    public boolean isReference() {
        return false;
    }

    public boolean isScitech() {
        return false;
    }

    public boolean isFiction() {
        return false;
    }
}
