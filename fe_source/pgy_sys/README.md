# pgy_sys

> A Vue.js project

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).

## npm installs

cnpm install axios --save
cnpm install js-md5 --save
cnpm install lodash --save
cnpm install moment --save



## 语法

Object.assign() 方法用于将所有可枚举属性的值从一个或多个源对象复制到目标对象。它将返回目标对象。

    const object1 = {
      a: 1,
      b: 2,
      c: 3
    };

    const object2 = Object.assign({c: 4, d: 5}, object1);

    console.log(object2.c, object2.d);
    // expected output: 3 5

lodash.defaults(a, b)
给对象添加字段，保持原来字段的值

    var o = {
        name: 'a'
    };

    _.defaults(o, {
        name: 'b'
    });
    // expected o: {name: 'a'}

lodash.omit(a, b)
去除对象中的某些字段

    var o2 = {
        spcecialty: 'c',
        employer: 'd'
    };
    _.omit(o2, 'employer');
    // expected o2: {spcecialty: 'c'}

    // 去除字段值为bool,o, null的字段。
    var r = _.omit(o, function(value){
        return !(!_.isBoolean(value) && value);
    });

遍历：  
_.forEach(collection, function(name, index){})