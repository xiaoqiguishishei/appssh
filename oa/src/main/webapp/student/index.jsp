﻿<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Untitled Document</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <link type="text/css" href="resources/css/reset.css" rel="Stylesheet" />
    <link type="text/css" href="resources/css/default.css" rel="Stylesheet" />

    <script type="text/javascript">
        if (location.href.toString().indexOf('file://localhost/') == 0) {
            location.href = location.href.toString().replace('file://localhost/', 'file:///');
        }
    </script>

    <script type="text/javascript" src="resources/scripts/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="resources/scripts/splitter.js"></script>
    <script type="text/javascript" src="resources/scripts/axutils.js"></script>
    <script type="text/javascript" src="resources/scripts/axprototype.js"></script>
    <script type="text/javascript" src="resources/scripts/messagecenter.js"></script>
    <script type="text/javascript" src="data/configuration.js"></script>
    <script type="text/javascript" src="data/sitemap.js"></script>
    <style type="text/css">

#outerContainer {
	width:100%;
	height:1000px;
}

.vsplitbar {
	width: 3px;
	background: #DDD;
}

#rightPanel {
    background-color: White;
}

#leftPanel 
{
    /*min-width: 190px;*/
}

.splitterMask
{
   position:absolute;
   top: 0;
   left: 0;
   width: 100%;
   height: 100%;
   overflow: hidden;
   background-image: url(resources/images/transparent.gif);
   z-index: 20000;
}


    </style>
    <script type="text/javascript" language="JavaScript"><!--
        // isolate scope
        (function () {
            setUpController();

            var _settings = {};
            _settings.projectId = configuration.prototypeId;
            _settings.isAxshare = configuration.isAxshare;
            _settings.loadFeedbackPlugin = configuration.loadFeedbackPlugin;

            $axure.player.settings = _settings;

            $(window).bind('load', function () {
                if (CHROME_5_LOCAL && !$('body').attr('pluginDetected')) {
                    window.location = 'resources/chrome/chrome.html';
                }
            });

            $(document).ready(function () {

                $axure.page.bind('load.start', mainFrame_onload);
                $axure.messageCenter.addMessageListener(messageCenter_message);

                if ($axure.player.settings.loadFeedbackPlugin == true) {
                    if ($axure.player.settings.isAxshare == true) {
                        $axure.utils.loadJS('/Scripts/plugins/feedback/feedback.js');
                    } else {
                        $axure.utils.loadJS('http://share.axure.com/Scripts/plugins/feedback/feedback.js');
                    }
                }

                initialize();
                $('#outerContainer').splitter({
                    //outline: true,
                    sizeLeft: 250
                });
                $('#leftPanel').width(250);

                $(window).resize(function () {
                    resizeContent();
                });

                //                $('#outerContainer').resize(function() { return false; });

                $('#maximizePanelContainer').hide();

                initializeLogo();

                $(window).resize();
                //                $('#outerContainer').trigger('resize');
                resizeContent();
                if ($.browser.msie && $.browser.version == "6.0") {
                    // wait for ie to get to a good state and resize
                    setTimeout(function() { $('#outerContainer').trigger('resize'); }, 30);
                }
            });


        })();

        lastLeftPanelWidth = 250;

        function messageCenter_message(message, data) {
            if (message == 'expandFrame') expand();
        }

        function resizeContent() {
            var newHeight = $(window).height();
            var newWidth = $(window).width();
            
            var controlContainerHeight = newHeight - 37;
            if ($('#interfaceControlFrameLogoContainer').is(':visible')) {
                controlContainerHeight -= $('#interfaceControlFrameLogoContainer').height() + 16;
            }

            $('#outerContainer').height(newHeight).width(newWidth);
            $('#leftPanel').height(newHeight);
            $('#interfaceControlFrame').height(newHeight);
            $('#interfaceControlFrameContainer').height(controlContainerHeight);

            $('#rightPanel').height(newHeight);
            $('#mainFrame').height(newHeight);

            $('#rightPanel').width($(window).width() - $('#leftPanel').width() - $('.vsplitbar').width());
        }

        function collapse() {
            $('#maximizePanelContainer').show();
            lastLeftPanelWidth = $('#leftPanel').width();
            $('#leftPanel').hide();
            $('.vsplitbar').hide();
            $('#rightPanel').width($(window).width());
            $(window).resize();
            $('#outerContainer').trigger('resize');
        }

        function expand() {
            $('#maximizePanelContainer').hide();
            $('#leftPanel').width(lastLeftPanelWidth);
            $('#leftPanel').show();
            $('.vsplitbar').show();
            $('#rightPanel').width($(window).width() - $('#leftPanel').width() - $('.vsplitbar').width());
            $(window).resize();
            $('#outerContainer').trigger('resize');
        }

        function initialize() {
            var mainFrame = document.getElementById("mainFrame");
            var pageName = QueryString("Page");
            if (pageName.length > 0) {
                mainFrame.contentWindow.location.href = pageName + ".html";
            }
            else {
                mainFrame.contentWindow.location.href = 
                    (sitemap.rootNodes.length > 0 ? sitemap.rootNodes[0].url : "about:blank");
            }
        }

        function initializeLogo() {
            if (configuration.logoImagePath) {
                $('#interfaceControlFrameLogoImageContainer').html('<img id="logoImage" src="" />');
                $('#logoImage').attr('src', configuration.logoImagePath).load(function () {
                    resizeContent();
                });
            } else {
                $('#interfaceControlFrameLogoImageContainer').hide();
            }

            if (configuration.logoImageCaption) {
                $('#interfaceControlFrameLogoCaptionContainer').html(configuration.logoImageCaption);
            } else {
                $('#interfaceControlFrameLogoCaptionContainer').hide();
            }

            if (!$('#interfaceControlFrameLogoImageContainer').is(':visible') && !$('#interfaceControlFrameLogoCaptionContainer').is(':visible')) {
                $('#interfaceControlFrameLogoContainer').hide();
            }
        }

        function mainFrame_onload() {
            //var mainFrame = document.getElementById("mainFrame");
            if ($axure.page.pageName) {
                //document.title = mainFrame.contentWindow.PageName;
                document.title = $axure.page.pageName;
            }
        }

        function QueryString(query) {
            var qstring = self.location.href.split("?");
            if (qstring.length < 2) return ""

            var prms = qstring[1].split("&");
            var frmelements = new Array();
            var currprmeter, querystr = "";

            for (i = 0; i < prms.length; i++) {
                currprmeter = prms[i].split("=");
                frmelements[i] = new Array();
                frmelements[i][0] = currprmeter[0];
                frmelements[i][1] = currprmeter[1];
            }

            for (j = 0; j < frmelements.length; j++) {
                if (frmelements[j][0] == query) {
                    querystr = frmelements[j][1];
                    break;
                }
            }
            return querystr;
        }

