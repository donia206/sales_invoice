import controller.HomePageController;
import view.HomePageGUI;

public class App {
    public static void main (String[] args){
        HomePageGUI homePageGUI = new HomePageGUI();
        homePageGUI.setVisible(true);
        HomePageController controller = new HomePageController(homePageGUI);
    }
}