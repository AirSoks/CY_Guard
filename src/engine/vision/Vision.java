package engine.vision;

import java.util.Map;

import engine.error.*;
import engine.map.Cell;
import engine.map.Position;
import engine.message.MessageError;
import engine.personnage.Personnage;
import engine.util.Either;

public interface Vision {
	
	public Either<MessageError, Map<Position, Cell>> see(Personnage personnage);
	
}
