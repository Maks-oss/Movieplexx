docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker build -t backend .
docker run --name back -p 8080:8080 backend
