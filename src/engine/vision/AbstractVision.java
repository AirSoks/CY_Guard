package engine.vision;

import java.util.List;
import java.util.Map;

import engine.map.Cell;
import engine.map.Grid;
import engine.map.Position;
import engine.message.MessageError;
import engine.personnage.Personnage;
import engine.util.Either;
import engine.util.Outcome;

public class AbstractVision implements Vision {
	
	private Grid grid;
	
	private Map<Position, Cell> view;
	
	public AbstractVision(Grid grid) {
		this.grid = grid;
	}

	@Override
	public Outcome<Map<Position, Cell>> see(Personnage personnage) {
		return null;
	}

	@Override
	public Either<MessageError, List<Position>> getVision() {
		return null;
	}

}
