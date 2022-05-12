public class Menu {

    private int choice = 0;

    public Menu() {}
    public void printMenu() {
        System.out.println("---------------");
        System.out.println("1 - sign");
        System.out.println("2 - validate");
        System.out.println("---------------");
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }
    public int getChoice() {
        return this.choice;
    }


}
