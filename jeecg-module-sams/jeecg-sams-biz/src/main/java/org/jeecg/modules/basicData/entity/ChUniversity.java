package org.jeecg.modules.basicData.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: ch_university
 * @Author: jeecg-boot
 * @Date:   2024-03-28
 * @Version: V1.0
 */
@Data
@TableName("ch_university")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ch_university对象", description="ch_university")
public class ChUniversity implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键ID")
    private String id;
	/**编码*/
	@Excel(name = "编码", width = 15)
    @ApiModelProperty(value = "编码")
    private String code;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
	/**中文名称*/
	@Excel(name = "中文名称", width = 15)
    @ApiModelProperty(value = "中文名称")
    private String chName;
	/**国家编码*/
	@Excel(name = "国家编码", width = 15)
    @ApiModelProperty(value = "国家编码")
    private String countryCode;
	/**图片Url地址*/
	@Excel(name = "图片Url地址", width = 15)
    @ApiModelProperty(value = "图片Url地址")
    private String picUrl;
	/**网站Url地址*/
	@Excel(name = "网站Url地址", width = 15)
    @ApiModelProperty(value = "网站Url地址")
    private String webUrl;
	/**985学校标志[0-不是,1-是]*/
	@Excel(name = "985学校标志[0-不是,1-是]", width = 15, dicCode = "nine_eight_five_college_flag")
	@Dict(dicCode = "nine_eight_five_college_flag")
    @ApiModelProperty(value = "985学校标志[0-不是,1-是]")
    private Integer nineEightFiveCollegeFlag;
	/**211学校标志[0-不是,1-是]*/
	@Excel(name = "211学校标志[0-不是,1-是]", width = 15, dicCode = "two_eleven_college_flag")
	@Dict(dicCode = "two_eleven_college_flag")
    @ApiModelProperty(value = "211学校标志[0-不是,1-是]")
    private Integer twoElevenCollegeFlag;
	/**双一流学校标志[0-不是,1-是]*/
	@Excel(name = "双一流学校标志[0-不是,1-是]", width = 15, dicCode = "double_first_class_college_flag")
	@Dict(dicCode = "double_first_class_college_flag")
    @ApiModelProperty(value = "双一流学校标志[0-不是,1-是]")
    private Integer doubleFirstClassCollegeFlag;
	/**QS世界排名*/
	@Excel(name = "QS世界排名", width = 15)
    @ApiModelProperty(value = "QS世界排名")
    private Integer qsWorldRank;
	/**QS国家排名*/
	@Excel(name = "QS国家排名", width = 15)
    @ApiModelProperty(value = "QS国家排名")
    private Integer qsCountryRank;
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
