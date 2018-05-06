var mockup = require('../mockup');

exports.response = function (path, params) {

    return mockup.ok([
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
    ]);
};
