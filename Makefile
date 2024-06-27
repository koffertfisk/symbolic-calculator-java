JAVA_COMPILER		= javac
JAVA_RUNTIME		= java
JAVADOC				= javadoc

SRC_DIR         	= src
TESTS_DIR       	= tests
CLASSES_DIR     	= classes

SRC_FILES       	= $(wildcard $(SRC_DIR)/*.java)
TEST_FILES      	= $(wildcard $(TESTS_DIR)/*.java)

PACKAGE_NAME_PREFIX = com.example.calculator

all:
	mkdir -p $(CLASSES_DIR)
	$(JAVA_COMPILER) -d $(CLASSES_DIR) $(SRC_FILES) $(TEST_FILES)

run: all
	$(JAVA_RUNTIME) -cp $(CLASSES_DIR) $(PACKAGE_NAME_PREFIX).Calculator

asttest: all
	$(JAVA_RUNTIME) -enableassertions -cp $(CLASSES_DIR) $(PACKAGE_NAME_PREFIX).ast.ASTTest

parser: all
	$(JAVA_RUNTIME) -cp $(CLASSES_DIR) $(PACKAGE_NAME_PREFIX).parser.ParserDriver

parsertest: all
	$(JAVA_RUNTIME) -enableassertions -cp $(CLASSES_DIR) $(PACKAGE_NAME_PREFIX).parser.ParserTest

test: all
	$(JAVA_RUNTIME) -enableassertions -cp $(CLASSES_DIR) $(PACKAGE_NAME_PREFIX).Test

doc: all
	$(JAVADOC) -d docs $(SRC_FILES)

clean:
	$(RMDIR) $(CLASSES_DIR) docs

RMDIR = rm -rf
