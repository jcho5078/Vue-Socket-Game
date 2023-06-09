# node image for npm build
FROM node AS builder
WORKDIR /app
# path for docker-compose
COPY front_vue/package.json ./

# copy all file and do build
COPY ./front_vue/ .
RUN npm install
RUN npm run build

# nginx image
FROM nginx:stable-alpine as production-stage
RUN mkdir /app
RUN echo 'ls /app/dist'

# copy build source to nginx
COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 80