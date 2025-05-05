package engine.action.vision;

import java.util.List;

import engine.action.PassiveAction;
import engine.map.Position;
import engine.message.MessageError;
import engine.personnage.Personnage;
import engine.util.Either;

public interface Vision extends PassiveAction<Personnage, Either<MessageError, List<Position>>> {
	
	@Override
    Either<MessageError, List<Position>> calculate(Personnage personnage);

    @Override
    Either<MessageError, List<Position>> getCurrent();
}