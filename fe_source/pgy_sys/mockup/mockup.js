
const session = require('./session');
const mockup = {};
const kWhiteListKeys = {};

function injectXSSCode(root) {
    if (session.get('no_xss') === true) {
        return root;
    }

    if (Array.isArray(root)) {
        for (let i = 0; i < root.length; i++) {
            root[i] = injectXSSCode(root[i]);
        }
    }
    else if (typeof root === 'object') {
        for (let key in root) {
            if (kWhiteListKeys[key]) {
                continue;
            }

            root[key] = injectXSSCode(root[key]);
        }
    }
    else if (typeof root === 'string') {
        return root + '<xmp>';
    }

    return root;
}

mockup.ok = function (result = {}, timeout) {
    return injectXSSCode({
        success: true,
        result,
        timeout
    });
};

mockup.list = function (result, page = {}) {
    return injectXSSCode({
        success: true,
        page: {
            totalCount: page.totalCount || 100,
            pageNo: page.pageNo || 1,
            pageSize: page.pageSize || 15,
            orderBy: page.orderBy || 'id',
            order: page.order || 'desc',
            result: result || []
        }
    });
};

mockup.fail = function (msg) {
    return injectXSSCode({
        success: false,
        message: msg || ''
    });
};
