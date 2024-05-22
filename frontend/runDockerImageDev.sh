docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker build -t frontend -f Dockerfile.dev .
docker run -d -p 3000:3000 frontend
