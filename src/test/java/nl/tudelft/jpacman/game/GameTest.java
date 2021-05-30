package nl.tudelft.jpacman.game;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GameTest {

    private Launcher launcher;

    @BeforeEach
    public void setUp() {

        launcher = new Launcher().withMapFile("/map4game.txt");
        launcher.launch();
    }

    @AfterEach
    public void endGame() {
        launcher.dispose();
    }

    @Test
    @DisplayName("点击开始按钮，游戏开始")
    public void clickStartBtn() {
        //arrange
        Game game = launcher.getGame();
        //act
        game.start();
        //assert
        assertThat(game.isInProgress()).isEqualTo(true);
    }

    @Test
    @DisplayName("点击开始按钮后，又点击停止按钮")
    public void clickStopBtn() {
        //arrange
        Game game = launcher.getGame();
        //act
        game.start();
        game.stop();
        //assert
        assertThat(game.isInProgress()).isEqualTo(false);
    }

    @Test
    @DisplayName("点击停止按钮后，又点击开始按钮")
    public void clickStopBtn_then_clickStartBtn() {
        //arrange
        Game game = launcher.getGame();
        //act
        game.start();
        game.stop();
        game.start();
        //assert
        assertThat(game.isInProgress()).isEqualTo(true);
    }

    @Test
    @DisplayName("游戏成功，并结束")
    public void winGame() {
        //arrange
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        //act
        game.start();
        game.move(player, Direction.SOUTH);
        game.move(player, Direction.WEST);
       //assert

        assertThat(player.isAlive()).isEqualTo(true);
        assertThat(game.isInProgress()).isEqualTo(false);
    }


    @Test
    @DisplayName("游戏失败，并结束")
    public void loseGame() throws InterruptedException {
        //arrange
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        //act
        game.start();
        Thread.sleep(12000L);
        // assert
        assertThat(game.isInProgress()).isEqualTo(false);
        assertThat(player.isAlive()).isEqualTo(false);
    }
}
