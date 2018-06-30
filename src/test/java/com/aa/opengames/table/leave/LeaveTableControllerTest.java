package com.aa.opengames.table.leave;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.table.Table;
import com.aa.opengames.table.TableRepository;
import com.aa.opengames.table.TableUpdatedEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import static com.aa.opengames.event.EventResponse.ResponseStatus.ERROR;
import static com.aa.opengames.event.EventResponse.ResponseStatus.SUCCESS;
import static com.aa.opengames.game.tictactoe.TicTacToeGamePlay.TIC_TAC_TOE_LABEL;
import static com.aa.opengames.utils.TestUtils.loginUser;
import static com.aa.opengames.utils.TestUtils.sessionHeader;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LeaveTableControllerTest {

    @Autowired
    private LeaveTableController leaveTableController;

    @Autowired
    private EventSender eventSender;

    @Autowired
    private TableRepository tableRepository;

    private ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);

    @Before
    public void setup() {
        Mockito.reset(eventSender);
        tableRepository.removeAllTables();
        SecurityContextHolder.clean();
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailIfNotLoggedIn() {
        // Given
        LeaveTableRequest leaveTableRequest = LeaveTableRequest.builder().build();
        SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");

        // When
        leaveTableController.leave(sessionHeader, leaveTableRequest);
    }

    @Test
    public void shouldLeaveATableIfNotAlreadyActiveInOne() {
        // Given
        String username = "user";
        String otherUser = "other-user";
        UUID tableId = UUID.randomUUID();
        LeaveTableRequest leaveTableRequest = LeaveTableRequest.builder().tableId(tableId).build();
        SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");
        loginUser(sessionHeader, username);

        // user is in progress on a table
        tableRepository.addTable(Table.builder()
                .id(tableId)
                .game(TIC_TAC_TOE_LABEL)
                .owner(username)
                .status(Table.Status.IN_PROGRESS)
                .build()
        );

        // When
        leaveTableController.leave(sessionHeader, leaveTableRequest);

        // Then
        Event leaveTableResponse = Event.builder()
                .type(LeaveTableResponse.EVENT_TYPE)
                .value(LeaveTableResponse.builder()
                        .responseStatus(SUCCESS)
                        .message("Table successfully left.")
                        .build())
                .build();

        Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
        assertThat(eventCaptor.getValue(), sameBeanAs(leaveTableResponse));

//        Event leaveTableEvent = Event.builder()
//                .type(TableUpdatedEvent.EVENT_TYPE)
//                .value(TableUpdatedEvent.builder()
//                        .id(tableId)
//                        .status(Table.Status.FINISHED) // status changes as the only opponent left
//                        .build())
//                .build();
//
//        Mockito.verify(eventSender).sendToAll(eventCaptor.capture());
//        assertThat(eventCaptor.getValue(), sameBeanAs(leaveTableEvent));
    }

    @Test
    public void shouldReturnAnErrorIfNotActiveInATable() {
        // Given
        String username = "user";
        UUID tableId = UUID.randomUUID();
        LeaveTableRequest leaveTableRequest = LeaveTableRequest.builder().tableId(tableId).build();
        SimpMessageHeaderAccessor sessionHeader = sessionHeader("sessionId");
        loginUser(sessionHeader, username);

        // When
        leaveTableController.leave(sessionHeader, leaveTableRequest);

        // Then
        Event createTableResponse = Event.builder()
                .type(LeaveTableResponse.EVENT_TYPE)
                .value(LeaveTableResponse.builder()
                        .responseStatus(ERROR)
                        .message("Cannot leave the table with id '" + tableId + "' as you are not active in it.")
                        .build())
                .build();

        Mockito.verify(eventSender).sendToUser(argThat(sameBeanAs("sessionId")), eventCaptor.capture());
        assertThat(eventCaptor.getValue(), sameBeanAs(createTableResponse));
    }

    @TestConfiguration
    public static class LeaveTableControllerTestConfiguration {
        @Bean
        @Primary
        public Clock clock () {
            return Clock.fixed(Instant.ofEpochSecond(0), ZoneId.of("Europe/London"));
        }
    }
}