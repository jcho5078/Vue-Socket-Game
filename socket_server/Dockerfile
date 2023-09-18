FROM node:16-alpine3.11

# WORKDIR /usr/src/app
WORKDIR /socket_server

# install package
COPY package.json .
#COPY ./socket_server/package.json /app/
RUN npm install

# copy source
#COPY ./socket_server /app
COPY . .

# bind port & run
EXPOSE 3000
#CMD ["node", "app.js"]
CMD npm start