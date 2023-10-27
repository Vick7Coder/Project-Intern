FROM node:18-alpine3.18 
#set working directory
COPY package.json yarn.lock ./

RUN yarn

COPY . .


CMD [ "yarn","run start" ]