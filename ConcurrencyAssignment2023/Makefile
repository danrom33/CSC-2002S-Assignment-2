JAVAC = /usr/bin/javac
JAVA = /usr/bin/java
.SUFFIXES: .java .class
SRCDIR = src/clubSimulation
BINDIR = bin

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) -sourcepath $(SRCDIR) $<

CLASSES= GridBlock.class PeopleCounter.class CounterDisplay.class PeopleLocation.class ClubGrid.class ClubView.class AndreBarman.class Clubgoer.class ClubSimulation.class
CLASS_FILES = $(CLASSES:%.class=$(BINDIR)/%.class)
JAVA_FILES = $(CLASSES:%.class=$(SRCDIR)/%.java)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/clubSimulation/*.class


run:
	$(JAVA) -cp $(BINDIR) ClubSimulation.java $(ARGS)
