/**
 * Aceasta clasa este implementata pentru a trata exceptiile
 * generate prin folosirea unui director care nu exista.
 *
 * @author Lacramioara Runcan
 */

public class DirectoryNotFoundException extends Exception {
    /**
     * Descrierea membrilor
     */
    private String message;

    /**
     * Constructorul clasei DirectoryNotFoundException
     * @param message Mesajul de eroare
    */
    public DirectoryNotFoundException(String message) {
        this.message = message;
    }

    /**
     * Getter pentru mesajul de eroare
     * @return message Mesajul de eroare
     */
    public String getMessage()
    {
        return this.message;
    }
}
