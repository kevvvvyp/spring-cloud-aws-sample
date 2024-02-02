#!/bin/bash
echo "########### Setting up localstack profile ###########"
aws configure set aws_access_key_id access_key --profile=localstack
aws configure set aws_secret_access_key secret_key --profile=localstack
aws configure set region "us-east-1" --profile=localstack

echo "########### Setting default profile ###########"
export AWS_DEFAULT_PROFILE=localstack

echo "########### Creating SQS ###########"
aws --profile=localstack --endpoint-url=http://localhost:4566 sqs create-queue --queue-name "my-queue"
aws --profile=localstack --endpoint-url=http://localhost:4566 sqs create-queue --queue-name "my-fifo.fifo" --attributes "{\"FifoQueue\":\"true\",\"DeduplicationScope\":\"messageGroup\",\"FifoThroughputLimit\":\"perMessageGroupId\"}"

echo "########### List queues ###########"
aws --endpoint-url=http://localhost:4566 sqs list-queues

echo "########### Creating SNS & SQS Subscription ###########"
aws --profile=localstack --endpoint-url=http://localhost:4566 sns create-topic --name "my-topic"
aws --profile=localstack --endpoint-url=http://localhost:4566 sns subscribe \
    --topic-arn arn:aws:sns:us-east-1:000000000000:my-topic \
    --protocol sqs --notification-endpoint arn:aws:sqs:us-east-1:000000000000:my-queue

echo "########### Echoing marker log line ###########"
echo "Init Script Completed"
