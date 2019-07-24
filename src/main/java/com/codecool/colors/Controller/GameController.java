package com.codecool.colors.Controller;

import com.codecool.colors.Model.Card;
import com.codecool.colors.Model.Game;
import com.codecool.colors.Model.Player;
import com.codecool.colors.Model.Red7Rules;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class GameController {

    @PostMapping("/playAgame")
    public String addNewGame(@RequestBody Game newGame) {
        newGame.setRules(new Red7Rules());
        String ruleColor = newGame.getRuleColor();

        return newGame.getRule(ruleColor);
    }

    @GetMapping("/playAgame")
    public Game getGame() {
        List<Card> cards = Arrays.asList(new Card((short)7, "yellow"), new Card((short)5, "red"));
        Player player1 = new Player("Ania", cards);
        Player player2 = new Player("Mira", cards);
        Game newGame = new Game(cards, Arrays.asList(player1));

        return newGame;
    }
}
