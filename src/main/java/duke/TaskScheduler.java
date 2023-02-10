package duke;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


//Bealdung
public class TaskScheduler extends TaskList<Recur> {

    private PriorityBlockingQueue<Recur> priorityQueue;
    private List<Timeline> recurResponse;
    private VBox dialogContainer;
    private Image dukeImage;

    TaskScheduler (int poolSize, PriorityBlockingQueue<Recur> priorityQueue) {
        ExecutorService priorityJobPoolExecutor = Executors.newFixedThreadPool(poolSize);
        this.priorityQueue = priorityQueue;
        //priorityQueue = new PriorityBlockingQueue<>(100, Comparator.comparing(Recur::getMockRemainingTime));
        ExecutorService priorityJobScheduler = Executors.newSingleThreadExecutor();
        priorityJobScheduler.execute(() -> {
            while (true) {
                try {
                    Recur nextScheduledEvent = priorityQueue.take();
                    priorityJobPoolExecutor.execute(nextScheduledEvent);
                    System.out.println(nextScheduledEvent.getDescription());
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
    }

    TaskScheduler(List<Timeline> recurResponse,VBox dialogContainer, Image dukeImage) {
        this.recurResponse = recurResponse;
        this.dialogContainer = dialogContainer;
        this.dukeImage = dukeImage;
    }

    void recurDialogContainer(String input) {
        if (input.contains("delete")) {
            int recurPos = Integer.parseInt(input.substring(input.length() - 1));
            Timeline removeRecur = recurResponse.remove(recurPos);
            removeRecur.stop();
        }
        Timeline timeline =
                new Timeline(new KeyFrame(Duration.millis(500), e -> dialogContainer.getChildren().addAll(
                        DialogBox.getDukeDialog(input, dukeImage)
                )));
        timeline.setCycleCount(Animation.INDEFINITE); // loop forever
        timeline.play();

        /*
        if (input.contains("recur")) {
            Timeline timeline =
                    new Timeline(new KeyFrame(Duration.millis(500), e -> dialogContainer.getChildren().addAll(
                            DialogBox.getDukeDialog("recur reminder!", dukeImage)
                    )));
            timeline.setCycleCount(Animation.INDEFINITE); // loop forever
            timeline.play();
        }
        */
    }


    /*
    TaskScheduler () {
        priorityQueue = new PriorityBlockingQueue<>(100, Comparator.comparing(Recur::getMockRemainingTime));
        this(20, priorityQueue);
    }
    */

    //Change to accept Recur event later
    void addRecurringEvent(Recur recurEvent) {
        for (int i=0; i<10;i++) {
            priorityQueue.add(recurEvent);
        }
        /*
        priorityQueue.add(new Recur("First zoom meeting","Monday","Monday",1000));
        priorityQueue.add(new Recur("Second zoom meeting","Tuesday","Tuesday",2000));
        priorityQueue.add(new Recur("Third zoom meeting","Wednesday","Wednesday",3000));
        priorityQueue.add(new Recur("Fourth zoom meeting","Thursday","Thursday",4000));
        */
    }





}
