import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.Set;

public class TagFrame extends JFrame {



        JPanel mainPnl;
        JPanel displayPnl;
        JPanel controlPnl;

        JTextArea displayTA;
        JScrollPane scroller;


        JButton quitBtn;

        Set<String> stopWords;
        Map<String, Integer> map;




        public TagFrame()
        {

            stopWords = TagExtractor.stopWords();
            map = TagExtractor.filterfile(stopWords);

            mainPnl = new JPanel();
            mainPnl.setLayout(new BorderLayout());

            createDisplayPanel();
            mainPnl.add(displayPnl, BorderLayout.CENTER);

            createControlPanel();
            mainPnl.add(controlPnl, BorderLayout.SOUTH);

            add(mainPnl);
            setSize(810, 700);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }





        private void createDisplayPanel()
        {

            displayPnl = new JPanel();
            displayTA = new JTextArea(10, 35);
            displayTA.setFont(new Font("Georgia", Font.PLAIN, 14));
            displayTA.setEditable(false);

            map.forEach((word, freq) -> displayTA.append(word + ": " + freq + "\n"));




            scroller = new JScrollPane(displayTA);
            displayPnl.add(scroller);
        }


        private void createControlPanel()
        {
            controlPnl = new JPanel();
            controlPnl.setLayout(new GridLayout(1, 1));


            quitBtn = new JButton("Quit!");
            quitBtn.setFont(new Font("Verdana", Font.PLAIN, 20));
            quitBtn.addActionListener((ActionEvent ae) -> {
                        int choice = JOptionPane.showConfirmDialog(null, "Would you like to Save the Map?", null, JOptionPane.YES_NO_OPTION);

                        if (choice == JOptionPane.YES_OPTION) {
                            TagExtractor.savefile(map);
                        }

                        if (choice == JOptionPane.NO_OPTION) {
                            return;
                        }

                        System.exit(0);
                    }
            );


            controlPnl.add(quitBtn);

        }





}
