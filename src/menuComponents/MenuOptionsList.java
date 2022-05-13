package menuComponents;

public class MenuOptionsList {
    public enum MenuOption { // hidden options only after visible ones //
        SIGN,
        VALIDATE,
        GEN_PAIR,
        CHECK_PAIR,
        DEFAULT
    }
    final private String menuOptionNames[] = new String[]{ // menu option names for enums //
            "sign file",
            "validate file",
            "generate key pair",
            "check keys pair"
    };
    private MenuOption menuOptions[] = MenuOption.values();
    private int menuOptionNamesLength = menuOptionNames.length;
    private MenuOption lastChoice = MenuOption.SIGN;

    public MenuOptionsList() {}


    public MenuOption getMenuOption(int i) {
        return menuOptions[i];
    }
    public MenuOption[] getMenuOptions() {
        return menuOptions;
    }
    public int getLength() {
        return menuOptionNamesLength;
    }
    public String getMenuOptionName(int i) {
        return menuOptionNames[i];
    }
    public String[] getMenuOptionNames() {
        return menuOptionNames;
    }
    public MenuOption getLastChoice() {
        return lastChoice;
    }

}
