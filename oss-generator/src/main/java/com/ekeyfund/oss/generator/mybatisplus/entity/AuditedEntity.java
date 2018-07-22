/**
 * <pre>
 * Title: 		AuditedObject.java
 * Author:		linriqing
 * Create:	 	2010-7-6 下午01:51:47
 * Copyright: 	Copyright (c) 2010
 * Company:		Shenzhen Helper
 * <pre>
 */
package com.ekeyfund.oss.generator.mybatisplus.entity;

import java.io.Serializable;

/**
 * <pre>
 * 需要记录操作日志的实体接口类型
 * </pre>
 * @author linriqing
 * @version 1.0, 2010-7-6
 */
public interface AuditedEntity extends Serializable
{
	public Object getId();
}
