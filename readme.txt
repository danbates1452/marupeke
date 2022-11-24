Prelude:
This project scored 88% for Part 1 (basic classes setting up the grid), 84% for Part 2 (validation and command line game), and 82% for Part 3 (the state this is currently in, fully featured JavaFX game), for an overall module average of 84%.

The following is the original readme submitted in my final submission 30/04/2021:
---
README - Marupeke GUI

Displaying the Data:
- Data is displayed from a GridPane grid, which is set-up in void method createGrid(). In createGrid() several
attributes of grid are set and the game (a MarupekeGrid instance) is generated using my MarupekeGrid method
generateGame() which generates a random finishable grid utilising new methods introduced to MarupekeGrid
(getTileGrid(), isPuzzleFilled(), isSolved(), solvePuzzle(), and isFinishable, aswell as another constructor
to aid in use of a new class GridState which is used in traversing states of the grid in solvePuzzle's DFS).

void method fillGrid() is then called which adds all tiles to the grid using the StackPane generateTile()
which takes a MarupekeTile (provided by the just generated MarupekeGrid game).

generateTile creates a StackPane tile and places a Rectangle rec and Text val onto it with appropriate
formatting for each tile.

The onMouseClicked event for the tile is also set here where the timer is started,
the tile change sound is played if the tile is editable, move counter incremented, tile text updated, clears
and adds reasons to the vbox if there are any, and runs the completion and reset dialogs if the game is solved
or the player has hit the current resetDialogThreshold and it seems they may need help.

- Data is fetched from the Marupeke object by way of a new get method I introduced in MarupekeGrid:
getTileGrid() - which returns the MarupekeTile[][] array
- The GUI is kept up to date as on every click of a tile reasons are re-checked, the move counter incremented,
and the finishing conditions checked

Editing the Data:
- In the onMouseClicked code for each tile there is a switch case that will use the mark methods from part2 to
attempt to mark each with the 'next along' tile - essentially creating a rotating dial of them not including
solid tiles. Whatever the outcome of this is output to the user by changing the set text of that tile.
-- Clicks on a blank tile -> markX
-- Clicks on an X tile -> markO
-- Clicks on an O tile -> unmark
- So blank goes to X goes to O goes back to blank

Extras:
- Reset Dialog
    - At increments of 20 illegalities in the grid, the player is asked if they want to reset the grid
    - This is implemented by the onClickEvent of each tile in generateTile(), runResetDialog() and
    resetDialog()

- Game Timer
    - Increments every second a timer of how long the player has been playing the particular game, starting on
    the first tile click and ending on completion (continues after a reset as the grid hasn't been completed)
    - Implemented by setUpTimer(), setUpdateSeconds() for the TimerTask, and startTimer() and stopTimer()
    (used in runCompletionDialog()), and start()

- Move Counter
    - Increments on every tile change, a counter of the moves the player has made
    - Implemented by the onClickEvent of each tile in generateTile(), setUpCounter(), and start()

- Sound effects/Music:
    - Plays sound effects (tileChange and applause) on the change of a tile and completion of a game
    respectively, and continuously plays background music from the loading of the GUI
    - Implemented using Media and MediaPlayer JavaFX classes in playTileChangeSound(), playApplauseSound() and
    startBackgroundMusic(), each called in the onClickEvent of each tile, runCompletionDialog(), and start()
    respectively
    - The MediaPlayer for the background music is a global variable as the audio file needs to be played for
    more than a few seconds.
    - Sources:
        - Background Music: Looped https://www.bensound.com/royalty-free-music/track/the-elevator-bossa-nova
        - Tile Change: 0.18 seconds of https://sound-effects.bbcrewind.co.uk/search?q=07022447
        - Applause: 2 seconds of https://sound-effects.bbcrewind.co.uk/search?q=07003039
