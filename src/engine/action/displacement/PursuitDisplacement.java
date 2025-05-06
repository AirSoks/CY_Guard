package engine.action.displacement;

import java.util.List;

import engine.map.Position;
import engine.personnage.Personnage;
import engine.util.Either;
import engine.util.message.MessageError;

public class PursuitDisplacement extends AbstractDisplacement{

	public PursuitDisplacement(Personnage target) {
		super();
	}

	@Override
	public Either<MessageError, List<Position>> calculate(Personnage personnage) {
		return null;
	}

}
