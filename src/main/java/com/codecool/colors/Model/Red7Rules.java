package com.codecool.colors.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Red7Rules {

    private FuncInterfacesImpl fii;

    public static Map<String, Method> r7rules;

    static {
        try {
            r7rules = new HashMap<>();
            r7rules.put("red", Red7Rules.class.getDeclaredMethod("playByRedRule", new Class[]{List.class}));
            r7rules.put("orange", Red7Rules.class.getDeclaredMethod("playByOrangeRule", new Class[]{List.class}));
            r7rules.put("yellow", Red7Rules.class.getMethod("playByYellowRule", List.class));
            r7rules.put("green", Red7Rules.class.getDeclaredMethod("playByGreenRule", List.class));
            r7rules.put("blue", Red7Rules.class.getMethod("playByBlueRule", List.class));
            r7rules.put("indigo", Red7Rules.class.getMethod("playByIndigoRule", List.class));
            r7rules.put("violet", Red7Rules.class.getMethod("playByVioletRule", List.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public Red7Rules() {
    }

    @Autowired
    public Red7Rules(FuncInterfacesImpl fii) {
        this.fii = fii;
    }

    public void setFii(FuncInterfacesImpl funcInterfaces) {
        this.fii = funcInterfaces;
    }

    public String playByRedRule(List<Player> players) {

        Player winner = players.stream()
                .max(Comparator.comparing(CardSelector.highestCard))
                .orElse(new Player());
        return winner.getName();
    }

    public String playByGreenRule(List<Player> players) {
        return chooseWinner(players, fii.cardsWithEvenRank);
    }

    public String playByVioletRule(List<Player> players) {
        return chooseWinner(players, fii.cardsWithRankLowerThan4);
    }

    public String playByOrangeRule(List<Player> players) {
        return chooseWinner(players, fii.cardsWithSameRank);
    }

    public String playByYellowRule(List<Player> players) {
        return chooseWinner(players, fii.cardsWithSameColor);
    }

    public String playByBlueRule(List<Player> players) {
        return chooseWinner(players, fii.cardsWithDifferentColor);
    }

    public String playByIndigoRule(List<Player> players) { //rule fii.bestRun
        return chooseWinner(players, fii.bestRun);
    }

    private String chooseWinner(List<Player> players, Function<Player, Player> cardsFilteringRule) {
        List<Player> playersWithBestCards = players.stream()
                .map(cardsFilteringRule)
                .collect(Collectors.toList());

        Map<Integer, List<Player>> result = playersWithBestCards.stream()
                .collect(Collectors.groupingBy(fii.palleteSize));

        if (result.size() == 1 && result.containsKey(0)) {
            return null;
        }

        List<Player> winners = result.entrySet().stream()
                .sorted(Comparator.comparing(entrySet -> entrySet.getKey()))
                .map(entrySet -> entrySet.getValue())
                .reduce((first, second) -> second)
                .orElse(new ArrayList<>());

        Player winner = winners.stream()
                .max(Comparator.comparing(CardSelector.highestCard))
                .orElse(new Player());

        return winner.getName();
    }
}
