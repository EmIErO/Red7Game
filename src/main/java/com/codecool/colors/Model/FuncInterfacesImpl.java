package com.codecool.colors.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FuncInterfacesImpl {

    private CardSelector cardSelector;

    @Autowired
    public FuncInterfacesImpl(CardSelector cardSelector) {
        this.cardSelector = cardSelector;
    }

    Function<Player, Player> bestRun = player -> {
        player.setPalette(cardSelector.selectBestRun(player));
        return player;
    };

    Function<Player, Player> cardsWithEvenRank = player -> {
        List<Card> filteredPalette = player.getPalette().stream()
                .filter(card -> card.getNumber() % 2 == 0)
                .collect(Collectors.toList());
        player.setPalette(filteredPalette);
        return player;
    };

    Function<Player, Player> cardsWithRankLowerThan4 = player -> {
        List<Card> filteredPallet = player.getPalette().stream()
                .filter(card -> card.getNumber() < 4)
                .collect(Collectors.toList());
        player.setPalette(filteredPallet);
        return player;
    };

    Function<Player, Player> cardsWithSameRank = player -> {
        player.setPalette(cardSelector.selectBestSetOfCardsWithSameRank(player));
        return player;
    };

    Function<Player, Player> cardsWithSameColor = player -> {
        player.setPalette(cardSelector.selectBestSetOfCardsWithSameColor(player));
        return player;
    };

    Function<Player, Player> cardsWithDifferentColor = player -> {
        player.setPalette(cardSelector.selectBestSetOfCardsWithDifferentColor(player));
        return player;
    };

    Function<Player, Integer> palleteSize = player -> player.getPalette().size();


}
