package com.cxytiandi.sharding.domain.common;

import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 统一返回数据结构
 * 
 * @author opal
 * @since 2017年10月23日
 */
@Getter
@Setter
@Builder
public class JsonReturn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2218013142423759088L;

	private Integer result;
	private String msg;
	private Object data;
	private String errorMsg;
	
	public JsonReturn() {}
	
	public JsonReturn(Integer result, String msg, Object data, String errorMsg) {
		this.result=result;
		this.msg=msg;
		this.data=data;
		this.errorMsg=errorMsg;
	}

	/**
	 * 成功结果
	 * @param msg 提示语
	 * @param data 返回数据
	 * @return 结果
	 */
	public static JsonReturn success(String msg, Object data) {
		return JsonReturn.builder().result(1).msg(msg).data(data).build();
	}

	/**
	 * 分页式列表返回，
	 * 
	 * @param msg
	 *            成功提示信息
	 * @param data
	 *            成功时返回的额外数据
	 * @param list
	 *            成功时返回的列表
	 * @param page
	 *            第几页
	 * @param pageSize
	 *            每页多少条
	 * @param totalCount
	 *            总记录数
	 * @return JsonReturn 成功结果
	 */
	public static JsonReturn success(String msg, Map<String, Object> data, Collection<?> list, Integer page,
			Integer pageSize, Integer totalCount) {
		Map<String, Object> map = ImmutableMap.<String, Object>builder().put("list", list).put("page", page).put("pageSize", pageSize)
				.put("totalCount", totalCount).build();
		map.putAll(data);
		return JsonReturn.builder().result(1).msg(msg).data(map).build();
	}

	/**
	 * 游标式列表 成功返回
	 * 
	 * @param msg
	 *            成功提示信息
	 * @param data
	 *            成功时返回的额外数据
	 * @param list
	 *            成功时返回的列表
	 * @param lastKey
	 *            游标值
	 * @return 成功结果
	 */
	public static JsonReturn success(String msg, Map<String, Object> data, Collection<?> list, String lastKey) {
		Map<String, Object> map = ImmutableMap.<String, Object>builder().put("list", list).put("lastKey", lastKey).build();
		map.putAll(data);
		return JsonReturn.builder().result(1).msg(msg).data(map).build();
	}

	/**
	 * 失败结果
	 * @param errorMsg 错误提示语
	 * @return 失败结果
	 */
	public static JsonReturn error(String errorMsg) {
		return JsonReturn.builder().result(0).errorMsg(errorMsg).build();
	}

	/**
	 * 失败结果
	 * @param errorMsg 错误提示语
	 * @param data 错误时返回的数据
	 * @return 失败结果
	 */
	public static JsonReturn error(String errorMsg, Object data) {
		return JsonReturn.builder().result(0).errorMsg(errorMsg).data(data).build();
	}

	/**
	 * 返回结果
	 * @param result 结果标识
	 * @param msg 提示语
	 * @param errorMsg 错误提示语
	 * @param data 返回数据
	 * @return 返回结果
	 */
	public static JsonReturn result(int result, String msg, String errorMsg, Object data) {
		return JsonReturn.builder().result(result).msg(msg).errorMsg(errorMsg).data(data).build();
	}

	@Override
	public String toString() {
		return "JsonReturn [result=" + result + ", msg=" + msg + ", data=" + data + ", errorMsg=" + errorMsg + "]";
	}
	
	
}
