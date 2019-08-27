package com.codecool.colors.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Red7RulesTest {
    private  int firstPlayer = 0;
    private int secondPlayer = 1;
    private int thirdPlayer = 2;

    private List<Player> players;
    private Red7Rules r7r = new Red7Rules(new FuncInterfacesImpl(new CardSelector()));

    @Before
    public void setUp() {
        List<Card> cards1 = new ArrayList<>();
        List<Card> cards2 = new ArrayList<>();
        List<Card> cards3 = new ArrayList<>();
        Player player1 = new Player("Player1Name", cards1);
        Player player2 = new Player("Player2Name", cards2);
        Player player3 = new Player("Player3Name", cards3);

        players = new ArrayList<>(Arrays.asList(player1, player2, player3));

    }

    @Test
    public void whenDiffNumOfCardsInPlayByIndigoRule_thenPlayerWithMostCardsWins() {
        players.get(firstPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card(8, "red"),
                new Card(2, "yellow"),
                new Card(3, "green"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card( 3, "orange"),
                new Card(1, "red"),
                new Card(4, "red"))));
        players.get(thirdPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card( 5, "yellow"),
                new Card(7, "red"),
                new Card(6, "yellow"))));

        String expected = "Player3Name";
        String actual = r7r.playByIndigoRule(players);

        assertEquals(expected, actual);

    }

    @Test
    public void whenDiffNumOfCardsInPlayByGreenRule_thenPlayerWithMostCardsWins() {
        players.get(firstPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card(8, "red"),
                new Card(2, "yellow"),
                new Card(3, "green"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card( 3, "orange"),
                new Card(1, "red"),
                new Card(4, "red"))));
        players.get(thirdPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card( 5, "yellow"),
                new Card(7, "red"),
                new Card(6, "yellow"))));

        String expected = "Player1Name";
        String actual = r7r.playByGreenRule(players);

        assertEquals(expected, actual);

    }

    @Test
    public void whenDiffNumOfSameRankedCards_thenPlayerWithMoreCardsWin() {
        players.get(firstPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card(4, "red"),
                new Card(4, "green"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card( 4, "orange"),
                new Card(8, "red"))));
        players.get(thirdPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card( 7, "yellow"),
                new Card(6, "yellow"))));

        String expected = "Player1Name";
        String actual = r7r.playByOrangeRule(players);

        assertEquals(expected, actual);
    }

    @Test
    public void whenSameNumOfSameRankedCards_thenPlayerWithHighestCardWin() {
        players.get(firstPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card(4, "orange"),
                new Card(4, "green"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card( 4, "red"),
                new Card(4, "yellow"))));
        players.get(thirdPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card( 3, "yellow"),
                new Card(3, "red"))));

        String expected = "Player2Name";
        String actual = r7r.playByOrangeRule(players);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDiffNumOfCardsWithSameColor_thenPlayerWithMoreCardsWin() {
        players.get(firstPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card(1, "red"),
                new Card(2, "green"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card( 3, "orange"),
                new Card(4, "red"))));
        players.get(thirdPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card( 5, "yellow"),
                new Card(6, "yellow"))));

        String expected = "Player3Name";
        String actual = r7r.playByYellowRule(players);

        assertEquals(expected, actual);
    }

    @Test
    public void whenSameNumOfCardsWithSameColor_thenPlayerWithHighestCardWin() {
        players.get(firstPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card(6, "red"),
                new Card(5, "red"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card( 4, "orange"),
                new Card(3, "orange"))));
        players.get(thirdPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card( 2, "yellow"),
                new Card(1, "yellow"))));

        String expected = "Player1Name";
        String actual = r7r.playByYellowRule(players);

        assertEquals(expected, actual);
    }

    @Test
    public void whenDiffNumOfCardsWithDifferentColor_thenPlayerWithMostCardsWin() {
        players.get(firstPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card(4, "red"),
                new Card(2, "yellow"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card(4, "yellow"),
                new Card(1, "red"),
                new Card( 3, "green"),
                new Card( 5, "green"))));
        players.get(thirdPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card(8, "yellow"))));

        String expected = "Player2Name";
        String actual = r7r.playByBlueRule(players);

        assertEquals(expected, actual);
    }

    @Test
    public void whenSameNumOfCardsWithDifferentColor_thenPlayerWithHigherCardWins() {
        players.get(firstPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card(9, "red"),
                new Card(2, "yellow"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card(4, "yellow"),
                new Card(1, "yellow"),
                new Card( 3, "green"),
                new Card( 5, "green"))));
        players.get(thirdPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card(9, "yellow"),
                new Card(9, "blue"),
                new Card(8, "yellow"))));

        String expected = "Player1Name";
        String actual = r7r.playByBlueRule(players);

        assertEquals(expected, actual);
    }
}