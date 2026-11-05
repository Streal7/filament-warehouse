enum RemovalReason {
    DEFECT("Брак", true),
    BROKEN("Сломалась", true),
    USED_UP("Закончилась", false);

    private final String title;
    private final boolean loss;

    RemovalReason(String title, boolean loss) {
        this.title = title;
        this.loss = loss;
    }

    String getTitle() {
        return title;
    }

    boolean isLoss(){
        return loss;
    }
}