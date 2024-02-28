package com.zyj.play.log;

import java.io.*;

public class LogExclude {
    public static void main(String[] args) throws IOException {
        String fileName = "/Users/yingjiezhang/Desktop/container.txt";
        String outName = "/Users/yingjiezhang/Desktop/container_out.txt";
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outName));
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            // 删除文件中特定内容，这里假设要删除包含"DELETE"的行
//            if (!line.contains("InvalidFormatException") && !line.contains("[Source: (String)") && !line.contains("Data parsing failed")
//                    && !line.contains("com.fasterxml.jackson") && !line.contains("Push [UnknownMetricInfo]")
//            && !line.contains("ParseProcessFunction")) {
//                content.append(line).append("\n");
//            }
            if (line.contains("[MetricsSeriesEsSinkFunction]: process sink failed")){
                content.append(line).append("\n");
            }
        }
        reader.close();
        writer.write(content.toString());
        writer.close();
        System.out.println("文件处理完成！");
    }
}
