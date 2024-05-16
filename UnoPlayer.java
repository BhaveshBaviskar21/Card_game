public class UnoPlayer extends Player {
    private Boolean unoCalled;
    public UnoPlayer(String name) {
        super(name);
        this.unoCalled = false;
    }
    public Boolean getUnoCalled() {
        return unoCalled;
    }
    public void setUnoCalled(Boolean unoCalled) {
        this.unoCalled = unoCalled;
    }
}
