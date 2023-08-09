# FROM node:18
# # 경로 설정하기
# WORKDIR /app

# COPY package.json .

# RUN npm install

# COPY . .

# EXPOSE 3000

# CMD ["npm", "start"]

FROM node:18

RUN npm install -g serve

RUN mkdir ./build

COPY ./build ./build

ENTRYPOINT ["serve", "-s", "build"]