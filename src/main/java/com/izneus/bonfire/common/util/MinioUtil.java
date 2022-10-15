package com.izneus.bonfire.common.util;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * minio工具类，官方example可以查看下方链接
 * https://github.com/minio/minio-java/tree/release/examples
 *
 * @author Izneus
 * @date 2022-10-12
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MinioUtil {

    private final MinioClient minioClient;

    private static final String BUCKET_NAME = "test";

    /**
     * 创建一个bucket
     */
    public void makeBucket(String bucketName) {
        // 查询bucket是否存在
        try {
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // 不存在
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                log.info("Bucket '{}' 已经存在", bucketName);
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            log.error("Bucket {} 创建失败", bucketName, e);
        }
    }

    /**
     * 查询所有至少可读的buckets
     */
    public List<Bucket> listBuckets() {
        List<Bucket> bucketList = null;
        try {
            bucketList = minioClient.listBuckets();
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            log.error("Bucket列表失败", e);
        }
        return bucketList;
    }

    /**
     * 删除一个bucket
     */
    public void removeBucket(String bucketName) {
        // Remove bucket 'my-bucketname' if it exists.
        // This operation will only work if your bucket is empty.
        try {
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (found) {
                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
                log.info("Bucket {} is removed successfully", bucketName);
            } else {
                log.info("Bucket {} does not exist", bucketName);
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            log.error("Remove Bucket {} Error: ", bucketName, e);
        }
    }

    /**
     * 上传一个object
     */
    public void putObject(InputStream inputStream, String bucketName, String objectName) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectName)
                            .stream(inputStream, inputStream.available(), -1)
                            .build());
            log.info("Bucket:{} Object:{} 上传成功", bucketName, objectName);
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("PutObject {} error: ", objectName, e);
        }
    }

    /**
     * 获得一个object
     */
    public InputStream getObject(String bucketName, String objectName) {
        InputStream inputStream = null;
        try {
            // Get input stream to have content of 'my-objectname' from 'my-bucketname'
            inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName).object(objectName).build());
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("Get Object error: {}/{}", bucketName, objectName, e);
        }
        return inputStream;

    }
}
