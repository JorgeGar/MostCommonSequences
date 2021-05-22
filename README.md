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

## About the code

#### ALGORITHM
This is a variation of the "K most common words" algorithm, which is widely discussed on the community. Places like [Leetcode](https://leetcode.com/problems/top-k-frequent-words/discuss/?currentPage=1&orderBy=hot&query=) even have it amongst their challenges.

The approach I decided to use is to store all the sequences on a hashmap. This allows us to store and gather the sequences and, as the existing ones will be found on insertion, we can add up the frequency at the same time.
The hashmap is, memory wise, the most consuming part. Because we need to all non-repeated sequences, it will be *O(n)* on memory cost.

On the other hand, we just need to go through the original text word by word once, which is neat.

Once we have the collection ready, we need to retrieve the top K occurrences of the sequences we have stored. To do so, we need a structure that help us maintain the most repeated ones each iteration, while we loop through all of them.
For this, PriorityQueue is ideal. PriorityQueue will use a Min-Max Heap to keep the order (using the comparator that we decide to) while keeping the elements on the structure to a minimum.

Nevertheless, the insertion operation of a key on [PriorityQueue](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/PriorityQueue.html) has a worst-case time complexity of *O(log n)*.
So we need to make sure we always have a maximum of K elements in the structure, that way we will have a time complexity of *O(log k)* when inserting.

With this accomplished, we end up having an overall time complexity of *O(n log K)*. 

#### MULTIPLE BOOKS

When decided how to handle different inputs, I could choose between having an individual answer for each one of them (just running the code one by one, or even with parallelism), or to consider all part of the same output.
As you could run the program independently for different inputs and improve performance, I decided that it would make more sense to keep with the second approach. Nevertheless, this approach won't allow us to use more resources to improve performance,
and will end up needing to increase the maximum java heap memory, to be able to handle like 1000 Moby Dick books.

This situation could be mitigated in many ways, such as running the scan of each input on parallel to end up with as many HashMaps as inputs you received, and then collapse all them on a single one to obtain the end result.
Nevertheless, I didn't have time to implement all the threading code to do so.

Another approach would be to return and/or store partial results on a permanent storage (let it be a database or just the serialized java object in an S3 bucket, for example) and then re-use them to increase the statistics of a feed of inputs.
To do so, it would be probably better to transform this code to a microservice using SpringBoot and to keep and endpoint alive to receive and process books. This way it would be easier to scale. 

## Known issues

#### REGULAR EXPRESSION

To be able to split the words, make them case-insensitive and avoid punctuation marks, I've used a fairly simple (and a bit ugly) regular expression on the Scanner delimiter ```[!\u0022#$%&()*+,-./:;<=>?@\u005B\u005D^_`{|}~\s\t\n\r]+```. 

This regular expression will have issues handling abbreviations and version marks such as Vol. 1 or V.1, but I'm not sure if this  is important for the challenge, and I have decided to focus on the
rest of the code, since this could be checked easily using any regex analyzer, such as [regexr.com](https://regexr.com/)

Also, it will consider ' character as a word as long as it is not part of a word. So "he's" or "Smiths'" will be a word, but "hey'" will be too
or even "hey ' guy" will be 3 words. I would need to expend more time reading about regular expressions to correct it.
Sorry :worried:

I also found out that java won't handle well all types of regular expressions, or rather each analyzer will have a different behavior.
I am not sure why this happens, but we could enhance that first expression to ```[!\u0022#$%&()*+,-./:;<=>?@\u005B\u005D^_`{|}~\s\t\n\r]+|( ' )+|(' )+|( ')+``` and it will handle the single quotes outside words better, but Java will change completely its behavior, ending up messing the algorithm.

#### STANDARD INPUT

While the code is able to manage the standard input as explained in [local execution](#local-execution) section, but it will end up on an infinite loop (until you stop the execution) if you don't set the input beforehand with pipes.
I mitigated this by only letting that happen if there are no parameters. Nevertheless, this should be corrected, but I am not sure how I would do it.

## Moving to the cloud

Cloud environments provide a wide variety of options to run a code as simple as this one. In AWS, you could use Lambda, EC2, or Elastic Container Registry, for example.
The choice will be driven more on a usage point of view. You could decide to use Lambda and trigger the code when a book is received in an S3 bucket and just send or store the output;
or you could decide to build a microservice and make use a EKS to build a full streaming architecture with Kafka and so on. 

There are many aspects to take into consideration to make a decision on how to migrate to cloud or how to develop a new cloud native app. You need to consider availability, networking, efficiency and cost so,
in my opinion, I wouldn't take a decision based on what the algorithm should do. I think it would be wise to analyze the use case and propose a solution that will be more suited for it.
