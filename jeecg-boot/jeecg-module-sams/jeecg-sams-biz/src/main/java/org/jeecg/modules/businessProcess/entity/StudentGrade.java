package org.jeecg.modules.businessProcess.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 学生成绩模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
@Data
@TableName("student_grade")
@ApiModel(value="student_grade对象", description="学生成绩模块")
public class StudentGrade implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键ID")
    private String id;
	/**雅思成绩*/
	@Excel(name = "雅思成绩", width = 15)
    @ApiModelProperty(value = "雅思成绩")
    private Double ielts;
	/**托福成绩*/
	@Excel(name = "托福成绩", width = 15)
    @ApiModelProperty(value = "托福成绩")
    private Integer toefl;
	/**GRE成绩*/
	@Excel(name = "GRE成绩", width = 15)
    @ApiModelProperty(value = "GRE成绩")
    private Integer gre;
	/**GMAT成绩*/
	@Excel(name = "GMAT成绩", width = 15)
    @ApiModelProperty(value = "GMAT成绩")
    private Integer gmat;
	/**GPA成绩*/
	@Excel(name = "GPA成绩", width = 15)
    @ApiModelProperty(value = "GPA成绩")
    private Double gpa;
	/**均分*/
	@Excel(name = "均分", width = 15)
    @ApiModelProperty(value = "均分")
    private Double wam;
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
