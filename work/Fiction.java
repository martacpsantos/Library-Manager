package bci.work;

public class Fiction extends Category{

    @Override
    public boolean isFiction() {
        return true;
    }

    @Override
    public String toString() {
        return "Ficção";
    }
}