--></script>

    <link type="text/css" rel="Stylesheet" href="plugins/sitemap/styles/sitemap.css" />
    <script type="text/javascript" src="plugins/sitemap/sitemap.js"></script>
    <link type="text/css" rel="Stylesheet" href="plugins/page_notes/styles/page_notes.css" />
    <script type="text/javascript" src="plugins/page_notes/page_notes.js"></script>

</head>
<body scroll="no">
    <div id="outerContainer">

        <div id="leftPanel">
            <div id="interfaceControlFrame">
                <div id="interfaceControlFrameMinimizeContainer">
                    <a title="Collapse Controls" id="interfaceControlFrameMinimizeButton" onclick="collapse();">&nbsp;</a>
                </div>
                <div id="interfaceControlFrameLogoContainer">
                    <div id="interfaceControlFrameLogoImageContainer"></div>
                    <div id="interfaceControlFrameLogoCaptionContainer"></div>
                </div>
                <div id="interfaceControlFrameHeaderContainer">
                    <ul id="interfaceControlFrameHeader">
                    </ul>
                </div>
                <div id="interfaceControlFrameContainer">
                </div>
            </div>
        </div>
        <div id="rightPanel">
            <iframe id="mainFrame" width="100%" height="100%" src="" frameborder="0"></iframe>
        </div>

    </div>

    <div id="maximizePanelContainer">
        <iframe id="expandFrame" src="resources/expand.html" width="100%" height="100%" scrolling="no" allowtransparency="true" frameborder="0"></iframe>
    </div>

</body>
</html>
