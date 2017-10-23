for(var i = 0; i < 13; i++) { var scriptId = 'u' + i; window[scriptId] = document.getElementById(scriptId); }

$axure.eventManager.pageLoad(
function (e) {

});

u12.style.cursor = 'pointer';
$axure.eventManager.click('u12', function(e) {

if (true) {

	self.location.href=$axure.globalVariableProvider.getLinkUrl('HistoryExam.html');

}
});
gv_vAlignTable['u4'] = 'top';gv_vAlignTable['u8'] = 'top';gv_vAlignTable['u10'] = 'top';gv_vAlignTable['u1'] = 'top';gv_vAlignTable['u6'] = 'top';gv_vAlignTable['u2'] = 'top';gv_vAlignTable['u11'] = 'top';