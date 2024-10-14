package ku.cs.services.datahandle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataManagementSystem {
    private final String directoryName;
    private final String fileName;

    public DataManagementSystem(String directoryName, String fileName) throws IOException {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }


    private void checkFileIsExisted() throws IOException {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public DataFrame readTable() throws IOException {
        DataFrame dataFrame = null;

        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);


        FileInputStream fileInputStream;

        fileInputStream = new FileInputStream(file);

        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );
        BufferedReader buffer = new BufferedReader(inputStreamReader);

        String line;
        boolean foundHeader = false;


        while ((line = buffer.readLine()) != null) {
            if (line.isEmpty()) continue;
            List<String> data = parseCSVLine(line);

            if (!foundHeader) {
                ArrayList<String> headerData = new ArrayList<>(data);
                dataFrame = new DataFrame(headerData);
                foundHeader = true;
            } else {
                dataFrame.addRow(data.toArray(new String[0]));
            }
        }


        return dataFrame;
    }

    private List<String> parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {

                    sb.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(sb.toString());
        return result;
    }

    public void writeTable(DataFrame dataFrame) throws IOException {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);


        FileOutputStream fileOutputStream;

        fileOutputStream = new FileOutputStream(file);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                fileOutputStream,
                StandardCharsets.UTF_8
        );
        BufferedWriter buffer = new BufferedWriter(outputStreamWriter);

        try{
            ArrayList<String> header = dataFrame.getHeader();
            for (int i = 0; i < header.size(); i++) {
                buffer.append(escapeCSVField(header.get(i)));
                if (i != header.size() - 1) buffer.append(",");
            }
            buffer.append("\n");

            for (int row = 0; row < dataFrame.getData().size(); row++) {
                StringBuilder line = new StringBuilder();
                ArrayList<String> data = dataFrame.getRowList(row);
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j) != null) {
                        line.append(escapeCSVField(data.get(j)));
                    }
                    if (j != data.size() - 1) line.append(",");
                }
                buffer.append(line.toString());
                buffer.append("\n");
            }
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            buffer.flush();
            buffer.close();
        }
    }

    private String escapeCSVField(String field) {
        boolean needQuotes = field.contains(",") || field.contains("\n") || field.contains("\"");
        if (field.contains("\"")) {
            field = field.replace("\"", "\"\"");
        }
        if (needQuotes) {
            field = "\"" + field + "\"";
        }
        return field;
    }
}