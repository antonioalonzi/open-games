package com.aa.opengames.game.tictactoe;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.game.play.GamePlayResult;
import com.aa.opengames.init.InitController;
import com.aa.opengames.table.Table;
import com.aa.opengames.table.TableRepository;
import com.aa.opengames.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static com.aa.opengames.game.play.GamePlayResult.GamePlayResultType.DRAW;
import static com.aa.opengames.game.play.GamePlayResult.GamePlayResultType.WIN_LOSS;
import static com.aa.opengames.game.tictactoe.TicTacToeGamePlayPlayerInfo.TIC_TAC_TOE_SYMBOL_O;
import static com.aa.opengames.game.tictactoe.TicTacToeGamePlayPlayerInfo.TIC_TAC_TOE_SYMBOL_X;

@Controller
public class TicTacToeController {

    private final Logger LOGGER = LoggerFactory.getLogger(InitController.class);

    private final TicTacToeGamePlayRepository ticTacToeGamePlayRepository;
    private final TableRepository tableRepository;
    private final EventSender eventSender;

    @Autowired
    public TicTacToeController(TicTacToeGamePlayRepository ticTacToeGamePlayRepository, TableRepository tableRepository, EventSender eventSender) {
        this.ticTacToeGamePlayRepository = ticTacToeGamePlayRepository;
        this.tableRepository = tableRepository;
        this.eventSender = eventSender;
    }

    @Scheduled(fixedRate = 1000)
    public void initialize() {
        Set<TicTacToeGamePlay> gamePlayToInitialize = ticTacToeGamePlayRepository.getAll().stream()
                .filter((gamePlay) -> !gamePlay.isInitialized())
                .collect(Collectors.toSet());

        gamePlayToInitialize.forEach((gamePlay) -> {
            Table table = tableRepository.getTableById(gamePlay.getTableId());

            ArrayList<String> players = table.getPlayers();
            Collections.shuffle(players);

            ArrayList<TicTacToeGamePlayPlayerInfo> playersInfo = createPlayersInfo(players);

            TicTacToeGamePlay initializedGamePlay = gamePlay.toBuilder()
                    .isInitialized(true)
                    .playersInfo(playersInfo)
                    .build();

            ticTacToeGamePlayRepository.update(initializedGamePlay);
            LOGGER.info("Initialized TicTacToeGamePlay with id " + gamePlay.getId());

            sendInitializationEvent(players, playersInfo, initializedGamePlay);
        });
    }

    @MessageMapping("/games/tic-tac-toe/actions")
    public synchronized void create(SimpMessageHeaderAccessor headerAccessor, TicTacToeActionRequest ticTacToeActionRequest) {
        String token = headerAccessor.getSessionId();
        User user = SecurityContextHolder.getAndCheckUser(token);
        LOGGER.info("TicTacToeActionRequest '{}' request received from username '{}'", ticTacToeActionRequest, user.getUsername());

        TicTacToeGamePlay gamePlay = ticTacToeGamePlayRepository.getById(ticTacToeActionRequest.getId()).orElseThrow(() -> new RuntimeException("GamePlay with id " + ticTacToeActionRequest.getId() + " not found."));
        TicTacToeGamePlayPlayerInfo playerInfo = gamePlay.getCurrentPlayerInfoAndCheckUser(user);

        TicTacToeGameState gameState = (TicTacToeGameState)gamePlay.getGameState();
        gameState.updateToNextPlayer();
        gameState.setSymbol(ticTacToeActionRequest.getAction().getI(), ticTacToeActionRequest.getAction().getJ(), playerInfo.getSymbol());

        TicTacToeUpdateEvent ticTacToeUpdateEvent = TicTacToeUpdateEvent.builder()
                .gameState(gameState)
                .build();

        eventSender.sendToUsers(gamePlay.getPlayersUsername(), Event.builder()
                .type(TicTacToeUpdateEvent.EVENT_TYPE)
                .value(ticTacToeUpdateEvent)
                .build());

        TicTacToaBoardUtils.GameFinishStatus gameFinishStatus = TicTacToaBoardUtils.isGameFinished(gameState.getBoard());
        if (gameFinishStatus.isFinished()) {
            gameState.setFinished(true);
            GamePlayResult gamePlayResult = GamePlayResult.builder()
                    .winner(user.getUsername())
                    .gamePlayResultType(gameFinishStatus.getWinningSymbol().isEmpty() ? DRAW : WIN_LOSS)
                    .build();
            TicTacToeGamePlay finishedGamePlay = gamePlay.toBuilder().gamePlayResult(gamePlayResult).build();
            ticTacToeGamePlayRepository.update(finishedGamePlay);

            eventSender.sendToUsers(gamePlay.getPlayersUsername(), Event.builder()
                    .type(TicTacToeFinishEvent.EVENT_TYPE)
                    .value(TicTacToeFinishEvent.builder()
                            .winningSymbol(gameFinishStatus.getWinningSymbol())
                            .build())
                    .build());
        }
    }

    private ArrayList<TicTacToeGamePlayPlayerInfo> createPlayersInfo(ArrayList<String> players) {
        ArrayList<TicTacToeGamePlayPlayerInfo> playerInfos = new ArrayList<>(players.size());
        for (int i = 0; i < players.size(); i++) {
            String player = players.get(i);
            String symbol = (i == 0) ? TIC_TAC_TOE_SYMBOL_X : TIC_TAC_TOE_SYMBOL_O;
            TicTacToeGamePlayPlayerInfo ticTacToeGamePlayPlayerInfo = TicTacToeGamePlayPlayerInfo.builder()
                    .username(player)
                    .symbol(symbol)
                    .build();
            playerInfos.add(ticTacToeGamePlayPlayerInfo);
        }
        return playerInfos;
    }

    private void sendInitializationEvent(ArrayList<String> players, ArrayList<TicTacToeGamePlayPlayerInfo> playersInfo, TicTacToeGamePlay initializedGamePlay) {
        ArrayList<TicTacToeInitializationEvent.PlayerInfo> playersInfoEvent = playersInfo.stream()
                .map((playerInfo) -> TicTacToeInitializationEvent.PlayerInfo.builder()
                        .username(playerInfo.getUsername())
                        .symbol(playerInfo.getSymbol())
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));

        eventSender.sendToUsers(players, Event.builder()
                .type(TicTacToeInitializationEvent.EVENT_TYPE)
                .value(TicTacToeInitializationEvent.builder()
                        .id(initializedGamePlay.getId())
                        .playersInfo(playersInfoEvent)
                        .currentPlayerIndex(initializedGamePlay.getGameState().getCurrentPlayerIndex())
                        .build())
                .build());
    }
}
