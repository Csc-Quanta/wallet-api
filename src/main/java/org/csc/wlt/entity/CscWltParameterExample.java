package org.csc.wlt.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CscWltParameterExample {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	protected String orderByClause;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	protected boolean distinct;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the
	 * database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	protected List<Criteria> oredCriteria;

	protected int offset;

	protected int limit;

	protected String sumCol;

	protected String groupSelClause;

	protected boolean forUpdate;

	protected String groupByClause;

	String selectCols;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public CscWltParameterExample() {
		oredCriteria = new ArrayList<Criteria>();
		offset = 0;
		limit = Integer.MAX_VALUE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
		this.offset = 0;
		this.limit = Integer.MAX_VALUE;
		this.sumCol = null;
		this.groupSelClause = null;
		this.groupByClause = null;
		this.forUpdate = false;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getLimit() {
		return limit;
	}

	public void setSumCol(String sumCol) {
		this.sumCol = sumCol;
	}

	public String getSumCol() {
		return sumCol;
	}

	public void setGroupSelClause(String groupSelClause) {
		this.groupSelClause = groupSelClause;
	}

	public String getGroupSelClause() {
		return groupSelClause;
	}

	public void setForUpdate(boolean forUpdate) {
		this.forUpdate = forUpdate;
	}

	public boolean isForUpdate() {
		return forUpdate;
	}

	public void setGroupByClause(String groupByClause) {
		this.groupByClause = groupByClause;
	}

	public String getGroupByClause() {
		return groupByClause;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the
	 * database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Data
	public abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		public GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		public void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		public void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		public void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andParamIdIsNull() {
			addCriterion("param_id is null");
			return (Criteria) this;
		}

		public Criteria andParamIdIsNotNull() {
			addCriterion("param_id is not null");
			return (Criteria) this;
		}

		public Criteria andParamIdEqualTo(Integer value) {
			addCriterion("param_id =", value, "paramId");
			return (Criteria) this;
		}

		public Criteria andParamIdNotEqualTo(Integer value) {
			addCriterion("param_id <>", value, "paramId");
			return (Criteria) this;
		}

		public Criteria andParamIdGreaterThan(Integer value) {
			addCriterion("param_id >", value, "paramId");
			return (Criteria) this;
		}

		public Criteria andParamIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("param_id >=", value, "paramId");
			return (Criteria) this;
		}

		public Criteria andParamIdLessThan(Integer value) {
			addCriterion("param_id <", value, "paramId");
			return (Criteria) this;
		}

		public Criteria andParamIdLessThanOrEqualTo(Integer value) {
			addCriterion("param_id <=", value, "paramId");
			return (Criteria) this;
		}

		public Criteria andParamIdIn(List<Integer> values) {
			addCriterion("param_id in", values, "paramId");
			return (Criteria) this;
		}

		public Criteria andParamIdNotIn(List<Integer> values) {
			addCriterion("param_id not in", values, "paramId");
			return (Criteria) this;
		}

		public Criteria andParamIdBetween(Integer value1, Integer value2) {
			addCriterion("param_id between", value1, value2, "paramId");
			return (Criteria) this;
		}

		public Criteria andParamIdNotBetween(Integer value1, Integer value2) {
			addCriterion("param_id not between", value1, value2, "paramId");
			return (Criteria) this;
		}

		public Criteria andParamCodeIsNull() {
			addCriterion("param_code is null");
			return (Criteria) this;
		}

		public Criteria andParamCodeIsNotNull() {
			addCriterion("param_code is not null");
			return (Criteria) this;
		}

		public Criteria andParamCodeEqualTo(String value) {
			addCriterion("param_code =", value, "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamCodeNotEqualTo(String value) {
			addCriterion("param_code <>", value, "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamCodeGreaterThan(String value) {
			addCriterion("param_code >", value, "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamCodeGreaterThanOrEqualTo(String value) {
			addCriterion("param_code >=", value, "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamCodeLessThan(String value) {
			addCriterion("param_code <", value, "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamCodeLessThanOrEqualTo(String value) {
			addCriterion("param_code <=", value, "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamCodeLike(String value) {
			addCriterion("param_code like", value, "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamCodeNotLike(String value) {
			addCriterion("param_code not like", value, "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamCodeIn(List<String> values) {
			addCriterion("param_code in", values, "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamCodeNotIn(List<String> values) {
			addCriterion("param_code not in", values, "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamCodeBetween(String value1, String value2) {
			addCriterion("param_code between", value1, value2, "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamCodeNotBetween(String value1, String value2) {
			addCriterion("param_code not between", value1, value2, "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamValueIsNull() {
			addCriterion("param_value is null");
			return (Criteria) this;
		}

		public Criteria andParamValueIsNotNull() {
			addCriterion("param_value is not null");
			return (Criteria) this;
		}

		public Criteria andParamValueEqualTo(String value) {
			addCriterion("param_value =", value, "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamValueNotEqualTo(String value) {
			addCriterion("param_value <>", value, "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamValueGreaterThan(String value) {
			addCriterion("param_value >", value, "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamValueGreaterThanOrEqualTo(String value) {
			addCriterion("param_value >=", value, "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamValueLessThan(String value) {
			addCriterion("param_value <", value, "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamValueLessThanOrEqualTo(String value) {
			addCriterion("param_value <=", value, "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamValueLike(String value) {
			addCriterion("param_value like", value, "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamValueNotLike(String value) {
			addCriterion("param_value not like", value, "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamValueIn(List<String> values) {
			addCriterion("param_value in", values, "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamValueNotIn(List<String> values) {
			addCriterion("param_value not in", values, "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamValueBetween(String value1, String value2) {
			addCriterion("param_value between", value1, value2, "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamValueNotBetween(String value1, String value2) {
			addCriterion("param_value not between", value1, value2, "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamDescIsNull() {
			addCriterion("param_desc is null");
			return (Criteria) this;
		}

		public Criteria andParamDescIsNotNull() {
			addCriterion("param_desc is not null");
			return (Criteria) this;
		}

		public Criteria andParamDescEqualTo(String value) {
			addCriterion("param_desc =", value, "paramDesc");
			return (Criteria) this;
		}

		public Criteria andParamDescNotEqualTo(String value) {
			addCriterion("param_desc <>", value, "paramDesc");
			return (Criteria) this;
		}

		public Criteria andParamDescGreaterThan(String value) {
			addCriterion("param_desc >", value, "paramDesc");
			return (Criteria) this;
		}

		public Criteria andParamDescGreaterThanOrEqualTo(String value) {
			addCriterion("param_desc >=", value, "paramDesc");
			return (Criteria) this;
		}

		public Criteria andParamDescLessThan(String value) {
			addCriterion("param_desc <", value, "paramDesc");
			return (Criteria) this;
		}

		public Criteria andParamDescLessThanOrEqualTo(String value) {
			addCriterion("param_desc <=", value, "paramDesc");
			return (Criteria) this;
		}

		public Criteria andParamDescLike(String value) {
			addCriterion("param_desc like", value, "paramDesc");
			return (Criteria) this;
		}

		public Criteria andParamDescNotLike(String value) {
			addCriterion("param_desc not like", value, "paramDesc");
			return (Criteria) this;
		}

		public Criteria andParamDescIn(List<String> values) {
			addCriterion("param_desc in", values, "paramDesc");
			return (Criteria) this;
		}

		public Criteria andParamDescNotIn(List<String> values) {
			addCriterion("param_desc not in", values, "paramDesc");
			return (Criteria) this;
		}

		public Criteria andParamDescBetween(String value1, String value2) {
			addCriterion("param_desc between", value1, value2, "paramDesc");
			return (Criteria) this;
		}

		public Criteria andParamDescNotBetween(String value1, String value2) {
			addCriterion("param_desc not between", value1, value2, "paramDesc");
			return (Criteria) this;
		}

		public Criteria andCreatedTimeIsNull() {
			addCriterion("created_time is null");
			return (Criteria) this;
		}

		public Criteria andCreatedTimeIsNotNull() {
			addCriterion("created_time is not null");
			return (Criteria) this;
		}

		public Criteria andCreatedTimeEqualTo(Date value) {
			addCriterion("created_time =", value, "createdTime");
			return (Criteria) this;
		}

		public Criteria andCreatedTimeNotEqualTo(Date value) {
			addCriterion("created_time <>", value, "createdTime");
			return (Criteria) this;
		}

		public Criteria andCreatedTimeGreaterThan(Date value) {
			addCriterion("created_time >", value, "createdTime");
			return (Criteria) this;
		}

		public Criteria andCreatedTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("created_time >=", value, "createdTime");
			return (Criteria) this;
		}

		public Criteria andCreatedTimeLessThan(Date value) {
			addCriterion("created_time <", value, "createdTime");
			return (Criteria) this;
		}

		public Criteria andCreatedTimeLessThanOrEqualTo(Date value) {
			addCriterion("created_time <=", value, "createdTime");
			return (Criteria) this;
		}

		public Criteria andCreatedTimeIn(List<Date> values) {
			addCriterion("created_time in", values, "createdTime");
			return (Criteria) this;
		}

		public Criteria andCreatedTimeNotIn(List<Date> values) {
			addCriterion("created_time not in", values, "createdTime");
			return (Criteria) this;
		}

		public Criteria andCreatedTimeBetween(Date value1, Date value2) {
			addCriterion("created_time between", value1, value2, "createdTime");
			return (Criteria) this;
		}

		public Criteria andCreatedTimeNotBetween(Date value1, Date value2) {
			addCriterion("created_time not between", value1, value2, "createdTime");
			return (Criteria) this;
		}

		public Criteria andUpdatedTimeIsNull() {
			addCriterion("updated_time is null");
			return (Criteria) this;
		}

		public Criteria andUpdatedTimeIsNotNull() {
			addCriterion("updated_time is not null");
			return (Criteria) this;
		}

		public Criteria andUpdatedTimeEqualTo(Date value) {
			addCriterion("updated_time =", value, "updatedTime");
			return (Criteria) this;
		}

		public Criteria andUpdatedTimeNotEqualTo(Date value) {
			addCriterion("updated_time <>", value, "updatedTime");
			return (Criteria) this;
		}

		public Criteria andUpdatedTimeGreaterThan(Date value) {
			addCriterion("updated_time >", value, "updatedTime");
			return (Criteria) this;
		}

		public Criteria andUpdatedTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("updated_time >=", value, "updatedTime");
			return (Criteria) this;
		}

		public Criteria andUpdatedTimeLessThan(Date value) {
			addCriterion("updated_time <", value, "updatedTime");
			return (Criteria) this;
		}

		public Criteria andUpdatedTimeLessThanOrEqualTo(Date value) {
			addCriterion("updated_time <=", value, "updatedTime");
			return (Criteria) this;
		}

		public Criteria andUpdatedTimeIn(List<Date> values) {
			addCriterion("updated_time in", values, "updatedTime");
			return (Criteria) this;
		}

		public Criteria andUpdatedTimeNotIn(List<Date> values) {
			addCriterion("updated_time not in", values, "updatedTime");
			return (Criteria) this;
		}

		public Criteria andUpdatedTimeBetween(Date value1, Date value2) {
			addCriterion("updated_time between", value1, value2, "updatedTime");
			return (Criteria) this;
		}

		public Criteria andUpdatedTimeNotBetween(Date value1, Date value2) {
			addCriterion("updated_time not between", value1, value2, "updatedTime");
			return (Criteria) this;
		}

		public Criteria andReserved1IsNull() {
			addCriterion("reserved1 is null");
			return (Criteria) this;
		}

		public Criteria andReserved1IsNotNull() {
			addCriterion("reserved1 is not null");
			return (Criteria) this;
		}

		public Criteria andReserved1EqualTo(String value) {
			addCriterion("reserved1 =", value, "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved1NotEqualTo(String value) {
			addCriterion("reserved1 <>", value, "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved1GreaterThan(String value) {
			addCriterion("reserved1 >", value, "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved1GreaterThanOrEqualTo(String value) {
			addCriterion("reserved1 >=", value, "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved1LessThan(String value) {
			addCriterion("reserved1 <", value, "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved1LessThanOrEqualTo(String value) {
			addCriterion("reserved1 <=", value, "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved1Like(String value) {
			addCriterion("reserved1 like", value, "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved1NotLike(String value) {
			addCriterion("reserved1 not like", value, "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved1In(List<String> values) {
			addCriterion("reserved1 in", values, "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved1NotIn(List<String> values) {
			addCriterion("reserved1 not in", values, "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved1Between(String value1, String value2) {
			addCriterion("reserved1 between", value1, value2, "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved1NotBetween(String value1, String value2) {
			addCriterion("reserved1 not between", value1, value2, "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved2IsNull() {
			addCriterion("reserved2 is null");
			return (Criteria) this;
		}

		public Criteria andReserved2IsNotNull() {
			addCriterion("reserved2 is not null");
			return (Criteria) this;
		}

		public Criteria andReserved2EqualTo(String value) {
			addCriterion("reserved2 =", value, "reserved2");
			return (Criteria) this;
		}

		public Criteria andReserved2NotEqualTo(String value) {
			addCriterion("reserved2 <>", value, "reserved2");
			return (Criteria) this;
		}

		public Criteria andReserved2GreaterThan(String value) {
			addCriterion("reserved2 >", value, "reserved2");
			return (Criteria) this;
		}

		public Criteria andReserved2GreaterThanOrEqualTo(String value) {
			addCriterion("reserved2 >=", value, "reserved2");
			return (Criteria) this;
		}

		public Criteria andReserved2LessThan(String value) {
			addCriterion("reserved2 <", value, "reserved2");
			return (Criteria) this;
		}

		public Criteria andReserved2LessThanOrEqualTo(String value) {
			addCriterion("reserved2 <=", value, "reserved2");
			return (Criteria) this;
		}

		public Criteria andReserved2Like(String value) {
			addCriterion("reserved2 like", value, "reserved2");
			return (Criteria) this;
		}

		public Criteria andReserved2NotLike(String value) {
			addCriterion("reserved2 not like", value, "reserved2");
			return (Criteria) this;
		}

		public Criteria andReserved2In(List<String> values) {
			addCriterion("reserved2 in", values, "reserved2");
			return (Criteria) this;
		}

		public Criteria andReserved2NotIn(List<String> values) {
			addCriterion("reserved2 not in", values, "reserved2");
			return (Criteria) this;
		}

		public Criteria andReserved2Between(String value1, String value2) {
			addCriterion("reserved2 between", value1, value2, "reserved2");
			return (Criteria) this;
		}

		public Criteria andReserved2NotBetween(String value1, String value2) {
			addCriterion("reserved2 not between", value1, value2, "reserved2");
			return (Criteria) this;
		}

		public Criteria andParamCodeLikeInsensitive(String value) {
			addCriterion("upper(param_code) like", value.toUpperCase(), "paramCode");
			return (Criteria) this;
		}

		public Criteria andParamValueLikeInsensitive(String value) {
			addCriterion("upper(param_value) like", value.toUpperCase(), "paramValue");
			return (Criteria) this;
		}

		public Criteria andParamDescLikeInsensitive(String value) {
			addCriterion("upper(param_desc) like", value.toUpperCase(), "paramDesc");
			return (Criteria) this;
		}

		public Criteria andReserved1LikeInsensitive(String value) {
			addCriterion("upper(reserved1) like", value.toUpperCase(), "reserved1");
			return (Criteria) this;
		}

		public Criteria andReserved2LikeInsensitive(String value) {
			addCriterion("upper(reserved2) like", value.toUpperCase(), "reserved2");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the
	 * database table csc_wlt_parameter
	 *
	 * @mbggenerated do_not_delete_during_merge Fri Jun 08 18:04:10 CST 2018
	 */
	public static class Criteria extends GeneratedCriteria {

		public Criteria() {
			super();
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the
	 * database table csc_wlt_parameter
	 *
	 * @mbggenerated Fri Jun 08 18:04:10 CST 2018
	 */
	@Data
	@NoArgsConstructor
	public static class Criterion {
		private String condition;

		private Object value;

		private Object secondValue;

		private boolean noValue;

		private boolean singleValue;

		private boolean betweenValue;

		private boolean listValue;

		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		public Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		public Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		public Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		public Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		public Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}
}