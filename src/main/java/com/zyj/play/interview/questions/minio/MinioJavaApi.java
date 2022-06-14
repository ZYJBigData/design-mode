package com.zyj.play.interview.questions.minio;

import io.minio.*;
import io.minio.messages.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MinioJavaApi {
    public static String bucket = "zyj";
    public static String endpoint = "http://192.168.31.203:9000";
    public static String accessKey = "minioadmin";
    public static String secretkey = "minioadmin";
    public static String addressPath = "/Users/yingjiezhang/test.csv";
    public static String addressPath1 = "/Users/yingjiezhang/test1.csv";
    public static String addressPath2 = "/Users/yingjiezhang/test2.csv";
    public static String addressPathCount = "/Users/yingjiezhang/count.csv";
    public static MinioClient minioClient = getClient();

    public static void main(String[] args) throws Exception {
//        isExist();
//        deleteObject(addressPath);
//        putObject(addressPath2);
//        listObject();
//        getObjectContent();
//        selectObjectContent();
//        composeObject();
//        uploadObject(addressPath);
        setLockObject();
        
    }

    public static MinioClient getClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretkey)
                .build();
    }

    public static void isExist() throws Exception {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            System.out.println("create bucket");
        } else {
            System.out.println("bucket is exist");
        }
    }

    /**
     * 将文件上传
     *
     * @throws Exception
     */
    public static void putObject(String addressPath) throws Exception {
        InputStream inputStream = new FileInputStream(addressPath);
        PutObjectArgs build = PutObjectArgs.builder()
                .bucket(bucket)
                .stream(inputStream, 266, -1)
                .object(addressPath).build();
        minioClient.putObject(build);
    }

    /**
     * 举例出所有的文件列表
     */
    public static void listObject() throws Exception {
        ListObjectsArgs build = ListObjectsArgs.builder().bucket(bucket).recursive(true).build();
        Iterator<Result<Item>> iterator = minioClient.listObjects(build).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().get().objectName());
        }
    }

    /**
     * 从minio上读取文件
     */
    public static void getObjectContent() throws Exception {
        GetObjectArgs build = GetObjectArgs.builder()
                .bucket(bucket)
                .object(addressPath)
                .build();
        String line;
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = minioClient.getObject(build);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        System.out.println(sb.toString());

    }

    /**
     * 从文件查询
     * 支持存储为json格式 或者csv格式
     * 注意： 查询语句中必须用单引号
     */
    public static void selectObjectContent() throws Exception {
        String sqlExpression = "select name from S3Object where name ='jack' limit 5";
        InputSerialization is = new InputSerialization(CompressionType.NONE, true,
                null, ',', FileHeaderInfo.USE, null, '\\', '\n');
        OutputSerialization os = new OutputSerialization(',', null, null, QuoteFields.ASNEEDED, '\n');
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "jack");
        SelectResponseStream stream =
                minioClient.selectObjectContent(
                        SelectObjectContentArgs.builder()
                                .bucket(bucket)
                                .object(addressPath)
                                .sqlExpression(sqlExpression)
                                .extraQueryParams(map)
                                .inputSerialization(is)
                                .outputSerialization(os)
                                .requestProgress(true)
                                .build());

        byte[] buf = new byte[512];
        int bytesRead = stream.read(buf, 0, buf.length);
        System.out.println(new String(buf, 0, bytesRead, StandardCharsets.UTF_8));

        Stats stats = stream.stats();
        System.out.println("bytes scanned: " + stats.bytesScanned());
        System.out.println("bytes processed: " + stats.bytesProcessed());
        System.out.println("bytes returned: " + stats.bytesReturned());
        stream.close();
    }

    public static void deleteObject(String addressPath) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucket).object(addressPath).build());
    }

    /**
     * source zyj//Users/yingjiezhang/test1.csv: size 266 must be greater than 5242880
     * allowed minimum part size is 5MiB in multipart upload.
     * 文件必须要大于5m，代码进行了检测
     * @throws Exception
     */
    public static void composeObject() throws Exception {
        List<ComposeSource> sourceObjectList = new ArrayList<ComposeSource>();
        sourceObjectList.add(
                ComposeSource.builder().bucket(bucket).object(addressPath1).build());
        sourceObjectList.add(
                ComposeSource.builder().bucket(bucket).object(addressPath2).build());

        ObjectWriteResponse objectWriteResponse = minioClient.composeObject(
                ComposeObjectArgs.builder()
                        .bucket(bucket)
                        .object(addressPathCount)
                        .sources(sourceObjectList)
                        .build());

        System.out.println(objectWriteResponse.etag());
    }

    /**
     * filename 本地文件地址
     * object  minio中的地址
     * 将本地文件上传到minio ,和putObject之间的区别： putObject比较适合
     * @param addressPath
     * @throws Exception
     */
    public static void uploadObject(String addressPath) throws Exception {
        minioClient.uploadObject(UploadObjectArgs.builder().bucket(bucket).object(addressPath)
                .filename("/Users/yingjiezhang/test1.csv").build());
    }

    /**
     * Sets object-lock configuration in a bucket.
     * retentionDurationDays:在配置的保留规则持续时间过后，MinIO 会自动解除锁定。
     * lock model
     * COMPLIANCE: 防止任何会改变或修改对象或其锁定设置的操作。没有 MinIO 用户可以修改对象或其设置，包括 MinIO root 用户。
     *             COMPLIANCE 锁定对象强制锁定对象完全不变。在配置的保留期限过去之前，您无法更改或删除锁定。
     * GOVERNANCE: 防止非特权用户进行任何会改变或修改对象或其锁定设置的操作。对存储桶或对象拥有 s3:BypassGovernanceRetention 权限的用户可以修改对象或其锁定设置。
     *             GOVERNANCE锁定对象强制锁定对象的托管不变性，其中具有 s3:BypassGovernanceRetention 操作的用户可以修改锁定对象、更改保留持续时间或完全解除锁定。
     *
     * 
     * 注意： 不是锁用完自动释放
     */
    public static void setLockObject() throws Exception{
        ObjectLockConfiguration config = new ObjectLockConfiguration(RetentionMode.COMPLIANCE, new RetentionDurationDays(100));
        minioClient.setObjectLockConfiguration(
                SetObjectLockConfigurationArgs.builder()
                .bucket(bucket)
                .config(config)
                .build());
    }
}
