package com.ekeyfund.oss.core.common.exception;

import com.ekeyfund.oss.core.exception.ServiceExceptionEnum;

/**
 * @Description 所有业务异常的枚举
 * @author fengshuonan
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum implements ServiceExceptionEnum{

	/**
	 * 字典
	 */
	DICT_EXISTED(400,"字典已经存在"),
	ERROR_CREATE_DICT(500,"创建字典失败"),
	ERROR_WRAPPER_FIELD(500,"包装字典属性失败"),

	/**
	 * 文件上传
	 */
	FILE_READING_ERROR(400,"FILE_READING_ERROR!"),
	FILE_NOT_FOUND(400,"FILE_NOT_FOUND!"),
	UPLOAD_ERROR(500,"上传图片出错"),

	/**
	 * 权限和数据问题
	 */
	DB_RESOURCE_NULL(400,"数据库中没有该资源"),
	NO_PERMITION(405, "权限异常"),
	REQUEST_INVALIDATE(400,"请求数据格式不正确"),
	INVALID_KAPTCHA(400,"验证码不正确"),
	CANT_DELETE_ADMIN(600,"不能删除超级管理员"),
	CANT_FREEZE_ADMIN(600,"不能冻结超级管理员"),
	CANT_CHANGE_ADMIN(600,"不能修改超级管理员角色"),

	/**
	 * 账户问题
	 */
	USER_ALREADY_REG(401,"该用户已经注册"),
	NO_THIS_USER(400,"没有此用户"),
	USER_NOT_EXISTED(400, "没有此用户"),
	ACCOUNT_FREEZED(401, "账号被冻结"),
	OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
	TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),

	/**
	 * 错误的请求
	 */
	MENU_PCODE_COINCIDENCE(400,"菜单编号和副编号不能一致"),
	EXISTED_THE_MENU(400,"菜单编号重复，不能添加"),
	DICT_MUST_BE_NUMBER(400,"字典的值必须为数字"),
	REQUEST_NULL(400, "请求有错误"),
	SESSION_TIMEOUT(400, "会话超时"),
	SERVER_ERROR(500, "服务器异常"),

	/**
	 * 部门问题
	 */
	DEPT_OUT_FOUR_LEVEL(504,"部门层级不能超过四级"),
	DEPT_NAME_REPEAT(505,"部门名称重复，请修改"),
	DEPT_DEL_HAS_SUB_DEPT(506,"此部门存在有效的下级部门，请先删除下级部门"),
	DEPT_DEL_HAS_USER(507,"此部门还有可正常登录的员工用户，先将员工调整到其他部门"),
	DEPT_EDIT_PID_EQ_ID(508,"同一部门不能建立上下级"),
	DEPT_EDIT_PID_IS_CHILD_ID(509,"上级部门不能选择当前部门的下属部门"),


	/**
	 * 角色问题
	 */
	ROLE_NAME_REPEAT(400,"角色名已存在"),
	;

	BizExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private Integer code;

	private String message;

	@Override
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
