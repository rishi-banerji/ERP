public abstract class User implements Menu {
    private String name;
    public String password;

    public User(String name) {
        this.name = name;
    }

    abstract boolean sign_up(String email, String password);
    abstract User login(String email, String password);
    abstract String role();
}
