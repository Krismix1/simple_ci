package monitor;

import messaging.Publisher;
import models.Commit;
import models.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sleepers.Sleeper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PollingMonitorTest {

    private static class MockSleeper implements Sleeper {

        @Override
        public void sleep(long millis) {}
    }

    private static class MockPollingMonitor extends PollingMonitor {

        MockPollingMonitor(Publisher<Commit> publisher, Repository repository, Sleeper sleeper) {
            super(publisher, repository, sleeper);
        }

        @Override
        Optional<Commit> checkNewCommits() {
            return Optional.empty();
        }

        @Override
        void init() {
        }

        @Override
        void notifyRepoUpdated(Commit commit) {
            assertNotNull(commit);
            super.notifyRepoUpdated(commit);
        }
    }

    private static class MockRepository extends Repository {}

    private PollingMonitor pollingMonitor;

    @Mock
    private AbstractMonitorTest.MockPublisher mockPublisher;

    @Mock
    private MockRepository mockRepository;

    @Mock
    private MockSleeper mockSleeper;

    @BeforeEach
    void setUp() {
        this.pollingMonitor = new MockPollingMonitor(mockPublisher, mockRepository, mockSleeper);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void doJob() {
        pollingMonitor.doCheck();

//        verify(pollingMonitor.notifyRepoUpdated(any()), never());
    }
}
