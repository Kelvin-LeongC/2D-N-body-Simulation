import controller_classes.Simulation;
import entities.Star;
import gateway_classes.DataLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SimulationSystem {
    private Simulation simulation;

    public SimulationSystem(){
        new ImportFileUI();
    }

    private void simulationRun(Star[] starsArray){
        this.simulation = new Simulation(starsArray);
        this.simulation.init();
    }

    private void obtainData(String filePath){
        DataLoader loader = new DataLoader();
        try{
            simulationRun(loader.loadData(filePath));
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }


    public class ImportFileUI {
        public ImportFileUI(){
            JFrame importFrame = new JFrame();

            importFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            importFrame.setResizable(false);

            importFrame.setTitle("2D-Simulation");
            importFrame.setPreferredSize(new Dimension(200, 100));

            JButton button = new JButton("Select File");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                    int returnValue = fileChooser.showOpenDialog(null);
                    if(returnValue == JFileChooser.APPROVE_OPTION){
                        File file = fileChooser.getSelectedFile();
                        String filePath = file.getPath();

                        System.out.println("Opening file: " + filePath);
                        obtainData(filePath);

                        importFrame.setVisible(false);
                    }
                }
            });

            importFrame.add(button);
            importFrame.pack();
            importFrame.setVisible(true);
        }
    }
}
