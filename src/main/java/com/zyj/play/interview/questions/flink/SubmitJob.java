package com.zyj.play.interview.questions.flink;


import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.JobID;
import org.apache.flink.client.deployment.ClusterDeploymentException;
import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.flink.client.deployment.StandaloneClusterId;
import org.apache.flink.client.program.*;
import org.apache.flink.client.program.rest.RestClusterClient;
import org.apache.flink.configuration.*;
import org.apache.flink.core.fs.Path;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;
import org.apache.flink.yarn.YarnClientYarnClusterInformationRetriever;
import org.apache.flink.yarn.YarnClusterDescriptor;
import org.apache.flink.yarn.YarnClusterInformationRetriever;
import org.apache.flink.yarn.configuration.YarnConfigOptions;
import org.apache.flink.yarn.configuration.YarnDeploymentTarget;
import org.apache.flink.yarn.configuration.YarnLogConfigUtil;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class SubmitJob {
//    public static String jarFilePath = "/Users/yingjiezhang/IdeaProjects/juggle/juggle-core/target/juggle-core.jar";
    //    public static String jarFilePath = "/Users/yingjiezhang/usr/local/flink-1.13.2/examples/streaming/SocketWindowWordCount.jar";
//    public static String jarFilePath = "/bs/running/flink-1.12.7/examples/streaming/SocketWindowWordCount.jar";
    public static String jarFilePath = "/bs/running/flink-1.13.2/examples/streaming/SocketWindowWordCount.jar";
    //    public static String configurationDirectory = "/Users/yingjiezhang/usr/local/flink-1.13.2/conf";
    public static String configurationDirectory = "/bs/running/flink-1.13.2/conf";
    //    存放flink集群相关的jar包目录
//    public static String flinkLibs = "hdfs://192.168.31.203:8280/flink1.13/lib";
    public static String flinkLibs = "hdfs://10.0.100.237:8280/flink1.13/lib";
    //    public static String flinkDistJar = "hdfs://192.168.31.203:8280/flink1.13/lib/flink-dist_2.11-1.13.2.jar";
    public static String flinkDistJar = "hdfs://10.0.100.237:8280/flink1.13/lib/flink-dist_2.11-1.13.2.jar";
    //    public static String hdfsDir = "hdfs://yingjiedeMacBook-Prolocal.local:8280";
    public static String hdfsDir = "hdfs://10.0.100.237:8280";
    //    public static String hostname = "192.168.31.20";
    public static String hostname = "10.0.100.237";


    public static void main(String[] args) {
//        Configuration configuration = new Configuration();
//        submitJobStandalone(configuration);
//        submitJobYarnPerJob();
    }

    public static void submitJobStandalone(Configuration configuration) {
        RestClusterClient<StandaloneClusterId> client;
        try {
            // 集群信息
            configuration.setString(JobManagerOptions.ADDRESS, hostname);
            configuration.setInteger(JobManagerOptions.PORT, 6123);
            configuration.setInteger(RestOptions.PORT, 8081);
            client = new RestClusterClient<>(configuration, StandaloneClusterId.getInstance());
            int parallelism = 1;
            File jarFile = new File(jarFilePath);
            SavepointRestoreSettings savepointRestoreSettings = SavepointRestoreSettings.none();
            PackagedProgram program = PackagedProgram.newBuilder()
                    .setConfiguration(configuration)
                    .setEntryPointClassName("com.bizseer.platform.juggle.Cli")
                    .setArguments("execute", "-url", "http://192.168.31.203:28070/api/pipeline/1", "-format", "JSON")
                    .setJarFile(jarFile)
                    .setSavepointRestoreSettings(savepointRestoreSettings).build();
            JobGraph jobGraph = PackagedProgramUtils.createJobGraph(program, configuration, parallelism, false);
            CompletableFuture<JobID> result = client.submitJob(jobGraph);
            JobID jobId = result.get();
            System.out.println("提交完成");
            System.out.println("jobId:" + jobId.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void submitJobYarnPerJob() {
        File jarFile = new File(jarFilePath);
        //初始化yarn客户端
        YarnConfiguration yarnConfiguration = new YarnConfiguration();
        yarnConfiguration.set("fs.defaultFS", hdfsDir);
        yarnConfiguration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        YarnClient yarnClient = YarnClient.createYarnClient();
        yarnClient.init(yarnConfiguration);
        yarnClient.start();
        // 设置日志的，没有的话看不到日志
        YarnClusterInformationRetriever clusterInformationRetriever = YarnClientYarnClusterInformationRetriever.create(yarnClient);
        //获取flink的配置
        Configuration flinkConfiguration = GlobalConfiguration.loadConfiguration(configurationDirectory);
        flinkConfiguration.set(
                PipelineOptions.JARS,
                Collections.singletonList(jarFilePath));

        Path remoteLib = new Path(flinkLibs);
        flinkConfiguration.set(
                YarnConfigOptions.PROVIDED_LIB_DIRS,
                Collections.singletonList(remoteLib.toString()));

        flinkConfiguration.set(
                YarnConfigOptions.FLINK_DIST_JAR,
                flinkDistJar);

        // 设置为per-job模式
        flinkConfiguration.set(DeploymentOptions.TARGET,
                YarnDeploymentTarget.PER_JOB.getName());
        flinkConfiguration.setString("yarn.application.name", "我在测试java api 提交任务");
        //配置所使用的日志文件
        //TODO 这句话可以将日志出来，修改配置文件应该可以实现按照大小清理日志的问题
        YarnLogConfigUtil.setLogConfigFileInConfig(flinkConfiguration, configurationDirectory);

        try {
//            PackagedProgram program = PackagedProgram.newBuilder().setJarFile(jarFile)
//                    .setEntryPointClassName("com.bizseer.platform.juggle.Cli")
//                    .setConfiguration(flinkConfiguration)
//                    .setArguments("execute", "-url", "http://192.168.31.203:28070/api/pipeline/1", "-format", "JSON")
//                    .build();
            PackagedProgram program = PackagedProgram.newBuilder().setJarFile(jarFile)
                    .setEntryPointClassName("org.apache.flink.streaming.examples.socket.SocketWindowWordCount")
                    .setConfiguration(flinkConfiguration)
                    .setArguments("--hostname", hostname, "--port", "6666")
                    .build();
            JobGraph jobGraph = PackagedProgramUtils.createJobGraph(program, flinkConfiguration, 2, false);

            ClusterSpecification clusterSpecification = new ClusterSpecification.ClusterSpecificationBuilder().createClusterSpecification();

            YarnClusterDescriptor yarnClusterDescriptor = new YarnClusterDescriptor(flinkConfiguration,
                    yarnConfiguration,
                    yarnClient,
                    clusterInformationRetriever,
                    true);


            ClusterClientProvider<ApplicationId> clusterClientProvider = yarnClusterDescriptor.deployJobCluster(clusterSpecification, jobGraph, true);
            if (Objects.isNull(clusterClientProvider)) {
                log.error("submit yarn per job error");
                return;
            }

            ClusterClient<ApplicationId> clusterClient = clusterClientProvider.getClusterClient();

            Field declaredField = clusterClient.getClass().getDeclaredFields()[0];
            declaredField.setAccessible(true);
            String webInterfaceURL = clusterClient.getWebInterfaceURL();
            ApplicationId applicationId = clusterClient.getClusterId();
            log.error("applicationId is {}", applicationId);
            log.error("webInterfaceURL is {}", webInterfaceURL);

            // 退出
            // yarnClusterDescriptor.killCluster(applicationId);

        } catch (ProgramInvocationException | ClusterDeploymentException e) {
            e.printStackTrace();
        }
    }
}
