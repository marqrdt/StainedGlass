package org.marqrdt.stainedglass;

import java.awt.Point;

public class SavedMove {
	Point savedSourcePosition;
	GameColor savedSourceColor;
	Point savedTargetPosition;

	public SavedMove(Point sourcePoint, GameColor sourceColor, Point targetPoint) {
		savedSourcePosition = sourcePoint;
		savedSourceColor = sourceColor;
		savedTargetPosition = targetPoint;
	}
}