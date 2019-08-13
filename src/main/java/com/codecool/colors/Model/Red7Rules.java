package com.codecool.colors.Model;

import com.codecool.colors.Factory.FIFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Red7Rules {

    public static final int FIRST = 0;
    @Autowired
    private FIFactory filter;

    public static Map<String, Method> r7rules;

    static {
        try {
            r7rules = new HashMap<>();
            r7rules.put("red", Red7Rules.class.getDeclaredMethod("highestCardWin", new Class[]{List.class}));
            r7rules.put("orange", Red7Rules.class.getDeclaredMethod("sameNumWin", new Class[]{List.class}));
            r7rules.put("yellow", Red7Rules.class.getMethod("sameColorWin", new Class[]{List.class}));
            r7rules.put("green", Red7Rules.class.getDeclaredMethod("evenNumWin", new Class[]{List.class}));
            r7rules.put("blue", Red7Rules.class.getMethod("diffColorWin", new Class[]{List.class}));
            r7rules.put("indigo", Red7Rules.class.getMethod("runNumWin", new Class[]{List.class}));
            r7rules.put("violet", Red7Rules.class.getMethod("numLowerThan4Win", new Class[]{List.class}));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public Red7Rules() {
    }

    public Red7Rules(FIFactory filter) {
        this.filter = filter;
    }

    public void setFactory(FIFactory filter) {
        this.filter = filter;
    }

    public static void main(String[] args) {
        List<Card> cards1 = new ArrayList<>(Arrays.asList(new Card(4, "red"),
                                                            new Card(4, "green"),
                                                            new Card(8, "red"),
                                                            new Card(8, "yellow"),
                                                            new Card(3, "green")));
        List<Card> cards2 = new ArrayList<>(Arrays.asList(new Card(8, "green"), new Card(8, "orange")));
        Player player1 = new Player("Ania", cards1);
        Player player2 = new Player("Mira", cards2);
        List<Player> players = new ArrayList<>(Arrays.asList(player1, player2));
        Red7Rules r7r = new Red7Rules();
        r7r.setFactory(new FIFactory());

    }

    public String highestCardWin(List<Player> players) {
        Optional<Player> player = filter.choosePlayerWithHighestCard(players);

        return player.get().getName();
    }

    public String sameNumWin(List<Player> players) {

        Map<Integer, List<Player>> result = filterCardsWithSame(filter.cardNumber, players);

        return chooseWinner(result);
    }

    public String sameColorWin(List<Player> players) {
        Map<Integer, List<Player>> result = filterCardsWithSame(filter.cardColor, players);
        return chooseWinner(result);
    }

    private Map<Integer, List<Player>> filterCardsWithSame(Function<Card, String> rule, List<Player> players) {
        List<Player> playerWithBestCardSets = players.stream()
                .map(player -> filter.filterBestSetOfCardsBy(rule, player))
                .collect(Collectors.toList());
        return playerWithBestCardSets.stream()
                .collect(Collectors.groupingBy(Player::getPalleteSize));
    }


    public String evenNumWin(List<Player> players) {
        Map<Integer, List<Player>> result = filterPlayers(players, filter.evenCards);
        return chooseWinner(result);
    }

    public String numLowerThan4Win(List<Player> players) {
        Map<Integer, List<Player>> result = filterPlayers(players, filter.lowerThan4Cards);
        return chooseWinner(result);
    }

    private Map<Integer, List<Player>> filterPlayers(List<Player> players, Predicate<Card> rule) {
        return players.stream()
                .map(player -> filter.filterCards(player, rule))
                .collect(Collectors.groupingBy(Player::getPalleteSize));
    }

    private String chooseWinner(Map<Integer, List<Player>> result) {
        Optional<Map.Entry<Integer, List<Player>>> optional = result.entrySet().stream()
                .filter(es -> es.getKey() != 0)
                .max(Comparator.comparing(es -> es.getKey()));

        if (noOneWon(optional)) {
            return null;
        }

        if (isDraw(optional)) {
            return highestCardWin(extractPlayers(optional));
        }
        return extractWinnerName(optional);
    }

    private boolean noOneWon(Optional<Map.Entry<Integer, List<Player>>> optional) {
        return !optional.isPresent();
    }

    private boolean isDraw(Optional<Map.Entry<Integer, List<Player>>> optional) {
        return optional.get().getValue().size() > 1;
    }

    private List<Player> extractPlayers(Optional<Map.Entry<Integer, List<Player>>> optional) {
        return optional.get().getValue();
    }

    private String extractWinnerName(Optional<Map.Entry<Integer, List<Player>>> optional) {
        return optional.get().getValue().get(FIRST).getName();
    }

    public String diffColorWin(List<Player> players) {
        List<Player> filteredPlayers = players.stream()
                .map(player -> filter.setCardsWithDifferent(filter.cardColor, player))
                .collect(Collectors.toList());
        Map<Integer, List<Player>> result = filteredPlayers.stream()
                .collect(Collectors.groupingBy(Player::getPalleteSize));

        return chooseWinner(result);
    }

    public String runNumWin(List<Player> players) {
        List<Player> filteredPlayers = players.stream()
                .map(player -> filter.setCardsWithDifferent(filter.cardNumber, player))
                .collect(Collectors.toList());

        return "Mira";
    }



}
