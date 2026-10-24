## Welcome to StainedGlass
StainedGlass is a Java port that I wrote of a horribly addictive game for old Motorola 68K Macs.
It uses the older javax.swing and java.awt classes. It has not been updated since JDK 7.

---

- Game Layout: The game board is a rectangular grid of 16 squares wide and 8 squares hign. These dimensions are defined in StanedGlassGame.java. The colors of the squares are the primary colors blue, red, and yellow, and secondary colors purple, orange, and green. They are arranged at random on the board with the constraint that the corner squares are not colored as compound colors.

- Game Objective: The objective is to leave as few colored squares on the board, with one square left being a perfect score, by jumping the colored squares over adjacent squares (horizontally, vericially, or diagonally) according to the rules until no more moves are possible.

- Game Rules: A primary-colored square can jump over an adjacent square in any direction only if it can land on another primary-colored square. If it lands on a square of a different primary color, the target square becomes the secondary color created from the source and target squares. If it lands on a square of the same primary color, the color of the target square remains the same. When the source square jumps over an adjacent square (the square in-between the source and target squares), the following rules apply:
  1. If the adjacent square is a compound color, the color of the source square is removed from the adjacent square, leaving a primary-colored square.
  2. If the adjacent square is a primary color, the adjacent square is set to an empty square.

A compound-colored square may only jump to another square of the same compound color, and a compound-colored square may not jump over a color it does not contain. For example, a purple square may not jump to another purple square over a yellow square.
  1. If the adjacent square is a compound color, the color of the source square is removed from the color of the adjacent square, leaving a primary-colored square.
  2. If the adjacent square is a primary color and contains one of the colors of the source square, the adjacent square is set to an empty square.

  - Game Play: When the player clicks on a square, all legal target squares are highlighted with a black circle blinking once per second.
