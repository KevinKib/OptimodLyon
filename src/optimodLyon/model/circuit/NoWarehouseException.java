package optimodLyon.model.circuit;

/**
 * Classe qui représente une exception si un circuit n'a pas comme point de départ un dépôt
 * @author Fanny ROUVEL
 * @since 1.0
 */
public class NoWarehouseException extends Exception{

    public NoWarehouseException(String message) {
            super(message);
    }
}
