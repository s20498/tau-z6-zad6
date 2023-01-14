import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import pl.pjatk.Client;
import pl.pjatk.Message;
import pl.pjatk.RaceResultService;

public class RaceResultServiceEasyMockTest {

    RaceResultService raceResultService = new RaceResultService();

    @Test
    public void should_send_twice_to_two_subscribers() {
        RaceResultService raceResultService = new RaceResultService();

        Client client1 = EasyMock.createMock(Client.class);
        Client client2 = EasyMock.createMock(Client.class);
        Message message = EasyMock.createMock(Message.class);

        client1.receive(message);
        EasyMock.expectLastCall();
        client2.receive(message);
        EasyMock.expectLastCall();

        EasyMock.replay(client1, client2, message);

        raceResultService.addSubscriber(client1);
        raceResultService.addSubscriber(client2);

        raceResultService.send(message);

        EasyMock.verify(client1, client2);
    }

    @Test
    public void remove_subscriber_should_send_once() {
        RaceResultService raceResultService = new RaceResultService();

        Client client1 = EasyMock.createMock(Client.class);
        Client client2 = EasyMock.createMock(Client.class);
        Message message = EasyMock.createMock(Message.class);

        client1.receive(message);
        EasyMock.expectLastCall();
        client2.receive(message);
        EasyMock.expectLastCall();

        EasyMock.replay(client1, client2, message);

        raceResultService.addSubscriber(client1);
        raceResultService.addSubscriber(client2);
        raceResultService.removeSubscriber(client2);

        raceResultService.send(message);

        EasyMock.verify(client1);
    }
}
