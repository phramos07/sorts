# Directories
SRC_DIR = src
BIN_DIR = bin

# Java compiler and flags
JAVAC = javac
JAVAC_FLAGS = -d $(BIN_DIR)

# Default target
all: prepare compile

# Create bin directory if it doesn't exist
prepare:
	mkdir -p $(BIN_DIR)

# Compile Java files
compile:
	$(JAVAC) $(JAVAC_FLAGS) $(SRC_DIR)/*.java $(SRC_DIR)/**/*.java
# Run the application
run: all
	java -cp $(BIN_DIR) App

# Clean build artifacts
clean:
	rm -rf $(BIN_DIR)

.PHONY: all prepare compile run clean
