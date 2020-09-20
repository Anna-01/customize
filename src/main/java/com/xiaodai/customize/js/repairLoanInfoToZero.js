//字符串拼接使用，利用数组
function StringBuffer() {
    this.buffer = [];
}

//将新添加的字符串添加到数组中
StringBuffer.prototype.append = function (str) {
    this.buffer.push(str);
    return this;
};
//转成字符串
StringBuffer.prototype.toString = function () {
    return this.buffer.join("");
};

//占位符
String.prototype.format = function () {
    if (arguments.length == 0) return this;
    for (var s = this, i = 0; i < arguments.length; i++)
        s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
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
function nextAction(data, index, length) {
    console.log("nextActionStart");
    let resultRows = data.data.rows;
    for (let i = 0; i < resultRows.length; i++) {
       /* loanStatus = resultRows[i][2];
        if (loanStatus === '002001008') {
            resultStr.append("update loan_info set update_time =now(), REJECTION_DAYS = 0,  loan_status='002001008' , is_unique = null where id = {0}; \n".format(resultRows[i][0]));
        } else if (loanStatus === '002001002') {
            resultStr.append("update loan_info set update_time =now(), REJECTION_DAYS = 0,  loan_status='002001008' , is_unique = null where id = {0}; \n".format(resultRows[i][0]));
            resultStr.append("update user_scene_status set status = '001001003', reject_day = 0 where del_flag=0 and user_id = {0}; \n".format(resultRows[i][1]))
        }else {

        }*/

        token = resultRows[i][1];
        if (isJSON(token)) {
            var nToken = JSON.parse(token);
            token = nToken.weibo_jieqian;
            resultStr.append("update user_info set WB_TOKEN = {0} where id = {1}; \n".format(token, resultRows[i][0]));
        }

    }
    if (index + 1 === length) {
        console.log('开始下载');
        download("loanInfoToZero.txt", resultStr.toString());
    }
    // download("userInfo.txt", resultStr.toString());
    console.log("nextActionEnd");
}

function sleep(milliSeconds) {
    var startTime = new Date().getTime();  // get the current time
    while (new Date().getTime() < startTime + milliSeconds) ;  // hog cpu
}


//起始sql
repeatSql = "select id,wb_token from user_info where  id = {0}  and DEL_FLAG = 0";

//查询sql
function sqlQuery(sql, index, length, next) {
    console.log(sql);
    $.ajax({
        type: "post",
        url: "/query/",
        dataType: "json",
        data: {
            instance_name: 'POLARDB查询从库',
            db_name: 'xiaodai',
            tb_name: '',
            sql_content: sql,
            limit_num: '1000'
        },
        success: function (data) {
            next(data, index, length)
        }, error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
    })
}
userIds = "23816811,7383749,45607315,7142183,42647288,25177231,4696172,30421642,58208537,2806633";

userIdList = userIds.split(",");



for (var i = 0; i < userIdList.length; i++) {
    sqlQuery(repeatSql.format(userIdList[i]), i, userIdList.length, nextAction)
    sleep(100)

}

/**
 * 判断是否是json格式
 * @param str
 * @returns {boolean}
 */
function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(typeof obj == 'object' && obj ){
                return true;
            }else{
                return false;
            }

        } catch(e) {
            console.log('error：'+str+'!!!'+e);
            return false;
        }
    }
    console.log('It is not a string!')
}





