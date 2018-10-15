import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class controller implements Initializable{
    @FXML
    private TextField textTemperature;
    @FXML
    private TextField textLambda;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnOpen;
    private Button btnSave;
    @FXML
    private AnchorPane panel;
    @FXML
    private Label labelNumInstance;
    @FXML
    private Label labelNbrVehicule;
    @FXML
    private Label labelCapVehicule;
    @FXML
    private Label labelNbrClients;
    @FXML
    private Label labelNbrDepot;
    @FXML
    private Label labelSolDistance;
    @FXML
    private Label labelSolVehicule;
    @FXML
    private Label labelSolDistance1;
    @FXML
    private Label labelSolVehicule1;
    @FXML
    private Label labelDSP;
    @FXML
    private Label labelNBRv;
    @FXML
    private Button btnDetails;


    protected String numInstance;
    protected int numVehicule;
    protected int capVehicule;
    protected int [][] clients;
    protected double [][] distances;
    protected VRPInstance instance;
    protected double temperature=500;
    protected double lambda=0.9997;
    protected String details="";



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnDetails.setOnAction(e->{
            VBox root = new VBox();
            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setPrefHeight(300);
            textArea.setPrefWidth(800);
            textArea.setText(details);
            Stage stage = new Stage();
            stage.setTitle("Solution Détails");
            Scene scene =new Scene(root, 800, 300);
            String css = getClass().getResource("Style.css").toExternalForm();

            scene.getStylesheets().addAll(css);
            stage.setScene(scene);
            stage.show();
            root.getChildren().add(textArea);

        });

        btnStart.setOnAction(event -> {
            labelSolDistance.setText("XXX");
            labelSolVehicule.setText("XX");
            btnDetails.setVisible(false);
            if(numInstance==null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                String s = "you have not select any Instance !!";
                alert.setContentText(s);
                alert.showAndWait();
            }else {
                if (!textTemperature.getText().isEmpty()) {
                    temperature = Double.valueOf(textTemperature.getText());
                }
                if (!textLambda.getText().isEmpty()) {
                    lambda = Double.valueOf(textLambda.getText());
                }

                Thread th = new Thread(() -> {

                    instance = new VRPInstance(numVehicule, capVehicule, clients, distances);
                    ProblemSolver p = new ProblemSolver(instance, temperature, lambda);
                    p.recuitSimuler();
                    //display(instance.creeInstanceInit(),0,0);


                });
                th.setDaemon(true);
                th.start();
            }
        });

        btnOpen.setOnAction(event -> {

            File file1;
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extTXTFilter = new FileChooser.ExtensionFilter("TXT Files", "*.txt");
            fileChooser.setTitle("Open Instance");



            fileChooser.getExtensionFilters().add(extTXTFilter);

            file1 = fileChooser.showOpenDialog(btnOpen.getScene().getWindow());
            if (file1 == null) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                String s = "you have not select any File !!";
                alert.setContentText(s);
                alert.showAndWait();
            } else {


                    openFile(file1.getAbsolutePath());



            }


        });

    }

    private void openFile(String path) {
        File f = new File(path);
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            int numLines = (int) br.lines().count();
            System.out.println(numLines);
            br = new BufferedReader(new FileReader(f));
            numInstance = br.readLine().replaceAll("\\s+","");
            System.out.println(numInstance);
            for (int i = 0; i < 3; i++) {
                br.readLine();
            }
            line = br.readLine();
            String tab[]=line.replaceFirst("\\s+","").split("\\s+");
            System.out.println(tab.length);

            if(tab.length!=2){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                String s = "Instance Invalude !!" ;
                alert.setContentText(s);
                alert.showAndWait();


            }else{
                numVehicule=Integer.parseInt(tab[0].replaceAll("\\s+",""));
                capVehicule=Integer.parseInt(tab[1].replaceAll("\\s+",""));
                System.out.println(numVehicule);
                System.out.println(capVehicule);

                for (int i = 0; i < 4; i++) {
                    br.readLine();
                }


                clients=new int[numLines-9][3];
                int i=0;
                while ((line = br.readLine()) != null) {

                    tab=line.replaceFirst("\\s+","").split("\\s+");

                    if(tab.length!=7)continue;
                    clients[i][0]=Integer.parseInt(tab[1].replaceAll("\\s+",""));
                    clients[i][1]=Integer.parseInt(tab[2].replaceAll("\\s+",""));
                    clients[i][2]=Integer.parseInt(tab[3].replaceAll("\\s+",""));
                    i++;


                }
                for(i=0;i<clients.length;i++){
                    System.out.print("[");
                    for(int j=0;j<clients[i].length;j++){
                        System.out.print(" "+clients[i][j]+" ");
                    }
                    System.out.println("]");
                }

                calculeDistances();
                labelNumInstance.setText(String.valueOf(numInstance));
                labelCapVehicule.setText(String.valueOf(capVehicule));
                labelNbrVehicule.setText(String.valueOf(numVehicule));
                labelNbrClients.setText(String.valueOf(clients.length-1));
                labelNbrDepot.setText("1");





            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // String lines = "";


    }

    private void calculeDistances(){
        distances = new double[clients.length][clients.length];
        for(int i=0;i<distances.length;i++){
            for(int j=0;j<distances.length;j++){
                if(j<=i)distances[i][j]=0.;
                else distances[i][j]=Eq(clients[i][0],clients[i][1],clients[j][0],clients[j][1]);

            }
        }
        for (double[] distance : distances) {
            System.out.print("[");
            for (int j = 0; j < distances.length; j++) {
                System.out.print(" " + distance[j] + " ");

            }
            System.out.println("]");
        }

    }

    public void display(ArrayList<ArrayList<Integer>> ins,int solPtimale,double distance){
    Platform.runLater(() ->{

            paint(ins,solPtimale,distance);

    });



}

    private void paint(ArrayList<ArrayList<Integer>> ins,int solOptimale,double distance){


        if(solOptimale==1){
            labelSolDistance.setText(String.valueOf(round(distance,4)));
            labelSolVehicule.setText(String.valueOf(ins.size()));
            details=toString(ins);
            btnDetails.setVisible(true);

        }


        panel.getChildren().removeAll(panel.getChildren());
        labelSolDistance1.setText(String.valueOf(round(distance,4)));
        labelSolVehicule1.setText(String.valueOf(ins.size()));
        panel.getChildren().addAll(labelDSP,labelSolDistance1,labelNBRv,labelSolVehicule1);
       // System.out.println("debut");

        Rectangle rq =  new Rectangle(clients[0][0]*6, clients[0][1]*6, 10,10);
        rq.setStroke(Color.BLUE);
        rq.setFill(Color.BLUE);
        Label label = new Label("depot");
        label.setLayoutX((clients[0][0]*6)-3);
        label.setLayoutY((clients[0][1]*6)+2);
        label.setId("labels");
        panel.getChildren().add(rq);
        panel.getChildren().add(label);
        btnSave=new Button("Save As Image");
        btnSave.setPrefHeight(47);
        btnSave.setPrefWidth(159);
        btnSave.setLayoutX(5);
        btnSave.setLayoutY(5);
        btnSave.setId("btnSave");
        btnSave.setOnAction(event -> {
            btnSave.setVisible(false);
            captureAndSaveDisplay();
        });
        panel.getChildren().add(btnSave);

        for(int i=1;i<clients.length;i++){
            rq =  new Rectangle(clients[i][0]*6, clients[i][1]*6, 5,5);
            label = new Label(String.valueOf(i));
            label.setLayoutX((clients[i][0]*6)-3);
            label.setLayoutY((clients[i][1]*6)+2);
            label.setId("labels");


            panel.getChildren().add(rq);
            panel.getChildren().add(label);
        }

        for (ArrayList<Integer> in : ins) {
            Random rand = new Random();
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();
            Color color = new Color(r, g, b, 1);
            Path path = new Path();
            MoveTo moveTo = new MoveTo(clients[0][0] * 6, clients[0][1] * 6);

            path.getElements().add(moveTo);
            for (int j = 1; j < in.size(); j++) {
                LineTo line1 = new LineTo(clients[in.get(j)][0] * 6, clients[in.get(j)][1] * 6);
                path.getElements().add(line1);

            }
            LineTo lineTo = new LineTo(clients[0][0] * 6, clients[0][1] * 6);

            path.getElements().add(lineTo);
            path.setStroke(color);
            path.minHeight(15);


            panel.getChildren().add(path);


        }
        //System.out.println("end");
        //captureAndSaveDisplay();

    }

    private double Eq(int x1,int y1,int x2,int y2){
        return round(Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2)),4);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void captureAndSaveDisplay(){
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
        //Prompt user to select a file
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try {
                //Pad the capture area
                WritableImage writableImage = new WritableImage((int)panel.getWidth(),
                        (int)panel.getHeight());
                panel.snapshot(new SnapshotParameters(), writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) { ex.printStackTrace(); }
        }
    }

    public String toString(ArrayList<ArrayList<Integer>>sol ){

    VRPInstance inc = new VRPInstance(numVehicule,capVehicule,clients,distances);
    String s1="\n\nProblem Set: "+numInstance;
    s1+="\nVehicle capacity: "+capVehicule;
    s1+="\nRoutes: "+sol.size();
    s1+="\nTotal travel distance: "+round(inc.calculerSolutionDistance(sol),4);
    String s= "";
    for(int i=0;i<sol.size();i++) {
        s+="\nRoute: "+(i+1)+", chemin: "+sol.get(i);
        s1 +="\nRoute: "+(i+1)+", len: "+(sol.get(i).size()-1)+", distance: "+round(inc.calculerRouteDistance(sol.get(i)),4)+", max cap: "+inc.calulerNbrDemandes(sol.get(i));
    }
    s+=s1;

    System.out.println(s);

        return s;
}


}
