//1.动态创建select
function createSelect(){
    var mySelect = document.createElement_x("select");
    mySelect.id = "mySelect";
    document.body.appendChild(mySelect);
}

//2.添加选项option
function addOption(){
    //根据id查找对象，
    var obj=document.getElementByIdx_x('mySelect');

    //添加一个选项
    obj.add(new Option("文本","值"));    //这个只能在IE中有效
    obj.options.add(new Option("text","value")); //这个兼容IE与firefox
}

//3.删除所有选项option
function removeAll(){
    var obj=document.getElementByIdx_x('mySelect');
    obj.options.length=0;

}

//4.删除一个选项option
function removeOne(){
    var obj=document.getElementByIdx_x('mySelect');

    //index,要删除选项的序号，这里取当前选中选项的序号

    var index=obj.selectedIndex;
    obj.options.remove(index);
}

//5.获得选项option的值
var obj=document.getElementByIdx_x('mySelect');

var index=obj.selectedIndex; //序号，取当前选中选项的序号

var val = obj.options[index].value;

//6.获得选项option的文本
var obj=document.getElementByIdx_x('mySelect');

var index=obj.selectedIndex; //序号，取当前选中选项的序号

var val = obj.options[index].text;

//7.修改选项option
var obj=document.getElementByIdx_x('mySelect');

var index=obj.selectedIndex; //序号，取当前选中选项的序号

var val = obj.options[index]=new Option("新文本","新值");

//8.删除select
function removeSelect(){
    var mySelect = document.getElementByIdx_x("mySelect");
    mySelect.parentNode.removeChild(mySelect);
}
