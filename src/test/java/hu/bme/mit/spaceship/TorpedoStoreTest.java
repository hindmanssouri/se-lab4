package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TorpedoStoreTest {

    @Test
    void fire_Success() {
        // Arrange
        TorpedoStore store = new TorpedoStore(1);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertEquals(true, result);
    }

    @Test
    void fire_EmptyStore() {
        TorpedoStore store = new TorpedoStore(0);

        assertThrows(IllegalArgumentException.class, () -> store.fire(1));
        assertTrue(store.isEmpty());
        assertEquals(0, store.getTorpedoCount());
    }

    @Test
    void fire_Failure() {
        // Arrange: Failure rate of 1 means firing always fails
        TorpedoStore store = new TorpedoStore(5, 1.0);

        // Act
        boolean result = store.fire(1);

        // Assert
        assertFalse(result);
        assertEquals(5, store.getTorpedoCount());
        assertFalse(store.isEmpty());
    }

    @Test
    void fire_MultipleTorpedoes() {
        // Arrange
        TorpedoStore store = new TorpedoStore(5, 0.0);

        // Act
        boolean result = store.fire(3);

        // Assert
        assertTrue(result);
        assertEquals(2, store.getTorpedoCount());
        assertFalse(store.isEmpty());
    }


    @Test
    void fire_ZeroTorpedoes() {
        TorpedoStore store = new TorpedoStore(5, 0.0);

        assertThrows(IllegalArgumentException.class, () -> store.fire(0));
        assertEquals(5, store.getTorpedoCount());
    }

    @Test
    void fire_NegativeTorpedoes() {
        TorpedoStore store = new TorpedoStore(5, 0.0);

        assertThrows(IllegalArgumentException.class, () -> store.fire(-1));
        assertEquals(5, store.getTorpedoCount());
    }

    @Test
    void fire_MoreThanAvailable() {
        TorpedoStore store = new TorpedoStore(3, 0.0);

        assertThrows(IllegalArgumentException.class, () -> store.fire(4));
        assertEquals(3, store.getTorpedoCount());
    }

    @Test
    void fire_AllRemainingTorpedoes() {
        TorpedoStore store = new TorpedoStore(3, 0.0);

        assertTrue(store.fire(3));
        assertEquals(0, store.getTorpedoCount());
        assertTrue(store.isEmpty());
    }

    @Test
    void fire_ConsecutiveShots() {
        TorpedoStore store = new TorpedoStore(5, 0.0);

        assertTrue(store.fire(2));
        assertEquals(3, store.getTorpedoCount());

        assertTrue(store.fire(2));
        assertEquals(1, store.getTorpedoCount());

        assertTrue(store.fire(1));
        assertEquals(0, store.getTorpedoCount());
        assertTrue(store.isEmpty());
    }
}
