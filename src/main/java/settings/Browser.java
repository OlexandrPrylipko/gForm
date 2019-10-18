package settings;

public enum Browser {

    CHROME_WINDOWS("chrome_windows"),
    CHROME_WINDOWS_HEADLESS("chrome_windows_headless"),

    CHROME_LINUX("chrome_linux"),
    CHROME_LINUX_HEADLESS("chrome_linux_headless"),

    CHROME_MAC("chrome_mac"),
    CHROME_MAC_HEADLESS("chrome_mac_headless"),

    FIREFOX_WINDOWS("firefox_windows"),
    FIREFOX_WINDOWS_HEADLESS("firefox_windows_headless"),

    FIREFOX_LINUX("firefox_linux"),
    FIREFOX_LINUX_HEADLESS("firefox_linux_headless"),

    FIREFOX_MAC("firefox_mac"),
    FIREFOX_MAC_HEADLESS("firefox_mac_headless"),

    SAFARI_MAC("safari_mac"),

    OPERA_WINDOWS("opera_windows");

    private String browserName;

    Browser(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserName() {
        return browserName;
    }

    public static Browser getByName(String name) {
        for (Browser browser : values()) {
            if (browser.getBrowserName().equalsIgnoreCase(name)) {
                return browser;
            }
        }
        return null;
    }
}