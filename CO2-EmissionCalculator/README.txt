This project uses Java version 1.8

Steps to run:

If Maven is not installed on your system follow these steps(For windows): 

1. Download and extract Maven from http://maven.apache.org/download.cgi Binary zip archive Link.
2. For adding dependency on your classpath variables: follow this tutorial https://www.mkyong.com/maven/how-to-install-maven-in-windows/
3. Open command prompt to check if mvn is added correctly to your PATH "mvn --version".

To run the program:

1. Open command prompt and "cd" to the extracted zip folder.
2. run "mvn clean install".
3. then execute: mvn exec:java -Dexec.mainClass="emissioncalculator.Co2Emissions" -Dexec.args="--transportation-method medium-diesel-car --distance 15 --unit-of-distance km"
	where -Dexec.args is where the arguments can be changed.

To test:

1. Open command prompt and "cd" to the extracted zip folder.
2. Run "mvn clean install".
