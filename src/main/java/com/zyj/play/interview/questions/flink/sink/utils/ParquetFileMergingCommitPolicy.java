package com.zyj.play.interview.questions.flink.sink.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.table.filesystem.PartitionCommitPolicy;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.schema.MessageType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yingjiezhang 
 */
@Slf4j
public class ParquetFileMergingCommitPolicy implements PartitionCommitPolicy {

    private static final String PREFIX = "part-";
    private static final String SUFFIX = ".parquet";

    @Override
    public void commit(Context context) throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://yingjiedeMacBook-Prolocal.local:8280");
        FileSystem fs = FileSystem.get(conf);
        String partitionPath = context.partitionPath().getPath();

        List<Path> files = listAllFiles(fs, new Path(partitionPath));

        MessageType schema = getParquetSchema(files, conf);

        if (schema == null) {
            return;
        }

        merge(partitionPath, schema, files, fs);
    }

    private List<Path> listAllFiles(FileSystem fs, Path dir) throws IOException {
        List<Path> result = new ArrayList<>();

        RemoteIterator<LocatedFileStatus> dirIterator = fs.listFiles(dir, false);

        while (dirIterator.hasNext()) {
            LocatedFileStatus fileStatus = dirIterator.next();
            Path filePath = fileStatus.getPath();
            if (fileStatus.isFile()) {
                if (filePath.getName().startsWith(PREFIX) || filePath.getName().endsWith(SUFFIX)) {
                    result.add(filePath);
                }
            }
        }

        return result;
    }

    private MessageType getParquetSchema(List<Path> files, Configuration conf) throws IOException {
        if (files.size() == 0) {
            return null;
        }

        HadoopInputFile inputFile = HadoopInputFile.fromPath(files.get(0), conf);
        ParquetFileReader reader = ParquetFileReader.open(inputFile);
        ParquetMetadata metadata = reader.getFooter();
        MessageType schema = metadata.getFileMetaData().getSchema();

        reader.close();
        return schema;
    }

    private void merge(String partitionPath, MessageType schema, List<Path> files, FileSystem fs) throws IOException {
        Path mergeDst = new Path(partitionPath + "/result-" + System.currentTimeMillis() + ".parquet");
        ParquetWriter<Group> writer = ExampleParquetWriter.builder(mergeDst)
                .withType(schema)
                .withConf(fs.getConf())
                .withWriteMode(ParquetFileWriter.Mode.CREATE)
                .withCompressionCodec(CompressionCodecName.SNAPPY)
                .build();

        for (Path file: files) {
            ParquetReader<Group> reader = ParquetReader.builder(new GroupReadSupport(), file)
                    .withConf(fs.getConf())
                    .build();

            Group data;
            while ((data = reader.read()) != null) {
                writer.write(data);
            }
            reader.close();
        }

        writer.close();

        for (Path file : files) {
            fs.delete(file, true);
        }
    }
}
