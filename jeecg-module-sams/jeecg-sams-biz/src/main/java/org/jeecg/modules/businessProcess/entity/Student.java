package org.jeecg.modules.businessProcess.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.jeecgframework.poi.excel.annotation.Excel;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: student
 * @Author: jeecg-boot
 * @Date:   2024-03-29
 * @Version: V1.0
 */
@Data
@TableName("student")
@ApiModel(value="student对象", description="student")
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键ID")
    private String id;
	/**身份类型[1-身份证,2-护照]*/
    @Excel(name = "身份类型[1-身份证,2-护照]", width = 15, dicCode = "id_type")
    @Dict(dicCode = "id_type")
    @ApiModelProperty(value = "身份类型[1-身份证,2-护照]")
    private String idType;
	/**身份证号码*/
    @Excel(name = "身份证号码", width = 15)
    @ApiModelProperty(value = "身份证号码")
    private String idCard;
	/**名称*/
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
	/**拼音*/
    @Excel(name = "拼音", width = 15)
    @ApiModelProperty(value = "拼音")
    private String pinyin;
	/**性别[1-男,2-女]*/
    @Excel(name = "性别[1-男,2-女]", width = 15, dicCode = "sex")
    @Dict(dicCode = "sex")
    @ApiModelProperty(value = "性别[1-男,2-女]")
    private String sex;
	/**头像Url地址*/
    @Excel(name = "头像Url地址", width = 15)
    @ApiModelProperty(value = "头像Url地址")
    private String avatarUrl;
	/**学业阶段[1-本科在读,2-专科在读,3-本科毕业,4-专科毕业,5-研究生在读,6-研究生毕业,7-普通高中在读,8-国际高中在读]*/
    @Excel(name = "学业阶段[1-本科在读,2-专科在读,3-本科毕业,4-专科毕业,5-研究生在读,6-研究生毕业,7-普通高中在读,8-国际高中在读]", width = 15, dicCode = "study_stage")
    @Dict(dicCode = "study_stage")
    @ApiModelProperty(value = "学业阶段[1-本科在读,2-专科在读,3-本科毕业,4-专科毕业,5-研究生在读,6-研究生毕业,7-普通高中在读,8-国际高中在读]")
    private String studyStage;
	/**出生日期*/
    @Excel(name = "出生日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;
	/**户口地*/
    @Excel(name = "户口地", width = 15)
    @ApiModelProperty(value = "户口地")
    private String registrationAddr;
	/**出生地*/
    @Excel(name = "出生地", width = 15)
    @ApiModelProperty(value = "出生地")
    private String birthplaceAddr;
	/**家庭地址*/
    @Excel(name = "家庭地址", width = 15)
    @ApiModelProperty(value = "家庭地址")
    private String homeAddr;
	/**手机*/
    @Excel(name = "手机", width = 15)
    @ApiModelProperty(value = "手机")
    private String telephone;
	/**紧急联系人手机*/
    @Excel(name = "紧急联系人手机", width = 15)
    @ApiModelProperty(value = "紧急联系人手机")
    private String emergencyPhone;
	/**电子邮件地址*/
    @Excel(name = "电子邮件地址", width = 15)
    @ApiModelProperty(value = "电子邮件地址")
    private String email;
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
