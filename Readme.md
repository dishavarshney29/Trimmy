# ![](images/logo_128.png) **Trimmy - URL Shorten API**

Trimmy is a URL Shortening API, which works on the following Authentication mechanisms

1. DB Authentication, using Form login
2. API Token based Authentication
3. Google Authentication

## Functionalities:

1. Creating shorten URL Via UI
2. Managing URLs via UI
3. Creating shorten URL Via API / Swagger UI
4. Redirecting shorten URL

### Login Page

![](images/Login.png)

### Home Page

![](images/HomePage.png)

#### Swagger UI

![](images/HomePage.png)

#### Redirection

Redirection has been done by the shorten URL

```
http://localhost:8081/r/GkPNMK
```

#### Compilation and Execution

```
ProjectDir> mvn clean install
ProjectDir> java -jar target/Trimmy-1.0.0-SNAPSHOT.jar
```

Go to the below URL in Browser

```
http://localhost:8081
```

#### Build and run docker image in your local

Build via Dockerfile using your image name
```
docker build -t trimmy-test:latest .
```
Map container port 8080 by listening to port 8081 from your host
```
docker run -p 8080:8081 trimmy-test:latest
```