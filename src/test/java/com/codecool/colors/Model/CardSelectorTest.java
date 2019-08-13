package com.codecool.colors.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardSelectorTest {
    private CardSelector cardSelector;

    @Before
    public void setUp() {
        cardSelector = new CardSelector();
    }

    @Test
    public void whenChooseBestRunPrivateMethodIsCalled_thenBestRunIsCorrectlyChosen() {
        List<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(1, "red"),
                new Card(2, "green"),
                new Card(2, "red"),
                new Card(7, "red"),
                new Card(8, "red"),
                new Card(9, "yellow"),
                new Card(7, "green")));
        Player player = new Player("PlayerName", cards);

        List<Card> expected = new ArrayList<>(Arrays.asList(
                new Card(7, "red"),
                new Card(8, "red"),
                new Card(9, "yellow")));

        List<Card> actual = cardSelector.selectBestRun(player);

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));
    }

    @Test
    public void whenIsLastCardPrivateMethodReturnsTrue_thanCurrentRunIsAddedToRuns() {
        List<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(7, "green")));
        Player player = new Player("PlayerName", cards);

        List<Card> expectedLastRun = new ArrayList<>(Arrays.asList(new Card(7, "green")));
        List<Card> actual = cardSelector.selectBestRun(player);

        assertEquals(expectedLastRun.size(), actual.size());
        assertTrue(actual.containsAll(expectedLastRun));

    }

    @Test
    public void whenCardIsHigherByOnePrivateMethodReturnsTrue_thenCardIsAddedToTheRun() {
        List<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(1, "red"),
                new Card(2, "red")));
        Player player = new Player("PlayerName", cards);

       Card expected = new Card(2, "red");
        List<Card> actual = cardSelector.selectBestRun(player);

        assertTrue(actual.contains(expected));
    }

    @Test
    public void whenCardHASSameRankPrivateMethodReturnsTrue_thenLastCardIsRemovedAndNewCardIsAddedToTheRun() {
        List<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(2, "yellow"),
                new Card(2, "red")));
        Player player = new Player("PlayerName", cards);

        List<Card> expected = new ArrayList<>(Arrays.asList(new Card(2, "red")));
        List<Card> actual = cardSelector.selectBestRun(player);

        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void whenIsLastCardPrivateMethodReturnsTrue_thenLastRunIsAddedToTheRuns() {
        List<Card> cards = new ArrayList<>(Arrays.asList(
                new Card(1, "yellow"),
                new Card(2, "green"),
                new Card(3, "red"),
                new Card(5, "red"),
                new Card(7, "red"),
                new Card(8, "yellow"),
                new Card(9, "red")));
        Player player = new Player("PlayerName", cards);

        List<Card> expected = new ArrayList<>(Arrays.asList(
                new Card(7, "red"),
                new Card(8, "yellow"),
                new Card(9, "red")));

        List<Card> actual = cardSelector.selectBestRun(player);

        assertTrue(actual.containsAll(expected));
        assertTrue(expected.containsAll(actual));
    }


    }