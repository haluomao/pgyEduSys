import {baseUrl} from './config/env'
//引入mockjs
const Mock = require('mockjs')
//使用mockjs模拟数据

Mock.mock('/api/v1/upload', (req, res) => {
    return {
		"success": true,
		"result": {
			"url" :"a.com/1.txt"
		}
	}
});


Mock.mock('/api/v1/logout', (req, res) => {
    return {
		"success": true
	}
});

Mock.mock('/api/v1/login', (req, res) => {
    return {
		"success": true,
		"result": {
			"accountId": 1,
		    "username": "user",
		    "role": "ADMIN"
		}
	}
});

Mock.mock('/api/v1/grade/all', (req, res) => {
    return {
		"success": true,
		"result": [{
		    "name": "一年级",
		    "value": 1
		},{
		    "name": "二年级",
		    "value": 2
		},{
		    "name": "三年级",
		    "value": 3
		}]
	}
});

Mock.mock('/api/v1/grade/list', (req, res) => {
    return {
		"success": true,
		"page": {
		    "totalCount": 2,
		    "pageNo": 1,
		    "pageSize": 10,
		    "orderBy": "id",
		    "order": "desc",
		    "result": []
		}
	}
});

Mock.mock('/api/v1/material/list', (req, res) => {
    return {
		"success": true,
		"page": {
		    "totalCount": 2,
		    "pageNo": 1,
		    "pageSize": 10,
		    "orderBy": "id",
		    "order": "desc",
		    "result": [
		        {
		            "id": 1,
		            "name": "category1",
		            "description": "desc1",
		            "price": 100,
		            "status": "ENABLED",
		            "url": "www.baidu.com"
		        },
		        {
		            "id": 2,
		            "name": "category2",
		            "description": "desc2",
		            "price": 200,
		            "status": "ENABLED",
		            "url": "www.baidu.com"
		        }
		    ]
		}
	}
});

Mock.mock('/api/v1/category/all', (req, res) => {
    return {
		"success": true,
		"result": [{
		    "name": "数学",
		    "value": 1
		},{
		    "name": "语文",
		    "value": 2
		}]
	}
});
Mock.mock('/api/v1/category/create', (req, res) => {
    return {
		"success": true
	}
});
Mock.mock('/api/v1/category/update', (req, res) => {
    return {
		"success": true
	}
});
Mock.mock('/api/v1/category/list', (req, res) => {
    return {
		"success": true,
		"page": {
		    "totalCount": 2,
		    "pageNo": 1,
		    "pageSize": 10,
		    "orderBy": "id",
		    "order": "desc",
		    "result": [
		        {
		            "id": 1,
		            "name": "category1",
		            "description": "desc1",
		            "price": 100,
		            "status": "ENABLED"
		        },
		        {
		            "id": 2,
		            "name": "category2",
		            "description": "desc2",
		            "price": 200,
		            "status": "ENABLED"
		        }
		    ]
		}
	}
});

Mock.mock('/api/v1/account/create', (req, res) => {
    return {
    	"success": true,
    	"result": 6
    }
});

Mock.mock('/api/v1/account/list', (req, res) => {
    return {
		"success": true,
		"result": [{
			"id": 1,
			"accountName": "admin2",
			"username": "user",
			"role": "ADMIN",
			"balance": 0,
			"email": "a@a.com",
			"phone": "1234567890",
			"permission": "ALL",
			"parentId": 0,
			"status": "ENABLED",
			"beginTime": 1466685616000,
			"endTime": 1466685616000,
			"config": {
				"id": 1,
				"accountId": 1,
				"teacherLimit": 10,
				"parentLimit": 50,
				"storageLimit": 1000,
				"storageUsed": 0,
				"status": "ENABLED",
				"beginTime": 1466685616000,
				"endTime": 1466685616000
			},
			"teacherCount": 0,
			"parentCount": 0
		}, {
			"id": 2,
			"accountName": "guest",
			"username": "user",
			"role": "TEACHER",
			"balance": 0,
			"email": "b@b.com",
			"phone": "1234567890",
			"permission": "ALL",
			"parentId": 0,
			"status": "ENABLED",
			"beginTime": 1466685661000,
			"endTime": 1466685661000,
			"config": {
				"id": 2,
				"accountId": 2,
				"teacherLimit": 20,
				"parentLimit": 100,
				"storageLimit": 2000,
				"storageUsed": 0,
				"status": "ENABLED",
				"beginTime": 1466685661000,
				"endTime": 1466685661000
			},
			"teacherCount": 0,
			"parentCount": 0
		}]
	}
})