#!/bin/bash
aws configure set default.region us-east-1
aws configure set default.output json
java -Djava.security.egd=file:/dev/./urandom -jar /app.jar
