@/*
    表单中textarea框标签中各个参数的说明:

    hidden : input hidden框的id
    id : textarea框id
    name : textarea框名称
    readonly : readonly属性
    style : 附加的css属性
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-8">
        <textarea class="form-control" id="${id}" name="${id}">@if(isNotEmpty(value)){
${tool.showValue(value)}
@}
</textarea>

</div>
</div>