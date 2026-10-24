package org.marqrdt.stainedglass;

/**
 * Listener interface for receiving notifications when a {@link GameBoard}'s
 * state changes (a move is made, the board is reset, or a move is undone).
 *
 * This replaces the legacy java.util.Observer/Observable pattern, which has
 * been deprecated since Java 9.
 */
public interface GameBoardListener {
	void gameBoardChanged(GameBoard board);
}
