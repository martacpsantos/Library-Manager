package bci.app.user;

public interface Message {

    static String registrationSuccessful(int idUser) {
        return "Novo utente criado com o número " + idUser + ".";
    }
    static String DuplicateKeyUserException(int key) {
        return "O utente '" + key + "' já existe.";
    }
    static Object duplicateAnimalKey(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'duplicateAnimalKey'");
    }
    static String duplicateKeyUser(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'duplicateKeyUser'");
    }
}
