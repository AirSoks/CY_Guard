package engine.action.vision;

import engine.message.error.*;
import engine.map.Zone;
import engine.message.MessageError;
import engine.util.Either;

public abstract class AbstractVision implements Vision {
	
	protected Zone view;
	
	public AbstractVision() { }

	@Override
	public final Either<MessageError, Zone> getCurrent() {
		if (view == null) {
			return Either.left(PathError.emptyPath());
        }
        return Either.right(view);
	}
}
