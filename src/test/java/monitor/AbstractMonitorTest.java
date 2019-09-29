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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AbstractMonitorTest {

    private static class MockMonitor extends AbstractMonitor {
        MockMonitor(Publisher<Commit> publisher) {
            super(publisher);
        }
    }

    static class MockPublisher implements Publisher<Commit> {
        @Override
        public void publishMessage(Commit message) {
            assertNotNull(message);
        }
    }

    @Mock
    private MockPublisher mockPublisher;

    private AbstractMonitor abstractMonitor;

    @BeforeEach
    void setUp() {
        this.abstractMonitor = new MockMonitor(mockPublisher);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void requireNotNullPublisher() {
        assertThrows(NullPointerException.class, () -> new MockMonitor(null));
    }

    @Test
    void notifyRepoUpdatedNotNullCommit() {
        Commit mockCommit = new Commit(new Repository() {}, "", "");
        this.abstractMonitor.notifyRepoUpdated(mockCommit);

        verify(mockPublisher)
            .publishMessage(mockCommit);
    }

    @Test
    void notifyRepoUpdatedNullCommit() {
        assertThrows(IllegalArgumentException.class, () ->
            this.abstractMonitor.notifyRepoUpdated(null)
        );

        verify(mockPublisher, never())
            .publishMessage(isNull());
    }
}
