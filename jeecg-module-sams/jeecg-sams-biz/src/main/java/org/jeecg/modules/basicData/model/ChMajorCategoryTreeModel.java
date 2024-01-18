package org.jeecg.modules.basicData.model;

import org.jeecg.modules.basicData.entity.ChMajorCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.jeecg.modules.basicData.service.IChMajorCategoryService.HAS_CHILD;

/**
 * <p>
 * 中国专业分类表 存储树结构数据的实体类
 * <p>
 * 
 * @Author Hu
 * @Since 2024-01-02
 */
public class ChMajorCategoryTreeModel implements Serializable{
	
    private static final long serialVersionUID = 1L;
    
    /** 对应ChMajorCategory中的code字段,前端数据树中的key*/
    private String key;

    /** 对应ChMajorCategory中的code字段,前端数据树中的value*/
    private String value;

    /** 对应name字段,前端数据树中的title*/
    private String title;


    private boolean isLeaf;
    // 以下所有字段均与ChMajorCategory相同

    private String id;
    /**编码号*/
    private String code;
    /**名称*/
    private String name;
    /**中国专业分类类型[1-一级专业分类,2-二级专业分类,3-专业]*/
    private Integer chMajorCategoryType;
    /**父编码号[root-根节点编码号]*/
    private String pcode;
    /**父编码*/
    private String pid;
    /**是否有子节点[0-否,1-是]*/
    private String hasChild;
    /**修业年限[4,5]*/
    private String durationOfYear;
    /**开设年份*/
    private Integer openYear;
    /**排序*/
    private Integer majorOrder;
    /**描述*/
    private String description;
    /**备注*/
    private String remark;
    /**创建日期*/
    private Date createTime;
    /**更新日期*/
    private Date updateTime;
    /**创建人*/
    private String createBy;
    /**更新人*/
    private String updateBy;

    private List<ChMajorCategoryTreeModel> children = new ArrayList<>();


    public ChMajorCategoryTreeModel() {

    }

    /**
     * 将ChMajorCategory对象转换成ChMajorCategoryTreeModel对象
     * @param chMajorCategory
     */
	public ChMajorCategoryTreeModel(ChMajorCategory chMajorCategory) {
		this.key = chMajorCategory.getId();
        this.value = chMajorCategory.getCode();
        this.title = chMajorCategory.getName();
        this.id = chMajorCategory.getId();
        this.code = chMajorCategory.getCode();
        this.name = chMajorCategory.getName();
        this.chMajorCategoryType = chMajorCategory.getChMajorCategoryType();
        this.pcode = chMajorCategory.getPcode();
        this.pid = chMajorCategory.getPid();
        this.hasChild = chMajorCategory.getHasChild();
        this.durationOfYear = chMajorCategory.getDurationOfYear();
        this.openYear = chMajorCategory.getOpenYear();
        this.majorOrder = chMajorCategory.getMajorOrder();
        this.description = chMajorCategory.getDescription();
        this.remark = chMajorCategory.getRemark();
        this.createTime = chMajorCategory.getCreateTime();
        this.updateTime = chMajorCategory.getUpdateTime();
        this.createBy = chMajorCategory.getCreateBy();
        this.updateBy = chMajorCategory.getUpdateBy();
        if(HAS_CHILD.equals(chMajorCategory.getHasChild())){
            this.isLeaf = false;
        }else{
            this.isLeaf = true;
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChMajorCategoryType() {
        return chMajorCategoryType;
    }

    public void setChMajorCategoryType(Integer chMajorCategoryType) {
        this.chMajorCategoryType = chMajorCategoryType;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public String getDurationOfYear() {
        return durationOfYear;
    }

    public void setDurationOfYear(String durationOfYear) {
        this.durationOfYear = durationOfYear;
    }

    public Integer getOpenYear() {
        return openYear;
    }

    public void setOpenYear(Integer openYear) {
        this.openYear = openYear;
    }

    public Integer getMajorOrder() {
        return majorOrder;
    }

    public void setMajorOrder(Integer majorOrder) {
        this.majorOrder = majorOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public List<ChMajorCategoryTreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<ChMajorCategoryTreeModel> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChMajorCategoryTreeModel that = (ChMajorCategoryTreeModel) o;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(chMajorCategoryType, that.chMajorCategoryType) && Objects.equals(pcode, that.pcode) && Objects.equals(pid, that.pid) && Objects.equals(hasChild, that.hasChild) && Objects.equals(durationOfYear, that.durationOfYear) && Objects.equals(openYear, that.openYear) && Objects.equals(majorOrder, that.majorOrder) && Objects.equals(description, that.description) && Objects.equals(remark, that.remark) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime) && Objects.equals(createBy, that.createBy) && Objects.equals(updateBy, that.updateBy) && Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, chMajorCategoryType, pcode, pid, hasChild, durationOfYear, openYear, majorOrder, description, remark, createTime, updateTime, createBy, updateBy, children);
    }
}
