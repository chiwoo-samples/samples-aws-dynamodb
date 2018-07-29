mvn clean package source:jar javadoc:jar 

docker build -f <Dockerfile name> -t <Docker image name> "."
docker images
docker run -p 8080:8080 springdocker
docker ps 


java -jar samples-aws-dynamodb.jar
