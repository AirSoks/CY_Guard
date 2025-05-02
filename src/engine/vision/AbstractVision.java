package engine.vision;

import java.util.Map;

import engine.error.*;
import engine.map.Cell;
import engine.map.Grid;
import engine.map.Position;
import engine.personnage.Personnage;
import engine.util.Either;

public class AbstractVision implements Vision {
	
	private Grid grid;
	
	private Map<Position, Cell> view;
	
	public AbstractVision(Grid grid) {
		this.grid = grid;
	}

	@Override
	public Either<MessageError, Map<Position, Cell>> see(Personnage personnage) {
		return null;
	}

}
