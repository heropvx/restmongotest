### Installation (build docker image)
```
./mvnw clean install && sudo docker build -t rest-mongo-test .

```

### How to Run service
```
sudo docker run --name mongo-container -d -p 27017:27017 mongo
sudo docker run -d -e mongouri=mongodb://$(sudo docker inspect -f "{{ .NetworkSettings.IPAddress }}" mongo-container):27017/testDB -p 8080:8080 rest-mongo-test
```

### Testing with curl commands
1. Create new product
```
curl -H "Content-Type: application/json" -X POST 'http://localhost:8080/products' -d '{"id":"1234", "name":"Nokia 3310", "description":"Nokia phone", "params":[{"manufacturer":"Nokia"}, {"price":"100"}]}'
```

2. Search product names by name and parameter.
```
curl 'http://localhost:8080/products?paramName=price&paramValue=100'
```

3. Get product info by id
```
curl 'http://localhost:8080/products/1234'
```
