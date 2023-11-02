package io.goji.team;


import io.goji.team.annotation.ExcelColumn;
import io.goji.team.annotation.ExcelSheet;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@ExcelSheet(name = "测试sheet")
public class PostDTO {



    @ExcelColumn(name = "岗位ID")
    private Long postId;


    @ExcelColumn(name = "岗位编码")
    private String postCode;

    @ExcelColumn(name = "岗位名称")
    private String postName;


    @ExcelColumn(name = "岗位排序")
    private String postSort;

    @ExcelColumn(name = "备注")
    private String remark;

    private String status;

    @ExcelColumn(name = "状态")
    private String statusStr;

    public PostDTO() {
    }


    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostSort() {
        return postSort;
    }

    public void setPostSort(String postSort) {
        this.postSort = postSort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PostDTO postDTO)) {
            return false;
        }

        return new EqualsBuilder().append(postId, postDTO.postId)
            .append(postCode, postDTO.postCode).append(postName, postDTO.postName).append(postSort, postDTO.postSort)
            .append(remark, postDTO.remark).append(status, postDTO.status).append(statusStr, postDTO.statusStr)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(postId).append(postCode).append(postName).append(postSort)
            .append(remark)
            .append(status).append(statusStr).toHashCode();
    }

    public PostDTO(Long postId, String postCode, String postName, String postSort, String remark, String status,
        String statusStr) {
        this.postId = postId;
        this.postCode = postCode;
        this.postName = postName;
        this.postSort = postSort;
        this.remark = remark;
        this.status = status;
        this.statusStr = statusStr;
    }
}
