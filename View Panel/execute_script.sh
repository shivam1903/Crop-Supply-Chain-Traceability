#!/bin/bash

# Paths
#PROJECT_DIR="Backend_GI"
POM_FILE="pom.xml"
#MAIN_CLASS="./target/classes/com/example/GI/Backend/BackendApplication.class"

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

# Navigate to the project directory
echo -e "${YELLOW}Navigating to project directory: $PROJECT_DIR${NC}"
cd "$PROJECT_DIR" || { echo -e "${RED}Error: Project directory not found!${NC}"; exit 1; }

# Find the JAR file in the target directory
JAR_FILE=$(find target -type f -name "*.jar" | head -n 1)
if [ -z "$JAR_FILE" ]; then
    echo -e "${RED}Error: No JAR file found in target directory.${NC}"
    exit 1
fi

echo -e "${GREEN}JAR file located: $JAR_FILE${NC}"

# Run the application
echo -e "${YELLOW}Running the application...${NC}"
if ! pgrep -f "$JAR_FILE" > /dev/null; then
    nohup java -jar "$JAR_FILE" > output.log 2>&1 &
    echo "Application started successfully."
else
    echo "Application is already running."
fi
check_command "Application run"

echo -e "${GREEN}Application is running successfully!${NC}"