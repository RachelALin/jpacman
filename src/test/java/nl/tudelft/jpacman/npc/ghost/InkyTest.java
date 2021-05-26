package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NavigableMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class InkyTest {

    /**
     * initialization
     */

    public MapParser setUp() {
        PacManSprites pacManSprites = new PacManSprites();
        BoardFactory boardFactory = new BoardFactory(pacManSprites);
        GhostFactory ghostFactory = new GhostFactory(pacManSprites);
        PointCalculator pointCalculator = new DefaultPointCalculator();
        LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory, pointCalculator);

        return new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }


    @Test
    @DisplayName("当没有blinky时，Inky的移动放向变化")
    public void inkyTest_01() {
        //Arrange
        MapParser mapParser = setUp();
        List<String> text = Lists.newArrayList("##..I...P..##");
        Level level = mapParser.parseMap(text);


        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();
        assertThat(inky.getDirection()).isEqualTo(Direction.valueOf("EAST"));

        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.EAST);

        level.registerPlayer(player);
        Player p = Navigation.findUnitInBoard(Player.class,level.getBoard());
        assertThat(p).isNotNull();
        assertThat(p.getDirection()).isEqualTo(Direction.valueOf("EAST"));

        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }


    @Test
    @DisplayName("当没有玩家时，Inky的移动方向变化")
    public void inkyTest_02() {
        MapParser mapParser = setUp();
        List<String> text = Lists.newArrayList("##..I..B..###");
        Level level = mapParser.parseMap(text);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("Inky接近玩家")
    public void inkyTest_03() {
        MapParser mapParser = setUp();
        List<String> text = Lists.newArrayList("##B..I...P.###");
        Level level = mapParser.parseMap(text);

        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove().get()).isEqualTo(Direction.EAST);
    }


    @Test
    @DisplayName("Inky远离玩家")
    public void inkyTest_04() {
        MapParser mapParser = setUp();
        List<String> text = Lists.newArrayList("#B..P.I.....#");
        Level level = mapParser.parseMap(text);


        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove().get()).isEqualTo(Direction.EAST);
    }


    @Test
    @DisplayName("不存在从Inky到目的地的路径")
    public void inkyTest_06() {
        MapParser mapParser = setUp();
        List<String> text = Lists.newArrayList("#BP.....#I..");
        Level level = mapParser.parseMap(text);

        Player player = new PlayerFactory(new PacManSprites()).createPacMan();
        player.setDirection(Direction.EAST);
        level.registerPlayer(player);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }
}

