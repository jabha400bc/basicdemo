/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package basic.demo;
import software.amazon.awssdk.services.sts.*;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityResponse;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.Random;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.sts.StsClientBuilder;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;
import java.net.URI;

public class App {
    static String REGION="ap-east-1";
    public static void main(String[] args) throws IOException{
        whoami();
        //listS3Buckets();
        putObject2S3Bucket();
    }
    private static void whoami(){
        Region region = Region.of(REGION);
        StsClient sts =  StsClient.builder().region(region).endpointOverride(URI.create("https://sts.ap-east-1.amazonaws.com")).build();
        GetCallerIdentityResponse id = sts.getCallerIdentity();
        System.out.println("**************** Who am I Details ***************");
        System.out.println("Account:"+id.account());
        System.out.println("ARN:"+id.arn());
    }
    private static void listS3Buckets(){
        Region region = Region.of(REGION);
        S3Client s3 = S3Client.builder().region(region).build();

        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);
        System.out.println("**************** List of S3 Buckets ***************");
        listBucketsResponse.buckets().stream().forEach(x -> System.out.println(x.name()));
    }
    private static void putObject2S3Bucket() throws IOException{
        Region region = Region.of(REGION);
        S3Client s3 = S3Client.builder().region(region).build();
        String bucket = "dev-ccba2kpmg";
        String key = "key";

        // Put Object
        s3.putObject(PutObjectRequest.builder().serverSideEncryption(ServerSideEncryption.AES256).bucket(bucket).key(key)
                .build(),
        RequestBody.fromByteBuffer(getRandomByteBuffer(10_000)));
        System.out.println("**************** Uploaded 10KB object with key in bucket:"+bucket+" ***************");
    }
    private static ByteBuffer getRandomByteBuffer(int size) throws IOException {
        byte[] b = new byte[size];
        new Random().nextBytes(b);
        return ByteBuffer.wrap(b);
    }
}


