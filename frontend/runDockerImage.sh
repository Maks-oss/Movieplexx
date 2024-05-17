docker build -t frontend .
docker run -d -p 443:443 frontend
