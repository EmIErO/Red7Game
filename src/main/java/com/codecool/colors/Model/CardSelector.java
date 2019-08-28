package com.codecool.colors.Model;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CardSelector {

    public CardSelector() {
    }

    public List<Card> selectBestSetOfCardsWithSameRank(Player player) {
        Player playerCopy = new Player(player);
        Collections.sort(playerCopy.getPalette());

        Map<Integer, List<Card>> result = playerCopy.getPalette().stream()
                .collect(Collectors.groupingBy(Card::getNumber));

        return selectBestSet(result);
    }

    public List<Card> selectBestSetOfCardsWithSameColor(Player player) {
        Player playerCopy = new Player(player);
        Collections.sort(player.getPalette());

        Map<Integer, List<Card>> result = playerCopy.getPalette().stream()
                .collect(Collectors.groupingBy(card -> Card.colorMap.get(card.getColor())));

        return selectBestSet(result);
    }

    List<Card> selectBestSetOfCardsWithDifferentColor(Player player) {
        Player playerCopy = new Player(player);
        Collections.sort(player.getPalette());

        Map<String, List<Card>> result = playerCopy.getPalette().stream()
                .collect(Collectors.groupingBy(Card::getColor));

        List<Card> bestSet = result.entrySet().stream()
                .map(entrySet -> {
                    List<Card> cards = entrySet.getValue();
                    Collections.sort(cards);
                    return cards.get(cards.size() - 1);
                })
                .collect(Collectors.toList());
        return bestSet;
    }

    private List<Card> selectBestSet(Map<Integer, List<Card>> result) {
        List<Card> bestSameRankSet = result.entrySet().stream()
                .sorted(Comparator.comparing(entrySet -> entrySet.getKey()))
                .map(entrySet -> entrySet.getValue())
                .reduce((first, second) -> second)
                .orElse(new ArrayList<>());

        return bestSameRankSet;
    }

    public List<Card> selectBestRun(Player player) {
        List<List<Card>> runs = new LinkedList<>();
        Player copyPlayer = new Player(player);
        List<Card> pallete = copyPlayer.getPalette();
        Collections.sort(pallete);
        List<Card> run = new ArrayList<>();

        for (Card card : pallete) {
            if (run.isEmpty()) {
                run.add(card);
            }
            else if (cardIsHigherByOne(getLastCard(run), card)) {
                run.add(card);
            } else if (cardHasSameRank(getLastCard(run), card)) {
                run.remove(getLastCard(run));
                run.add(card);
            } else {
                runs.add(run);
                run = new ArrayList<>();
                run.add(card);
            }
            if (isLastCard(pallete, card)) {
                runs.add(run);
            }
        }

        if (runs.size() == pallete.size()) {
            List<Card> bestRun = new ArrayList<>();
            bestRun.add(pallete.get(pallete.size() - 1));
            return bestRun;
        }
        return filterRuns(runs);
    }

    private List<Card> filterRuns(List<List<Card>> runs) {
        List<Card> bestRun = runs.get(0);
        int size = runs.get(0).size();

        for (List<Card> run : runs) {
            if (run.size() >= size) {
                bestRun = run;
                size = run.size();
            }
        }
        return bestRun;
    }

    private boolean isLastCard(List<Card> pallete, Card card) {
        return getLastCard(pallete).equals(card);
    }

    private boolean cardHasSameRank(Card lastCard, Card thisCard) {
        return lastCard.getNumber() == thisCard.getNumber();
    }

    private Card getLastCard(List<Card> run) {
        return run.get(run.size() - 1);
    }

    private boolean cardIsHigherByOne(Card lastCard, Card thisCard) {
        return thisCard.getNumber() - lastCard.getNumber() == 1;
    }

    static Function<Player, Card> highestCard = player -> {
        Collections.sort(player.getPalette());
        return player.getPalette().get(player.getPalette().size() - 1);
    };


}
