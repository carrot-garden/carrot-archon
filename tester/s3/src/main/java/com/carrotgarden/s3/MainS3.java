package com.carrotgarden.s3;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class MainS3 {

	static final Logger log = LoggerFactory.getLogger(MainS3.class);

	public static void main(final String[] args) {

		log.info("init");

		final AWSCredentials creds = new AWSCredentials() {

			@Override
			public String getAWSAccessKeyId() {
				return "AKIAJIHOOSZFVS2GVVRQ";
			}

			@Override
			public String getAWSSecretKey() {
				return "vRXQkh0plDGHZ0xdSxN8XvZcWd8tBdmXTMlNW3tY";
			}

		};

		final AmazonS3 client = new AmazonS3Client(creds);

		final String name = "carrot-tester";

		final Bucket bucket = client.createBucket(name);

		log.info("bucket : " + bucket);

		final File file = new File("./pom.xml");

		client.putObject(name, file.getName(), file);

		final S3Object item = client.getObject(name, file.getName());

		final ObjectMetadata meta = item.getObjectMetadata();

		log.info("meta size : " + meta.getContentLength());
		log.info("meta time : " + meta.getLastModified());

		final ListObjectsRequest listRequest = new ListObjectsRequest();
		listRequest.setBucketName(name);
		listRequest.setDelimiter("/");

		ObjectListing listResult = client.listObjects(listRequest);

		if (listResult.isTruncated()) {
			listResult = client.listNextBatchOfObjects(listResult);
		}

		final List<S3ObjectSummary> listSummary = listResult
				.getObjectSummaries();

		for (final S3ObjectSummary summary : listSummary) {

			log.info("item key : " + summary.getKey());
			log.info("item md5 : " + summary.getETag());

		}

		log.info("done");

	}

}
