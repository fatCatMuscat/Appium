
        package AcceptanceTests;

        import ScreenFactories.MoviesScreenFactory;
        import ScreenObjects.LoginScreen;
        import ScreenObjects.MoviesScreen;
        import Utils.BaseTest;
        import org.openqa.selenium.By;
        import org.testng.Assert;
        import org.testng.annotations.AfterMethod;
        import org.testng.annotations.BeforeMethod;
        import org.testng.annotations.DataProvider;
        import org.testng.annotations.Test;

public class UserProfile extends BaseTest {

    public MoviesScreenFactory moviesScreenFactory = new MoviesScreenFactory();
    @BeforeMethod
    private void successfulGoogleLoginWithValidCredential() {
        LoginScreen loginScreen = new LoginScreen();

        loginScreen.clickLoginGoogleBtn();
        loginScreen.clickFirstAccountRadioBtn();
        loginScreen.clickOkBtn();

        Boolean result = elementIsNotPresent(By.id("com.android.packageinstaller:id/permission_allow_button"));
        if (!result) {
            //driver.switchTo().alert().accept(); DOES NOT WORK!!!!
            driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
        }
        Assert.assertTrue(driver.findElementById("btnHamburger").isDisplayed());
    }

    @AfterMethod
    public void afterEachTest() {
        System.out.println("Resetting App");
        driver.resetApp();
    }

    @DataProvider(name = "validNamesProvider")
    public Object[][] getValidNames() {
        return new Object[][] {
                {"Hruysha", "Filya"},
                {"Karkusha Polikarpovna", "Polikarp Himself"},
        };
    }

    @DataProvider(name = "longNamesProvider")
    public Object[][] getLongNames() {
        return new Object[][] {
                {"Randolph Sherman Thomas Uncas Victor William Xerxes"},
                {"YancyWolfeschlegelsteinhausenbergerdorffSeniorwholivedinPhiladelphia"}
        };
    }

    @Test(dataProvider = "validNamesProvider")
    public void changeValidName(String[] validNames) {
        MoviesScreen moviesScreen = new MoviesScreen();
        String newName = validNames[0];
        MoviesScreen secondMoviesScreen = moviesScreen.clickProfileButton()
                .clickEditName().removeName().enterName(newName)
                .clickOkButton().clickMoviesButton();
        String currentName = secondMoviesScreen.clickProfileButton().getCurrentName();
        System.out.println(currentName);
        Assert.assertEquals(currentName, newName);
    }

    @Test(dataProvider = "longNamesProvider")
    public void nameMorethan50Char(String[] longNames) {
        MoviesScreen moviesScreen = new MoviesScreen();
        String newName = longNames[0];
        MoviesScreen secondMoviesScreen = moviesScreen.clickProfileButton()
                .clickEditName().removeName().enterName(newName)
                .clickOkButton().clickMoviesButton();
        String currentName = secondMoviesScreen.clickProfileButton().getCurrentName();
        System.out.println(currentName);
        Assert.assertNotEquals(currentName, newName);
    }






}
