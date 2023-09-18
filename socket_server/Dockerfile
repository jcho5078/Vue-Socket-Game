FROM node:16

# WORKDIR /usr/src/app
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# install package
# COPY /package.json ./
COPY ./socket_server/package.json /usr/src/app/
RUN npm install

# copy source
COPY . ./

# bind port & run
EXPOSE 3000
CMD ["node", "app.js"]