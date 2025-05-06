package engine.action.vision;

import engine.map.GridService;
import engine.map.Position;
import engine.map.Zone;
import engine.personnage.Personnage;
import engine.util.Either;
import engine.util.Unit;
import engine.util.message.MessageError;
import engine.util.message.error.NullClassError;

/**
 * Implémentation basique de la vision : le personnage voit dans un carré autour de lui,
 * selon une distance donnée.
 * 
 * La vision prend en compte la taille de la grille pour ne pas sortir des bornes,
 * mais n'intègre pas la gestion des obstacles dans cette version simple.
 * 
 * @author AirSoks
 * @since 2025-05-04
 * @version 1.0
 */
public class SimpleSquareVision extends AbstractVision {
	
    private final GridService gridService;

    private final int distance;

    /**
     * Construit une vision carrée simple.
     * 
     * @param grid La grille de jeu.
     * @param distance La distance de vision (nombre de cases autour du personnage).
     */
    public SimpleSquareVision(int distance) {
    	super();
        this.distance = distance;
        this.gridService = GridService.getInstance();
    }

    @Override
    public Either<MessageError, Zone> calculate(Personnage p) {
    	if (p == null) {
            return Either.left(new NullClassError(Personnage.class));
        } Position pos = p.getPosition();
    	if (pos == null) {
            return Either.left(new NullClassError(Position.class));
        }
    	
    	Zone visiblePositions = new Zone();

        for (int dx = -distance; dx <= distance; dx++) {
            for (int dy = -distance; dy <= distance; dy++) {
                int x = pos.x() + dx;
                int y = pos.y() + dy;
                
                Position newPos = new Position(x, y);
                Either<MessageError, Unit> boundsCheck = gridService.isInBounds(pos);
                if (boundsCheck.isLeft()) {
                    return Either.left(boundsCheck.getLeft());
                }
                visiblePositions.addPosition(newPos);
            }
        }
        
        this.view = visiblePositions;
        return Either.right(visiblePositions);
    }
}