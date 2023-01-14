import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjatk.Client;
import pl.pjatk.Message;
import pl.pjatk.RaceResultService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RaceResultServiceMockitoTest {

    RaceResultService raceResultService = new RaceResultService();

    @Test
    void should_send_twice_to_two_subscribers() {
        // given
        Client client1 = mock(Client.class);
        Client client2 = mock(Client.class);
        raceResultService.addSubscriber(client1);
        raceResultService.addSubscriber(client2);

        Message message = new Message();
        doNothing().when(client1).receive(any());
        doNothing().when(client2).receive(any());
        // when
        raceResultService.send(message);

        // then
        verify(client1, times(1)).receive(any());
        verify(client2, times(1)).receive(any());
    }

    @Test
    void remove_subscriber_should_send_once() {
        // given
        Client client1 = mock(Client.class);
        Client client2 = mock(Client.class);
        raceResultService.addSubscriber(client1);
        raceResultService.addSubscriber(client2);
        raceResultService.removeSubscriber(client1);

        Message message = new Message();
        doNothing().when(client2).receive(any());
        // when
        raceResultService.send(message);

        // then
        verify(client1, times(0)).receive(any());
        verify(client2, times(1)).receive(any());
    }
}
