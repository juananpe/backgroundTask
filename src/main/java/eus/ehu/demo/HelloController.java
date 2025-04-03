package eus.ehu.demo;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class HelloController {
    @FXML
    private Label welcomeText;
    
    @FXML
    private ProgressBar progressBar;
    
    @FXML
    private Button startButton;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    
    @FXML
    protected void onStartTaskClick() {
        // Disable button while task is running
        startButton.setDisable(true);
        welcomeText.setText("Task started...");
        
        // Create background task
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Simulate work in background thread
                for (int i = 0; i <= 100; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    
                    // Update progress
                    updateProgress(i, 100);
                    
                    final int progress = i;
                    // Update UI using Platform.runLater (thread-safe)
                    Platform.runLater(() -> 
                        welcomeText.setText("Processing: " + progress + "%")
                    );
                    
                    // Simulate work
                    Thread.sleep(50);
                }
                
                return null;
            }
        };
        
        // Set up task completion handler
        task.setOnSucceeded(event -> {
            welcomeText.setText("Task completed successfully!");
            startButton.setDisable(false);
        });
        
        // Set up error handler
        task.setOnFailed(event -> {
            welcomeText.setText("Task failed: " + task.getException().getMessage());
            startButton.setDisable(false);
        });
        
        // Bind progress property to progress bar
        progressBar.progressProperty().bind(task.progressProperty());
        
        // Run the task in a background thread
        new Thread(task).start();
    }
}