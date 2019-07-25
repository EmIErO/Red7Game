package com.codecool.colors.Factory;

import com.codecool.colors.Model.Card;
import com.codecool.colors.Model.Player;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FIFactory {

    public Predicate<Card> evenCards = card -> card.getNumber() % 2 == 0;
    public Predicate<Card> lowerThan4Cards = card -> card.getNumber() < 4;
    public Predicate<Card> all = card -> card instanceof Card;

    public Function<Player, List<Card>> getEvenCards() {
        return player -> player.getPalette()
                .stream()
                .filter(evenCards)
                .collect(Collectors.toList());
    }
    public Function<Card, String> cardNumber = card -> String.valueOf(card.getNumber());
    public Function<Card, String> cardColor = card -> card.getColor();


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

    public Player filterCards(Player player, Predicate<Card> rule) {
        List<Card> filteredPallete = player.getPalette().stream()
                .filter(rule)
                .collect(Collectors.toList());
        player.setPalette(filteredPallete);
        return player;
    }

    Function<Map<Integer, List<Card>>, Integer> func = map -> map.entrySet().size();

    public Player filterBestSetOfCardsBy(Function<Card, String> function,Player player) {

        Map<String, List<Card>> filteredPallete = player.getPalette().stream()
                .collect(Collectors.groupingBy(function));

        OptionalInt optional = filteredPallete.entrySet().stream()
                .mapToInt(entrySet -> entrySet.getValue().size())
                .max();

        int maxNumOfSameCards = optional.getAsInt();
        int firstElement = 0;

        List<List<Card>> candidateCardSets = filteredPallete.entrySet().stream()
                .filter(entrySet -> entrySet.getValue().size() == maxNumOfSameCards)
                .map(entrySet -> entrySet.getValue())
                .sorted(Comparator.comparing(element -> element.get(firstElement).getNumber()))
                .collect(Collectors.toList());

        if (candidateCardSets.size() == 1) {
            player.setPalette(candidateCardSets.get(0));
        }

        int lastElement = candidateCardSets.size() -1;
        player.setPalette(candidateCardSets.get(lastElement));

        return player;
    }

   

}
