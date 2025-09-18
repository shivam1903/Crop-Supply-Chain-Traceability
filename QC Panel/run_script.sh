#!/bin/bash

# Paths
POM_FILE="pom.xml"
PORT=8031  # Define the port your application runs on

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to check if a command succeeded
check_command() {
    if [ $? -ne 0 ]; then
        echo -e "${RED}Error: $1 failed. Exiting.${NC}"
        exit 1
    fi
}

# Check if the application is already running on the port
if lsof -i :"$PORT" | grep LISTEN > /dev/null; then
    echo -e "${GREEN}Application is already running on port $PORT.${NC}"
    exit 0
fi

# Clean and build the project
echo -e "${YELLOW}Building the project...${NC}"
mvn clean install -f "$POM_FILE"
check_command "Maven build"

# Find the JAR file in the target directory
JAR_FILE=$(find target -type f -name "*.jar" | head -n 1)
if [ -z "$JAR_FILE" ]; then
    echo -e "${RED}Error: No JAR file found in target directory.${NC}"
    exit 1
fi

echo -e "${GREEN}Build successful. JAR file located: $JAR_FILE${NC}"

# Run the application
echo -e "${YELLOW}Running the application on port $PORT...${NC}"
nohup java -jar "$JAR_FILE" > output.log 2>&1 &
check_command "Application run"

echo -e "${GREEN}Application is running successfully on port $PORT!${NC}"
