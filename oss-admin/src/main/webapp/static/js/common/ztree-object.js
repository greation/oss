/**
 * ztree插件的封装
 */
(function() {

	var $ZTree = function(id, url) {
		this.id = id;
		this.url = url;
		this.onClick = null;
		this.settings = null;
		this.ondblclick=null;
        this.expandAll = true;// 是否默认全部展开
	};

	$ZTree.prototype = {
		/**
		 * 初始化ztree的设置
		 */
		initSetting : function() {
			var settings = {
				view : {
					dblClickExpand : true,
					selectedMulti : false
				},
				data : {simpleData : {enable : true}},
				callback : {
					onClick : this.onClick,
					onDblClick:this.ondblclick
				}
			};
			return settings;
		},

		/**
		 * 手动设置ztree的设置
		 */
		setSettings : function(val) {
			this.settings = val;
		},

		/**
		 * 初始化ztree
		 */
		init : function() {
			var zNodeSeting = null;
			if(this.settings != null){
				zNodeSeting = this.settings;
			}else{
				zNodeSeting = this.initSetting();
			}
			var zNodes = this.loadNodes();
			$.fn.zTree.init($("#" + this.id), zNodeSeting, zNodes);
		},

		/**
		 * 绑定onclick事件
		 */
		bindOnClick : function(func) {
			this.onClick = func;
		},
		/**
		 * 绑定双击事件
		 */
		bindOnDblClick : function(func) {
			this.ondblclick=func;
		},
		/**
		 * 加载节点
		 */
		loadNodes : function() {
			var zNodes = null;
			var ajax = new $ax(Feng.ctxPath + this.url, function(data) {
				zNodes = data;
			}, function(data) {
				Feng.error("加载ztree信息失败!");
			});
			ajax.start();
			return zNodes;
		},

		/**
		 * 获取选中的值
		 */
		getSelectedVal : function(){
			var zTree = $.fn.zTree.getZTreeObj(this.id);
			var nodes = zTree.getSelectedNodes();
			return nodes[0].name;
		},

        getCheckedVals : function(){
            var zTree = $.fn.zTree.getZTreeObj(this.id);
            var nodes = zTree.getCheckedNodes(true);
            var txt = '';
            var ids = '';
            if(nodes){
                $.each(nodes,function(i){
                	var node = nodes[i];
                	var id = node.id;
                	if(node.level == 0){//父子级是不为0，后期如果有父级那改为不等于0，node.level !=0;
                        ids += ',' + id;
                        txt += ',' + node.name;
                    }
                });
                txt = txt.substring(1);
                ids = ids.substring(1);
            }
            $("#roleIds").val(ids);
            return txt;
        },
        getCheckedValadd : function(){
            var zTree = $.fn.zTree.getZTreeObj(this.id);
            var nodes = zTree.getCheckedNodes(true);
            var txt = '';
            var ids = '';
            if(nodes){
                $.each(nodes,function(i){
                    var node = nodes[i];
                    var id = node.id;
                    if(node.level != 0){//父子级是不为0，后期如果有父级那改为不等于0，node.level !=0;
                        ids += ',' + id;
                        txt += ',' + node.name;
                    }
                });
                txt = txt.substring(1);
                ids = ids.substring(1);
            }
            $("#roleIds").val(ids);
            return txt;
        }
	};

	window.$ZTree = $ZTree;

}());