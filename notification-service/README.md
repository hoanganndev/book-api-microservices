# notification service
## MongoDB
### Pull images 
```text
docker pull bitnami/mongodb:7.0.11
```
### start container 
```text
docker run -d --name mongodb_container -p 27017:27017 -e MONGODB_ROOT_USER=root -e MONGODB_ROOT_PASSWORD=root bitnami/mongodb:7.0.11
```