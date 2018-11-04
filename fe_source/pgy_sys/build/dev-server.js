const jsonServer = require('json-server')
const server = jsonServer.create()
const router = jsonServer.router('../mockup/db.json')
const middlewares = jsonServer.defaults()

server.use(middlewares)
server.use(router)
server.listen(3000, () => {//端口号需要配置
  console.log('JSON Server is running')
})