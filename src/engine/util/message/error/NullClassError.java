package engine.util.message.error;

import engine.util.message.MessageError;

/**
 * Représente une erreur indiquant qu'un objet d'une certaine classe ne doit pas être {@code null}.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public final class NullClassError implements MessageError {

    private final Class<?> clazz;

    /**
     * Construit une nouvelle {@code NullClassError} pour la classe spécifiée.
     *
     * @param clazz la classe de l'objet qui est {@code null}.
     */
    public NullClassError(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * Retourne le message d'erreur indiquant que l'objet ne peut pas être {@code null}.
     *
     * @return un message d'erreur lisible.
     */
    @Override
    public String getMessage() {
        return "Object of class " + clazz.getSimpleName() + " cannot be null";
    }
}