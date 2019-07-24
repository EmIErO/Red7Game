package com.codecool.colors.Factory;

import com.codecool.colors.Model.Card;
import com.codecool.colors.Model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FIFactory {

    Predicate<Card> evenCards = card -> card.getNumber() % 2 == 0;
    Predicate<Card> lowerThan4Cards = card -> card.getNumber() < 4;

    public Function<Player, List<Card>> getEvenCards() {
        return player -> player.getPalette()
                .stream()
                .filter(evenCards)
                .collect(Collectors.toList());
    }

    public Function<Player, Player> removeUnevenCards() {
        return player -> filterEvenCards(player);
    }

    public Collector<? super List<Card>, ArrayList<Card>, ArrayList<Card>> getCustomCardCollector() {
        return Collector.of(
                () -> new ArrayList<Card>(),
                (result, cardList) -> result.addAll(cardList),
                (result1, result2) -> {
                    result1.addAll(result2);
                    return result1;
                }
        );
    }

    public Player filterEvenCards(Player player) {
        List<Card> filteredPallete = player.getPalette().stream()
                .filter(evenCards)
                .collect(Collectors.toList());
        player.setPalette(filteredPallete);
        return player;
    }

    public Player filterCards(Player player, Predicate<Card> rule) {
        List<Card> filteredPallete = player.getPalette().stream()
                .filter(rule)
                .collect(Collectors.toList());
        player.setPalette(filteredPallete);
        return player;
    }

}
