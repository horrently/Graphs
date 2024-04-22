import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ChoiceBox<String> taskChoiceBox = new ChoiceBox<>();
        taskChoiceBox.getItems().addAll(
                "Задача 1",
                "Задача 2",
                "Задача 3",
                "Задача 4",
                "Задача 5",
                "Задача 6",
                "Задача 7",
                "Задача 8",
                "Задача 9",
                "Задача 10",
                "Задача 11",
                "Задача 12"
        );
        
        // Создание надписи с отступом
        Label titleLabel = new Label("Графы");
        titleLabel.setFont(Font.font("verdana", 23));
        titleLabel.setPadding(new Insets(-200, 0, 0, 0));

        Button openTaskButton = new Button("Открыть");
        openTaskButton.setOnAction(event -> {
            String selectedTask = taskChoiceBox.getSelectionModel().getSelectedItem();
            openTaskInterface(selectedTask);
        });

        HBox hbox = new HBox(10, taskChoiceBox, openTaskButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20));

        VBox vbox = new VBox(titleLabel, hbox);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 500, 500);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Выбор задачи");
        primaryStage.show();
    }

    private void openTaskInterface(String task) {
        if (task.equals("Задача 1")) {
            // вызов интерфейса для задачи 1
            aaaaaaa task1Interface = new aaaaaaa();
            task1Interface.start(new Stage());
        }
        else if (task.equals("Задача 2")) {
            // вызов интерфейса для задачи 2
            bbbbbbb task2Interface = new bbbbbbb();
            task2Interface.start(new Stage());
        }
        else if (task.equals("Задача 3")) {
            // вызов интерфейса для задачи 3
            task3 task3Interface = new task3();
            task3Interface.start(new Stage());
        }
        else if (task.equals("Задача 4")) {
            // вызов интерфейса для задачи 4
            task4 task4Interface = new task4();
            task4Interface.start(new Stage());
        }
        else if (task.equals("Задача 5")) {
            // вызов интерфейса для задачи 5
            task5GUI task5Interface = new task5GUI();
            task5Interface.start(new Stage());
        }
        else if (task.equals("Задача 6")) {
            // вызов интерфейса для задачи 6
            task6GUI task6Interface = new task6GUI();
            task6Interface.start(new Stage());
        }
        else if (task.equals("Задача 7")) {
            // вызов интерфейса для задачи 7
            task7GUI task7Interface = new task7GUI();
            task7Interface.start(new Stage());
        }
//        else if (task.equals("Задача 8")) {
//            // вызов интерфейса для задачи 8
//            task8 task8Interface = new task8();
//            task8Interface.start(new Stage());
//        }
        else if (task.equals("Задача 9")) {
            // вызов интерфейса для задачи 9
            task9GUI task9Interface = new task9GUI();
            task9Interface.start(new Stage());
        }
//        else if (task.equals("Задача 10")) {
//            // вызов интерфейса для задачи 10
//            task10GUI task10Interface = new task10GUI();
//            task10Interface.start(new Stage());
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}