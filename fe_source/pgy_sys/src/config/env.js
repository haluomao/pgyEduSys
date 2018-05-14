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

if (process.env.NODE_ENV == 'development') {
	// baseUrl = 'http://mockserver/';
 //    baseUploadPath = 'http://mockserver/api/upload';

    // Use mock data,(goto main.js first) remove the // below. then go api/getData.js.
 	baseUrl = 'http://localhost:8899/';
 	baseUploadPath = 'http://localhost:8899/api/upload';
} else {
	baseUrl = 'http://ip:7000';
    baseUploadPath = 'http://ip:7000/img/';
}

export {
	baseUrl,
	routerMode,
	baseUploadPath
}