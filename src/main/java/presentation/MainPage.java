package presentation;

import data.SnowMonitorEntryDAO;
import domain.SkiResorts;
import domain.SnowMonitorEntry;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class MainPage {
    private JPanel contentPane;
    private JComboBox<String> cbSkiResortSelection;
    private JTextField tfSnowDepth;
    private JTextField tfDate;
    private JTextArea taDataDisplay;
    private JButton btnAddEntry;
    private JButton btnRefresh;

    public MainPage() {
        setupSkiResortSelection();
        btnAddEntry.addActionListener(e -> {
            try {
                SnowMonitorEntry entry = getSnowMonitoryEntryFromInput();
                SnowMonitorEntryDAO.INSTANCE.insert(entry);
                displaySnowMonitorEntries();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnRefresh.addActionListener(e -> displaySnowMonitorEntries());
    }

    private SnowMonitorEntry getSnowMonitoryEntryFromInput() throws Exception {
        String skiResort = (String) cbSkiResortSelection.getSelectedItem();
        int snowDepth = Integer.parseInt(tfSnowDepth.getText());
        LocalDate date = LocalDate.parse(tfDate.getText());

        if(skiResort == null) throw new IllegalStateException("No ski resort selected");
        return new SnowMonitorEntry(-1, skiResort, snowDepth, date);
    }

    private void displaySnowMonitorEntries(){
        ArrayList<SnowMonitorEntry> entries = SnowMonitorEntryDAO.INSTANCE.getAllEntries();
        Collections.reverse(entries);
        
        StringBuilder sb = new StringBuilder();
        for(SnowMonitorEntry entry:entries){
            sb.append(snowMonitorEntryAsReadableString(entry)).append("\n");
        }

        taDataDisplay.setText(sb.toString());
    }

    private String snowMonitorEntryAsReadableString(SnowMonitorEntry entry){
        return String.format("%12s %5d %12s", entry.getSkiResort(), entry.getSnowDepth(), entry.getDate().toString());
    }

    private void setupSkiResortSelection(){
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) cbSkiResortSelection.getModel();
        model.removeAllElements();

        for(SkiResorts resort:SkiResorts.values()){
            model.addElement(resort.getValue());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SnowMonitor");
        frame.setContentPane(new MainPage().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
