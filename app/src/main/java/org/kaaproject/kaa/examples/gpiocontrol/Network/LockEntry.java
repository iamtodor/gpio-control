package org.kaaproject.kaa.examples.gpiocontrol.Network;


public class LockEntry {

    private long id;
    private String lockPassword;

    public LockEntry() {
    }

    public LockEntry(long id, String lockPassword) {
        this.id = id;
        this.lockPassword = lockPassword;
    }

    @Override
    public String toString() {
        return "LockEntry{" +
                "id=" + id +
                ", lockPassword='" + lockPassword + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LockEntry lockEntry = (LockEntry) o;

        if (id != lockEntry.id) return false;
        return lockPassword != null ? lockPassword.equals(lockEntry.lockPassword) : lockEntry.lockPassword == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (lockPassword != null ? lockPassword.hashCode() : 0);
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLockPassword() {
        return lockPassword;
    }

    public void setLockPassword(String lockPassword) {
        this.lockPassword = lockPassword;
    }
}

