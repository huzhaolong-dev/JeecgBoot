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
 * @Description: program
 * @Author: jeecg-boot
 * @Date:   2024-04-18
 * @Version: V1.0
 */
@Data
@TableName("program")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="program对象", description="program")
public class Program implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键ID")
    private String id;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
	/**中文名称*/
	@Excel(name = "中文名称", width = 15)
    @ApiModelProperty(value = "中文名称")
    private String chName;
	/**要求的雅思成绩*/
	@Excel(name = "要求的雅思成绩", width = 15)
    @ApiModelProperty(value = "要求的雅思成绩")
    private Double reqIelts;
	/**要求的托福成绩*/
	@Excel(name = "要求的托福成绩", width = 15)
    @ApiModelProperty(value = "要求的托福成绩")
    private Integer reqToefl;
	/**要求的GRE成绩*/
	@Excel(name = "要求的GRE成绩", width = 15)
    @ApiModelProperty(value = "要求的GRE成绩")
    private Integer reqGre;
	/**要求的GMAT成绩*/
	@Excel(name = "要求的GMAT成绩", width = 15)
    @ApiModelProperty(value = "要求的GMAT成绩")
    private Integer reqGmat;
	/**要求的专业分类类型[1-全部符合,2-部分符合,3-部分不符合]*/
	@Excel(name = "要求的专业分类类型[1-全部符合,2-部分符合,3-部分不符合]", width = 15, dicCode = "req_major_category_type")
	@Dict(dicCode = "req_major_category_type")
    @ApiModelProperty(value = "要求的专业分类类型[1-全部符合,2-部分符合,3-部分不符合]")
    private String reqMajorCategoryType;
	/**要求的专业分类编码集合，如[0101,0102,0202]*/
	@Excel(name = "要求的专业分类编码集合，如[0101,0102,0202]", width = 15, dictTable = "ch_major_category where ch_major_category_type = '2'", dicText = "name", dicCode = "code")
	@Dict(dictTable = "ch_major_category where ch_major_category_type = '2'", dicText = "name", dicCode = "code")
    @ApiModelProperty(value = "要求的专业分类编码集合，如[0101,0102,0202]")
    private String reqMajorCategoryCodes;
	/**学校录取规则*/
	@Excel(name = "学校录取规则", width = 15)
    @ApiModelProperty(value = "学校录取规则")
    private String collegeAdmissionRule;
	/**大学编码*/
	@Excel(name = "大学编码", width = 15, dictTable = "university", dicText = "ch_name", dicCode = "code")
	@Dict(dictTable = "university", dicText = "ch_name", dicCode = "code")
    @ApiModelProperty(value = "大学编码")
    private String universityCode;
	/**开放状态[0-未开放,1-已开放,2-已关闭]*/
	@Excel(name = "开放状态[0-未开放,1-已开放,2-已关闭]", width = 15, dicCode = "open_status")
	@Dict(dicCode = "open_status")
    @ApiModelProperty(value = "开放状态[0-未开放,1-已开放,2-已关闭]")
    private String openStatus;
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
