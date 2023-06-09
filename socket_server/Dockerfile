FROM node:16

WORKDIR /usr/src/app

# install package
COPY package.json ./
RUN npm install

# copy source
COPY . .

# bind port & run
EXPOSE 3000
CMD ["node", "app.js"]