package engine.util;

/**
 * Représente un type singleton "Unit" similaire au concept de "Unit" dans certains langages fonctionnels,
 * comme Kotlin ou Scala. Cette classe est utilisée pour signifier l'absence de valeur utile,
 * tout en permettant de respecter des contraintes de type.
 * 
 * <p>Le design suit le pattern singleton : une unique instance de {@code Unit} existe pendant toute
 * la durée de vie de l'application.</p>
 */
public final class Unit {
	
    /**
     * L'unique instance de {@code Unit}.
     */
    private static final Unit instance = new Unit();

    /**
     * Constructeur privé pour empêcher l'instanciation externe.
     */
    private Unit() {}

    /**
     * Renvoie l'unique instance de {@code Unit}.
     *
     * @return l'unique instance de {@code Unit}
     */
    public static Unit get() {
        return instance;
    }
}