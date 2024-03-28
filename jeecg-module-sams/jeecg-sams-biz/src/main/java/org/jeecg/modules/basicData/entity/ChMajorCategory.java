package org.jeecg.modules.basicData.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 中国专业分类表
 * <p>
 *
 * @Author Hu
 * @Since  2023-12-30
 */
@Data
@TableName("ch_major_category")
public class ChMajorCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**编码号*/
    @Excel(name="编码号",width=15)
    private String code;
    /**名称*/
    @Excel(name="名称",width=15)
    private String name;
    /**中国专业分类类型[1-一级专业分类,2-二级专业分类,3-专业]*/
    @Dict(dicCode = "ch_major_category_type")
    @Excel(name="中国专业分类类型",width=15)
    private String chMajorCategoryType;
    /**父编码号[root-根节点编码号]*/
    @Excel(name="父编码号",width=15)
    private String pcode;
    /**父编码*/
    @Excel(name="父编码",width=15)
    private String pid;
    /**是否有子节点[0-否,1-是]*/
    @Dict(dicCode = "yn")
    @Excel(name="是否有子节点",width=15)
    private String hasChild;
    /**修业年限[4,5]*/
    @Excel(name="修业年限",width=15)
    private String durationOfYear;
    /**开设年份*/
    @Excel(name="开设年份",width=15)
    private Integer openYear;
    /**排序*/
    @Excel(name="排序",width=15)
    private Integer majorOrder;
    /**描述*/
    @Excel(name="描述",width=15)
    private String description;
    /**备注*/
    @Excel(name="备注",width=15)
    private String remark;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**更新日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**创建人*/
    private String createBy;
    /**更新人*/
    private String updateBy;

    /**
     * 重写equals方法
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ChMajorCategory chMajorCategory = (ChMajorCategory) o;
        return Objects.equals(id, chMajorCategory.id) &&
                Objects.equals(code, chMajorCategory.code) &&
                Objects.equals(name, chMajorCategory.name) &&
                Objects.equals(chMajorCategoryType, chMajorCategory.chMajorCategoryType) &&
                Objects.equals(pcode, chMajorCategory.pcode) &&
                Objects.equals(pid, chMajorCategory.pid) &&
                Objects.equals(hasChild, chMajorCategory.hasChild) &&
                Objects.equals(durationOfYear, chMajorCategory.durationOfYear) &&
                Objects.equals(openYear, chMajorCategory.openYear) &&
                Objects.equals(majorOrder, chMajorCategory.majorOrder) &&
                Objects.equals(description, chMajorCategory.description) &&
                Objects.equals(remark, chMajorCategory.remark) &&
                Objects.equals(createTime, chMajorCategory.createTime) &&
                Objects.equals(updateTime, chMajorCategory.updateTime) &&
                Objects.equals(createBy, chMajorCategory.createBy) &&
                Objects.equals(updateBy, chMajorCategory.updateBy);
    }

    /**
     * 重写hashCode方法
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, code, name,
                chMajorCategoryType, pcode, pid, hasChild, durationOfYear,
                openYear, majorOrder, description, remark, createTime, updateTime,
                createBy, updateBy);
    }
}
