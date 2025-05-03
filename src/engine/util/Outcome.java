package engine.util;

import java.util.function.Consumer;
import java.util.function.Function;

import engine.message.Message;
import engine.message.MessageError;
import engine.message.MessageSuccess;

/**
 * Représente le résultat d'une opération métier sous forme de succès ou d'échec, 
 * avec la capacité de transporter une valeur de résultat et un message associé.
 * <p>
 * Contrairement aux modèles fonctionnels classiques comme {@code Either}, {@code Outcome}
 * est conçu pour exprimer l'issue concrète d'une action métier, en indiquant 
 * explicitement si l'opération a réussi ou échoué. Cette classe est utile dans des
 * contextes tels qu'un déplacement de personnage, une action sur la carte, ou toute 
 * autre opération à impact métier clair.
 * <p>
 * Ses objectifs principaux sont :
 * <ul>
 *     <li>Permettre un chaînage fluide d'opérations en fournissant un statut clair de succès ou d'échec ;</li>
 *     <li>Transporter un résultat métier optionnel qui peut être exploité quelle que soit l'issue ;</li>
 *     <li>Offrir un message métier explicatif (succès ou erreur), utile pour enrichir le retour utilisateur ou les logs.</li>
 * </ul>
 *
 * <h3>Caractéristiques principales :</h3>
 * <ul>
 *     <li>Statut explicite : {@link #isSuccess()} / {@link #isFailure()};</li>
 *     <li>Accès au résultat métier : {@link #getResult()};</li>
 *     <li>Accès au message associé : {@link #getMessage()};</li>
 *     <li>API fonctionnelle : {@link #map(Function)}, {@link #flatMap(Function)}, {@link #onValue(Consumer)} etc.</li>
 * </ul>
 * 
 * <h3>Exemple d'utilisation :</h3>
 * <pre>{@code
 * Outcome<Position> outcome = moveCharacter();
 * 
 * outcome
 *     .ifSuccess(pos -> System.out.println("Déplacement réussi vers " + pos))
 *     .ifFailure(pos -> System.out.println("Déplacement échoué, tentative vers " + pos))
 *     .ifMessage(msg -> log.info("Contexte : " + msg.getText()));
 * }</pre>
 *
 * @param <T> le type de la valeur métier transportée
 * 
 * @version 1.1
 * @since 2025-05-03
 */
public final class Outcome<T> {

    private final boolean success;
    private final T result;
    private final Message message;

    private Outcome(boolean success, T result, Message message) {
        this.success = success;
        this.result = result;
        this.message = message;
    }

    /**
     * Crée un {@code Outcome} représentant un succès avec un résultat et un message explicatif.
     *
     * @param result la valeur métier résultante
     * @param message le message associé (ex. : succès métier)
     * @return un {@code Outcome} en succès
     */
    public static <T> Outcome<T> success(T result, MessageSuccess message) {
        return new Outcome<>(true, result, message);
    }

    /**
     * Crée un {@code Outcome} représentant un échec avec un résultat et un message explicatif.
     *
     * @param result la valeur métier résultante (peut représenter l'intention ou un fallback)
     * @param message le message d'erreur ou d'information
     * @return un {@code Outcome} en échec
     */
    public static <T> Outcome<T> failure(T result, MessageError message) {
        return new Outcome<>(false, result, message);
    }

    /**
     * Indique si l'opération est un succès.
     *
     * @return {@code true} si succès, sinon {@code false}
     */
    public boolean isSuccess() {
        return success;
    }
    
    /**
     * Indique si l'opération est un échec.
     *
     * @return {@code true} si échec, sinon {@code false}
     */
    public boolean isFailure() {
        return !success;
    }

    /**
     * Retourne la valeur métier résultante (peut être {@code null}).
     *
     * @return la valeur métier
     */
    public T getResult() {
        return result;
    }

    /**
     * Retourne le message métier associé (peut être {@code null}).
     *
     * @return le message métier
     */
    public Message getMessage() {
        return message;
    }
    
    /**
     * Retourne le message en tant que {@link MessageError} si applicable.
     *
     * @return le message en tant que {@code MessageError}, ou {@code null} si ce n'est pas le cas.
     */
    public MessageError getMessageError() {
        return message instanceof MessageError ? (MessageError) message : null;
    }

    /**
     * Retourne le message en tant que {@link MessageSuccess} si applicable.
     *
     * @return le message en tant que {@code MessageSuccess}, ou {@code null} si ce n'est pas le cas.
     */
    public MessageSuccess getMessageSuccess() {
        return message instanceof MessageSuccess ? (MessageSuccess) message : null;
    }

    /**
     * Transforme la valeur métier en appliquant la fonction fournie, 
     * tout en conservant le statut (succès/échec) et le message.
     *
     * @param mapper la fonction de transformation
     * @param <U> le nouveau type de la valeur
     * @return un nouveau {@code Outcome} avec la valeur transformée
     */
    public <U> Outcome<U> map(Function<? super T, ? extends U> mapper) {
        U newResult = (result != null) ? mapper.apply(result) : null;
        return new Outcome<>(success, newResult, message);
    }

    /**
     * Applique une fonction qui retourne directement un autre {@code Outcome}.
     *
     * @param mapper la fonction de transformation
     * @param <U> le nouveau type de la valeur
     * @return le résultat {@code Outcome} de la fonction fournie
     */
    public <U> Outcome<U> flatMap(Function<? super T, Outcome<U>> mapper) {
        if (result != null) {
            return mapper.apply(result);
        }
        return new Outcome<>(success, null, message);
    }

    /**
     * Exécute une action sur la valeur métier si le résultat est un succès.
     *
     * @param action l'action à exécuter
     * @return ce {@code Outcome} pour chaînage
     */
    public Outcome<T> ifSuccess(Consumer<? super T> action) {
        if (success && result != null) {
            action.accept(result);
        }
        return this;
    }

    /**
     * Exécute une action sur la valeur métier si le résultat est un échec.
     *
     * @param action l'action à exécuter
     * @return ce {@code Outcome} pour chaînage
     */
    public Outcome<T> ifFailure(Consumer<? super T> action) {
        if (!success && result != null) {
            action.accept(result);
        }
        return this;
    }

    /**
     * Exécute une action systématiquement si la valeur métier est non-nulle,
     * indépendamment du statut succès/échec.
     *
     * @param action l'action à exécuter
     * @return ce {@code Outcome} pour chaînage
     */
    public Outcome<T> onValue(Consumer<? super T> action) {
        if (result != null) {
            action.accept(result);
        }
        return this;
    }

    /**
     * Exécute une action si un message est présent.
     *
     * @param action l'action à exécuter
     * @return ce {@code Outcome} pour chaînage
     */
    public Outcome<T> ifMessage(Consumer<? super Message> action) {
        if (message != null) {
            action.accept(message);
        }
        return this;
    }
}