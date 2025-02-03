# Cron Expression Parser

## Features
- Parse cron expressions and show their schedule
- Support for both 5 fields (cron expression) and 6 fields (with command)
- Interactive command line interface with proper error messages
- Support for all special characters (*, /, -, ,)

## Prerequisites
- Java 17 or higher
- JUnit 5 (for tests)

## Valid and Invalid Cron Examples

Valid Examples:
```
*/15 0 1,15 * 1-5 /usr/bin/find     (6 fields with command)
*/15 0 1,15 * 1-5                   (5 fields without command)
* * * * * /usr/find                 (run every minute)
```

Invalid Examples:
```
*/15 0 * * * 1-5 /usr/find extra   (too many fields)
*/15 1,15 *                        (too few fields)
60 * * * * /usr/find               (minute > 59)
* 24 * * * /usr/find               (hour > 23)
```

## Run Locally
0. Clone the repo

1. If you have IntelliJ, open this project and simply click on Run button. 
Otherwise, execute following steps to execute from the terminal / command line

2. Create directories for compiled classes
```bash
mkdir -p target/classes
```

3. Compile (from project root directory)
### Windows
```commandline
javac -d target/classes .\src\main\java\org\deliveroo\exception\*.java .\src\main\java\org\deliveroo\model\*.java .\src\main\java\org\deliveroo\service\*.java .\src\main\java\org\deliveroo\*.java
```
### Linux
```bash
javac -d target/classes src/main/java/org/deliveroo/exception/*.java \
                        src/main/java/org/deliveroo/model/*.java \
                        src/main/java/org/deliveroo/service/*.java \
                        src/main/java/org/deliveroo/Main.java
```

4. Run (from project root directory)
```bash
java -cp target/classes org.deliveroo.Main
```

## Run Tests
```bash
# Create test classes directory
mkdir -p target/test-classes
```
### Compile tests (Linux)
```bash
javac -d target/test-classes \
      -cp "target/classes:junit-jupiter-api-5.8.1.jar" \
      src/test/java/com/cron/test/*.java
```
### Run tests
```
java -jar junit-platform-console-standalone-1.8.1.jar \
     --class-path target/classes:target/test-classes \
     --scan-class-path
```
