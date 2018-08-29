/**
 * 配置编译环境和线上环境之间的切换
 * 
 * baseUrl: 域名地址
 * routerMode: 路由模式
 * baseImgPath: 图片存放地址
 * 
 */
let baseUrl = ''; 
let routerMode = 'history';
let baseUploadPath;
let baseLoginUrl = '';

if (process.env.NODE_ENV == 'development') {
	baseUrl = 'http://mockserver';
    baseUploadPath = 'http://mockserver/api/v1/upload';
    baseLoginUrl= 'http://mockserver/#login';

    // Use mock data,(goto main.js first) remove the // below. then go api/getData.js.
 	// baseUrl = 'http://localhost:8899/';
 	// baseUploadPath = '/api/v1/upload';
} else {
	baseUrl = 'http://180.76.171.104:8080';
    baseUploadPath = 'http://180.76.171.104:8080/api/v1/upload';
    baseLoginUrl = 'http://180.76.171.104:8080/pgy/#/login';
}

export {
	baseUrl,
	routerMode,
	baseUploadPath,
	baseLoginUrl
}