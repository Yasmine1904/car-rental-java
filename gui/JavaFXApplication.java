package gui;

import Domain.Inchiriere;
import Domain.Masina;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import Repository.DuplicateEntityException;
import Repository.IRepository;
import Repository.InchirieriDbRepository;
import Repository.MasiniDbRepository;
import Service.InchiriereService;
import Service.MasinaService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaFXApplication extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {

        IRepository<Masina> repositoryMasina = new MasiniDbRepository();
        MasinaService masinaService = new MasinaService(repositoryMasina);

        IRepository<Inchiriere> repositoryInchiriere = new InchirieriDbRepository();
        InchiriereService inchiriereService = new InchiriereService(repositoryInchiriere);

        VBox root = new VBox();
        Scene scene = new Scene(root, 600, 400);



        ObservableList<Masina> masini = FXCollections.observableArrayList(masinaService.getAll());
        ListView<Masina> listViewMasini = new ListView<Masina>(masini);
        root.getChildren().add(listViewMasini);

        ObservableList<Inchiriere> inchirieri = FXCollections.observableArrayList(inchiriereService.getAll());
        ListView<Inchiriere> listViewInchirieri = new ListView<Inchiriere>(inchirieri);
        root.getChildren().add(listViewInchirieri);


        GridPane gridPane = new GridPane();
        Label labelId = new Label("Id: ");
        Label labelMarca = new Label("Marca: ");
        Label labelModel = new Label("Model: ");
        TextField textFieldId = new TextField();
        TextField textFieldMarca = new TextField();
        TextField textFieldModel = new TextField();
        Button buttonAdauga = new Button("Adaugă");
        Button buttonModifica = new Button("Modifică");
        Button buttonSterge = new Button("Șterge");
        gridPane.add(labelId, 0, 0);
        gridPane.add(labelMarca, 0, 1);
        gridPane.add(labelModel, 0, 2);
        gridPane.add(textFieldId, 1, 0);
        gridPane.add(textFieldMarca, 1, 1);
        gridPane.add(textFieldModel, 1, 2);
        gridPane.add(buttonAdauga, 0, 3);
        gridPane.add(buttonModifica, 1, 3);
        gridPane.add(buttonSterge, 2, 3);
        root.getChildren().add(gridPane);

        buttonAdauga.setOnAction(event -> {
            try
            {
                IRepository<Masina> dbrepositoryMasina = new MasiniDbRepository();

                ((MasiniDbRepository) dbrepositoryMasina).connectToDb();

                repositoryMasina.setAll(dbrepositoryMasina.getAll());

                masinaService.add(Integer.parseInt(textFieldId.getText()), textFieldMarca.getText(), textFieldModel.getText());
                masini.setAll(masinaService.getAll());
            } catch (Exception e)
            {
                Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                errorPopUp.setTitle("ERROR");
                errorPopUp.setContentText(e.getMessage());
                errorPopUp.show();
            }
        });

        buttonModifica.setOnAction(event -> {
            try {
                IRepository<Masina> dbrepositoryMasina = new MasiniDbRepository();

                ((MasiniDbRepository) dbrepositoryMasina).connectToDb();

                repositoryMasina.setAll(dbrepositoryMasina.getAll());

                masinaService.modify(Integer.parseInt(textFieldId.getText()), new Masina(Integer.parseInt(textFieldId.getText()), textFieldMarca.getText(), textFieldModel.getText()));
                masini.setAll(masinaService.getAll());
            } catch (Exception e)
            {
                Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                errorPopUp.setTitle("ERROR");
                errorPopUp.setContentText(e.getMessage());
                errorPopUp.show();
            }
        });


        buttonSterge.setOnAction(event -> {
            try {
                IRepository<Masina> dbrepositoryMasina = new MasiniDbRepository();

                ((MasiniDbRepository) dbrepositoryMasina).connectToDb();

                repositoryMasina.setAll(dbrepositoryMasina.getAll());

                masinaService.remove(Integer.parseInt(textFieldId.getText()));
                masini.setAll(masinaService.getAll());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }catch (Exception e)
            {
                Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                errorPopUp.setTitle("ERROR");
                errorPopUp.setContentText(e.getMessage());
                errorPopUp.show();
            }
        });

        GridPane gridPaneInchirieri = new GridPane();
        Label labelIdInchiriere = new Label("Id inchiriere: ");
        Label labelIdMasina = new Label("Id masina: ");
        Label labelDataInceput = new Label("Data Inceput: ");
        Label labelDataSfarsit = new Label("Data Sfarsit: ");
        TextField textFieldIdInchiriere = new TextField();
        TextField textFieldIdMasina = new TextField();
        TextField textFieldDataInceput = new TextField();
        TextField textFieldDataSfarsit = new TextField();
        Button buttonAdaugaInchiriere = new Button("Adaugă");
        Button buttonModificaInchiriere = new Button("Modifică");
        Button buttonStergeInchiriere = new Button("Șterge");
        gridPaneInchirieri.add(labelIdInchiriere, 0, 0);
        gridPaneInchirieri.add(labelIdMasina, 0, 1);
        gridPaneInchirieri.add(labelDataInceput, 0, 2);
        gridPaneInchirieri.add(labelDataSfarsit, 0, 3);
        gridPaneInchirieri.add(textFieldIdInchiriere, 1, 0);
        gridPaneInchirieri.add(textFieldIdMasina, 1, 1);
        gridPaneInchirieri.add(textFieldDataInceput, 1, 2);
        gridPaneInchirieri.add(textFieldDataSfarsit, 1, 3);
        gridPaneInchirieri.add(buttonAdaugaInchiriere, 0, 6);
        gridPaneInchirieri.add(buttonModificaInchiriere, 1, 6);
        gridPaneInchirieri.add(buttonStergeInchiriere, 2, 6);
        root.getChildren().add(gridPaneInchirieri);

        buttonAdaugaInchiriere.setOnAction(event -> {
            try {

                IRepository<Masina> dbrepositoryMasina = new MasiniDbRepository();
                IRepository<Inchiriere> dbrepositoryInchiriere = new InchirieriDbRepository();

                ((MasiniDbRepository) dbrepositoryMasina).connectToDb();
                ((InchirieriDbRepository) dbrepositoryInchiriere).connectToDb();

                repositoryMasina.setAll(dbrepositoryMasina.getAll());
                repositoryInchiriere.setAll(dbrepositoryInchiriere.getAll());

                Masina masina = masinaService.readMasina(Integer.parseInt(textFieldIdMasina.getText()));

                inchiriereService.add(Integer.parseInt(textFieldIdInchiriere.getText()), new Masina(Integer.parseInt(textFieldIdMasina.getText()), masina.getMarca(), masina.getModel()), LocalDate.parse(textFieldDataInceput.getText()), LocalDate.parse(textFieldDataSfarsit.getText()));
                inchirieri.setAll(inchiriereService.getAll());


            } catch (DuplicateEntityException | IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }catch (Exception e)
            {
                Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                errorPopUp.setTitle("ERROR");
                errorPopUp.setContentText(e.getMessage());
                errorPopUp.show();
            }
        });

        buttonModificaInchiriere.setOnAction(event -> {
            try {
                IRepository<Masina> dbrepositoryMasina = new MasiniDbRepository();
                IRepository<Inchiriere> dbrepositoryInchiriere = new InchirieriDbRepository();


                ((InchirieriDbRepository) dbrepositoryInchiriere).connectToDb();

                ((MasiniDbRepository) dbrepositoryMasina).connectToDb();

                repositoryMasina.setAll(dbrepositoryMasina.getAll());
                repositoryInchiriere.setAll(dbrepositoryInchiriere.getAll());

                Masina masina = masinaService.readMasina(Integer.parseInt(textFieldIdMasina.getText()));

                inchiriereService.modify(Integer.parseInt(textFieldIdInchiriere.getText()), new Inchiriere(Integer.parseInt(textFieldIdInchiriere.getText()), new Masina(Integer.parseInt(textFieldIdMasina.getText()), masina.getMarca(), masina.getModel()), LocalDate.parse(textFieldDataInceput.getText()), LocalDate.parse(textFieldDataSfarsit.getText())));
                inchirieri.setAll(inchiriereService.getAll());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException | DuplicateEntityException e) {
                throw new RuntimeException(e);
            }catch (Exception e)
            {
                Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                errorPopUp.setTitle("ERROR");
                errorPopUp.setContentText(e.getMessage());
                errorPopUp.show();
            }
        });

        buttonStergeInchiriere.setOnAction(event ->
        {
            try {
                InchirieriDbRepository dbrepositoryInchiriere = new InchirieriDbRepository();

                dbrepositoryInchiriere.connectToDb();

                repositoryInchiriere.setAll(dbrepositoryInchiriere.getAll());

                inchiriereService.remove(Integer.parseInt(textFieldIdInchiriere.getText()));
                inchirieri.setAll(inchiriereService.getAll());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            }catch (Exception e)
            {
                Alert errorPopUp = new Alert(Alert.AlertType.ERROR);
                errorPopUp.setTitle("ERROR");
                errorPopUp.setContentText(e.getMessage());
                errorPopUp.show();
            }

        });


        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);


        Button buttonAfisareMasiniCuNrInchirieri = new Button("Afisare masini cu nr inchirieri");
        buttonAfisareMasiniCuNrInchirieri.setOnAction(event -> {

            StringBuilder resultText = new StringBuilder();


            InchirieriDbRepository dbrepositoryInchiriere = new InchirieriDbRepository();

            dbrepositoryInchiriere.connectToDb();

            repositoryInchiriere.setAll(dbrepositoryInchiriere.getAll());

            Map<Masina, Long> masiniCuNrInchirieri = inchirieri.stream()
                    .collect(Collectors.groupingBy(Inchiriere::getMasina, Collectors.counting()));
            masiniCuNrInchirieri.entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey().getMarca() + ", " + entry.getKey().getModel(),
                            Map.Entry::getValue, Long::sum))
                    .entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(entry -> {
                        String masina = entry.getKey();
                        Long numarInchirieri = entry.getValue();
                        resultText.append(masina).append(" - Numar total de inchirieri: ").append(numarInchirieri).append("\n");
                    });

            resultArea.setText(resultText.toString());
        });


        Button buttonNumarulDeInchirieriEfectuateInFiecareLuna = new Button("Numarul de inchirieri efectuate in fiecare luna");

        buttonNumarulDeInchirieriEfectuateInFiecareLuna.setOnAction(event -> {
            StringBuilder resultText = new StringBuilder();
            InchirieriDbRepository dbrepositoryInchiriere = new InchirieriDbRepository();

            dbrepositoryInchiriere.connectToDb();

            repositoryInchiriere.setAll(dbrepositoryInchiriere.getAll());

            Map<Integer, Long> numarulDeInchirieriEfectuateInFiecareLuna = inchirieri.stream()
                    .collect(Collectors.groupingBy(inchiriere -> inchiriere.getDataInceput().getMonthValue(), Collectors.counting()));
            numarulDeInchirieriEfectuateInFiecareLuna.entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey(),
                            Map.Entry::getValue, Long::sum)) // combinarea valorilor duplicate
                    .entrySet().stream()
                    .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                    .forEach(entry -> {
                        Integer luna = entry.getKey();
                        Long numarInchirieri = entry.getValue();
                        resultText.append("Luna ").append(luna).append(" - Numar total de inchirieri: ").append(numarInchirieri).append("\n");
                    });

            resultArea.setText(resultText.toString());
        });

        Button buttonNumarulDeZileInCareFiecareMasinaAInchiriata = new Button("Numarul de zile in care fiecare masina a fost inchiriata");

        buttonNumarulDeZileInCareFiecareMasinaAInchiriata.setOnAction(event ->
        {
            InchirieriDbRepository dbrepositoryInchiriere = new InchirieriDbRepository();
            dbrepositoryInchiriere.connectToDb();
            repositoryInchiriere.setAll(dbrepositoryInchiriere.getAll());


            Map<Masina, Long> masiniCuTimpInchiriat = inchirieri.stream()
                    .collect(Collectors.groupingBy(
                            Inchiriere::getMasina,
                            Collectors.summingLong(inchiriere ->
                                    inchiriere.getDataSfarsit().toEpochDay() - inchiriere.getDataInceput().toEpochDay())
                    ));

            StringBuilder resultText = new StringBuilder();
            masiniCuTimpInchiriat.entrySet().stream()
                    .collect(Collectors.toMap(entry -> entry.getKey().getMarca() + ", " + entry.getKey().getModel(),
                            Map.Entry::getValue, Long::sum)) // combinarea valorilor duplicate
                    .entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(entry -> {
                        String masina = entry.getKey();
                        Long timpInchiriat = entry.getValue();
                        resultText.append(masina).append(" - Timp total de inchiriere: ").append(timpInchiriat).append(" zile\n");
                    });

            resultArea.setText(resultText.toString());
        });

        GridPane gridPaneMasini = new GridPane();
        gridPaneMasini.add(buttonAfisareMasiniCuNrInchirieri, 1, 0);
        gridPaneMasini.add(buttonNumarulDeInchirieriEfectuateInFiecareLuna, 1, 1);
        gridPaneMasini.add(buttonNumarulDeZileInCareFiecareMasinaAInchiriata, 1, 2);
        root.getChildren().add(gridPaneMasini);
        root.getChildren().addAll(resultArea);


        stage.setTitle("Administrare Închirieri Mașini");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
