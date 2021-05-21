# MostCommonSequences
An approach to the Top K frequent words algorithm for New Relic's code challenge.

## Requirements
To be able to run the code you will need [Maven 3.6.3](https://maven.apache.org/download.cgi) or higher. Lower versions could be used to, but not guaranteed to run.

The java version used is [openJDK 11](https://openjdk.java.net/projects/jdk/11/).

Latest [Docker version](https://www.docker.com/products/docker-desktop) is highly recommended. I have used version 20.10.6 when developing this code.

## Local execution
To run the code locally, go to the root folder and execute `mvn clean package`, which will produce a `target`folder where the jar will be placed.

Go to the target folder and execute a command like `java -jar MostCommonSequences-1.0-SNAPSHOT.jar file1.txt file2.txt`, where file1.txt and file2.txt are valid file paths.

If you want to use a test file, just run the following command `java -jar MostCommonSequences-1.0-SNAPSHOT.jar ../src/test/resources/mobydick.txt`

Alternatively you could use the standard input to run it `cat ../src/test/resources/mobydick.txt | java -jar MostCommonSequences-1.0-SNAPSHOT.jar`.

**WARNING**

Please take into consideration that if you run the program without arguments, nor setting the file through standard input as mentioned above, it will enter an infinite loop until you cancel its execution.
I am not sure on how to handle this situation :disappointed:

## Docker execution
The `Dockerfile` is coded to use the mobydick.txt example. If you want to add more files to it, you just need to add more `COPY` statements, following the existing one, and modify the `ENTRYPOINT` to add as many arguments as files you'd like to process.

First, you need to build the image. You can do so by running `docker build -t mostcommonsequences .`  on the root folder.
This may take a bit since it will download the openJDK 11 image.

Once this step is completed, you can run the image by using the command `docker run mostcommonsequences`