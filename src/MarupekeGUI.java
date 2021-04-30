import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class MarupekeGUI extends Application {

    private final int TILE_SIZE = 80; //configurable size of tiles
    private final int TILE_GAP = 5; //configurable gap between tiles
    private final int REASONS_WIDTH = 350; //width of reasons vbox (350 is about ideal)

    private GridPane grid;
    private MarupekeGrid game;
    private GridState initialGame; //initial copy of game
    private static int size;
    private VBox reasons;

    private Timer timer;
    private TimerTask updateSeconds;
    private Text timerText;
    private long time;

    private Text moveCounter;
    private int moves;

    private int resetDialogThreshold = 20;
    private MediaPlayer backgroundMediaPlayer;

    private MarupekeGrid generateGame(int size) {
        int fillNums = Math.round(size / 2);
        MarupekeGrid lGrid = null;
        boolean regen = true;
        while (regen){
            try{
                lGrid = MarupekeGrid.randomPuzzle(size, fillNums, fillNums, fillNums);
            }catch(TooManyMarkedSquares tmms){
                fillNums = Math.round(fillNums / 2);
            }
            if (lGrid.isFinishable()){
                regen = false;
            }
        }
        return lGrid;
    }

    private void fillGrid() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid.add(generateTile(game.getTileGrid()[x][y]), y, x);
            }
        }
    }

    private void createGrid() {
        game = generateGame(size);
        initialGame = new GridState(game.getTileGrid()); //save initial state of the game for resetting
        grid = new GridPane();
        grid.setVgap(TILE_GAP);
        grid.setHgap(TILE_GAP);

        grid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        fillGrid();
    }

    private void createReasons() {
        reasons = new VBox();
        reasons.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        reasons.setPrefWidth(REASONS_WIDTH);
    }

    private Dialog<ButtonType> completionDialog() {
        Dialog<ButtonType> completionDialog = new Dialog<ButtonType>();
        completionDialog.setTitle("Puzzle Completed");
        completionDialog.setContentText("Congratulations.\nPlease choose to either start a New Puzzle, or quit.");
        ButtonType newGame = new ButtonType("New Puzzle");
        ButtonType quit = new ButtonType("Quit");
        completionDialog.getDialogPane().getButtonTypes().addAll(newGame, quit);
        return completionDialog;
    }

    private void runCompletionDialog() {
        stopTimer(); //stop the timer
        playApplauseSound(); //play the congratulatory 'applause' sound effect
        reasons.getChildren().clear(); //clear the reasons (illegalities)
        Optional<ButtonType> result = completionDialog().showAndWait(); //show and wait for response on completion dialog
        if (result.isPresent()) { //if an option has been chosen
            if (result.get().getText().equals("Play Again")) playAgain(); //if player clicked play again button, play again
            else if (result.get().getText().equals("Quit")) Platform.exit(); //if player clicked quit button, exit the platform
        }
    }

    private Dialog<ButtonType> resetDialog() {
        Dialog<ButtonType> resetDialog = new Dialog<ButtonType>();
        resetDialog.setTitle(String.format("You've got %d or more illegalities in this grid", resetDialogThreshold));
        resetDialog.setContentText("Would you like to reset it?\nOr start a new puzzle?");
        ButtonType reset = new ButtonType("Reset");
        ButtonType newGame = new ButtonType("New Puzzle");
        ButtonType cancel = new ButtonType("Cancel");
        resetDialog.getDialogPane().getButtonTypes().addAll(reset, newGame, cancel);
        return resetDialog;
    }

    private void runResetDialog() {
        Optional<ButtonType> result = resetDialog().showAndWait();
        if (result.isPresent()) {
            if (result.get().getText().equals("Reset")) {
                game = new MarupekeGrid(initialGame.getTiles()); //set the game back
                resetGrid();
            }
            else if (result.get().getText().equals("New Game")) playAgain();
            //otherwise we don't have to act on the result ('Cancel' option will result in the dialog being shut anyway)
        }
        resetDialogThreshold += 20; //increment threshold for this dialog triggering
    }

    private void playTileChangeSound() {
        final String tileChangeFileName = "tileChange.mp3";
        Media tileChangeMedia = new Media(new File(tileChangeFileName).toURI().toString());
        MediaPlayer tileChangeMediaPlayer = new MediaPlayer(tileChangeMedia);
        tileChangeMediaPlayer.setVolume(0.5); //turn down the sound's volume since it could get annoying
        tileChangeMediaPlayer.play();
    }

    private void playApplauseSound() {
        final String applauseFileName = "applause.mp3";
        Media applauseMedia = new Media(new File(applauseFileName).toURI().toString());
        MediaPlayer applauseMediaPlayer = new MediaPlayer(applauseMedia);
        applauseMediaPlayer.setVolume(0.5); //turn down the sound's volume since it could be jarringly loud
        applauseMediaPlayer.play();
    }

    private void startBackgroundMusic() {
        final String backgroundFileName = "background.mp3";
        Media backgroundMedia = new Media(new File(backgroundFileName).toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundMedia);
        backgroundMediaPlayer.setVolume(0.1); //background volume - not too intrusive, for atmosphere only
        backgroundMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        backgroundMediaPlayer.play();
    }

    private boolean firstTileChange = true;

    /**
     * Generates a new Stackpane tile, aswell as setting its onMouseClicked event code
     * @param t MarupekeTile
     * @return tile
     */
    private StackPane generateTile(MarupekeTile t) {
        StackPane tile = new StackPane();
        Rectangle rec = new Rectangle(TILE_SIZE, TILE_SIZE);

        Color tileColour;
        switch (t.getTileValue()) {
            default:
                tileColour = Color.WHITE;
                break;
            case solid:
                tileColour = Color.BLACK;
                break;
            case X:
            case O:
                tileColour = Color.GREY;
                break;
        }
        rec.setFill(tileColour);

        Text val = new Text(t.toString());
        val.setFont(new Font("Arial", 30));

        tile.getChildren().addAll(rec, val);

        tile.setOnMouseClicked(event -> {
            if (firstTileChange) {
                startTimer();
                firstTileChange = false;
            }
            if (t.isEditable()) playTileChangeSound();
            moves++;
            moveCounter.setText(String.format("Move" + (moves == 1 ? "" : "s") + ": %d", moves));

            StackPane sourceTile = (StackPane)event.getSource();
            Text v = (Text) sourceTile.getChildren().get(1);

            int y = GridPane.getColumnIndex(sourceTile);
            int x = GridPane.getRowIndex(sourceTile);
            switch (v.getText()) {
                case "_":
                    game.markX(x,y); //switch to x
                    break;
                case "X":
                    game.markO(x,y); //switch to o
                    break;
                case "O":
                    game.unmark(x,y); //switch to unmarked
                    break;
                default:
                    //do nothing (incl. solids)
                    break;
            }
            //if mistakenly set (i.e. solid or uneditable) mark methods handle this
            v.setText(game.getTileGrid()[x][y].toString());

            reasons.getChildren().clear(); //clear all reasons, as the new list will include all current illegalities
            if (!game.isLegalGrid()) {
                for (Reason r : game.illegalitiesInGrid()) {
                    Text rT = new Text(r.toString());
                    rT.setFill(Color.GHOSTWHITE);
                    reasons.getChildren().add(rT);
                }
            }

            if (game.isSolved()) runCompletionDialog(); //on completion of the puzzle
            else if (!game.isLegalGrid()) {
                if (game.illegalitiesInGrid().size() >= resetDialogThreshold) runResetDialog();
            }

        });
        return tile;
    }

    /**
     * Common method for starting a new game
     */
    private void playAgain() {
        game = generateGame(size);
        resetGrid();
    }

    /**
     * Common method for resetting the grid
     */
    private void resetGrid() {
        grid.getChildren().clear();
        fillGrid();
        reasons.getChildren().clear();
        firstTileChange = true;
        moves = 0;
    }

    private void setUpTimer() {
        timer = new Timer();
        timerText = new Text("00:00");
        timerText.setFont(new Font(30));
        time = 0;
        setUpdateSeconds();
    }

    private void setUpdateSeconds() {
        //re-creates the task, as you can't re-use the same task once cancelled
        updateSeconds = new TimerTask() {
            @Override
            public void run() {
                timerText.setText(String.format("%02d:%02d", time / 60, time % 60));
                time++;
            }
        };
    }

    private void startTimer() {
        time = 0; //reset timer
        timer = new Timer();
        setUpdateSeconds();
        timer.scheduleAtFixedRate(updateSeconds, 0, 1000L);
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge(); //clear cancelled task
    }

    private void setUpCounter() {
        moveCounter = new Text("Moves: 0");
        moveCounter.setFont(new Font(30));
    }

    @Override
    public void stop() {
        System.exit(0); //closes whole process when the application is closed
    }

    @Override
    public void start(Stage primaryStage) {
        startBackgroundMusic(); //will always begin with a little delay regardless of placement
        BorderPane root = new BorderPane();
        BorderPane body = new BorderPane();
        root.setLeft(body);

        HBox rightTop = new HBox(10); //top part of the 'right' VBox - for timer and counter
        setUpTimer();
        setUpCounter();
        rightTop.getChildren().addAll(timerText, moveCounter);
        VBox right = new VBox();
        createReasons();
        right.getChildren().addAll(rightTop, reasons);

        ScrollPane rightScroll = new ScrollPane();
        rightScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //disable horizontal scrolling
        rightScroll.setContent(right);
        body.setRight(rightScroll);

        createGrid();
        body.setCenter(grid);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Marupeke");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws IllegalGridSize {
        System.out.print("Please enter a number between 4 and 10 (the size of your puzzle)\n    Note: generation for sizes 9 and 10 will take much longer\n> ");
        size = new Scanner(System.in).nextInt(); //doesn't matter what's entered until it's an int
        if (size < 4 || size > 10) throw new IllegalGridSize("Illegal Grid Size provided");
        launch(args);
    }
}
