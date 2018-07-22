
$(function () {
/*创建一个date日历 */
/*   laydate.render({
       elem: '#beginTime' //指定ID元素
       ,theme: '#2f4050' //颜色
       ,calendar: true //显示公历
       ,type: 'datetime' //时分秒
   });*/

//同时绑定多个date日历控件
lay('.layer-date').each(function(){
    laydate.render({
        elem: this
        ,trigger: 'click'
        ,calendar: true //显示公历
        ,theme: '#2f4050' //颜色
    });
});

})


