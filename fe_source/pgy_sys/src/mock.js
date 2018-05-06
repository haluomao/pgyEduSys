import {baseUrl} from './config/env'
//引入mockjs
const Mock = require('mockjs')
//使用mockjs模拟数据
Mock.mock('/api/category/create', (req, res) => {
    return {
		"success": true
	}
});
Mock.mock('/api/category/update', (req, res) => {
    return {
		"success": true
	}
});
Mock.mock('/api/category/list', (req, res) => {
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
})