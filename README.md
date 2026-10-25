## Welcome to StainedGlass
StainedGlass is a Java port that I wrote of a horribly addictive game for old Motorola 68K Macs.

- Game Objective: The objective is to leave as few colored squares on the board, with one square left being a perfect score, by jumping the colored squares over adjacent squares (horizontally, vertically, or diagonally) according to the rules until no more moves are possible.

- Game Layout: The game board is a rectangular grid of 16 squares wide and 8 squares high. The colors of the squares are the primary colors blue, red, and yellow, and secondary colors purple, orange, and green. They are arranged at random on the board with the constraint that the corner squares are never colored as compound colors.

- Game Rules: A primary-colored square can jump over an adjacent square in any direction only if it can land on another primary-colored square or an empty square.
    - If it lands on a square of a different primary color, the target square becomes the secondary color created from the starting and target squares.
    - If it lands on a square of the same primary color, the color of the target square remains the same.
    - When the starting square jumps over an adjacent square (the square in-between the starting and target squares), the following rules apply:
      - If the adjacent square is a compound color, the color of the starting square is removed from the adjacent square, leaving a primary-colored square.
      - If the adjacent square is a primary color, the adjacent square is set to an empty square.

    A compound-colored square may only jump over and to another square of the same compound color. The square between the starting and ending square becomes blank.

- Game Play: When the player clicks on a square, all legal target squares are highlighted with a gray circle.

- Tips: Have fun! Playing the game is way more fun than reading the rules. As a general strategy, clear the corners as soon as possible, especially of compound colors, and work inwards.

## License
StainedGlass is licensed under the [MIT License](LICENSE).

## Release Notes
See [RELEASE_NOTES.md](RELEASE_NOTES.md) for a summary of changes in each release.
