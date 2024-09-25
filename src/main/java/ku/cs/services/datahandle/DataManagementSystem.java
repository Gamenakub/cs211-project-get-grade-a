package ku.cs.services.datahandle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataManagementSystem {
    private String directoryName;
    private String fileName;

    private static final String ROW_SPLIT = "\n";
    private static final String COL_SPLIT = ",";

    public DataManagementSystem(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    // ตรวจสอบว่ามีไฟล์ให้อ่านหรือไม่ ถ้าไม่มีให้สร้างไฟล์เปล่า
    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    public DataFrame readTable() {
        DataFrame dataFrame = null;

        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        // เตรียม object ที่ใช้ในการอ่านไฟล์
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );
        BufferedReader buffer = new BufferedReader(inputStreamReader);

        String line = "";
        Boolean foundHeader = false;
        ArrayList<String> header = new ArrayList<>();
        try {
            // ใช้ while loop เพื่ออ่านข้อมูลรอบละบรรทัด
            StringBuilder contentBuilder = new StringBuilder();
            while ((line = buffer.readLine()) != null) {
                contentBuilder.append(line);
                if (ROW_SPLIT.equals("\n")) {
                    contentBuilder.append("\n");
                }
            }

            String content = contentBuilder.toString();

            String[] rows = content.split(ROW_SPLIT);

            for (String row : rows) {
                if (row.isEmpty()) continue;
                String[] data = row.split(COL_SPLIT);

                if (!foundHeader) {
                    ArrayList<String> headerData = new ArrayList<>(List.of(data));
                    dataFrame = new DataFrame(headerData);
                    foundHeader = true;
                } else {
                    dataFrame.addRow(data);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!foundHeader) {
            throw new RuntimeException("No header found is the file empty? or set file path incorrect");
        }

        return dataFrame;
    }

    public void writeTable(DataFrame dataFrame) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        // เตรียม object ที่ใช้ในการเขียนไฟล์
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                fileOutputStream,
                StandardCharsets.UTF_8
        );
        BufferedWriter buffer = new BufferedWriter(outputStreamWriter);

        try {
            // สร้าง csv ของ Student และเขียนลงในไฟล์ทีละบรรทัด
            ArrayList<String> header = dataFrame.getHeader();
            for (int i = 0; i < header.size(); i++) {
                buffer.append(header.get(i));
                if (i != header.size() - 1) buffer.append(COL_SPLIT);
            }
            buffer.append(ROW_SPLIT);

            for (int row = 0; row < dataFrame.getData().size(); row++) {
                StringBuilder line = new StringBuilder();
                ArrayList<String> data = dataFrame.getRowList(row);
                for (int j = 0; j < data.size(); j++) {
                    if (j == data.size() - 1) {
                        line.append(data.get(j));
                    }
                    else {
                        line.append(data.get(j)).append(COL_SPLIT);
                    }
                }
                buffer.append(line.toString());
                buffer.append(ROW_SPLIT);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading file");
        } catch (NullPointerException e){
            System.out.println("null pointer exception");
        }
        finally {
            try {
                buffer.flush();
                buffer.close();
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}