FROM node:18-alpine3.18 
#Set working directory

COPY package.json package-lock.json ./

RUN npm install

COPY . .

CMD [ "npm", "start" ]
