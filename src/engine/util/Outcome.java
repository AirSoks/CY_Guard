package engine.util;

import engine.message.MessageError;
import engine.message.MessageSuccess;

/**
 * Représente le résultat d'une opération métier sous forme de statut : succès ou échec.
 * <p>
 * Contrairement à {@link Either} ou à d'autres classes fonctionnelles pures, {@code Outcome}
 * ne vise pas seulement à transporter une valeur ou une alternative : elle exprime l'issue concrète
 * d'une action métier (ex. : un déplacement de personnage, une modification d'état).
 * <p>
 * Sa vocation principale est :
 * <ul>
 *     <li>De permettre un chaînage fluide d'actions métier, avec un statut clair de succès/échec,</li>
 *     <li>D'offrir, en cas de succès, un résultat optionnellement enrichi par un message métier (facultatif),</li>
 *     <li>De transporter une erreur claire et détaillée en cas d'échec, sans jamais mélanger état succès/échec.</li>
 * </ul>
 * <p>
 * Cette classe est donc particulièrement adaptée aux cas où :
 * <ul>
 *     <li>L'opération réalisée a un impact métier concret et doit signaler son statut,</li>
 *     <li>On veut éviter les erreurs silencieuses ou les valeurs ambiguës en imposant un contrat clair : succès ou échec explicite.</li>
 * </ul>
 *
 * @param <T> Le type du résultat
 * 
 * @author AirSoks
 * @since 2025-05-03
 * @version 1.1
 */
public final class Outcome<T> {

    private final boolean success;
    private final MessageError messageError;
    private final MessageSuccess messageSuccess;
    private final T result;

    private Outcome(boolean success, MessageError messageError, MessageSuccess messageSuccess,  T result) {
        this.success = success;
        this.messageError = messageError;
        this.messageSuccess = messageSuccess;
        this.result = result;
    }

    
    public static <T> Outcome<T> success(MessageSuccess messageSuccess) {
        return new Outcome<>(true, null, messageSuccess, null);
    }
    
    public static <T> Outcome<T> success(T result, MessageSuccess messageSuccess) {
        return new Outcome<>(true, null, messageSuccess, result);
    }

    public static <T> Outcome<T> success(T result) {
        return new Outcome<>(true, null, null, result);
    }
    
    public static <T> Outcome<T> success() {
        return new Outcome<>(true, null, null, null);
    }
    
    public static <T> Outcome<T> failure() {
        return new Outcome<>(false, null, null, null);
    }
    
    public static <T> Outcome<T> failure(T result) {
        return new Outcome<>(false, null, null, result);
    }

    public static <T> Outcome<T> failure(T result, MessageError messageError) {
        return new Outcome<>(false, messageError, null, result);
    }
    
    public static <T> Outcome<T> failure(MessageError messageError) {
        return new Outcome<>(false, messageError, null, null);
    }

    public boolean isSuccess() {
        return success;
    }
    
    public boolean isFailure() {
        return !success;
    }

    public MessageError getErrorMessage() {
        return messageError;
    }

    public MessageSuccess getSuccessMessage() {
        return messageSuccess;
    }

    public T getResult() {
        return result;
    }
}