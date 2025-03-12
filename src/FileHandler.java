import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
public class FileHandler {
    private final String filepath;

    public FileHandler(String filepath) {
        this.filepath = filepath;
    }

    public void createDirectory() {
        File directory = new File(filepath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created successfully.");
            } else {
                System.out.println("Failed to create directory.");
            }
        } else {
            System.out.println("Directory already exists.");
        }
    }


    public void createFile(String filename) throws IOException {
        File file = new File(filepath + File.separator + filename);
        if (file.createNewFile()) {
            System.out.println("File created successfully.");
        } else {
            System.out.println("File already exists.");
        }
    }

    public boolean fileExists(String filename) {
        File file = new File(filepath + File.separator + filename);
        return file.exists();
    }


    public void writeFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filepath + File.separator + filename)) {
            writer.write(content);
            System.out.println("Content written successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    public String readFile(String filename) {
        StringBuffer content = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath + File.separator + filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return content.toString();
    }


    public void deleteFile(String filename) {
        File file = new File(filepath + File.separator + filename);
        if (file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete file.");
        }
    }

    public void deleteLineById(String filename, String id) {
        if (searchContent(filename,id)) {

            ArrayList<String> lines = (ArrayList<String>) readLines(filename);
            ArrayList<String> updatedLines = new ArrayList<>();

            boolean deleted = false;

            for (String line : lines) {

                if (line.contains(id)) {
                    deleted = true;
                    continue;
                }
                updatedLines.add(line);
            }

            if (!deleted) {
                System.out.println("Cannot find the specified ID in the file.");
                return;
            }

            try (FileWriter writer = new FileWriter(filepath + File.separator + filename)) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine + System.lineSeparator());
                }
                System.out.println( id + " deleted successfully.");
            } catch (IOException e) {
                System.out.println("Error deleting line in file: " + e.getMessage());
            }
        } else {
            System.out.println("Cannot find this ID in the file.");
        }
    }



    public void appendToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filepath + File.separator + filename, true)) {
            writer.write(content);
            System.out.println("Content appended successfully.");
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
        }
    }

    public boolean searchContent(String filename, String id) {
        List<String> lines = readLines(filename);
        for (String line : lines) {
            if (line.contains(id)) {
                return true;
            }
        }
        return false;
    }

    public void update(String filename, String id, String oldContent, String newContent) {
        if (searchContent(filename,id)) {
            ArrayList<String> lines = (ArrayList<String>) readLines(filename);
            ArrayList<String> updatedLines = new ArrayList<>();
            boolean updated = false;

            for (String line : lines) {
                if (line.contains( id)) {
                    if (line.contains(oldContent) && oldContent!="") {
                        line = line.replace(oldContent, newContent);
                        updated = true;
                    } else {
                        System.out.println("The content to replace was not found in the line: ");
                    }
                }
                updatedLines.add(line);
            }


            if (!updated) {
                System.out.println("Content not updated as old content was not found.");
                return;
            }

            try (FileWriter writer = new FileWriter(filepath + File.separator + filename)) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine + System.lineSeparator());
                }
                System.out.println("Content updated successfully.");
            } catch (IOException e) {
                System.out.println("Error updating content in file: " + e.getMessage());
            }

        } else {
            System.out.println("Cannot find this ID in the file.");
        }
    }

    public String readLineById(String filename, String id) {
        List<String> lines = readLines(filename);
        for (String line : lines) {
            if (line.contains(id)) {
                return line;
            }
        }
        return null;
    }

    public List<String> readLines(String filename) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(filepath + File.separator + filename));
        } catch (IOException e) {
            System.out.println("Error reading lines from file: " + e.getMessage());
        }
        return lines;
    }



    public List<String> listFilesInDirectory(String dirPath) {
        List<String> filesList = new ArrayList<>();
        File dir = new File(dirPath);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    filesList.add(file.getName());
                }
            }
        }
        return filesList;
    }


    public String readLastLine(String filename) {
        List<String> lines = new FileHandler("myDirectory").readLines("login.txt"); // Use existing method to read all lines
        if (lines.isEmpty()) {
            return null; // File is empty
        }
        return lines.get(lines.size() - 1); // Return the last line
    }
}
