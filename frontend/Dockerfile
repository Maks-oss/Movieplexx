## Use the official Node.js image as the base image
FROM node:latest as build-stage

# Set the working directory inside the container
WORKDIR /app

# Copy the package.json and package-lock.json files to the container
COPY package*.json ./

# Install dependencies
RUN npm install  \
    && npm install @mui/material @emotion/react @emotion/styled @mui/icons-material \
    && npm install react-router-dom@6

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

