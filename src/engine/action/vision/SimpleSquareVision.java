package engine.action.vision;

import java.util.ArrayList;
import java.util.List;

import engine.message.error.*;
import engine.map.Grid;
import engine.map.Position;
import engine.message.MessageError;
import engine.personnage.Personnage;
import engine.util.Either;
import engine.util.Unit;

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

    private final int distance;

    /**
     * Construit une vision carrée simple.
     * 
     * @param grid La grille de jeu.
     * @param distance La distance de vision (nombre de cases autour du personnage).
     */
    public SimpleSquareVision(Grid grid, int distance) {
        super(grid);
        this.distance = distance;
    }

    @Override
    public Either<MessageError, List<Position>> calculate(Personnage p) {
    	if (p == null) {
            return Either.left(new NullClassError(Personnage.class));
        } Position pos = p.getPosition();
    	if (pos == null) {
            return Either.left(new NullClassError(Position.class));
        }
    	
        List<Position> visiblePositions = new ArrayList<>();

        for (int dx = -distance; dx <= distance; dx++) {
            for (int dy = -distance; dy <= distance; dy++) {
                int x = pos.x() + dx;
                int y = pos.y() + dy;
                
                Position newPos = new Position(x, y);
                Either<MessageError, Unit> boundsCheck = grid.isInBounds(pos);
                if (boundsCheck.isLeft()) {
                    return Either.left(boundsCheck.getLeft());
                }
                visiblePositions.add(newPos);
            }
        }
        
        this.view = visiblePositions;
        return Either.right(visiblePositions);
    }
}