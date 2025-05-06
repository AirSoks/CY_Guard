package engine.action.displacement;

import java.util.List;

import engine.map.Position;
import engine.message.MessageError;
import engine.personnage.Personnage;
import engine.util.Either;

public class EscapeDisplacement extends AbstractDisplacement{

	public EscapeDisplacement(Personnage target) {
		super();
	}

	@Override
	public Either<MessageError, List<Position>> calculate(Personnage personnage) {
		return null;
	}

}
