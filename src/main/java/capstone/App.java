package capstone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        scene = new Scene(loadFXML("Landing"));
        
        stage.setScene(scene);
        stage.centerOnScreen();
        //stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        ///scene.setRoot(loadFXML(fxml));
      setNewScene(fxml);
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static void setNewScene(String fxml) throws IOException{
    
        scene = new Scene(loadFXML(fxml));
        scene.getStylesheets().add(App.class.getResource("Stylesheet.css").toExternalForm());
        mainStage.setScene(scene);
       
        mainStage.centerOnScreen();
        
        mainStage.show();
        //stage.setScene(scene);
    
    
    }
    

}