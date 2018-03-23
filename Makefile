#Thabo Kopane
#Makefile for CSC2001f_2
#Due 24 April 2017

JAVAC=/usr/bin/javac
JFLAGS = -g -d $(BIN) -cp $(BIN):$(JUNIT)
BIN =bin/
SRC = src/
DOCDIR = doc/
TESTD = ..test/

JUNIT = ./bin/:../lib/junit/junit-4.12.jar:../lib/junit/hamcrest-core-1.3.jar
JACOCO = $(LIB)/jacoco/org.jacoco.core-0.7.9201702052155.jar:$(LIB)/org.jacoco.report-0.7.9.201702052155.jar

.SUFFIXES: .java .class

$(BIN)*: $(SRC)*
	$(JAVAC) $(JFLAGS) $(SRC)*.java				
				
default: classes

classes: $(CLASSES:.java=.class)

run:
	java -cp $(BIN)/ Server
args:
	ARGS="asdf" client
TESTF = $(TESTD)*.java

client:
	java -cp $(BIN)/ Client

testB:
	$(JAVAC) -cp $(JUNIT) -d $(BIN) $(TESTF)
	javac -cp .:/home/thabo/Desktop/junit/junit-4.12.jar GraphTest.java

docs:
	javadoc -d $(DOCDIR) $(SRC)*.java

clean:
	rm $(BIN)/*.class
	rm $(DOCDIR)/*.css *.html *.js