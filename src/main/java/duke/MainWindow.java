package duke;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 * @author Muhammad Reyaaz
 * @version %I% %G%
 * @see Task
 * @since 11
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Khabib.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Ronaldo.png"));

    /**
     * Initialize is ran at the start of the javafx lifecycle.
     * The greetings message is done at initialize so that once the user launches the chatbot, it will always be shown.
     */
    @FXML
    public void initialize() {

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        ByteArrayOutputStream storeString = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(storeString);
        PrintStream oldPrintStream = System.out;
        System.setOut(printStream);
        Ui ui = new Ui("Greetings");
        ui.showWelcome();
        System.out.flush();
        System.setOut(oldPrintStream);
        Label greeting = new Label(storeString.toString());
        dialogContainer.getChildren().addAll(greeting);
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();


//        userText.getStyleClass().add("right-label");
//        userText.setText(userInput.getText());
//
//        Label dukeText = new Label(getResponse(userInput.getText()));
//        dukeText.getStyleClass().add("left-label");
//
//        dialogContainer.getChildren().addAll(
//        DialogBox.getUserDialog(userText, new ImageView(user)),
//        DialogBox.getDukeDialog(dukeText, new ImageView(duke))
//        );
//        userInput.clear();


    }
}
