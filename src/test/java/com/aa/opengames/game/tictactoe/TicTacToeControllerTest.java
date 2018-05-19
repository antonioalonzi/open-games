package com.aa.opengames.game.tictactoe;

import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.exceptions.HandledRuntimeException;
import com.aa.opengames.game.play.GamePlayResult;
import com.aa.opengames.table.Table;
import com.aa.opengames.table.TableRepository;
import com.aa.opengames.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static com.aa.opengames.utils.TestUtils.sessionHeader;
import static com.google.common.collect.Sets.newHashSet;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TicTacToeControllerTest {

    @Autowired
    private TicTacToeController ticTacToeController;

    @Autowired
    private TicTacToeGamePlayFactory ticTacToeGamePlayFactory;

    @Autowired
    private TicTacToeGamePlayRepository ticTacToeGamePlayRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private EventSender eventSender;

    private ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

    @Before
    public void setup() {
        Mockito.reset(eventSender);
        ticTacToeGamePlayRepository.removeAllGamePlays();
    }

    @Test
    public void shouldPlayATicTacToeGame() {
        // Given a game
        String owner = "user-1";
        String joiner = "user-2";

        UUID tableId = UUID.randomUUID();
        tableRepository.addTable(Table.builder()
                .id(tableId)
                .owner(owner)
                .joiner(joiner)
                .build());

        UUID gamePlayId = UUID.randomUUID();
        TicTacToeGamePlay ticTacToeGamePlay = ticTacToeGamePlayFactory.create(gamePlayId, tableId);

        // When game is initialized
        ticTacToeController.initialize();

        // Then the game is initialized and saved in the repository
        TicTacToeGamePlay initializedTacToeGamePlay = ticTacToeGamePlayRepository.getById(gamePlayId).orElseThrow(RuntimeException::new);
        assertThat(initializedTacToeGamePlay.isInitialized(), sameBeanAs(true));
        assertThat(initializedTacToeGamePlay.getPlayersInfo().size(), sameBeanAs(2));

        // And init event is sent to the users
        Event initEvent = Event.builder()
                .type(TicTacToeInitializationEvent.EVENT_TYPE)
                .value(TicTacToeInitializationEvent.builder()
                        .id(gamePlayId)
                        .currentPlayerIndex(0)
                        .playersInfo(newArrayList(
                                TicTacToeInitializationEvent.PlayerInfo.builder().username(owner).symbol("X").build(),
                                TicTacToeInitializationEvent.PlayerInfo.builder().username(joiner).symbol("O").build()
                        ))
                        .build())
                .build();

        Mockito.verify(eventSender).sendToUsers(argThat(sameBeanAs(newHashSet("user-1", "user-2"))), eventCaptor.capture());
        assertThat(eventCaptor.getValue(), sameBeanAs(initEvent).ignoring("value.playersInfo"));
        TicTacToeInitializationEvent ticTacToeInitializationEvent = (TicTacToeInitializationEvent) eventCaptor.getValue().getValue();
        assertThat(ticTacToeInitializationEvent.getPlayersInfo().size(), sameBeanAs(2));


        // Given players
        String firstPlayer = ticTacToeInitializationEvent.getPlayersInfo().get(0).getUsername();
        SimpMessageHeaderAccessor firstPlayerSessionHeader = sessionHeader("firstPlayerSessionId");
        TestUtils.loginUser(firstPlayerSessionHeader, firstPlayer);
        String secondPlayer = ticTacToeInitializationEvent.getPlayersInfo().get(1).getUsername();
        SimpMessageHeaderAccessor secondPlayerSessionHeader = sessionHeader("secondPlayerSessionId");
        TestUtils.loginUser(secondPlayerSessionHeader, secondPlayer);

        // When sends an action
        ticTacToeController.create(firstPlayerSessionHeader, TicTacToeActionRequest.builder()
                .id(gamePlayId)
                .action(TicTacToeActionRequest.TicTacToeAction.builder().i(0).j(0).build())
                .build());

        // Then the board is updated and control is passed to the other player
        TicTacToeGamePlay updatedTacToeGamePlay = ticTacToeGamePlayRepository.getById(gamePlayId).orElseThrow(RuntimeException::new);
        assertThat(updatedTacToeGamePlay.getCurrentPlayerInfo(), sameBeanAs(ticTacToeInitializationEvent.getPlayersInfo().get(1)));
        assertThat(updatedTacToeGamePlay.getGameState().getCurrentPlayerIndex(), sameBeanAs(1));
        assertThat(((TicTacToeGameState)updatedTacToeGamePlay.getGameState()).getSymbol(0, 0), sameBeanAs("X"));

        // And
        TicTacToeGameState gameState = new TicTacToeGameState();
        gameState.setSymbol(0, 0, "X");
        gameState.setCurrentPlayerIndex(1);
        Event updateEvent = Event.builder()
                .type(TicTacToeUpdateEvent.EVENT_TYPE)
                .value(TicTacToeUpdateEvent.builder()
                        .gameState(gameState)
                        .build())
                .build();

        Mockito.verify(eventSender, Mockito.times(2)).sendToUsers(argThat(sameBeanAs(newHashSet("user-1", "user-2"))), eventCaptor.capture());
        assertThat(eventCaptor.getAllValues().get(2), sameBeanAs(updateEvent));


        // When same player sends an action
        try {
            ticTacToeController.create(firstPlayerSessionHeader, TicTacToeActionRequest.builder()
                    .id(gamePlayId)
                    .action(TicTacToeActionRequest.TicTacToeAction.builder().i(0).j(0).build())
                    .build());
            Assertions.fail("Expected HandledRuntimeException.");

        } catch (HandledRuntimeException e) {
            assertThat(e.getMessage().contains(" sent an action during "), sameBeanAs(true));
        }


        // When Game finishes
        ticTacToeController.create(secondPlayerSessionHeader, TicTacToeActionRequest.builder()
                .id(gamePlayId)
                .action(TicTacToeActionRequest.TicTacToeAction.builder().i(1).j(0).build())
                .build());
        ticTacToeController.create(firstPlayerSessionHeader, TicTacToeActionRequest.builder()
                .id(gamePlayId)
                .action(TicTacToeActionRequest.TicTacToeAction.builder().i(0).j(1).build())
                .build());
        ticTacToeController.create(secondPlayerSessionHeader, TicTacToeActionRequest.builder()
                .id(gamePlayId)
                .action(TicTacToeActionRequest.TicTacToeAction.builder().i(1).j(1).build())
                .build());
        ticTacToeController.create(firstPlayerSessionHeader, TicTacToeActionRequest.builder()
                .id(gamePlayId)
                .action(TicTacToeActionRequest.TicTacToeAction.builder().i(0).j(2).build())
                .build());

        // Then the board is updated and control is passed to the other player
        TicTacToeGamePlay finishedTacToeGamePlay = ticTacToeGamePlayRepository.getById(gamePlayId).orElseThrow(RuntimeException::new);
        assertThat(finishedTacToeGamePlay.getGameState().isFinished(), sameBeanAs(true));
        assertThat(finishedTacToeGamePlay.getGamePlayResult(), sameBeanAs(GamePlayResult.builder()
                .gamePlayResultType(GamePlayResult.GamePlayResultType.WIN_LOSS)
                .winner(firstPlayer)
                .build()));
    }
}