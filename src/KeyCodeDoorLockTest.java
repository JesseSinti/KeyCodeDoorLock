public class KeyCodeDoorLockTest {
    private static int testsRun = 0;
    private static int testsFailed = 0;
    public static void main(String[] args) {
        testInitialState();
        testUnlockWithCorrectCode();
        testFailedAttemptsHardLock();
        testResetWhenUnlocked();
        testResetWhenLocked();
        testManualLock();
        if (testsFailed > 0) {
            throw new AssertionError(testsFailed + " tests failed out of " +
                    testsRun);
        }
        System.out.println("All tests passed (" + testsRun + " total).");
    }
    private static void assertTrue(boolean condition, String message) {
        testsRun++;
        if (!condition) {
            testsFailed++;
            System.err.println("FAIL: " + message);
        }
    }
    private static void testInitialState() {
        KeyCodeDoorLock lock = new KeyCodeDoorLock();
        assertTrue(lock.is_locked(), "new lock should start locked");
        assertTrue(!lock.is_hard_locked(), "new lock should not be hard locked");
    }
    private static void testUnlockWithCorrectCode() {
        KeyCodeDoorLock lock = new KeyCodeDoorLock();
        boolean unlocked = lock.enter_code("0000");
        assertTrue(unlocked, "entering correct code should unlock");
        assertTrue(!lock.is_locked(), "lock should be unlocked after correct code");
    }
    private static void testFailedAttemptsHardLock() {
        KeyCodeDoorLock lock = new KeyCodeDoorLock();
        assertTrue(!lock.enter_code("1111"), "wrong code attempt 1 should fail");
        assertTrue(!lock.enter_code("2222"), "wrong code attempt 2 should fail");
        assertTrue(!lock.enter_code("3333"), "wrong code attempt 3 should fail");
        assertTrue(lock.is_hard_locked(), "lock should be hard locked after 3 failures");
                assertTrue(lock.is_locked(), "lock should be locked when hard locked");
        assertTrue(!lock.enter_code("0000"), "hard locked should not unlock even with correct code");
    }
    private static void testResetWhenUnlocked() {
        KeyCodeDoorLock lock = new KeyCodeDoorLock();
        lock.enter_code("0000");
        boolean reset = lock.reset("1234");
        assertTrue(reset, "reset should succeed when unlocked");
        lock.lock();
        assertTrue(lock.enter_code("1234"), "new code should unlock after reset");
    }
    private static void testResetWhenLocked() {
        KeyCodeDoorLock lock = new KeyCodeDoorLock();
        boolean reset = lock.reset("9999");
        assertTrue(!reset, "reset should fail when locked");
        assertTrue(lock.enter_code("0000"), "original code should still work after failed reset");
    }
    private static void testManualLock() {
        KeyCodeDoorLock lock = new KeyCodeDoorLock();
        lock.enter_code("0000");
        lock.lock();
        assertTrue(lock.is_locked(), "lock should be locked after manual lock");
        assertTrue(!lock.is_hard_locked(), "manual lock should not hard lock");
    }
}

