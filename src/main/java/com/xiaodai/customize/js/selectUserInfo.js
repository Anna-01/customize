//字符串拼接使用，利用数组
function StringBuffer(){
    this.buffer = [];
}
//将新添加的字符串添加到数组中
StringBuffer.prototype.append = function(str){
    this.buffer.push(str);
    return this;
};
//转成字符串
StringBuffer.prototype.toString = function(){
    return this.buffer.join("");
};

//占位符
String.prototype.format=function()
{
    if(arguments.length==0) return this;
    for(var s=this, i=0; i<arguments.length; i++)
        s=s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i]);
    return s;
};


//输出结果字符串
let resultStr = new StringBuffer();



//下载输出sql
function download(filename, text) {
    let element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
    element.setAttribute('download', filename);

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
}


//查询结束后动作
function nextAction(data, startId, endId) {
    console.log("nextActionStart");
    let resultRows = data.data.rows;
    for (let i = 0; i < resultRows.length; i++) {
        //对于输出结果进行处理
        // resultStr.append("INSERT INTO `user_flexible_black_log` (`USER_ID`, `REASON`, `CHANNEL`, `CREATE_TIME`) VALUES\t({0}, 'initial', 'weibo', '2020-09-10 11:03:33'); \n".format(resultRows[i][0]));
        resultStr.append("{0},\n,{1},\n".format(resultRows[i][0], resultRows[i][1]));
    }
    if (startId > endId){
        console.log('开始下载' )
        console.log(endId + "  " + startId)
        download("creditSkuApplyPassaa.txt", resultStr.toString());
    }
    // download("userInfo.txt", resultStr.toString());
    console.log("nextActionEnd");
}

function sleep(milliSeconds){
    var startTime = new Date().getTime();  // get the current time
    while (new Date().getTime() < startTime + milliSeconds);  // hog cpu
}

function getLocalTime(nS) {
    return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
}
//起始sql
//repeatSql= "select ID , CONFLICT_USER_ID,USER_ID from user_conflict_info where   id > {0} and id <= {1};";
repeatSql = "SELECT id,WB_TOKEN  FROM user_info WHERE WB_TOKEN LIKE '{%' AND UPDATE_TIME > {0} AND UPDATE_TIME <= {1} AND DEL_FLAG = 0;";
// repeatSql= " SELECT * FROM credit_sku_apply csa LEFT JOIN loan_info li on csa .USER_ID  = li .USER_ID WHERE li .SKU = 'flexible_loan' and li .BRAND_NAME !='weibo_jieqian' and  csa.id > {0} and csa.id <= {1}";
startId = '2020-09-15 20:00:14';
//startId = '2020-09-17 14:35:14';
//间隔三小时
interval = 3;
endId = '2020-09-16 08:00:14';


function getNewDay(dateTemp, days) {
    var dateTemp = dateTemp.split("-");
    var nDate = new Date(dateTemp[1] + '-' + dateTemp[2] + '-' + dateTemp[0]); //转换为MM-DD-YYYY格式
    var millSeconds = Math.abs(nDate) + (days * 24 * 60 * 60 * 1000);
    var rDate = new Date(millSeconds);
    var year = rDate.getFullYear();
    var month = rDate.getMonth() + 1;
    if (month < 10) month = "0" + month;
    var date = rDate.getDate();
    if (date < 10) date = "0" + date;
    return (year + "-" + month + "-" + date);
}
function getNowTime(startId, interval) {

    var ddate = new Date(startId);
    //加一小时
    ddate.setHours(ddate.getHours() + interval);
    //转回string
    var year = ddate.getFullYear();
    var month = ddate.getMonth();
    if (month < 10) month = "0" + month;
    var date = ddate.getDate();
    if (date < 10) date = "0" + date;

    var hour = ddate.getHours();
    var min = ddate.getMinutes();
    var mill = ddate.getMilliseconds();

    var finalTime = year + "-" + month + "-" + date + " " + hour + ":" + min + ":" + mill;
    console.log("最终时间" + finalTime)
    return finalTime;

}



//查询sql
function sqlQuery(sql, startId, endId, next) {
    console.log(sql);
    $.ajax({
        type: "post",
        url: "/query/",
        dataType: "json",
        data: {
            instance_name: 'POLARDB查询从库',
            //instance_name: 'POLARDB查询从库',
            db_name: 'xiaodai',
            tb_name: '',
            sql_content: sql,
            limit_num: '1000'
        },
        success: function (data) {
            console.log(data);
            next(data,startId, endId)
            if ((startId < endId)){
                sleep(50)
                startId += interval;
                sqlQuery(repeatSql.format(startId,endId), startId, endId, nextAction);
            }
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
    })
}

//开始查询
// while (startId < endId){
//     sqlQuery(repeatSql.format(startId,startId+interval), startId, endId, nextAction);
//     sleep(1000)
//     startId += interval;
// }

sqlQuery(repeatSql.format(startId,endId), startId, endId, nextAction);

function getTime(startId, interval) {
    startId + interval

}





