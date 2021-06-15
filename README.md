## URL QUEEZR -  a url shortening service

### Running the service
From the root of the project execute:
```
gradlew bootRun
```
Alternatively just run the class **SqueezrApplication.kt** in your preferred IDE as Java Application.

### Testing 
The tests for this little demo application run with spring boot. To execute them use:
```
gradlew test
```

### Building the service 
To build a deployable jar file run
```
gradlew build
```
After a successful build you will find your jar in folder **build/libs**

To build docker image run
```
gradlew bootBuildImage --imageName=geeksven/squeezr
```
After a successful build your docker image will be available on your local machine

### Running the service
To run a built jar use:
```
java -jar build\libs\squeezr-0.0.1-SNAPSHOT.jar
```
To run the docker version use:
```
docker run -p 8080:8080 -t geeksven/squeezr
```


### HTTP testing via Insomnia collection
As I'am a big fan of Insomnia HTTP Client you will find a **insomnia.json** in the root of this application.
To use it just import this file into a running Insomnia HTTP Client. You are then able to make requests to the default
location **http://localhost:8080**

### HTTP testing via IntelliJ .http file
If you prefer the IDE solution you may want to have a look to **url_playground.http** in the root folder of the application.
It is executed the out of the box http client from IntelliJ.

### HTTP testing via curl
To create a new shortened url:
```
curl --request POST \
  --url http://localhost:8080/squeeze \
  --header 'Content-Type: application/json' \
  --data '{
	"fullUrl": "https://www.google.com"
}'
```
To create a new shortened url with custom slug:
```
curl --request POST \
  --url http://localhost:8080/squeeze \
  --header 'Content-Type: application/json' \
  --data '{
	"fullUrl": "https://www.google.com", "customSlug": "demo-slug"
}'
```
To test the redirecting to a full url (make sure you are using an already created slug):
```
curl --request GET \
  --url http://localhost:8080/demo-slug
```
To see the statistics:
```
curl --request GET \
  --url http://localhost:8080/statistics
```