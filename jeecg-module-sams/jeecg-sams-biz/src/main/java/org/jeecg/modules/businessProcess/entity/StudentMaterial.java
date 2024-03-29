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
 * @Description: 学生材料模块
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
@Data
@TableName("student_material")
@ApiModel(value="student_material对象", description="学生材料模块")
public class StudentMaterial implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键ID")
    private String id;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
	/**材料Url地址*/
	@Excel(name = "材料Url地址", width = 15)
    @ApiModelProperty(value = "材料Url地址")
    private String materiaUrl;
	/**材料类型[1-身份证明材料,2-学科成绩证明材料,3-语言证明材料,4-学校活动证明材料,5-资金证明材料]*/
	@Excel(name = "材料类型[1-身份证明材料,2-学科成绩证明材料,3-语言证明材料,4-学校活动证明材料,5-资金证明材料]", width = 15)
    @Dict(dicCode = "material_type")
    @ApiModelProperty(value = "材料类型[1-身份证明材料,2-学科成绩证明材料,3-语言证明材料,4-学校活动证明材料,5-资金证明材料]")
    private String materialType;
	/**材料后缀*/
	@Excel(name = "材料后缀", width = 15)
    @ApiModelProperty(value = "材料后缀")
    private String materialSuffix;
	/**大小，单位：字节*/
	@Excel(name = "大小，单位：字节", width = 15)
    @ApiModelProperty(value = "大小，单位：字节")
    private Integer size;
	/**学生ID*/
    @ApiModelProperty(value = "学生ID")
    private String studentId;
	/**审核状态[wait-待审核,pass-通过,fail-未通过]*/
	@Excel(name = "审核状态[wait-待审核,pass-通过,fail-未通过]", width = 15)
    @Dict(dicCode = "audit_status")
    @ApiModelProperty(value = "审核状态[wait-待审核,pass-通过,fail-未通过]")
    private String auditStatus;
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
