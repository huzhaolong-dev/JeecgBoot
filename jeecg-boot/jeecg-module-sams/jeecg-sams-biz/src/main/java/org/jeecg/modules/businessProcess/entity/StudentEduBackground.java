package org.jeecg.modules.businessProcess.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.jeecg.common.aspect.annotation.Dict;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 学生教育信息模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
@Data
@TableName("student_edu_background")
@ApiModel(value="student_edu_background对象", description="学生教育信息模块")
public class StudentEduBackground implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键ID")
    private String id;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startDate;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endDate;
	/**在读年级[first-一年级,second-二年级,third-三年级,forth-四年级,finish-已毕业]*/
	@Excel(name = "在读年级[first-一年级,second-二年级,third-三年级,forth-四年级,finish-已毕业]", width = 15)
    @Dict(dicCode = "grade")
    @ApiModelProperty(value = "在读年级[first-一年级,second-二年级,third-三年级,forth-四年级,finish-已毕业]")
    private String grade;
	/**内容*/
	@Excel(name = "内容", width = 15)
    @ApiModelProperty(value = "内容")
    private String content;
	/**成果*/
	@Excel(name = "成果", width = 15)
    @ApiModelProperty(value = "成果")
    private String results;
	/**中国大学编码*/
	@Excel(name = "中国大学编码", width = 15)
    @Dict(dicCode = "code",dicText = "ch_name",dictTable = "ch_university")
    @ApiModelProperty(value = "中国大学编码")
    private String chUniversityCode;
	/**中国专业分类编码*/
	@Excel(name = "中国专业分类编码", width = 15)
    @Dict(dicCode = "code",dicText = "name",dictTable = "ch_major_category")
    @ApiModelProperty(value = "中国专业分类编码")
    private String chMajorCategoryCode;
	/**中国专业编码*/
	@Excel(name = "中国专业编码", width = 15)
    @Dict(dicCode = "code",dicText = "name",dictTable = "ch_major_category")
    @ApiModelProperty(value = "中国专业编码")
    private String chMajorCode;
	/**学生ID*/
    @ApiModelProperty(value = "学生ID")
    private String studentId;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private String description;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**创建者*/
    @ApiModelProperty(value = "创建者")
    private String createBy;
	/**更新者*/
    @ApiModelProperty(value = "更新者")
    private String updateBy;
}
