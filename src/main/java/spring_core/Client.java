package spring_core;

public class Client {

    private String id;
    private String fullName;
    private String greetings;

    public Client() {
    }

    public Client(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGreeting(String greeting) {
        this.greetings = greeting;
    }

    public String getGreeting() {
        return greetings;
    }
}
