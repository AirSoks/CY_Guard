package engine.action.vision;

import java.util.Collections;
import java.util.List;

import engine.message.error.*;
import engine.map.Grid;
import engine.map.Position;
import engine.message.MessageError;
import engine.util.Either;

public abstract class AbstractVision implements Vision {
	
	protected final Grid grid;
	
	protected List<Position> view;
	
	public AbstractVision(Grid grid) {
		this.grid = grid;
	}

	@Override
	public Either<MessageError, List<Position>> getCurrent() {
		if (view == null || view.isEmpty()) {
			return Either.left(PathError.emptyPath());
        }
        return Either.right(Collections.unmodifiableList(view));
	}
}
