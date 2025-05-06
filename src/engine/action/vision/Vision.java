package engine.action.vision;

import engine.action.PassiveAction;
import engine.map.Zone;
import engine.message.MessageError;
import engine.personnage.Personnage;
import engine.util.Either;

public interface Vision extends PassiveAction<Personnage, Either<MessageError, Zone>> {
	
	@Override
    Either<MessageError, Zone> calculate(Personnage personnage);

    @Override
    Either<MessageError, Zone> getCurrent();
}