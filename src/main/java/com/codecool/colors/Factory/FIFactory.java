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

    public Function<Player, List<Card>> getEvenCards() {
        return player -> player.getPalette()
                .stream()
                .filter(evenCards)
                .collect(Collectors.toList());
    }
    public Function<Card, String> cardNumber = card -> String.valueOf(card.getNumber());
    public Function<Card, String> cardColor = card -> card.getColor();
    Function<List<Card>, Card> highestCard = list -> list.stream()
                                                        .max(Comparator.comparing(Card::getNumber))
                                                        .get();

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

    public Player filterBestSetOfCardsBy(Function<Card, String> rule,Player player) {

        Map<String, List<Card>> sortedPallete = sortPalleteBy(rule, player);

        OptionalInt optional = sortedPallete.entrySet().stream()
                .mapToInt(entrySet -> entrySet.getValue().size())
                .max();

        int maxNumOfSameCards = optional.getAsInt();
        int firstElement = 0;

        List<List<Card>> candidateCardSets = sortedPallete.entrySet().stream()
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

    public Map<String, List<Card>> sortPalleteBy(Function<Card, String> rule, Player player) {
        return player.getPalette().stream()
                .collect(Collectors.groupingBy(rule));
    }

    public Player setCardsWithDifferent(Function<Card, String> rule, Player player) {
        Map<String, List<Card>> sortedCards = sortPalleteBy(rule, player);
        if (player.getPalette().size() == sortedCards.size()) {
            return player;
        }
        List<Card> newCardSet = sortedCards.entrySet().stream()
                .map(entrySet -> entrySet.getValue())
                .map(highestCard)
                .collect(Collectors.toList());
        player.setPalette(newCardSet);
        return player;
    }

    public Optional<Player> choosePlayerWithHighestCard(List<Player> players) {
        return players.stream()
                .max(Comparator.comparing(Player::getHighest));
    }

    public Player setCardRun(Player player) {
        List<Card> pallete = player.getPalette().stream()
                .sorted(Comparator.comparing(Card::getNumber))
                .collect(Collectors.toList());

        Map<Integer, List<List<Card>>> runs = new HashMap<>();
        List<Card> temp = new ArrayList<>();
        temp.add(pallete.get(0));
        for (int i = 1; i < pallete.size(); i++) {
            Card lastCard = pallete.get(i - 1);
            Card currentCard = pallete.get(i);
            if (isGreaterByOne(currentCard, lastCard)) {
                temp.add(currentCard);
            } else {
                List<Card> copy = new ArrayList<>(temp);
                if (runs.containsKey(copy.size())) {
                    runs.get(copy.size()).add(temp);
                }
                List<List<Card>> list = new ArrayList<>();
                list.add(copy);
                runs.put(temp.size(), list);
            }

        }
        return null;
    }

    private boolean isGreaterByOne(Card bigger, Card smaller) {
        if (bigger.getNumber() - smaller.getNumber() == 1) {
            return true;
        }
        return false;
    }

}
