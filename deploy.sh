#!/bin/bash

# Deployment script for HTML Analyzer
# This script deploys the HTML Analyzer to a remote server

# Configuration
REMOTE_USER="b07"
REMOTE_HOST="144.91.108.111"
REMOTE_DIR="/opt/html-analyzer"

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${GREEN}Starting deployment of HTML Analyzer to ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_DIR}${NC}"

# Step 1: Create a deployment package
echo "Creating deployment package..."
tar -czf html-analyzer.tar.gz *.html *.java Dockerfile docker-compose.yml

# Step 2: Create the remote directory if it doesn't exist
echo "Creating remote directory..."
ssh ${REMOTE_USER}@${REMOTE_HOST} "sudo mkdir -p ${REMOTE_DIR} && sudo chown ${REMOTE_USER}:${REMOTE_USER} ${REMOTE_DIR}"

# Step 3: Copy the deployment package to the remote server
echo "Copying files to remote server..."
scp html-analyzer.tar.gz ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_DIR}/

# Step 4: Extract the deployment package on the remote server
echo "Extracting files on remote server..."
ssh ${REMOTE_USER}@${REMOTE_HOST} "cd ${REMOTE_DIR} && tar -xzf html-analyzer.tar.gz && rm html-analyzer.tar.gz"

# Step 5: Build and start the Docker container on the remote server
echo "Building and starting Docker container..."
ssh ${REMOTE_USER}@${REMOTE_HOST} "cd ${REMOTE_DIR} && sudo docker-compose down && sudo docker-compose up -d --build"

# Step 6: Clean up local deployment package
echo "Cleaning up local deployment package..."
rm html-analyzer.tar.gz

echo -e "${GREEN}Deployment completed successfully!${NC}"
echo -e "The HTML Analyzer is now available at: ${GREEN}http://${REMOTE_HOST}:6464/${NC}"
