import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE;


public class TagExtractor {


    public static Set<String> stopWords() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose the stop words file");
        int result = fileChooser.showOpenDialog(null);
        String stopFile = null;

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            stopFile = selectedFile.getAbsolutePath();
        }


        if (stopFile == null) {
            return null;
        }

        Set<String> stopWords = new TreeSet<>();

        try (Stream<String> lines = Files.lines(Paths.get(stopFile))) {
            lines
                    .forEach(stopWords::add);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stopWords;

    }

    public static Map<String, Integer> filterfile(Set<String> stopWords) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose the input text file");
        int result = fileChooser.showOpenDialog(null);
        String path = null;

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            path = selectedFile.getAbsolutePath();
        }


        if (path == null) {
            return null;
        }


        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {


            Map<String, Integer> Map = reader.lines()
                    .flatMap(line -> Arrays.stream(line.split(" ")))
                    .map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase())
                    .filter(word -> !word.isEmpty() && !stopWords.contains(word))
                    .collect(Collectors.toMap(
                            word -> word,
                            word -> 1,
                            Integer::sum));


            return Map;

        } catch (IOException e) {
        e.printStackTrace();
        return null;
        }


    }

    public static void savefile(Map<String, Integer> map) {

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\savedmap.txt");

        try {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!
            map.forEach((word, freq) -> {
                try {
                    writer.write(word + ": " + freq + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}


