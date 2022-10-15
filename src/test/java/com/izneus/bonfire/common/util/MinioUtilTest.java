package com.izneus.bonfire.common.util;

import io.minio.messages.Bucket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Izneus
 * @date 2022-10-15
 */
@SpringBootTest
class MinioUtilTest {

    @Autowired
    private MinioUtil minioUtil;

    @Test
    void makeBucket() {
    }

    @Test
    void listBuckets() {
        List<Bucket> bucketList = minioUtil.listBuckets();
        for (Bucket bucket : bucketList) {
            System.out.println(bucket.creationDate() + ", " + bucket.name());
        }
    }

    @Test
    void removeBucket() {
    }
}