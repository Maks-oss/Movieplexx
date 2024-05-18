docker build -t frontend -f Dockerfile.dev .
docker run -d -p 3000:3000 frontend
