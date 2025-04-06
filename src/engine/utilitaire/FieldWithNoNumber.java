package engine.utilitaire;

@SuppressWarnings("serial")
public class FieldWithNoNumber extends IllegalArgumentException {
	
	/**
	 * Cette exception défini un champ de texte sans numéro valide.
	 *
     * @param field Le champs de texte non valide
     */
    public FieldWithNoNumber(String field) {
        super("Le texte saisi n'est pas un nombre valide"+ field);
    }
}