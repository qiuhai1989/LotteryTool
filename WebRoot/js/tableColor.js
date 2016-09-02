/* table表格单双行颜色交替显示 在table出加上id="table1"*/
window.onload = function() {
    var Table=document.getElementById("table1");
	var s = Table.rows.length;
    for (var i=1;i<s;i++) 
    	Table.rows[i].bgColor=i%2?"":"#e3e3e3";
}