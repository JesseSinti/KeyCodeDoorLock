public class KeyCodeDoorLock {
    public static void main(String[] args) {

    }
    public boolean lock_status = true;
    public int attempts = 0;
    public String code = "0000";

    public boolean enter_code(String user_code) {
        if (is_hard_locked() == false) {
            if (user_code.equals(code)) {
                lock_status = false;
                attempts = 0;
                return true;
            } else {
                attempts += 1;
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean is_locked() {

        if (lock_status) {
            return true;
        } else {
            return false;
        }
    }

    public boolean is_hard_locked() {

        if (attempts >= 3) {
            return true;
        } else {
            return false;
        }
    }

    public boolean reset(String user_code) {
        if (is_locked() == false ) {
            code = user_code;
            return true;
        } else {
            return false;
        }
    }

    public void lock() {
        lock_status = true;
    }
}
