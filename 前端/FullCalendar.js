//时区的设置
/*
timezone
设置日历的时区范围。默认false，还可以是'local','UTC'或者字符串如'America/Chicago'。

1.false：默认，没有时区

如果不存储时区信息，那么所有活动事件在所有客户机上呈现一致性，时间上跟时区就没关系了。

2.'local'：客户机时区

使用本地时区，则你的活动事件在不同时区的客户机上会显示客户机的当前所在时区的时间。

3.'UTC'：世界标准时间

日历中的事件活动以标准时间展示。

4.时区字符串：例如"America/Chicago"

根据世界各地时区差别，定制某个时区时间。

*/

//一个例子
$('#calendar').fullCalendar({
    buttonText: {
        today: '今天',
        month: '月视图',
        week: '周视图',
        day: '日视图'
    },
    allDayText: "全天",
    timeFormat: {
        '': 'H:mm{-H:mm}'
    },
    weekMode: "variable",
    columnFormat: {
        month: 'dddd',
        week: 'dddd M-d',
        day: 'dddd M-d'
    },
    titleFormat: {
        month: 'yyyy年 MMMM月',
        week: "[yyyy年] MMMM月d日 { '&#8212;' [yyyy年] MMMM月d日}",
        day: 'yyyy年 MMMM月d日 dddd'
    },
    monthNames: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
    dayNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
    header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,agendaWeek,agendaDay'
    },
    eventClick: function (date, allDay, jsEvent, view) {
        //...
    },
    events: function (start, end, callback) {
        //...
    }
});