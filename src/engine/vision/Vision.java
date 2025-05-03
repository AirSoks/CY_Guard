package engine.vision;

import java.util.List;
import java.util.Map;

import engine.map.Cell;
import engine.map.Position;
import engine.message.MessageError;
import engine.personnage.Personnage;
import engine.util.Either;
import engine.util.Outcome;

public interface Vision {
	
	public Outcome<Map<Position, Cell>> see(Personnage personnage);
	
	public Either<MessageError, List<Position>> getVision();
	
}
