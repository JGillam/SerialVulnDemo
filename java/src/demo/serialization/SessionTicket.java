package demo.serialization;


import java.io.Serializable;

public class SessionTicket implements Serializable{
    static final long serialVersionUID = 42L;

    private long timestamp;
    private String username = "guest";
    private String role = "guest";

    public SessionTicket() {
        timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
