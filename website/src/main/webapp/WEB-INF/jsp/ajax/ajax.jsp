<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Title</title>

    <script src="//js.3conline.com/wap/pconline/2018/js/swiper.js" charset="utf-8"></script>

    <script type="text/javascript">
        function load() {
            $.ajax({
                type: "POST",
                url: "https://best.pconline.com.cn/intf/app/baby/getTopicTagsAndTypes.jsp",
                data: {},
                dataType: 'json',
                crossDomain:true,
                success: function (res) {
                    if(res.status==0){
                        renderArea(res.typeList,res.tags); // typeList, tags 为借口返回的属性
                    }else{
                        showTip("网络请求不成功");
                    }
                }
            });
        }

        // 提示层
        function showTip(str){
            if($('.popTip').length==0){
                $('body').append('<p class="popTip">'+str+'</p>');
                var tipT = setTimeout(function(){
                    $('.popTip').remove();
                },2500)
            }
        }

        // http://js.3conline.com/pconlinenew/data/app/2019/best_muying/js/muying.js
        function renderArea(areaData,tagData){
            console.log(areaData);
            console.log(tagData);
        }
    </script>

</head>
<body>
<button onclick="load()">滴滴我</button>

</body>
</html>