package optimodLyon.io;

/**
 * Classe qui représente une exception si un fichier XML est malformé.
 * @author Dorian TERBAH
 * @since 1.0
 */
public class MalformedXMLException extends Exception
{
    public MalformedXMLException(String message)
    {
        super(message);
    }
}
