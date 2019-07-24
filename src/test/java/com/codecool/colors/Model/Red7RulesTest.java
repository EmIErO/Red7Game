package com.codecool.colors.Model;

import com.codecool.colors.Factory.FIFactory;
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
    private Red7Rules r7r = new Red7Rules(new FIFactory());

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

    // Testing evenNumWin() method
    @Test
    public void whenDiffNumOfEvenCards_thenPlayerWithMoreEvenCardsWin() {
        players.get(firstPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card((short)4, "red"),
                new Card((short)2, "yellow"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card((short)4, "yellow"),
                new Card((short)1, "red"),
                new Card((short) 3, "green"),
                new Card((short) 5, "green"))));
        players.get(thirdPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card((short)8, "yellow"))));

        String expected = "Player1Name";
        String actual = r7r.evenNumWin(players);

        assertEquals(expected, actual);
    }

    @Test
    public void whenSameNumOfEvenCards_thenPlayerWithHigherCardWins() {
        players.get(firstPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card((short)2, "red"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card((short) 2, "green"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card((short) 3, "red"))));

        String expected = "Player1Name";
        String actual = r7r.evenNumWin(players);

        assertEquals(expected, actual);
    }

    @Test
    public void whenNoNumOfEvenCards_thenNullIsReturned() {
        players.get(firstPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card((short)1, "red"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card((short) 1, "green"))));
        players.get(secondPlayer).setPalette(new ArrayList<>(Arrays.asList(
                new Card((short) 1, "yellow"))));

        String expected = null;
        String actual = r7r.evenNumWin(players);

        assertEquals(expected, actual);
    }
}