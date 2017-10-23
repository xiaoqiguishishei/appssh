<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>        
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <!--[if gt IE 8]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <![endif]-->
    <title>Aquarius - responsive admin panel - posted by Dospel & GanjaParker</title>
    <link rel="icon" type="image/ico" href="favicon.ico"/>
    <link href="css/stylesheets.css" rel="stylesheet" type="text/css" />  
    <link rel='stylesheet' type='text/css' href='css/fullcalendar.print.css' media='print' />
    <!--[if lt IE 8]>
        <link href="css/ie7.css" rel="stylesheet" type="text/css" />
    <![endif]-->            
    <link rel='stylesheet' type='text/css' href='css/fullcalendar.print.css' media='print' />
    <script type='text/javascript' src='js/jquery.min.js'></script>
    <script type='text/javascript' src='js/jquery-ui.min.js'></script>
    <script type='text/javascript' src='js/plugins/jquery/jquery.mousewheel.min.js'></script>
    <script type='text/javascript' src='js/plugins/cookie/jquery.cookies.2.2.0.min.js'></script>
    <script type='text/javascript' src='js/plugins/bootstrap.min.js'></script>
    <script type='text/javascript' src='js/plugins/sparklines/jquery.sparkline.min.js'></script>
    <script type='text/javascript' src='js/plugins/fullcalendar/fullcalendar.min.js'></script>
    <script type='text/javascript' src='js/plugins/select2/select2.min.js'></script>
    <script type='text/javascript' src='js/plugins/uniform/uniform.js'></script>
    <script type='text/javascript' src='js/plugins/maskedinput/jquery.maskedinput-1.3.min.js'></script>
    <script type='text/javascript' src='js/plugins/validation/languages/jquery.validationEngine-en.js' charset='utf-8'></script>
    <script type='text/javascript' src='js/plugins/validation/jquery.validationEngine.js' charset='utf-8'></script>
    <script type='text/javascript' src='js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js'></script>
    <script type='text/javascript' src='js/plugins/animatedprogressbar/animated_progressbar.js'></script>
    <script type='text/javascript' src='js/plugins/qtip/jquery.qtip-1.0.0-rc3.min.js'></script>
    <script type='text/javascript' src='js/plugins/cleditor/jquery.cleditor.js'></script>
    <script type='text/javascript' src='js/actions.js'></script>
    <script type='text/javascript' src='js/plugins.js'></script>
    <script type='text/javascript' src='js/plugins/plupload/plupload.js'></script>
    <script type='text/javascript' src='js/plugins/plupload/plupload.browserplus.js'></script>
    <script type='text/javascript' src='js/plugins/plupload/plupload.html4.js'></script>
    <script type='text/javascript' src='js/plugins/plupload/jquery.plupload.queue/jquery.plupload.queue.js'></script>
    <script type='text/javascript' src='js/plugins/fancybox/jquery.fancybox.pack.js'></script>
    <!-- <script type='text/javascript' src='js/cookies.js'></script> -->
    <!-- <script type='text/javascript' src='js/charts.js'></script> -->
    <!-- <script type="text/javascript" src="js/plugins/elfinder/elfinder.min.js"></script> -->
    <!-- <script type='text/javascript' src='js/plugins/plupload/plupload.gears.js'></script> -->
    <!-- <script type='text/javascript' src='js/plugins/plupload/plupload.silverlight.js'></script> -->
    <!-- <script type='text/javascript' src='js/plugins/plupload/plupload.flash.js'></script> -->
    <!-- <script type='text/javascript' src='js/plugins/plupload/plupload.html5.js'></script> -->
    <!-- <script type='text/javascript' src='http://bp.yahooapis.com/2.4.21/browserplus-min.js'></script> -->
</head>
<body>
    <!-- 头 -->
    <div class="header">
        <ul class="header_menu">
            <li class="list_icon"><a href="#">&nbsp;</a></li>
        </ul>    
    </div>
    
    <div class="menu">                
        
        <div class="breadLine">            
            <div class="arrow"></div>
            <div class="adminControl active">
                Hi, 尊敬的管理员 ${admin.username }
            </div>
        </div>
        <!-- 管理员 -->
        <div class="admin">
            <div class="image">
                <img src="img/users/aqvatarius.jpg" class="img-polaroid"/>                
            </div>
            <ul class="control">                
                <li><span class="icon-share-alt"></span> <a href="login.html">退出</a></li>
            </ul>
        </div>
        
        <ul class="navigation"> 
            <!-- 项目总结报告 -->
            <li class="active">
                <a href="index.html">
                    <span class="isw-grid"></span><span class="text">讲师管理</span>
                </a>
            </li>

            <!-- 项目情况进展表 -->
            <li>
                <a href="ui.html">
                    <span class="isw-list"></span><span class="text">班主任管理</span>
                </a>               
            </li>          
            <li>
                <a href="forms.html">
                    <span class="isw-archive"></span><span class="text">班级管理</span>                 
                </a>
            </li>                        
            <li>
                <a href="messages.html">
                    <span class="isw-chat"></span><span class="text">学生管理</span>
                </a>
            </li>                                    
            <li>
                <a href="statistic.html">
                    <span class="isw-graph"></span><span class="text">试题管理</span>
                </a>
            </li>   
            <li>
                <a href="tables.html">
                    <span class="isw-text_document"></span><span class="text">试卷管理</span>
                </a>                
            </li>   
            <li>
                <a href="user.html">
                    <span class="isw-documents"></span><span class="text">考试管理</span>
                </a>
            </li>            
        </ul>
        <div class="dr"><span></span></div>   
    </div>
        
    <div class="content">
        <div class="breadLine">
            <ul class="breadcrumb">
                <li><a>管理员</a> <span class="divider">></span></li>                
                <li class="active">项目总结报告</li>
            </ul>   
        </div>

        
        
        <div class="workplace">
                    
        <div class="row-fluid">
            <div class="span12">                    
                <div class="head clearfix">
                    <div class="isw-grid"></div>
                    <h1>总结报告列表</h1>      
                    <ul class="buttons">
                        <li><a href="javascript:;" onclick="Download()" class="isw-download"></a></li>  
						
                    </ul>                        
                </div>
            </div>                                
        </div>
        </div>
        <jsp:include page="ClassManage.jsp" />
    </div>
</body>
</html>
