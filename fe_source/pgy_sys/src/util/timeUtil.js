/**
 * @file 时间相关的方法
 * 语法参考：https://blog.csdn.net/qq_16633405/article/details/77961539
 *
 * @author Felix
 */

define(function (require) {
    var moment = require('moment');

    return {
        getStartTime: function (date) {
            return moment(date).format('YYYY-MM-DD 00:00:00');
        },
        getEndTime: function (date) {
            return moment(date).format('YYYY-MM-DD 23:59:59');
        },
        getWeekFirstDay: function (d) {
            d = d || new Date();
            return moment(new Date().setDate(d.getDate() - 6)).format('YYYY-MM-DD');
        },
        getMonthFirstDay: function (d) {
            d = d || new Date();
            return moment(d).format('YYYY-MM-01');
        },
        timeToUtc: function (timeStr) {
            return moment(timeStr).utc().format('YYYY-MM-DDTHH:mm:ss') + 'Z';
        },
        toUtcTime: function (utcTimeStr) {
            return moment(utcTimeStr).utc().format('YYYY-MM-DD HH:mm:ss');
        },
        toTime: function (utcTimeStr) {
            return utcTimeStr ? moment(utcTimeStr).format('YYYY-MM-DD HH:mm:ss') : '';
        },
        toTimeFormat: function (utcTimeStr) {
            return utcTimeStr ? moment(utcTimeStr).format('HH:mm') : '';
        },
        utcToMinute: function (utcTimeStr) {
            if (null != utcTimeStr) {
                return moment(utcTimeStr).format('YYYY-MM-DD HH:mm');
            }
            return '';
        },
        utcToMinuteCN: function (utcTimeStr) {
            if (null != utcTimeStr) {
                return moment(utcTimeStr).format('YYYY年MM月DD日HH时mm秒');
            }
            return '';
        },
        toDate: function (utcTimeStr) {
            return moment(utcTimeStr).format('YYYY-MM-DD');
        },
        toDateCN: function (utcTimeStr) {
            return moment(utcTimeStr).format('YYYY年MM月DD日');
        },
        // 检查是否是UTC格式参数
        checkUTC: function (utcTime, isEndDate) {
            var utcFormat = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]Z$/;
            var dateFormat = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
            if (utcFormat.test(utcTime)) {
                return utcTime;
            }
            else if (dateFormat.test(utcTime)) {
                return moment(utcTime + (isEndDate ? ' 23:59:59' : '')).utc().format('YYYY-MM-DDTHH:mm:ss') + 'Z';
            }

            return null;
        },
        checkTime: function (time, isEndDate) {
            var timeFormat = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}\s([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$/;
            var dateFormat = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
            if (timeFormat.test(time)) {
                return time;
            }
            else if (dateFormat.test(time)) {
                return moment(time + (isEndDate ? ' 23:59:59' : '')).format('YYYY-MM-DD HH:mm:ss');
            }

            return null;
        },
        diff: function (startTime, endTime) {
            return moment(startTime).diff(moment(endTime));
        },
        oneYearAfter: function (time) {
            return moment(time).add(1, 'years').toDate();
        },
        tomorrow: function (time) {
            return moment(time).add(1, 'days').toDate();
        }
    };
});
