package com.codecool.colors.Service;

import com.codecool.colors.Model.GameState;
import com.codecool.colors.Model.Red7Rules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
public class GameService {

    private GameState gameState;
    private Red7Rules r7r;

    @Autowired
    public GameService(Red7Rules r7r) {
        this.r7r = r7r;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public String playGame() {
        try {
            Method rule = Red7Rules.r7rules.get(getRuleColor());
            return (String) rule.invoke(r7r, gameState.getPlayers());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return "An error has occurred!";
    }
    private String getRuleColor() {
        return this.gameState.getRuleColor();
    }
}
