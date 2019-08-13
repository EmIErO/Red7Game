package com.codecool.colors.Model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void createsDeepCopyOfPlayerPallete() {
        List<Card> cards1 = new ArrayList<>(Arrays.asList(new Card(4, "red")));
        Player player1 = new Player("Ania", cards1);

        Player copyOfPlayer1 = new Player(player1);

        assertEquals(player1.getPalette().get(0), copyOfPlayer1.getPalette().get(0));

        assertFalse(player1.getPalette() == copyOfPlayer1.getPalette());

        assertFalse(player1.getPalette().get(0) == copyOfPlayer1.getPalette().get(0));
    }


}