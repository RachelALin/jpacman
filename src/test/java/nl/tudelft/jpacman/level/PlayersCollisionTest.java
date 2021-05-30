package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.game.SinglePlayerGame;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostMapParser;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.ImageSprite;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.sprite.SpriteStore;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class PlayersCollisionTest {

    private PlayerCollisions collisions;
    private DefaultPointCalculator pointCalculator;

    /**
     * initialization
     */
    @BeforeEach
    public void setUp() {
        pointCalculator = new DefaultPointCalculator();
        collisions = new PlayerCollisions(pointCalculator);
    }


    @Test
    @DisplayName("吃豆人碰撞鬼魂")
    public void testPlayerVsGhost() {
        Player player = mock(Player.class);
        Ghost ghost = mock(Ghost.class);
        collisions.collide(player, ghost);
        verify(player).setKiller(ghost);
        verify(player).setAlive(false);
    }


    @Test
    @DisplayName("吃豆人碰撞豆子")
    public void testPlayerVsPellet() {
        Player player = mock(Player.class);
        Pellet pellet = mock(Pellet.class);
        collisions.collide(player, pellet);
        verify(pellet).getValue();
        verify(player).addPoints(pellet.getValue());
        verify(pellet).leaveSquare();
    }


    @Test
    @DisplayName("鬼魂碰撞吃豆人")
    public void testGhostVsPlayer() {
        Ghost ghost = mock(Ghost.class);
        Player player = mock(Player.class);
        collisions.collide(ghost, player);
        verify(player).setAlive(false);
        verify(player).setKiller(ghost);
    }


    @Test
    @DisplayName("鬼魂1碰撞鬼魂2")
    public void testGhostVsGhost() {
        Ghost ghost1 = mock(Ghost.class);
        Ghost ghost2 = mock(Ghost.class);
        collisions.collide(ghost1, ghost2);
        verifyNoMoreInteractions(ghost1, ghost2);
    }


    @Test
    @DisplayName("鬼魂碰撞小豆子")
    public void testGhostVsPellet() {
        Ghost ghost = mock(Ghost.class);
        Pellet pellet = mock(Pellet.class);
        collisions.collide(ghost, pellet);
        verifyNoMoreInteractions(ghost, pellet);
    }


}
