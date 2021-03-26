package optimodLyon.io;

/**
 * Classe qui représente une exception si un fichier n'est pas au format attendu.
 * @author Alexandre DUFOUR
 * @since 1.0
 */
public class UnsupportedFileException extends Exception
{
    public UnsupportedFileException(String message)
    {
        super(message);
    }
}
