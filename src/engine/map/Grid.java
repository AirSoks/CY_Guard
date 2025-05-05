package engine.map;

import engine.message.error.*;
import engine.map.Position.PositionPair;
import engine.message.MessageError;
import engine.message.success.MoveSuccess;
import engine.mouvement.MovementRule;
import engine.personnage.Personnage;
import engine.util.Outcome;
import engine.util.Either;
import engine.util.Unit;

import java.util.HashMap;
import java.util.Map;

/**
 * Représente une grille de jeu où des personnages peuvent se déplacer.
 * La grille est composée de cellules disposées en lignes et en colonnes, et chaque cellule peut contenir des personnages.
 * La classe permet de gérer les positions des personnages, vérifier la validité des mouvements, 
 * et transférer des personnages entre les cellules.
 * 
 * @author AirSoks
 * @since 2025-05-02
 * @version 1.0
 */
public class Grid {
	
	private final MovementRule movementRule;
	
    private int width;
    private int height;
    private Cell[][] cells;
    
    /**
     * Constructeur pour créer une grille avec une largeur, une hauteur et une règle de mouvement spécifiques.
     *
     * @param width La largeur de la grille en nombre de cellules.
     * @param height La hauteur de la grille en nombre de cellules.
     * @param rule La règle de mouvement utilisée pour valider les déplacements des personnages.
     */
    public Grid(int width, int height, MovementRule rule) {
        this.width = width;
        this.height = height;
        this.movementRule = rule;
        cells = new Cell[width][height];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(ObstacleType.PLAIN);
            }
        }
    }
    
    /**
     * Retourne la largeur de la grille.
     *
     * @return La largeur de la grille.
     */
    public int width() {
        return width;
    }

    /**
     * Retourne la hauteur de la grille.
     *
     * @return La hauteur de la grille.
     */
    public int height() {
        return height;
    }
    
    /**
     * Vérifie si une position donnée est valide, c'est-à-dire si elle est à l'intérieur des limites de la grille.
     *
     * @param position La position à vérifier.
     * @return Un {@code Either} contenant un message d'erreur si la position est hors limites ou {@link Unit} si la position est valide.
     */
    public Either<MessageError, Unit> isInBounds(Position position) {
        if (position == null) {
            return Either.left(new NullClassError(Position.class));
        }

        if (position.x() < 0 || position.x() >= width || position.y() < 0 || position.y() >= height) {
            return Either.left(PositionError.outOfBounds(position));
        }
        return Either.right(Unit.get());
    }

    /**
     * Récupère la cellule située à la position donnée.
     * Si la position est invalide, retourne une erreur.
     *
     * @param position La position de la cellule à récupérer.
     * @return Un {@code Either} contenant la cellule si elle existe, ou un message d'erreur si la position est invalide.
     */
    public Either<MessageError, Cell> getCell(Position position) {
    	if (position == null) {
            return Either.left(new NullClassError(Position.class));
        }
    	
    	Either<MessageError, Unit> boundsCheck = isInBounds(position);
        if (boundsCheck.isLeft()) {
            return Either.left(boundsCheck.getLeft());
        }
        return Either.right(cells[position.x()][position.y()]);
    }

    /**
     * Vérifie la cohérence de la position d'un personnage sur la grille.
     * Vérifie si le personnage est bien positionné dans la cellule correspondante.
     *
     * @param personnage Le personnage à vérifier.
     * @return Un {@code Either} contenant un message d'erreur si la position du personnage est incohérente, sinon {@code Void}.
     */
    public Either<MessageError, Unit> verifyPersonnagePositionCoherence(Personnage p) {
    	if (p == null) {
            return Either.left(new NullClassError(Personnage.class));
        }
    	
        Position position = p.getPosition();
        Either<MessageError, Cell> cellOpt = getCell(position);

        if (cellOpt.isRight()) {
            Cell cell = cellOpt.getRight();
            if (cell.getPersonnageManager().getPersonnages().contains(p)) {
                return Either.right(Unit.get());
            } else {
                return Either.left(new PersonnageError(p, cell));
            }
        } else {
            return Either.left(cellOpt.getLeft());
        }
    }

    /**
     * Tente d'ajouter un personnage à une cellule de la grille.
     * Vérifie que la position est valide avant d'ajouter le personnage.
     *
     * @param personnage Le personnage à ajouter.
     * @return Un {@code Either} contenant le personnage ajouté si l'ajout est réussi, ou un message d'erreur si la position est invalide.
     */
    public Either<MessageError, Personnage> addPersonnage(Personnage p) {
    	if (p == null) {
            return Either.left(new NullClassError(Personnage.class));
        } 
    	Position position = p.getPosition();
        Either<MessageError, Unit> isValideResult = isInBounds(position);
        if (isValideResult.isLeft()) {
            return Either.left(isValideResult.getLeft());
        }

        Either<MessageError, Cell> cellEither = getCell(position);
        if (cellEither.isLeft()) {
            return Either.left(cellEither.getLeft());
        }

        Cell cell = cellEither.getRight();
        cell.getPersonnageManager().addPersonnage(p);

        return Either.right(p);
    }
    
    /**
     * Vérifie si le mouvement d'un personnage vers une position donnée est accepté par la règle de mouvement.
     * La vérification se base sur la position d'origine du personnage, la position cible, et les obstacles présents dans les cellules.
     *
     * @param p Le personnage à déplacer.
     * @param to La position cible du mouvement.
     * @return Un {@link Outcome} représentant l'acceptation ou le rejet du mouvement.
     */
    public Outcome<PositionPair> isMoveAccepted(Personnage p, Position to) {
    	if (p == null) {
            return Outcome.failure(null, new NullClassError(Personnage.class));
        } 
    	Position from = p.getPosition();
    	if (from == null) {
            return Outcome.failure(null, new NullClassError(Position.class).with(() -> "parameter: from"));
        }if (to == null) {
            return Outcome.failure(null, new NullClassError(Position.class).with(() -> "parameter: toCell"));
        }
    	PositionPair pPaire = new PositionPair(from, to);
    	
        Either<MessageError, Cell> fromCellEither = getCell(from);
        Either<MessageError, Cell> toCellEither = getCell(to);

        if (fromCellEither.isLeft()) {
            return Outcome.failure(pPaire, fromCellEither.getLeft());
        } if (toCellEither.isLeft()) {
            return Outcome.failure(pPaire, toCellEither.getLeft());
        }

        Cell fromCell = fromCellEither.getRight();
        Cell toCell = toCellEither.getRight();

        return movementRule.isMoveAccepted(p, from, to, fromCell, toCell);
    }
    
    /**
     * Tente de déplacer un personnage vers une position cible sur la grille.
     * Si le mouvement est accepté, le personnage est transféré à la nouvelle position.
     *
     * @param p Le personnage à déplacer.
     * @param to La position cible du mouvement.
     * @return Un {@link Outcome} représentant le résultat du déplacement (succès ou échec).
     */
    public Outcome<PositionPair> movePersonnage(Personnage p, Position to) {
        if (p == null) {
            return Outcome.failure(null, new NullClassError(Personnage.class));
        }
        Position from = p.getPosition();
    	if (from == null) {
            return Outcome.failure(null, new NullClassError(Position.class).with(() -> "parameter: from"));
        }if (to == null) {
            return Outcome.failure(null, new NullClassError(Position.class).with(() -> "parameter: to"));
        }

        Either<MessageError, Unit> personnageCoherent = verifyPersonnagePositionCoherence(p);
        if (personnageCoherent.isLeft()) {
            return Outcome.failure(null, personnageCoherent.getLeft());
        }

        Outcome<PositionPair> isMoveAccepted = isMoveAccepted(p, to);
        if (isMoveAccepted.isFailure()) {
            return isMoveAccepted;
        }
        
        PositionPair posPair = isMoveAccepted.getResult();

        Either<MessageError, Cell> fromCellEither = getCell(from);
        Either<MessageError, Cell> toCellEither = getCell(to);

        if (fromCellEither.isLeft()) {
            return Outcome.failure(posPair, fromCellEither.getLeft());
        } if (toCellEither.isLeft()) {
            return Outcome.failure(posPair, toCellEither.getLeft());
        }

        Cell fromCell = fromCellEither.getRight();
        Cell toCell = toCellEither.getRight();
        
    	Either<MessageError, Unit> transfer = Cell.transferPersonnage(p, toCell, fromCell);
        if (transfer.isLeft()) {
            return Outcome.failure(posPair, transfer.getLeft());
        }
        
        p.setPosition(to);
        
        return Outcome.success(posPair, MoveSuccess.success(posPair));
    }
    
    /**
     * Tente de déplacer un personnage dans une direction donnée.
     * Cela implique de calculer la nouvelle position du personnage en fonction de la direction et d'essayer de déplacer le personnage.
     *
     * @param p Le personnage à déplacer.
     * @param direction La direction du mouvement.
     * @return Un {@link Outcome} représentant le résultat du déplacement (succès ou échec).
     */
    public Outcome<PositionPair> movePersonnage(Personnage p, Direction direction) {
    	if (p == null) {
            return Outcome.failure(null, new NullClassError(Personnage.class));
        }Position from = p.getPosition();
    	if (from == null) {
            return Outcome.failure(null, new NullClassError(Position.class).with(() -> "parameter: from"));
        } if (direction == null) {
            return Outcome.failure(null, new NullClassError(Direction.class));
        }
        
        Either<MessageError, Position> positionEither = from.move(direction);
        if (positionEither.isLeft()) {
        	return Outcome.failure(null, positionEither.getLeft());
        } else {
            return movePersonnage(p, positionEither.getRight());
        }
    }
    
    /**
     * Retourne une entrée aléatoire de la grille sous forme de paire {@code Map.Entry<Position, Cell>}.
     * Utilisé pour générer des positions ou cellules au hasard dans la grille.
     *
     * @return Une entrée aléatoire sous la forme {@code Map.Entry<Position, Cell>}.
     */
    public Map.Entry<Position, Cell> getRandomEntry() {
        int randomX = (int) (Math.random() * width);
        int randomY = (int) (Math.random() * height);
        Position position = new Position(randomX, randomY);
        Cell cell = cells[randomX][randomY];

        return new HashMap.SimpleEntry<>(position, cell);
    }
}