## Use the official Node.js image as the base image
FROM node:latest as build-stage

# Set the working directory inside the container
WORKDIR /app

# Copy the package.json and package-lock.json files to the container
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code to the container
COPY . .

# Build the React app
RUN npm run build

# Use NGINX as the lightweight web server to serve the built React files
FROM nginx:latest as production-stage

# Copy SSL certificates to the container
COPY nginx-selfsigned.crt /etc/nginx/cert.crt
COPY nginx-selfsigned.key /etc/nginx/private.key
#
# Copy NGINX configuration file with HTTPS support
COPY --from=build-stage /app/build /usr/share/nginx/html

COPY nginx.conf /etc/nginx/nginx.conf
# Copy the built React app from the build-stage container to the NGINX web server

# Expose port 80 (default port for HTTP)
EXPOSE 80
EXPOSE 443

# Start NGINX when the container starts
CMD ["nginx", "-g", "daemon off;"]


# Use the official Node.js image as the base image
#FROM node:16-alpine
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the package.json and package-lock.json files to the container
#COPY package*.json ./
#
## Install dependencies
#RUN npm install
#
## Copy the rest of the application code to the container
#COPY . .
#
## Build the React app
#RUN npm run build
#
## Install `serve` to serve the build folder
#RUN npm install -g serve@10.1.1
#COPY cert.pem /etc/ssl/cert.pem
#COPY key.pem /etc/ssl/key.pem
## Set the command to serve the application with HTTPS
#CMD ["serve", "-s", "build", "-l", "3000", "--ssl-cert", "/etc/ssl/cert.pem", "--ssl-key", "/etc/ssl/key.pem"]
## Set the command to serve the application
##CMD ["serve", "-s", "build", "-l", "3000"]
#
## Expose the port the app runs on
#EXPOSE 3000
