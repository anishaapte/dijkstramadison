runTests: *.java
	javac -cp ../junit5.jar *.java
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests

clean:
	rm *.class
