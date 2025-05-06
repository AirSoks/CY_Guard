package engine.action.vision;

import engine.map.Zone;
import engine.util.Either;
import engine.util.message.MessageError;
import engine.util.message.error.PathError;

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
