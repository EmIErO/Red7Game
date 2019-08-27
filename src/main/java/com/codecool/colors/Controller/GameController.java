package com.codecool.colors.Controller;

import com.codecool.colors.Model.*;
import com.codecool.colors.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/playGame")
    public String addNewGame(@RequestBody GameState newGameState) {
        gameService.setGameState(newGameState);
        return gameService.playGame();
    }

    @GetMapping("/playGame")
    public GameState getGameService() {
        List<Card> cards = Arrays.asList(new Card((short)7, "yellow"), new Card((short)5, "red"));
        Player player1 = new Player("Ania", cards);
        Player player2 = new Player("Mira", cards);
        GameState newGameState = new GameState(cards, Arrays.asList(player1, player2));

        return newGameState;
    }
}
